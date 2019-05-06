package model.battle;

import model.account.Account;
import model.card.*;
import model.item.ActivationTimeOfItem;
import model.item.Collectible;
import model.item.Flag;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;

import java.util.ArrayList;

public abstract class Player {
    private Deck mainDeck;
    private Hand hand;
    protected String type;
    private Player opponent;
    private ArrayList<Card> cardsOnLand = new ArrayList<>();
    //private Card flagSaver;
    private int turnForSavingFlag = 0;
    private ArrayList<Flag> ownFlags = new ArrayList<>();
    private Account account;
    private Match match;
    private int turnsPlayed = 0;
    private int manaOfThisTurn = 2 /*don't change this except in initPerTurn*/, mana = 2;
    private GraveYard graveYard = new GraveYard(this);
    private ArrayList<Buff> buffsOnThisPlayer = new ArrayList<>();
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
        playerCard.setPosition(getHero().getPosition());
        if (!playerCard.canInsertToCoordination(this.getHero().getPosition().getCoordinate(), coordinate)) {
            error = ErrorType.INVALID_TARGET;
            error.printMessage();
            return;
        }
        playerCard.setPosition(null);
        Square square = land.passSquareInThisCoordinate(coordinate);
        if (square == null) {

            error = ErrorType.INVALID_SQUARE;
            error.printMessage();
            return;
        }

        if (playerCard instanceof Spell) {
            playerCard.setTarget(land.passSquareInThisCoordinate(coordinate));
            playerCard.getChange().affect(playerCard.getPlayer(), playerCard.getTarget().getTargets());
            graveYard.addCardToGraveYard(playerCard, land.passSquareInThisCoordinate(coordinate));
            return;
        }

        //todo instace of collectible

        //cellEffect:
        for (Buff buff : square.getBuffs()) {
            this.addBuffToPlayer(buff);
        }

        if (square.getFlags().size() > 0) {
            for (Flag flag : square.getFlags()) {
                flag.setOwnerCard(playerCard);
                match.addToGameFlags(flag);
                playerCard.getPlayer().addToOwnFlags(flag);
            }
            square.clearFlags();
        }

        if (playerCard instanceof Minion) {
            if (((Minion) playerCard).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_SPAWN) {
                playerCard.useSpecialPower(square);
                playerCard.getChange().affect(this, playerCard.getTargetClass().getTargets());
            }
        }

        mana -= playerCard.getMp();
        hand.removeUsedCardsFromHand(playerCard);
        playerCard.setPosition(square);
        getCardsOnLand().add(playerCard);
        Square[][] squares = land.getSquares();
        squares[coordinate.getX()][coordinate.getY()].setObject(playerCard);

        if (mainDeck.getItem() != null && mainDeck.getItem().getActivationTimeOfItem() == ActivationTimeOfItem.ON_PUT &&
                mainDeck.getItem().getTarget().checkTheOneWhoDoesTheThing(playerCard)) {
            mainDeck.getItem().setTarget(this);
            mainDeck.getItem().getChange().affect(this, mainDeck.getItem().getTarget().getTargets());
        }
    }

    public String getUserName() {
        if (this instanceof ComputerPlayer) {
            return "computer";
        }
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

    public void useCollectibleItemOnLand(Coordinate coordinate, String collectibleItemId) {
        ArrayList<Collectible> collectibleItems = getHand().getCollectibleItems();
        Collectible selected = null;
        for (Collectible collectible : collectibleItems)
            if (collectible.getName().equals(collectibleItemId))
                selected = (Collectible) collectible;
        Square square = match.getLand().passSquareInThisCoordinate(coordinate);
        selected.setTarget(this);
        selected.getChange().affect(this, selected.getTarget().getTargets());
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
            if (outPutCard.equalCard(cardId) && outPutCard.getPlayer().equals(this))
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
            if (card.getBuffsOnThisCard() != null) {
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

            if (card instanceof Minion && ((Minion) card).getHaveSpecialPower() &&
                    ((Minion) card).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.PASSIVE)
                card.useSpecialPower(card.getPosition());
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
        mainDeck.getHero().addToTurnNotUsedSpecialPower(1);

        if (mainDeck.getItem() != null && mainDeck.getItem().getActivationTimeOfItem() == ActivationTimeOfItem.EACH_ROUND) {
            mainDeck.getItem().setTarget(this);
            mainDeck.getItem().getChange().affect(this, mainDeck.getItem().getTarget().getTargets());
        }

        if(ownFlags.size() > 0)
            turnForSavingFlag++;
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

    /*public void setFlagSaver(Card card) {
        flagSaver = card;
    }*/

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

    public int getManaOfThisTurn() {
        return manaOfThisTurn;
    }
}
