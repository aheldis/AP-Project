package model.account;

import view.enums.ErrorType;
import model.Item.Item;
import model.Item.Usable;
import model.Item.UsableId;
import model.Item.Collectable;
import model.Item.CollectableId;
import model.card.*;
import view.AccountView;

import java.util.ArrayList;


//todo SHOW COLLECTION (HAMON SHOW COLLECTION HAST)
//what?

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
        //model.account.getCollection().searchCardName(name);
        //model.account.getCollection().searchItemName(name);

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
            card.setCardId(new CardId(account, card));
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
            Item item = getItem(name);
            if (item instanceof Usable) {
                UsableId id = new UsableId(account, (Usable) item);
            } else if (item instanceof Collectable) {
                CollectableId id = new CollectableId(account, (Collectable) item);
            }
            if (!enoughDaricForBuy(account, item.getCost()))
                return;
            account.getCollection().addToItems(item);
        }
    }

    public void sell(Account account, String id) {
        Collection collection = account.getCollection();
        Card card = collection.passCardByCardId(id);
        Item item = collection.passItemByItemId(id);
        if (card != null) {
            collection.removeCard(card);
        } else if (item != null) {
            collection.removeItem(item);
        } else {
            ErrorType error = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_Collection;
            accountView.printError(error);
            return;
        }
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
