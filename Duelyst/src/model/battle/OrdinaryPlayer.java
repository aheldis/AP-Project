package model.battle;

import model.Item.Collectable;
import model.Item.Flag;
import model.account.Account;
import model.card.*;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;

import javax.accessibility.AccessibleTable;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class OrdinaryPlayer extends Player {


    public OrdinaryPlayer(Account account, Deck deck, int mana) {
        this.setAccount(account);
        this.setMainDeck(deck);
        this.setType("OrdinaryPlayer");
        mainDeck.setRandomOrderForDeck();
        setMana(mana);
        setHand();
    }

    public void addToAccountWins() {
        getAccount().addToWins();
    }

    public void addMatchInfo(MatchInfo matchInfo) {
        getAccount().addMatchInfo(matchInfo);
    }

    public void playTurn() {

        //bere request begire

    }

    public void useSpecialPower(Card card) {
        ErrorType error;
        if (card instanceof Spell) {
            error = ErrorType.CAN_NOT_USE_SPECIAL_POWER;
            error.printMessage();
            return;
        }
        if (card instanceof Minion) {
            if(((Minion) card).getHaveSpecialPower()){
               //todo AffectSpecialPower
                return;
            }

        }
        if (card instanceof Hero) {
            if(((Hero) card).getHaveSpecialPower()){
                //todo AffectSpecialPower
                return;
            }
        }
        error=ErrorType.DO_NOT_HAVE_SPECIAL_POWER;
        error.printMessage();
    }

    public void putCardOnLand(Card playerCard, Coordinate coordinate, LandOfGame land) {
        if (playerCard == null)
            return;
        if (!playerCard.canMoveToCoordination(coordinate)) {
            ErrorType error = ErrorType.INVALID_TARGET;
        }

        cardsOnLand.add(playerCard);
        Square[][] squares = land.getSquares();
        squares[coordinate.getX()][coordinate.getY()].setObject(playerCard);

    }

    public void attack(Card card,Square target) {
        if (true/*check range*/) {
            //todo ERROR not within range attack
        }
        if (!card.isCanAttack()) {
            //todo ERROR cannot attack
        }
        if (target.getObject() == null || !(target.getObject() instanceof Card)) {
            //todo ERROR cannot attack to this
        }

        Card attacked = (Card) target.getObject();

        //todo age vijegish mogheye attack anjam mishe

        //todo change in ap hp

        opponent.counterAttack(attacked, card);
    }

    public void move(Card card, Square newPosition) {
        ErrorType error;
        if (!withinRange(card.getPosition(), newPosition, 2)) {
            error=ErrorType.CAN_NOT_MOVE;
            error.printMessage();
            return;
        }
        if (!card.isCanMove() && card.canMoveToCoordination(newPosition.getCoordinate())) {
            error=ErrorType.CAN_NOT_MOVE;
            error.printMessage();
            return;
        }
        if(card instanceof Minion){
            if(((Minion) card).getActivationTimeOfSpecialPower()== ActivationTimeOfSpecialPower.ON_RESPAWN){
                card.setTarget(card,newPosition);

                //todo AffectSpecialpower
            }
        }
        if(newPosition.getObject() instanceof Flag){
            flags.add((Flag) newPosition.getObject());
            flagSaver=card;
            turnForSavingFlag++;
        }
        if(newPosition.getObject() instanceof Collectable){
            hand.addToCollectableItem((Collectable) newPosition.getObject());
        }
        card.setPosition(newPosition);
        newPosition.setObject(card);
    }

    public boolean withinRange(Square square1, Square square2, int range) {
        if (abs(square1.getXCoordinate() - square2.getXCoordinate()) +
                abs(square1.getYCoordinate() - square2.getYCoordinate()) <= range) {
            return true;
        }
        return false;
    }
//    public static OrdinaryPlayer makeNewPlayer(Account account, Deck mainDeck) {
//        if (mainDeck == null) {
//            ErrorType error = ErrorType.DONT_HAVE_MAIN_DECK;
//            BattleView.getInstance().printError(error);
//            return null;
//        }
//        if (!mainDeck.validate()) {
//            ErrorType error = ErrorType.SELECTED_INVALID_DECK;
//            BattleView.getInstance().printError(error);
//            return null;
//        }
//
//        OrdinaryPlayer player = new OrdinaryPlayer(account, mainDeck);
//        return player;
//    }
}
