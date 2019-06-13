package model.battle;

import model.item.Collectible;
import model.item.Item;
import model.card.Card;

import java.util.ArrayList;
import java.util.Random;

public class Hand {
    private Deck deck;
    private ArrayList<Card> gameCards;
    private ArrayList<Collectible> CollectibleItems = new ArrayList<>();

    Hand(Deck deck) {
        this.deck = deck;
    }

    public Collectible passCollectibleInHand(String CollectibleId) {
        for (Item Collectible : CollectibleItems) {
            if (((Collectible) Collectible).getCollectibleId().getCollectibleIdAsString().equals(CollectibleId))
                return (Collectible) Collectible;
        }
        return null;
    }

    public void addToCollectibleItem(Collectible item) {
        CollectibleItems.add(item);
    }

    public Card passCardInHand(String cardId) {
        for (Card card : gameCards) {
            if (card.equalCard(cardId))
                return card;
        }
        return null;
    }

    void setCards() {//set cards for start of game after shuttle cards in deck
//        Random random = new Random();
//        if (random.nextInt() % RAND_NUMBER == 0) {//to have item sometimes in game not never or always
//            this.gameCards = new ArrayList<>(deck.getCardsOfDeck().subList(0, 3));
//            deck.setIndexOfCards(4);
//            this.gameUsableItem.add(deck.getItem());
//
//            return;
//        }
        this.gameCards = new ArrayList<>(deck.getCardsOfDeck().subList(0, 4));
        deck.setIndexOfCards(5);
    }

    void checkTheHandAndAddToIt() {//call it after each turn
        while (gameCards.size() < 5) {
            deck.increaseIndexOfCards();
            gameCards.add(deck.passNextCard());
        }
//            Random random = new Random();
//            if (random.nextInt() % RAND_NUMBER == 0) {
//                if(gameUsableItem.size()==0) {
//                    gameUsableItem.add(deck.getItem());
//                }
//                else {
//                    deck.increaseIndexOfCards();
//                    gameCards.add(deck.passNextCard());
//                }
//            }
//            else{
//                if(gameUsableItem.size()==0) {
//                    gameCards.add(deck.passNextCard());
//                    deck.increaseIndexOfCards();
//                }
//            }
//        }

    }

    void removeUsedCardsFromHand(Card card) {
        gameCards.remove(card);
    }


    Card chooseARandomCard() {
        Random random = new Random();
        return gameCards.get(random.nextInt(100) % gameCards.size());
    }

    public ArrayList<Card> getGameCards() {
        return gameCards;
    }

    public ArrayList<Collectible> getCollectibleItems() {
        return CollectibleItems;
    }


}
