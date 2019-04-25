package model.battle;

import model.Item.Flag;
import model.account.Account;
import model.account.Collection;
import model.card.Card;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;

import java.util.ArrayList;

public abstract class Player {
    private Account account;
    protected Deck mainDeck;
    private Hand hand;
    protected String type;
    private Match match;
    private int turnsPlayed = 0;
    private GraveYard graveYard=new GraveYard();
    protected Player opponent;
    ArrayList<Flag> flags;
    private ArrayList<Card> cardsOnLand = new ArrayList<>();

    public void putCardOnLand(String cardId, Coordinate coordinate, LandOfGame land){
        Card playerCard = null;
        for(Card card : hand.getGameCards()){
            if(card.equalCard(cardId))
                playerCard=card;
        }
        if(playerCard==null)
            return;
        cardsOnLand.add(playerCard);
        Square[][] squares =land.getSquares();
        squares[coordinate.getX()][coordinate.getY()] .setCard(playerCard);

    }

    public void initPerTurn() {
        hand.checkTheHandAndAddToIt();
        for (Card card :cardsOnLand){
            card.changeTurnOfCanNotAttack(-1);
            card.changeTurnOfCanNotCounterAttack(-1);
            card.changeTurnOfCanNotMove(-1);
            if(card.getTurnOfCanNotAttack()<=0)
                card.setCanCounterAttack(true);
            if(card.getTurnOfCanNotCounterAttack()<=0)
                card.setCanCounterAttack(true);
            if(card.getTurnOfCanNotMove()<=0)
                card.setCanMove(true);
        }
        turnsPlayed ++;
    }

    public void addToCardsOfLand(Card card){
        cardsOnLand.add(card);
    }
    public GraveYard getGraveYard() {
        return graveYard;
    }

    public Account getAccount() {
        return account;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public Hand getHand() {
        return hand;
    }

    public String getType() {
        return type;
    }

    public Match getMatch() {
        return match;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }


    public abstract void playTurn();

    public abstract void setType();

    public abstract void addToAccountWins();

    public abstract void setDeck();

    public void setHand() {

    }

    public void counterAttack(Card card, Card theOneWhoAttacked) {

    }

}
