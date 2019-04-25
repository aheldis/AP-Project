package model.battle;

import model.account.Account;

public abstract class Player {
    private Account account;
    protected Deck mainDeck;
    private Hand hand;
    protected String type;
    private Match match;
    private int turnsPlayed;
    private GraveYard graveYard;
    private Player opponent;

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

    public void init() {

    }

    public void initPerTurn() {

    }

    public abstract void playTurn();

    public abstract void setType();

    public abstract void addToAccountWins();

    public abstract void setDeck();

    public void setHand() {

    }

}
