package model.account;

import model.battle.Deck;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import model.item.Item;
import model.item.Usable;
import view.AccountView;
import view.enums.ErrorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Collection implements Cloneable, Serializable {

    private ArrayList<Spell> spells = new ArrayList<>();
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Minion> minions = new ArrayList<>();
    private Usable[] items = new Usable[3];
    private Account account;
    private ArrayList<Deck> decks = new ArrayList<>();
    private AccountView accountView = AccountView.getInstance();

    public Usable[] getItems() {
        return items;
    }

    public Account getAccount() {
        return account;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    Collection(Account account) {
        this.account = account;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean checkTheDeckForImport(Deck deck) {
        ArrayList<Card> cards = deck.getCardsOfDeck();
        cards.add(deck.getHero());
        for (Card card : cards) {
            if (passCardByCardId(card.getCardId().getCardIdAsString()) == null) {
                return false;//does not have Spell or minion
            }
        }
        //if does not have item
        return passUsableItemByUsableItemId(deck.getItem().getUsableId().getUsableIdAsString()) != null;//have all cards
    }


    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public String passCardIdByName(String name) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(spells);
        cards.addAll(heroes);
        cards.addAll(minions);

        for (Card card : cards) {
            if (name.equals(card.getName()))
                return card.getCardId().getCardIdAsString();
        }
        return "";
    }

    public void showCardsAndItems() {
        accountView.cardsAndItemsView(spells, minions, heroes, new ArrayList<>(Arrays.asList(items)));
    }

    public void addToCards(Card card) {
        if (card instanceof Hero)
            addToHeros((Hero) card);
        if (card instanceof Minion)
            addToMinions((Minion) card);
        if (card instanceof Spell)
            addToSpells((Spell) card);
    }

    void addToHeros(Hero hero) {
        heroes.add(hero);
    }

    void addToMinions(Minion minion) {
        minions.add(minion);
    }

    void addToSpells(Spell spell) {
        spells.add(spell);
    }

    void addToItems(Usable item) {
        for (int i = 0; i < 3; i++) {
            if (items[i] == null) {
                items[i] = item;
                return;
            }
        }
    }

    public ErrorType selectADeckAsMainDeck(String deckName) {

        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (errorForDeck(deck)) {
            if (!deck.validate()) {
                ErrorType error = ErrorType.SELECTED_INVALID_DECK;
                error.printMessage();
                return error;

            }
            account.setMainDeck(deck);
        }
        return null;
    }

    public Deck passTheDeckIfHaveBeenExist(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public ArrayList<String> searchCardName(String cardName) {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(spells);
        cards.addAll(minions);
        cards.addAll(heroes);
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                ids.add(card.getCardId().getCardIdAsString());
            }
        }
        return ids;
    }

    public ArrayList<String> searchItemName(String itemName) {
        ArrayList<String> ids = new ArrayList<>();
        for (Usable item : items) {
            if (item == null)
                continue;
            if (item.getUsableId().getUsableIdAsString().equals(itemName)) {
                ids.add(item.getUsableId().getUsableIdAsString());
            }
        }
        return ids;

    }

    public ErrorType createDeck(String deckName) {
        if (passTheDeckIfHaveBeenExist(deckName) != null) {
            ErrorType error = ErrorType.DECK_HAVE_BEEN_EXIST;
            error.printMessage();
            return error;

        }
        Deck deck = new Deck();
        deck.setName(deckName);
        decks.add(deck);
        return null;
    }

    public ErrorType deleteDeck(String deckName) {
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (deck != null)
            decks.remove(deck);
        else {
            ErrorType error = ErrorType.HAVE_NOT_DECK;
            error.printMessage();
            return error;
        }
        return null;
    }

    private int numberOfCardsExceptHeroesForThisDeck(String deckName) {
        for (Deck item : decks) {
            if (item.getName().equals(deckName)) {
                return item.getCardsOfDeck().size();
            }
        }
        ErrorType error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return 0;
    }

    public int numberOfCards() {//for make cardID
        return minions.size() + spells.size() + heroes.size();

    }

    public boolean heroHaveBeenExistInThisDeck(String deckName, String heroId) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                if (deck.getHero() == null)
                    return false;
                else {
                    if (deck.getHero().equals(heroId)) {//equal is a function in model.card
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

    public Card passCardByCardId(String cardId) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(heroes);
        cards.addAll(minions);
        cards.addAll(spells);
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equalCard(cardId)) {
                return cards.get(i);
            }
        }
        return null;
    }

    public Usable passUsableItemByUsableItemId(String usableItemId) {
        for (Usable item : items) {
            if (item == null)
                continue;
            if (item.getUsableId().getUsableIdAsString().equals(usableItemId))
                return item;
        }
        return null;
    }

    public void addCardToThisDeck(Card card, String deckName) {
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (!errorForDeck(deck))
            return;
        deck.addToCardsOfDeck(card);


    }

    private boolean errorForDeck(Deck deck) {
        ErrorType error;
        if (deck == null) {
            error = ErrorType.HAVE_NOT_DECK;
            //error.printMessage();
            return false;
        }
        return true;
    }

    public void addItemToThisDeck(Usable item, String deckName) {
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (!errorForDeck(deck))
            return;
        deck.addItemToDeck(item);
    }

    public void removeCardFromDeck(Card card, String deckName) {
        ErrorType error;
        if (card == null)
            return;
        String cardId = card.getCardId().getCardIdAsString();
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (!errorForDeck(deck))
            return;

        if (card instanceof Hero) {
            if (card.equalCard(deck.getHero().getCardId().getCardIdAsString()))
                deck.setHero(null);
            else {
                error = ErrorType.HAVE_NOT_HERO_IN_DECK;
                error.printMessage();
            }
            return;
        }
        if (deck.cardHaveBeenExistInThisDeck(cardId) == null) {
            error = ErrorType.HAVE_NOT_CARD_IN_DECK;
            error.printMessage();
            return;
        }
        deck.removeFromCardsOfDeck(card);
    }

    public void removeItemFromDeck(Usable usableItem, String deckName) {
        ErrorType error;
        String usableItemId = usableItem.getUsableId().getUsableIdAsString();
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (!errorForDeck(deck))
            return;
        Usable item = passItemIfHaveBeenExistInThisDeck(deck, usableItemId);
        if (item == null) {
            error = ErrorType.HAVE_NOT_ITEM_IN_DECK;
            error.printMessage();
            return;
        }
        deck.removeItemOfDeck();
    }

    private Usable passItemIfHaveBeenExistInThisDeck(Deck deck, String UsableItem) {
        for (int i = 0; i < 3; i++) {
            if (items[i].equals(UsableItem))
                return items[i];
        }
        return null;


    }

    public boolean validateDeck(String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (deck != null) {
            if (!deck.validate()) {
                ErrorType.SELECTED_INVALID_DECK.printMessage();
            }
            return deck.validate();
        }
        error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return false;
    }

    public void showAllDecks() {//if it has main deck we should show it first
        Deck deck = account.getMainDeck();
        if (deck != null) {//change the place of main deck to the end and print from last index
            decks.remove(deck);
            decks.add(deck);
        }

        accountView.decksView(decks);
    }

    public void showAllDecksName() {
        Deck deck = account.getMainDeck();
        if (deck != null) {
            decks.remove(deck);
            decks.add(deck);
        }
        accountView.decksNameView(decks);
    }

    public void showThisDeck(String deckName) {
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (deck == null) {
            ErrorType error = ErrorType.HAVE_NOT_DECK;
            error.printMessage();
            return;
        }
        ArrayList<Item> items = new ArrayList<>();
        items.add(deck.getItem());
        accountView.DeckAndHandView(deck.getHero(), items, deck.getCardsOfDeck());
    }

    public String helpOfCollection() {
        return accountView.helpViewForCollection();
    }

    void removeCard(Card card) {
        if (card instanceof Hero) {
            for (Hero hero : heroes) {
                if (hero.equals(card)) {
                    heroes.remove(hero);
                    return;
                }
            }
        }
        if (card instanceof Minion) {
            for (Minion minion : minions) {
                if (minion.equals(card)) {
                    minions.remove(minion);
                    return;
                }
            }
        }
        if (card instanceof Spell) {
            for (Spell spell : spells) {
                if (spell.equals(card)) {
                    spells.remove(spell);
                    return;
                }
            }
        }

        for (Deck deck : decks) {
            deck.removeFromCardsOfDeck(card);
        }
    }

    void removeItem(Usable item) {
        for (int i = 0; i < 3; i++) {
            if (item.equals(items[i])) {
                items[i] = null;
                return;
            }
        }

        for (Deck deck : decks) {
            deck.removeItemOfDeck();
        }
    }

    public int getNumberOfCardId(Card card) {
        ArrayList<Card> cards = getAllCards();
        int number = 1;
        for (Card card1 : cards) {
            if (card1.getName().equals(card.getName())) {
                number++;
            }
        }
        return number;
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(heroes);
        cards.addAll(minions);
        cards.addAll(spells);
        return cards;
    }

    public int getNumberOfItemId(Item item) {
        int number = 1;
        for (Item item1 : items) {
            if (item1 != null && item != null && item1.getName().equals(item.getName())) {
                number++;
            }
        }
        return number;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName))
                return deck;
        }
        return null;
    }

}
