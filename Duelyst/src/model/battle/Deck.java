package model.battle;

import model.Item.Item;
import model.card.*;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {
    private ArrayList<Card> cardsOfDeck = new ArrayList<>(20);
    private String deckName;
    private Item item;
    private Hero hero;

    public Hero getHero() {
        return hero;
    }

    public Item getItem() {
        return item;
    }

    public void setHero(Hero hero) {
        if (hero == null)
            this.hero = hero;
    }

    public ArrayList<Card> getCardsOfDeck() {
        return cardsOfDeck;
    }

    public String getName() {
        return deckName;
    }

    public void setName(String deckName) {
        this.deckName = deckName;
    }

    public boolean validate() {//have 20 cards and 1 hero
        if (cardsOfDeck.size() == 20 && hero != null)
            return true;
        return false;
    }

    public void addToCardsOfDeck(Card card) {
        cardsOfDeck.add(card);
    }

    public void removeFromCardsOfDeck(Card card) {
        cardsOfDeck.remove(card);
    }

    public void addItemToDeck(Item item) {
        if (this.item != null)
            this.item = item;
    }

    public void removeItemOfDeck(Item item) {
        this.item = null;

    }

    public void removeHeroFromDeck() {
        this.hero = null;
    }

    public Card showTheNextCardFromNextTurn() {
        //todo
    }

    public void setRandomOrderForDeck() {//faqat shoroye bazi seda kon
        Collections.shuffle(cardsOfDeck);

    }
    public Card cardHaveBeenExistInThisDeck( String cardId) {
        ArrayList<Card> cards = getCardsOfDeck();
        for (Card card : cards) {
            if (cards.equals(cardId))
                return card;
        }
        return null;
    }
    public abstract void chooseCardsAndAddToCards();

    public abstract void chooseOfItemsAndAddToItems();
}
