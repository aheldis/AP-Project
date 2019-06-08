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

public class Shop {
    private static Shop singleInstance = null;
    private static String pathOfFiles = "resource/";
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> collectibles = new ArrayList<>();
    private AccountView accountView = AccountView.getInstance();
//    private String[] type = {"hero", "minion", "spell", "item"}; //ina ye enumi chizi mibood behtar mibood

    public static Shop getInstance() {
        if (singleInstance == null) {
            singleInstance = new Shop();
            singleInstance.init();
        }
        return singleInstance;
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

            if (type.equals(FilesType.HERO)) {
                Hero hero = mapper.fromJson(reader, model.card.Hero.class);
                addCard(hero);
            }
            if (type.equals(FilesType.MINION)) {
                Minion minion = mapper.fromJson(reader, model.card.Minion.class);
                addCard(minion);
            }
            if (type.equals(FilesType.SPELL)) {
                Spell spell = mapper.fromJson(reader, model.card.Spell.class);
                addCard(spell);
            }
            if (type.equals(FilesType.COLLECTIBLE)) {
                Collectible item = mapper.fromJson(reader, model.item.Collectible.class);
                addCollectible(item);
            }
            if (type.equals(FilesType.USABLE)) {
                Usable item = mapper.fromJson(reader, model.item.Usable.class);
                addUsable(item);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addUsable(Item item) {
        items.add(item);
    }

    public void addCollectible(Collectible item) {
        collectibles.add(item);
    }

    public static String getPathOfFiles() {
        return pathOfFiles;
    }

    public Object search(Account account, String name) {
        if (cardExist(name)==null && !itemExist(name)) {
            ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP.printMessage();
            return null;
        }
        if (cardExist(name)!=null) {
            Card card = getCard(name);
            card.setMp(10);
            card.setHp(10);
            card.setAp(10);
            card.setDescription("heoo");
            card.setPathOfThePicture("pics/minion_background.png");
            card.setPathOfAnimation("pics/spell/fireBall.png");
            card.setCountOfAnimation(16);
            card.setName("Fireball");
            card.setFrameSize(48);
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
        boolean foundInCards = account.getCollection().searchCardName(name).size()!=0;
        boolean foundInItems = account.getCollection().searchItemName(name).size()!=0;
        if (!foundInCards && !foundInItems) {
            ErrorType error = ErrorType.HAVE_NOT_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
        }
    }

    public void buy(Account account, String name) {
        Card card = getCard(name);
        if (card != null) {
            new CardId(account, card, account.getCollection().getNumberOfCardId(card));
            if (notEnoughDaricForBuy(account, card.getCost()))
                return;
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
            makeNewFromFile(pathOfFiles + typeOfFile + "/" + card.getName() + ".json", typeOfFile);
            cards.remove(card);
            return;
        }
        Usable item = getItem(name);
        if (item != null) {
            new UsableId(account, item, account.getCollection().getNumberOfItemId(item));
            if (notEnoughDaricForBuy(account, item.getCost()))
                return;
            account.getCollection().addToItems(item);
            items.remove(item);
            makeNewFromFile(pathOfFiles + "/" + FilesType.USABLE.getName() + "/" + item.getName() + ".json", FilesType.ITEM);
            return;
        }
        ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
        accountView.printError(error);
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

    public void sell(Account account, String id) {
        Collection collection = account.getCollection();
        Card card = collection.passCardByCardId(id);
        Usable item = collection.passUsableItemByUsableItemId(id);
        if (card != null) {
            account.changeValueOfDaric(card.getCost());
            collection.removeCard(card);
        } else if (item != null) {
            account.changeValueOfDaric(item.getCost());
            collection.removeItem(item);
        } else {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
        }
    }


    //for making story game
    public Card getNewCardByName(String name) {
        Card card = getCard(name);
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

    public ArrayList<Item> getCollectibles() {
        return collectibles;
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

    public void help() {
        accountView.viewHelpOfShop();
    }
}
