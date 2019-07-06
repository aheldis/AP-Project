package model.account;

import com.gilecode.yagson.YaGson;
import model.card.*;
import model.item.Collectible;
import model.item.Item;
import model.item.Usable;
import model.item.UsableId;
import view.AccountView;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    final static private int INITIAL_NUMBER_OF_CARDS = 5;
    private static Shop singleInstance = null;
    private static String pathOfFiles = "resource/";
    private HashMap<String, Integer> remainingCards = new HashMap<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Usable> items = new ArrayList<>();
    private ArrayList<Item> collectibles = new ArrayList<>();
    private AccountView accountView = AccountView.getInstance();
//    private String[] type = {"hero", "minion", "Spell", "item"}; //ina ye enumi chizi mibood behtar mibood

    public static Shop getInstance() {
        if (singleInstance == null) {
            singleInstance = new Shop();
            singleInstance.init();
        }
        return singleInstance;
    }

    public HashMap<String, Integer> getRemainingCards() {
        return remainingCards;
    }

    public int getNumberOfRemaingCard(String name){
        if(!remainingCards.containsKey(name))
            return 0;
        return remainingCards.get(name);
    }


    //    public ArrayList<Card> passDeckForComputer(){
//        Collections.shuffle(cards);
//        ArrayList<Card> computerCards = new ArrayList<>();
//        FilesType typeOfFile = null;
//        for(int i=0 ; i<19 ; i++){
//            if(cards.get(i) instanceof Minion)
//                typeOfFile = FilesType.MINION;
//            if(cards.get(i) instanceof Spell)
//                typeOfFile = FilesType.HERO;
//            if(!( cards.get(i) instanceof Hero)){
//                new CardId(account, cards.get(i), account.getCollection().getNumberOfCardId(cards.get(i)));
//                makeNewFromFile(pathOfFiles + typeOfFile+ "/" + card.getName()+".json", typeOfFile);
//                computerCards.add(cards.get(i));
//                cards.remove(computerCards.get(i));
//            }
//        }
//        return computerCards;
//    }

    private void init() {
        for (FilesType typeOfFile : FilesType.values()) {
            File folder = new File(pathOfFiles + typeOfFile.getName());
            File[] listOfFiles = folder.listFiles();
            if (!(listOfFiles == null || typeOfFile == FilesType.BUFF || typeOfFile == FilesType.ITEM)) {
                for (File listOfFile : listOfFiles) {
                    makeNewFromFile(listOfFile.getPath(), typeOfFile);
                }
            }
        }
    }

    public void makeNewFromFile(String path, FilesType type) {
        try {
            InputStream input = new FileInputStream(path);
            Reader reader = new InputStreamReader(input);
            YaGson mapper = new YaGson();

            String name = null;
            if (type.equals(FilesType.HERO)) {
                Hero hero = mapper.fromJson(reader, model.card.Hero.class);
                name = addCard(hero);
            }
            if (type.equals(FilesType.MINION)) {
                Minion minion = mapper.fromJson(reader, model.card.Minion.class);
                name = addCard(minion);
            }
            if (type.equals(FilesType.SPELL)) {
                Spell spell = mapper.fromJson(reader, model.card.Spell.class);
                name = addCard(spell);
            }
            if (type.equals(FilesType.COLLECTIBLE)) {
                Collectible item = mapper.fromJson(reader, model.item.Collectible.class);
                name = null;
            }
            if (type.equals(FilesType.USABLE)) {
                Usable item = mapper.fromJson(reader, model.item.Usable.class);
                name = addUsable(item);
            }
            if (name != null)
                remainingCards.put(name, INITIAL_NUMBER_OF_CARDS);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String addCard(Card card) {
        cards.add(card);
        return card.getName();
    }

    private String addCollectible(Collectible item) {
        collectibles.add(item);
        return item.getName();
    }

    private String addUsable(Usable item) {
        items.add(item);
        return item.getName();
    }

    public static String getPathOfFiles() {
        return pathOfFiles;
    }

    public Object search(Account account, String name) {
        if (cardExist(name) == null && !itemExist(name)) {
            ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP.printMessage();
            return null;
        }
        if (cardExist(name) != null) {
            Card card = getCard(name);
            int number = account.getCollection().getNumberOfCardId(card);
            accountView.print(account.getUserName() + "_" + name + "_" + number);
            return card;
        }
        if (itemExist(name)) {
            Usable item = getItem(name);
            int number = account.getCollection().getNumberOfItemId(item);
            accountView.print(account.getUserName() + "_" + name + "_" + number);
            return item;
        }
        return null;
    }

    public Card cardExist(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    private boolean itemExist(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Card getCard(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    private Usable getItem(String name) {
        for (Item item : items) {
            if (item instanceof Usable && item.getName().equals(name)) {
                return (Usable) item;
            }
        }
        return null;
    }

    public void searchCollection(Account account, String name) {
        boolean foundInCards = account.getCollection().searchCardName(name).size() != 0;
        boolean foundInItems = account.getCollection().searchItemName(name).size() != 0;
        if (!foundInCards && !foundInItems) {
            ErrorType error = ErrorType.HAVE_NOT_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
        }
    }

    public ErrorType buy(Account account, String name) {
        Card card = getCard(name);

        if (remainingCards.containsKey(name) && remainingCards.get(name) == 0) {
            return ErrorType.NO_REMAINING_CARD;
        }
        int numberOfRemainingCards = remainingCards.get(name);

        if (card != null) {
            new CardId(account, card, account.getCollection().getNumberOfCardId(card));
            if (notEnoughDaricForBuy(account, card.getCost()))
                return ErrorType.NOT_ENOUGH_MANA;

            Collection collection = account.getCollection();
            FilesType typeOfFile = null;
            if (card instanceof Hero) {
                collection.addToHeros((Hero) card);
                typeOfFile = FilesType.HERO;
            } else if (card instanceof Spell) {
                collection.addToSpells((Spell) card);
                typeOfFile = FilesType.SPELL;
            } else if (card instanceof Minion) {
                collection.addToMinions((Minion) card);
                typeOfFile = FilesType.MINION;
            }
            assert typeOfFile != null;

            makeNewFromFile(pathOfFiles + typeOfFile + "/" + card.getName() + ".json", typeOfFile);
            cards.remove(card);
            remainingCards.put(name, numberOfRemainingCards - 1);
            return null;
        }

        Usable item = getItem(name);
        if (item != null) {
            new UsableId(account, item, account.getCollection().getNumberOfItemId(item));
            if (notEnoughDaricForBuy(account, item.getCost()))
                return ErrorType.NOT_ENOUGH_MONEY;

            account.getCollection().addToItems(item);
            items.remove(item);
            makeNewFromFile(pathOfFiles + "/" + FilesType.USABLE.getName() + "/" + item.getName() + ".json", FilesType.ITEM);
            remainingCards.put(name, numberOfRemainingCards - 1);
            return null;
        }
        return ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
    }

    private boolean notEnoughDaricForBuy(Account account, int cost) {
        if (account.getDaric() < cost) {
            ErrorType error = ErrorType.NOT_ENOUGH_MONEY;
            accountView.printError(error);
            return true;
        }
        account.changeValueOfDaric(-cost);
        return false;
    }

    public ErrorType sell(Account account, String id) {
        Collection collection = account.getCollection();
        Card card = collection.passCardByCardId(id);
        Usable item = collection.passUsableItemByUsableItemId(id);
        if (card != null) {
            account.changeValueOfDaric(card.getCost());
            collection.removeCard(card);
            int numberOfRemainingCards = remainingCards.get(card.getName());
            remainingCards.put(card.getName(), numberOfRemainingCards + 1);
        } else if (item != null) {
            account.changeValueOfDaric(item.getCost());
            collection.removeItem(item);
            int numberOfRemainingCards = remainingCards.get(item.getName());
            remainingCards.put(item.getName(), numberOfRemainingCards + 1);
        } else {
            return ErrorType.NO_SUCH_CARD_OR_ITEM_IN_COLLECTION;
        }
        return null;
    }

    //for making story game
    public Card getNewCardByName(String name) {
        Card card = getCard(name);
        if (card == null)
            return null;
        FilesType typeOfFile = null;
        if (card instanceof Hero) {
            typeOfFile = FilesType.HERO;
        } else if (card instanceof Spell) {
            typeOfFile = FilesType.SPELL;
        } else if (card instanceof Minion) {
            typeOfFile = FilesType.MINION;
        }
        if (card != null)
            makeNewFromFile(pathOfFiles + typeOfFile.getName() + "/" + card.getName() + ".json", typeOfFile);
        cards.remove(card);
        return card;
    }

    public Item getNewItemByName(String name) {
        Usable item = getItem(name);
        if (item != null)
            makeNewFromFile(pathOfFiles + FilesType.USABLE.getName() + "/" + item.getName() + ".json", FilesType.USABLE);
        items.remove(item);
        return item;
    }

    public void show() {
        accountView.cardsAndItemsView(Card.getSpells(cards),
                Card.getMinions(cards),
                Card.getHeroes(cards), items);
    }

    public void removeCollectible(Collectible collectible) {
        makeNewFromFile(pathOfFiles + FilesType.COLLECTIBLE.getName() + "/" + collectible.getName() + ".json", FilesType.COLLECTIBLE);
        collectibles.remove(collectible);
    }

    public String help() {
        return accountView.viewHelpOfShop();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Usable> getItems() {
        return items;
    }

    public ArrayList<Item> getCollectibles() {
        return collectibles;
    }
}
