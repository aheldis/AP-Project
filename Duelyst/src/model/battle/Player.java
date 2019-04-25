package model.battle;

import model.Item.Flag;
import model.account.Account;
import model.card.Card;

import java.util.ArrayList;

public abstract class Player {
    private Account account;
    protected Deck mainDeck;
    private Hand hand;
    protected String type;
    private Match match;
    private int turnsPlayed = 0;
    private GraveYard graveYard;
    protected Player opponent;

    ArrayList<Flag> flags;
    ArrayList<Card> cardsOnLand = new ArrayList<>();

    public void initPerTurn() {
        //check hand and add if it is less than 5

        for (Card card : cardsOnLand) {
            card.changeTurnOfCanNotAttack(-1);
            card.changeTurnOfCanNotCounterAttack(-1);
            card.changeTurnOfCanNotMove(-1);
            if (card.getTurnOfCanNotAttack() <= 0)
                card.setCanCounterAttack(true);
            if (card.getTurnOfCanNotCounterAttack() <= 0)
                card.setCanCounterAttack(true);
            if (card.getTurnOfCanNotMove() <= 0)
                card.setCanMove(true);
        }


        //turnsplayed ++

    }

    public void addToCardsOfLand(Card card) {
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

    public void setHand() {

    }

    public void counterAttack(Card card, Card theOneWhoAttacked) {

    }

    public abstract void playTurn();

    public abstract void setType();

    public abstract void addToAccountWins();

    public abstract void setDeck();


}
