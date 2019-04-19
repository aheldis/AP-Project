package account;

import IDK.ErrorType;
import Item.Item;
import card.Card;
import card.CardId;
import view.AccountView;

import java.util.ArrayList;


//todo SHOW COLLECTION (HAMON SHOW COLLECTION HAST)

public class Shop {
    public static Shop singleInstance = null;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private static AccountView accountView = AccountView.getInstance();

    private boolean itemExist(String name) {
        for (Item item : items) {
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

    private Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    private CardId getExistingCardId(String name) {
        //todo ??
    }

    private CardId getNewCardId(Account account, Card card) {
        CardId cardId = new CardId(account, card);
        return cardId;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addItem(Item item) {
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
            //todo sout what?
        }
        if (itemExist(name)) {
            Item item = getItem(name);
            //todo
        }
    }

    public void searchCollection(String name, Account account) {
        //todo
        //account.getCollection().searchCardName(name);
        //account.getCollection().searchItemName(name);
    }

    public void buy(Account account, String name) {
        if (!cardExist(name) && !itemExist(name)) {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
            accountView.printError(error);
        }
        if (cardExist(name)) {
            Card card = getCard(name);
            card.setCardId(new CardId(account, card));
            account.getCollection().addCardToThisDeck();
        }
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
