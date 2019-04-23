package model.battle;

import model.Item.Item;
import model.card.*;

import java.util.ArrayList;

public abstract class Hand {
    protected ArrayList<Card> cards;
    protected ArrayList<Item> items;

    public void setCards(Deck deck ) {//set cards for start of game after shuttle cards in deck
       this.cards= new ArrayList<>(deck.getCardsOfDeck().subList(0,4));

    }

    private void addToCardsOfHand(Card card) {
    }

    private void removeFromCardsOfHand(Card card) {

    }

    private void addToItemsOfHand(Item item) {

    }

    private void removeFromItemsOfHand(Item item) {

    }

    public abstract void addACardOrItemFromDeck();
    public abstract void removeUsedCardOrItem();

    public abstract void chooseACardOrItem();

}
