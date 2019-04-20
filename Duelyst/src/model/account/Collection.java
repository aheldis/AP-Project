package model.account;

import view.enums.ErrorType;
import model.Item.*;
import model.battle.*;
import model.card.*;
import view.*;

import java.util.ArrayList;



public class Collection {

    private ArrayList<Spell> spells;
    private ArrayList<Hero> heroes;
    private ArrayList<Minion> minions;
    private Item[] items = new Item[3];
    private Account account;
    private ArrayList<Deck> decks;

    public Collection(Account account) {
        this.account = account;
    }

    public String passCardIdByName(String name){
        ArrayList<Card> cards=new ArrayList<>();
        cards.addAll(spells);
        cards.addAll(heroes);
        cards.addAll(minions);

        for(Card card : cards){
            if(name.equals(card.getName()))
                return card.getCardId().getCardIdAsString();
        }
        return "";
    }

    public ArrayList getDecks() {
        return decks;
    }

    public void showCardsAndItems() {
        AccountView accountView = AccountView.getInstance();
        accountView.cardsAndItemsView(spells, minions, heroes, items);
    }

    public void addToHeros(Hero hero){
        heroes.add(hero);
    }

    public void addToMinions(Minion minion) {
        minions.add(minion);
    }

    public void addToSpells(Spell spell) {
        spells.add(spell);
    }

    public boolean addToItems(Item item) {
        for (int i = 0; i < 3; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    public void searchCardName(String cardName) {
        boolean have = false;
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(spells);
        cards.addAll(minions);
        cards.addAll(heroes);
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                System.out.println(card.getCardId().getCardIdAsString());
                have = true;
            }
        }
        if (!have) {
            ErrorType error = ErrorType.HAVE_NOT_CARD_IN_COLLECTION;
            error.printMessage();
        }
    }

    public void searchItemName(String itemName) {
        boolean have = false;
        for (Item item : items) {
            if (item instanceof Usable) {
                if (item.getUsableId().getItemName().equals(itemName)) {
                    System.out.println(item.getUsableId().getUsableId());
                    have = true;
                }
            }
            if (item instanceof Collectable) {
                if (item.getCollectableId().getItemName().equals(itemName)) {
                    System.out.println(item.getCollectableId().getCollectableId());
                    have = true;
                }
            }
            if (!have) {
                ErrorType error = ErrorType.HAVE_NOT_ITEM_IN_COLLECTION;
                error.printMessage();
            }
        }

    }

    public void save() {

    }

    private Deck passTheDeckIfHaveBeenExist(String deckName) {
        for (Deck deck : decks) {
            if (decks.getName().equals(deckName))
                return deck;
        }
        return null;
    }

    public void createDeck(String deckName) {
        if (passTheDeckIfHaveBeenExist(deckName) != null) {
            ErrorType error = ErrorType.DECK_HAVE_BEEN_EXIST;
            error.printMessage();
            return;
        }
        Deck deck = new NormalDeck();
        deck.setName(deckName);
        decks.add(deck);

    }

    public void deleteDeck(String deckName) {
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (deck != null)
            decks.remove(deck);
        else {
            ErrorType error = ErrorType.HAVE_NOT_DECK;
            error.printMessage();

        }
    }

    private int numberOfCardsExceptHeroesForThisDeck(String deckName) {
        for (Deck item : decks) {
            if (item.getName.equals(deckName)) {
                return item.getCardsOfDeck().size();
            }
        }
        ErrorType error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return 0;
    }

    private int numberOfItemsForThisDeck(String deckName) {
        for (Deck item : decks) {
            if (item.getName.equals(deckName)) {
                return item.getItemOfDeck().size();
            }
        }
        ErrorType error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return 0;
    }

    public int numberOfCards() {//for make cardID
        return minions.size() + spells.size() + heroes.size();

    }

    private boolean heroHaveBeenExistInThisDeck(String deckName, String heroId) {
        for (Deck deck : decks) {
            if (deck.getName.equals(deckName)) {
                if (deck.hero == null)
                    return false;
                else {
                    if (deck.hero.equals(heroId)) {//equal is a function in model.card
                        return true;
                    }
                    return true;
                }
            }
        }
        ErrorType error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return false;
    }

    private Card cardHaveBeenExistInThisDeck(String deckName, String cardId) {
        for (Deck deck : decks) {
            if (deck.getName.equals(deckName)) {
                ArrayList<Card> cards = deck.getCardOfDeck();
                for (Card card : cards) {
                    if (cards.equals(cardId))
                        return card;
                }
                return null;
            }
        }
        ErrorType error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return null;
    }

    public Card passCardByCardId(String cardId) {
        ArrayList<Card> cards=new ArrayList<>();
        cards.addAll(heroes);
        cards.addAll(minions);
        cards.addAll(spells);
        for (Card card: cards) {
            if (card.equals(cardId))
                return card;
        }
        return null;
    }

