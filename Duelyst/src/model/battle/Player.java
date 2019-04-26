package model.battle;

import model.Item.Flag;
import model.account.Account;
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
    private GraveYard graveYard = new GraveYard();
    protected Player opponent;

    ArrayList<Flag> flags;
    private ArrayList<Card> cardsOnLand = new ArrayList<>();


    public void putCardOnLand(String cardId, Coordinate coordinate, LandOfGame land) {
        Card playerCard = null;
        for (Card card : hand.getGameCards()) {
            if (card.equalCard(cardId))
                playerCard = card;
        }
        if (playerCard == null)
            return;
        cardsOnLand.add(playerCard);
        Square[][] squares = land.getSquares();
        squares[coordinate.getX()][coordinate.getY()].setCard(playerCard);

    }

    public void initPerTurn() {
        hand.checkTheHandAndAddToIt();
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
        turnsPlayed++;
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
        hand = new Hand(mainDeck);
        hand.setCards();
    }

    public void counterAttack(Card card, Card theOneWhoAttacked) {

    }

    public abstract void playTurn();

    public abstract void addToAccountWins();

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setTurnsPlayed(int turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
    }

    public void setGraveYard(GraveYard graveYard) {
        this.graveYard = graveYard;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
