package model.battle;

import model.account.Account;
import model.card.Buff;
import model.card.Card;
import model.card.Hero;
import model.item.Collectible;
import model.item.Flag;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;

import java.util.ArrayList;

public abstract class Player {
    Deck mainDeck;
    Hand hand;
    protected String type;
    private Player opponent;
    ArrayList<Card> cardsOnLand = new ArrayList<>();
    private Card flagSaver;
    private int turnForSavingFlag = 0;
    private ArrayList<Flag> ownFlags;
    private Account account;
    Match match;
    private int turnsPlayed = 0;
    int manaOfThisTurn /*don't change this except in initPerTurn*/, mana;
    private GraveYard graveYard = new GraveYard(this);
    private ArrayList<Buff> buffsOnThisPlayer;
    //Collectible item to hand ast :D

    //    public abstract void move(Card card, Square newPosition);
//    public abstract void attack(Card card, Square target);
//    public abstract void useSpecialPower(Card card);

    public void putCardOnLand(Card playerCard, Coordinate coordinate, LandOfGame land) {
        ErrorType error;
        if (playerCard == null) {
            error = ErrorType.INVALID_CARD_ID;
            error.printMessage();
            return;
        }
        if (!playerCard.canMoveToCoordination(playerCard, coordinate)) {
            error = ErrorType.INVALID_TARGET;
            error.printMessage();
            return;
        }
        Square square = land.passSquareInThisCoordinate(coordinate);
        if (square == null) {
            error = ErrorType.INVALID_SQUARE;
            error.printMessage();
            return;
        }

        //cellEffect:
        for (Buff buff : square.getBuffs()) {
            this.addBuffToPlayer(buff);
        }

        if (square.getObject() instanceof Flag) {
            ((Flag) square.getObject()).setOwnerCard(playerCard);
            match.addToGameFlags((Flag) square.getObject());
            setFlagSaver(playerCard);
            addToTurnForSavingFlag();
        }
        mana -= playerCard.getMp();
        hand.removeUsedCardsFromHand(playerCard);
        playerCard.setPosition(square);
        getCardsOnLand().add(playerCard);
        Square[][] squares = land.getSquares();
        squares[coordinate.getX()][coordinate.getY()].setObject(playerCard);

    }

    public String getUserName() {
        return account.getUserName();
    }

    public void setTurnForSavingFlag(int turnForSavingFlag) {
        this.turnForSavingFlag = turnForSavingFlag;
    }

    public int getTurnForSavingFlag() {
        return turnForSavingFlag;
    }

    public ArrayList<Flag> getOwnFlags() {
        return ownFlags;
    }


    public abstract void addToAccountWins();

    public abstract void addMatchInfo(MatchInfo matchInfo);

    public void putCollectibleItemOnLand(Coordinate coordinate, String CollectibleItemId) {
        //todo
    }

    public void addItemToCollectibles(Collectible collectible) {
        hand.getCollectibleItems().add(collectible);
    }

    public Card passCardInGame(String cardId) {
        Card card = hand.passCardInHand(cardId);
        if (card != null) {
            return card;
        }
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(cardsOnLand);
        for (Card outPutCard : cards) {
            if (outPutCard.equalCard(cardId) && outPutCard.getPlayer().getAccount().equals(account))
                card = outPutCard;
        }
        return card;
    }

    public void initPerTurn() {
        hand.checkTheHandAndAddToIt();
        for (Card card : cardsOnLand) {
            card.changeTurnOfCanNotAttack(-1);
            card.changeTurnOfCanNotCounterAttack(-1);
            card.changeTurnOfCanNotMove(-1);
            if (card.getTurnOfCanNotAttack() <= 0)
                card.setCanCounterAttack(true, 0);
            if (card.getTurnOfCanNotCounterAttack() <= 0)
                card.setCanCounterAttack(true, 0);
            if (card.getTurnOfCanNotMove() <= 0)
                card.setCanMove(true, 0);

            ArrayList<Buff> buffsToBeRemoved = new ArrayList<>();
            for (Buff buff : card.getBuffsOnThisCard()) {
                if (!buff.isContinuous()) {
                    int forHowManyTurn = buff.getForHowManyTurn();
                    forHowManyTurn--;
                    if (forHowManyTurn > 0) {
                        buff.affect(card);
                    } else {
                        if (buff.isHaveUnAffect())
                            buff.unAffect(card);
                        buffsToBeRemoved.add(buff);
                    }
                }
            }

            for (Buff buff : buffsToBeRemoved)
                card.getBuffsOnThisCard().remove(buff);
        }

        ArrayList<Buff> buffsToBeRemoved = new ArrayList<>();
        for (Buff buff : buffsOnThisPlayer) {

            if (!buff.isContinuous()) {
                int forHowManyTurn = buff.getForHowManyTurn();
                forHowManyTurn--;
                if (forHowManyTurn > 0) {
                    buff.affect(this);
                } else
                    buffsToBeRemoved.add(buff);
            }
        }

        for (Buff buff : buffsToBeRemoved)
            buffsOnThisPlayer.remove(buff);


        turnsPlayed++;
        if (manaOfThisTurn < 9) {
            manaOfThisTurn++;
            mana = manaOfThisTurn;
        }
        mainDeck.getHero().

                addToTurnNotUsedSpecialPower(1);

    }

    public void addToCardsOfLand(Card card) {
        cardsOnLand.add(card);
    }

    public void addToOwnFlags(Flag flag) {
        ownFlags.add(flag);
    }

    public void addToTurnForSavingFlag() {
        turnForSavingFlag++;
    }

    public void setHand() {
        hand = new Hand(mainDeck);
        hand.setCards();
    }

    public void removeCard(Card card) {
        cardsOnLand.remove(card);
    }

    public void setFlagSaver(Card card) {
        flagSaver = card;
    }

    public Hero getHero() {
        return mainDeck.getHero();
    }

    public int getNumberOfFlagsSaved() {
        return ownFlags.size();
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public void setGraveYard(GraveYard graveYard) {
        this.graveYard = graveYard;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

    public void setTurnsPlayed(int turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public ArrayList<Card> getCardsOnLand() {
        return cardsOnLand;
    }

    public void playTurnForComputer() {
        //write nothing here for access to this function in computer
    }

    public void addBuffToPlayer(Buff buff) {
        buffsOnThisPlayer.add(buff);
    }

    public void manaChange(int number) {
        mana += number;
    }
}