    public Item passUsableItemByUsableItemId(String usableItemId) {
        for (Item item : items) {
            if (item.getUsableId().getUsableIdAsString().equals(usableItemId)) {
                return item;
            }
        }
        return null;
    }

    public void addCardToThisDeck(String cardId, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if(!errorForDeck(deck))
            return ;

        if (deck.getCardsOfDeck().size() == 20) {
            error = ErrorType.CAN_NOT_ADD_CARD;
            error.printMessage();
            return;
        }

        Card card = passCardByCardId(cardId);
        if (card == null) {
            error = ErrorType.HAVE_NOT_CARD_IN_COLLECTION;
            error.printMessage();
        } else {
            if (cardHaveBeenExistInThisDeck(deckName, cardId) != null) {
                error = ErrorType.HAVE_CARD_IN_DECK;
                error.printMessage();
                return;
            }
            deck.addToCardsOfDeck(card);
        }

    }

    public void addHeroToThisDeck(String heroId, String deckName) {
        ErrorType error;
        if (heroHaveBeenExistInThisDeck(deckName, heroId))
            return;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if(!errorForDeck(deck))
            return ;
        Card card = passCardByCardId(heroId);
        if (card == null) {
            error = ErrorType.HAVE_NOT_HERO_IN_COLLECTION;
            error.printMessage();
            return;
        } else {
            if (heroHaveBeenExistInThisDeck(deckName, heroId)) {
                error = ErrorType.HAVE_HERO_IN_DECK;
                error.printMessage();
                return;
            }
            deck.setHero(card);
        }

    }

    public void addItemToThisDeck(String usableItemId, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if(!errorForDeck(deck))
            return ;

        if (deck.item != null) {
            error = ErrorType.HAVE_ONE_ITEM_IN_DECK;
            error.printMessage();
            return;
        }
        Item item = passUsableItemByUsableItemId(usableItemId);
        if (item == null) {
            error = ErrorType.HAVE_ONE_ITEM_IN_DECK;
            error.printMessage();
            return;
        }
        deck.addItemToDeck(item);


    }

    public boolean errorForDeck(Deck deck){
        ErrorType error;
        if (deck == null) {
            error = ErrorType.HAVE_NOT_DECK;
            error.printMessage();
            return false;
        }
        return true;
    }

    public void removeCardFromDeck(String cardId, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if(!errorForDeck(deck))
            return ;
        Card card = cardHaveBeenExistInThisDeck(deckName, cardId);
        if (card == null) {
            error = ErrorType.HAVE_NOT_CARD_IN_DECK;
            error.printMessage();
            return;
        }
        deck.removeFromCardsOFDeck(card);
    }

    public void removeHeroFromDeck(String heroId, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if(!errorForDeck(deck))
            return ;
        Card card = passCardByCardId(heroId);

        if (card.equals(heroId)) {
            deck.removeFromCardsOFDeck(card);

        } else {
            error = ErrorType.HAVE_NOT_HERO_IN_DECK;
            error.printMessage();
        }


    }

    public void removeItemFromDeck(String usableItemId, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if(!errorForDeck(deck))
            return ;
        Item item = passUsableItemByUsableItemId(usableItemId);
        if (item == null) {
            error = ErrorType.HAVE_NOT_ITEM_IN_COLLECTION;
            error.printMessage();
            return;
        }
        if (item.equalUsableItem(usableItemId)) {
            deck.addItemToDeck(item);
        } else {
            error = ErrorType.HAVE_NOT_ITEM_IN_DECK;
            error.printMessage();
        }


    }

    public boolean validateDeck(String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (deck != null) {
            if (deck.validate())
                return true;
            else
                return false;
        }
        error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return false;
    }

    public void showAlldecks() {//if it has main deck we should show it first
        NormalDeck deck = account.getMainDeck();
        if (deck != null) {//change the place of main deck to the end and print from last index
            decks.remove(deck);
            decks.add(deck);
        }
        AccountView accountView = AccountView.getInstance();
        accountView.decksView(decks);
    }

    public void showThisDeck(String deckName) {
        AccountView accountView = AccountView.getInstance();
        accountView.deckView(passTheDeckIfHaveBeenExist(deckName));
    }

    public void helpOfCollection() {
        AccountView accountView = AccountView.getInstance();
        accountView.helpViewForCollection();
    }

    public void removeCard(Card card) {
        for (Hero hero : heroes) {
            if (card instanceof Hero && hero.equals(card)) {
                heroes.remove(hero);
                break;
            }
        }
        for (Minion minion : minions) {
            if (card instanceof Minion && minion.equals(card)) {
                minions.remove(minion);
            }
        }
        for (Spell spell : spells) {
            if (card instanceof Spell && spell.equals(card)) {
                spells.remove(spell);
                break;
            }
        }
    }

    public void removeItem(Item item) {
        for (int i = 0; i < 3; i++) {
            if (item.equals(items[i])) {
                items[i] = null;
                return;
            }
        }
    }

}
