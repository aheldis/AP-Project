package model.battle;

import model.Item.Item;
import model.card.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class Hand {
    protected ArrayList<Card> gameCards;
    protected ArrayList<Item> gameUsableItem;
    private final int RAND_NUMBER=11;

    public void setCards(Deck deck) {//set cards for start of game after shuttle cards in deck
        Random random = new Random();
        if (random.nextInt() % RAND_NUMBER == 0) {//to have item sometimes in game not never or always
            this.gameCards = new ArrayList<>(deck.getCardsOfDeck().subList(0, 3));
            this.gameUsableItem.add(deck.getItem());
            return;
        }
        this.gameCards = new ArrayList<>(deck.getCardsOfDeck().subList(0, 4));
    }

    public void checkTheHandAndAddToIt(Deck deck) {
        if (gameCards.size() + gameUsableItem.size() < 5) {
            Random random = new Random();
            if (random.nextInt() % RAND_NUMBER == 0) {
                if(gameUsableItem.size()==0)
                    gameUsableItem.add(deck.getItem());
                else
                    gameCards.add(deck.passNextCard());
            }
            else{
                if(gameUsableItem.size()==0)
                    gameCards.add(deck.passNextCard());
            }
        }
    }

    public void removeUsedCardsFromHand(Card card) {
        gameCards.remove(card);
    }

    public void removeUsedItemFromHand(Item item){
        gameUsableItem.remove(item);
    }

    public abstract void chooseACardOrItem();

}
