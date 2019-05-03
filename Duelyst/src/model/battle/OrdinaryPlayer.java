package model.battle;

import model.item.Flag;
import model.account.Account;
import model.card.Buff;
import model.card.Card;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;

import java.util.ArrayList;

public class OrdinaryPlayer extends Player {

    public OrdinaryPlayer(){
    }

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

/*
    public void move(Card card, Square newPosition) {
        ErrorType error;
        if (!withinRange(card.getPosition(), newPosition, 2)) {
            error = ErrorType.CAN_NOT_MOVE_IN_SQUARE;
            error.printMessage();
            return;
        }
        if (!card.isCanMove() && card.canMoveToCoordination(card, newPosition.getCoordinate())) {
            error = ErrorType.CAN_NOT_MOVE_IN_SQUARE;
            error.printMessage();
            return;
        }

        ArrayList<Buff> buffsOfSquare = newPosition.getBuffs();
        for (Buff buff : buffsOfSquare) {
            buff.affect(card);
        }

        if (card instanceof Minion) {
            if (((Minion) card).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_SPAWN) {
                card.setTarget(card, newPosition);

                //todo AffectSpecialPower
            }
        }
        if (newPosition.getObject() instanceof Flag) {
            flags.add((Flag) newPosition.getObject());
            flagSaver = card;
            turnForSavingFlag++;//todo dead
        }
        if (newPosition.getObject() instanceof Collectible) {
            hand.addToCollectibleItem((Collectible) newPosition.getObject());
        }
        card.setPosition(newPosition);
        newPosition.setObject(card);
    }*/


   /* @Override
    public void attack(Card card, Square target) {

    }*/

    /*public boolean withinRange(Square square1, Square square2, int range) {
        if (abs(square1.getXCoordinate() - square2.getXCoordinate()) +
                abs(square1.getYCoordinate() - square2.getYCoordinate()) <= range) {
            return true;
        }
        return false;
    }*/
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
