package model.battle;

import model.card.CardId;
import model.item.Item;
import model.account.FilesType;
import model.account.Shop;
import model.card.Card;
import model.card.Hero;
import model.item.Usable;
import model.item.UsableId;
import view.enums.ErrorType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {//if it is normal deck you had initialize it in collection
    // else call setRandomCardsAndItemsInDeck()
    private ArrayList<Card> cardsOfDeck = new ArrayList<>(20);//minon Spell
    private String deckName;
    private Usable item;
    private Hero hero = null;
    private int indexOfCards = 0;

    void setIndexOfCards(int indexOfCards) {
        this.indexOfCards = indexOfCards;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Usable getItem() {
        return item;
    }

    public String getName() {
        return deckName;
    }

    public void setName(String deckName) {
        this.deckName = deckName;
    }

    void increaseIndexOfCards() {
        this.indexOfCards++;
    }

    public boolean validate() {//have 20 cards and 1 hero
        return cardsOfDeck.size() == 20 && hero != null;
    }

    public boolean addToCardsOfDeck(Card card) {
        ErrorType error;
        if (card instanceof Hero) {
            if (getHero() != null) {
                error = ErrorType.HAVE_HERO_IN_DECK;
                error.printMessage();
                return false;
            }
            setHero((Hero) card);
            return true;
        }

        if (getCardsOfDeck().size() == 20) {
            error = ErrorType.CAN_NOT_ADD_CARD;
            error.printMessage();
            return false;
        }
        if (cardHaveBeenExistInThisDeck(card.getCardId().getCardIdAsString()) != null) {
            error = ErrorType.HAVE_CARD_IN_DECK;
            error.printMessage();
            return false;
        }
        cardsOfDeck.add(card);
        return true;
    }


    public void removeFromCardsOfDeck(Card card) {
        cardsOfDeck.remove(card);
    }

    public void addItemToDeck(Usable item) {
        if (this.item != null) {
            ErrorType.HAVE_ONE_ITEM_IN_DECK.printMessage();
            return;
        }
        this.item = item;
    }

    public void removeItemOfDeck(Item item) {
        this.item = null;

    }

    public void removeHeroFromDeck() {
        this.hero = null;
    }

//    public void showTheNextCardFromNextTurn() {
//        BattleView battleView = BattleView.getInstance();
//        battleView.showCardId(passNextCard().getCardId().getCardIdAsString());
//    }

    public Card passNextCard(boolean graphic) {
        if(graphic)
        return cardsOfDeck.get(indexOfCards-1);
        else
            return cardsOfDeck.get(indexOfCards);
    }

    void setRandomOrderForDeck() {//faqat shoroye bazi seda kon
        Collections.shuffle(cardsOfDeck);
    }

    public Card cardHaveBeenExistInThisDeck(String cardId) {
        ArrayList<Card> cards = getCardsOfDeck();
        for (Card card : cards) {
            if (card.equalCard(cardId))
                return card;
        }
        return null;
    }

    public ArrayList<Card> getCardsOfDeck() {
        return cardsOfDeck;
    }

    static Deck getDeckForStoryMode(int level) {
        Deck deck = new Deck();
        try {
            FileReader fileReader = new FileReader("resource/Game/" + level + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String type = "Hero";
            int number = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.charAt(0) == '_') {
                    type = line.substring(1);
                } else {
                    if (FilesType.HERO.getName().equals(type)) {
                        Hero hero = (Hero) Shop.getInstance().getNewCardByName(line.trim());
                        new CardId(hero, number++);
                        deck.setHero(hero);
                    } else if (FilesType.ITEM.getName().equals(type)) {
                        Item item = Shop.getInstance().getNewItemByName(line.trim());
                        new UsableId((Usable) item, number++);
                        deck.addItemToDeck((Usable) item);
                    } else {
                        Card card = Shop.getInstance().getNewCardByName(line.trim());
                        new CardId(card, number++);
                        deck.addToCardsOfDeck(card);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("getDeckForStoryMode: " + e.getMessage());
        }
        return deck;
    }
}
