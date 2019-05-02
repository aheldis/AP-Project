package model.account;

import com.gilecode.yagson.YaGson;
import model.item.Item;
import model.item.Usable;
import model.item.UsableId;
import model.card.*;
import view.AccountView;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;

public class Shop {
    public static Shop singleInstance = null;
    private static String pathOfFiles = "Duelyst/";
    private ArrayList<Card> cards = new ArrayList<>();// todo (Saba) faghat havaset bashe age bar midari az inja chizi remove koni jash yeki bezari
    private ArrayList<Usable> items = new ArrayList<>();
    private AccountView accountView = AccountView.getInstance();
//    private String[] type = {"hero", "minion", "spell", "item"}; //ina ye enumi chizi mibood behtar mibood

    public static Shop getInstance() {
        if (singleInstance == null) {
            singleInstance = new Shop();
            singleInstance.init();
        }
        return singleInstance;
    }

    private void init() {
        for (FilesType typeOfFile : FilesType.values()) {
            File folder = new File(pathOfFiles + typeOfFile.getName());
            File[] listOfFiles = folder.listFiles();
            if (!(listOfFiles == null || typeOfFile == FilesType.BUFF)) {
                for (int i = 0; i < listOfFiles.length; i++) {
                    makeNewFromFile(listOfFiles[i].getPath(), typeOfFile);
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
            if (type.equals(FilesType.ITEM)) {
                Usable item = mapper.fromJson(reader, model.item.Usable.class);
                addItem(item);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addItem(Usable item) {
        items.add(item);
    }

    public static String getPathOfFiles() {
        return pathOfFiles;
    }

    public void search(Account account, String name) {
        if (!cardExist(name) && !itemExist(name)) {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
            accountView.printError(error);
            return;
        }
        if (cardExist(name)) {
            Card card = getCard(name);
            int number = account.getCollection().getNumberOfCardId(card);
            accountView.print(account.getUserName() + "_" + name + "_" + number);
        }
        if (itemExist(name)) {
            Usable item = getItem(name);
            int number = account.getCollection().getNumberOfItemId(item);
            accountView.print(account.getUserName() + "_" + name + "_" + number);
        }
    }

    private boolean cardExist(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean itemExist(String name) {
        for (Usable item : items) {
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
        for (Usable item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void searchCollection(Account account, String name) {
        boolean foundInCards = account.getCollection().searchCardName(name);
        boolean foundInItems = account.getCollection().searchItemName(name);
        if (!foundInCards && !foundInItems) {
            ErrorType error = ErrorType.HAVE_NOT_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
        }
    }

    public void buy(Account account, String name) {
        if (cardExist(name)) {
            Card card = getCard(name);
            new CardId(account, card, account.getCollection().getNumberOfCardId(card));
            if (!enoughDaricForBuy(account, card.getCost()))
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
            makeNewFromFile(pathOfFiles + typeOfFile+ "/" + card.getName()+".json", typeOfFile); //todo check lotfan
            cards.remove(card);
            return;
        }
        else if (itemExist(name)) {
            Usable item = getItem(name);
            new UsableId(account, item, account.getCollection().getNumberOfItemId(item));
            if (!enoughDaricForBuy(account, item.getCost()))
                return;
            account.getCollection().addToItems(item);
            items.remove(item);
            makeNewFromFile(pathOfFiles +"/"+ FilesType.ITEM.getName() + item.getName(), FilesType.ITEM); //todo check lotfan
            return;
        }
        ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
        accountView.printError(error);
    }

    private boolean enoughDaricForBuy(Account account, int cost) {
        if (account.getDaric() < cost) {
            ErrorType error = ErrorType.NOT_ENOUGH_MONEY;
            accountView.printError(error);
            return false;
        }
        account.changeValueOfDaric(-cost);
        return true;
    }

    public void sell(Account account, String id) {
        Collection collection = account.getCollection();
        Card card = collection.passCardByCardId(id);
        Usable item = collection.passUsableItemByUsableItemId(id);
        if (card != null) {
            account.changeValueOfDaric(card.getCost());
            collection.removeCard(card);
        } else if (item != null) {
            account.changeValueOfDaric(card.getCost());
            collection.removeItem(item);
        } else {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
        }
    }


    //todo for making story game
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
        makeNewFromFile(pathOfFiles + typeOfFile + card.getName(), typeOfFile);
        cards.remove(card);
        return card;
    }

    public Item getNewItemByName(String name) {
        Usable item = getItem(name);
        makeNewFromFile(pathOfFiles + FilesType.ITEM.getName() + item.getName(), FilesType.ITEM);
        items.remove(item);
        return item;
    }


    public void show() {
        accountView.cardsAndItemsView(Card.getSpells(cards),
                Card.getMinions(cards),
                Card.getHeroes(cards), items);
    }

    public void help() {
        accountView.viewHelpOfShop();
    }
}
