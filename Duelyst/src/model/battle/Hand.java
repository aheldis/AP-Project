package model.battle;

import model.Item.Item;
import model.card.*;

import java.util.ArrayList;

public abstract class Hand {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;

    private void addToCardsOfHand(Card card) {

    }

    private void removeFromCardsOfHand(Card card) {

    }

    private void addToItemsOfHand(Item item) {

    }

    private void removeFromItemsOfHand(Item item) {

    }

    public abstract void addACardOrItemFromDeck();

    public abstract void chooseACardOrItem();

}
