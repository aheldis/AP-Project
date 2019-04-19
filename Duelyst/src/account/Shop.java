package account;

import Item.Item;
import card.Card;
import card.CardId;
import view.AccountView;

import java.util.ArrayList;

public class Shop {
    public static Shop singleInstance = null;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private static AccountView accountView = AccountView.getInstance();

    private boolean cardOrItemExist(String name) {

    }

    private CardId getExistingCardId(String name) {

    }

    private CardId getNewCardId(Card card) {

    }

    public void addCard(Card card) {

    }

    public void addItem(Item item) {

    }

    public int search(String name) {

    }

    public int searchCollection(String name, Account account) {
        //account.getCollection().searchCardName(name);
        //account.getCollection().searchItemName(name);
    }

    public Card buy(Account account, String name) {

    }

    public void sell(Account account, CardId cardId) {

    }

    public static void help() {
        accountView.viewHelpOfShop();
    }

    public static Shop getInstance() {
        if (singleInstance == null)
            singleInstance = new Shop();
        return singleInstance;
    }
}
