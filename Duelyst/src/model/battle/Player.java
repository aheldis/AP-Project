package model.battle;

import model.account.Account;

public abstract class Player {
    private Account account;
    private Deck mainDeck;
    private Hand hand;
    protected String type;
    private Match match;
    private int turnsPlayed;

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

    public abstract void setType();

    public abstract void addToAccountWins();

    public abstract void setDeck();

    public abstract void setHand();

}
