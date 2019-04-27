package model.account;

import model.Item.Item;
import model.Item.Usable;
import model.Item.UsableId;
import model.card.*;
import view.AccountView;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;

public class Shop {
    public static Shop singleInstance = null;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Usable> items = new ArrayList<>();
    private static AccountView accountView = AccountView.getInstance();

    //todo bere az har card yedoone besaze

    private boolean itemExist(String name) {
        for (Usable item : items) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean cardExist(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
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

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addItem(Usable item) {
        items.add(item);
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
            accountView.print(account.getUserName() + "_" + name + "_" + Integer.toString(number));
        }
        if (itemExist(name)) {
            Usable item = getItem(name);
            int number = account.getCollection().getNumberOfItemId(item);
            accountView.print(account.getUserName() + "_" + name + "_" + Integer.toString(number));
        }
    }

    public void searchCollection(Account account, String name) {
        boolean foundInCards = account.getCollection().searchCardName(name);
        boolean foundInItems = account.getCollection().searchItemName(name);
        if (!foundInCards && !foundInItems) {
            ErrorType error = ErrorType.HAVE_NOT_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
        }
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

    public void buy(Account account, String name) {
        if (!cardExist(name) && !itemExist(name)) {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
            accountView.printError(error);
            return;
        }
        if (cardExist(name)) {
            Card card = getCard(name);
            new CardId(account, card, account.getCollection().getNumberOfCardId(card));
            if (!enoughDaricForBuy(account, card.getCost()))
                return;
            Collection collection = account.getCollection();
            if (card instanceof Hero) {
                collection.addToHeros((Hero) card);
            } else if (card instanceof Spell) {
                collection.addToSpells((Spell) card);
            } else if (card instanceof Minion) {
                collection.addToMinions((Minion) card);
            }
        }
        if (itemExist(name)) {
            Usable item = getItem(name);
            new UsableId(account, item, account.getCollection().getNumberOfItemId(item));
            if (!enoughDaricForBuy(account, item.getCost()))
                return;
            account.getCollection().addToItems(item);
        }
    }

    public void sell(Account account, String id) {
        Collection collection = account.getCollection();
        Card card = collection.passCardByCardId(id);
        Usable item = collection.passUsableItemByUsableItemId(id);
        if (card != null) {
            collection.removeCard(card);
        } else if (item != null) {
            collection.removeItem(item);
        } else {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_COLLECTION;
            accountView.printError(error);
            return;
        }
    }


    public void show() {
    accountView.cardsAndItemsView(Card.getSpells(cards), Card.getMinions(cards), Card.getHeroes(cards), items);
    }

    public void help() {
        accountView.viewHelpOfShop();
    }

    public static Shop getInstance() {
        if (singleInstance == null)
            singleInstance = new Shop();
        return singleInstance;
    }

    public void  makeNewFromFile(String fileName,String type){
        Gson gson = new GsonBuilder().create();

        InputStream input = null;

        try{


            input = new FileInputStream("D:\\jacksoncore\\src\\main\\"+fileName);//file name

            Reader reader = new InputStreamReader(input);
            //card - item - game file
            if(type.equals("card")){
                Card card = gson.fromJson(reader,Card.class);
            }
            if(type.equals("item")){
                Item item = gson.fromJson(reader,Item.class);
                //todo cherte in kar :))) item ke change nadare :)
            }
            if(type.equals("game file")){
                // :/
            }

            int data = input.read();
            while(data != -1) {
                //do something with data...

                data = input.read();
            }
        }catch(IOException e) {
            //do something with e... log, perhaps rethrow etc.
        }
        finally {
            try{
                if(input != null) input.close();
            } catch(IOException e){
                //do something, or ignore.
            }
        }


    }
}
