package model.battle;

import model.Item.Item;
import model.card.*;

import java.util.ArrayList;
import java.util.Random;

public class Hand {
    private Deck deck;
    private ArrayList<Card> gameCards;
    private ArrayList<Item> gameUsableItem=new ArrayList<>();
    private final int RAND_NUMBER=11;

    public Hand (Deck deck){
        this.deck=deck;
    }

    public void setCards() {//set cards for start of game after shuttle cards in deck
        Random random = new Random();
        if (random.nextInt() % RAND_NUMBER == 0) {//to have item sometimes in game not never or always
            this.gameCards = new ArrayList<>(deck.getCardsOfDeck().subList(0, 3));
            deck.setIndexOfCards(4);
            this.gameUsableItem.add(deck.getItem());

            return;
        }
        this.gameCards = new ArrayList<>(deck.getCardsOfDeck().subList(0, 4));
        deck.setIndexOfCards(5);
    }

    public void checkTheHandAndAddToIt() {//call it after each turn
        if (gameCards.size() + gameUsableItem.size() < 5) {
            Random random = new Random();
            if (random.nextInt() % RAND_NUMBER == 0) {
                if(gameUsableItem.size()==0) {
                    gameUsableItem.add(deck.getItem());
                }
                else {
                    deck.increaseIndexOfCards();
                    gameCards.add(deck.passNextCard());
                }
            }
            else{
                if(gameUsableItem.size()==0) {
                    gameCards.add(deck.passNextCard());
                    deck.increaseIndexOfCards();
                }
            }
        }
    }

    public void removeUsedCardsFromHand(Card card) {
        gameCards.remove(card);
    }

    public void removeUsedItemFromHand(Item item){
        gameUsableItem.remove(item);
    }
    public Card chooseARandomCard(){
        Random random=new Random();
       return gameCards.get(random.nextInt()%gameCards.size());
    }


}
