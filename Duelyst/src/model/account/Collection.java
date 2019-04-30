package model.account;

import model.Item.Item;
import model.Item.Usable;
import model.battle.Deck;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.AccountView;
import view.enums.ErrorType;

import java.util.ArrayList;
import java.util.Arrays;

public class Collection {

    private ArrayList<Spell> spells = new ArrayList<>();
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Minion> minions = new ArrayList<>();
    private Usable[] items = new Usable[3];
    private Account account;
    private ArrayList<Deck> decks = new ArrayList<>();
    private AccountView accountView = AccountView.getInstance();

    public Collection(Account account) {
        this.account = account;
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
        accountView.cardsAndItemsView(spells, minions, heroes, new ArrayList<Usable>(Arrays.asList(items)));
    }

    public void addToHeros(Hero hero) {
        heroes.add(hero);
    }

    public void addToMinions(Minion minion) {
        minions.add(minion);
    }

    public void addToSpells(Spell spell) {
        spells.add(spell);
    }

    public boolean addToItems(Usable item) {
        for (int i = 0; i < 3; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    public void selectADeckAsMainDeck(String deckName) {

        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (deck == null) {
            ErrorType error = ErrorType.HAVE_NOT_DECK;
            error.printMessage();

        }
        account.setMainDeck(deck);
    }

    public Deck passTheDeckIfHaveBeenExist(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName))
                return deck;
        }
        return null;
    }

    public boolean searchCardName(String cardName) {
        boolean have = false;
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(spells);
        cards.addAll(minions);
        cards.addAll(heroes);
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                accountView.print(card.getCardId().getCardIdAsString());
                have = true;
            }
        }
        return have;
    }

    public boolean searchItemName(String itemName) {
        boolean have = false;
        for (Usable item : items) {
            if (item == null)
                continue;
            if (item.getUsableId().getUsableIdAsString().equals(itemName)) {
                accountView.print(item.getUsableId().getUsableIdAsString());
                have = true;
            }
        }
        return have;

    }

    public void createDeck(String deckName) {
        if (passTheDeckIfHaveBeenExist(deckName) != null) {
            ErrorType error = ErrorType.DECK_HAVE_BEEN_EXIST;
            error.printMessage();
            return;
        }
        Deck deck = new Deck();
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
        for (Card card : cards) {
            if (card.equals(cardId))
                return card;
        }
        return null;
    }

    public Usable passUsableItemByUsableItemId(String usableItemId) {
        for (Usable item : items) {
            if (item.getUsableId().getUsableIdAsString().equals(usableItemId)) {
                return item;
            }
        }
        return null;
    }

    public void addCardToThisDeck(Card card, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (!errorForDeck(deck))
            return;

        if (card instanceof Hero) {
            if (deck.getHero() != null) {
                error = ErrorType.HAVE_HERO_IN_DECK;
                error.printMessage();
                return;
            }
            deck.setHero((Hero) card);
            return;
        }

        if (deck.getCardsOfDeck().size() == 20) {
            error = ErrorType.CAN_NOT_ADD_CARD;
            error.printMessage();
            return;
        }
//        if (cardHaveBeenExistInThisDeck(deckName, cardId) != null) {
//            error = ErrorType.HAVE_CARD_IN_DECK;
//            error.printMessage();
//            return;
//            deck.addToCardsOfDeck(card);
//        }

    }

    public boolean errorForDeck(Deck deck) {
        ErrorType error;
        if (deck == null) {
            error = ErrorType.HAVE_NOT_DECK;
            error.printMessage();
            return false;
        }
        return true;
    }

    public void addItemToThisDeck(Usable item, String deckName) {
        ErrorType error;
        Deck deck = passTheDeckIfHaveBeenExist(deckName);
        if (!errorForDeck(deck))
            return;

        if (deck.getItem() != null) {
            error = ErrorType.HAVE_ONE_ITEM_IN_DECK;
            error.printMessage();
            return;
        }
        deck.addItemToDeck(item);


    }

    public void removeCardFromDeck(Card card, String deckName) {
        ErrorType error;
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
        deck.removeItemOfDeck(item);
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
            return deck.validate();
        }
        error = ErrorType.HAVE_NOT_DECK;
        error.printMessage();
        return false;
    }

    public void showAlldecks() {//if it has main deck we should show it first
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

    public void helpOfCollection() {
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

    public void removeItem(Usable item) {
        for (int i = 0; i < 3; i++) {
            if (item.equals(items[i])) {
                items[i] = null;
                return;
            }
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
            if (item1.getName().equals(item.getName())) {
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

    public void save() {

    }
}
