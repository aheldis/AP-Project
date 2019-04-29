package model.battle;

import model.account.Account;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;

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
               //todo useSpecialPower
                return;
            }

        }
        if (card instanceof Hero) {
            if(((Hero) card).getHaveSpecialPower()){
                //todo useSpecialPower
                return;
            }
        }
        error=ErrorType.DO_NOT_HAVE_SPECTIAL_POWER;


    }

    public boolean checkPutCard() {//by distance with other squares
        //todo
    }

    public void putCardOnLand(Card playerCard, Coordinate coordinate, LandOfGame land) {
        if (playerCard == null)
            return;
        if (!checkPutCard()) {
            ErrorType error = ErrorType.INVALID_TARGET;
        }

        cardsOnLand.add(playerCard);
        Square[][] squares = land.getSquares();
        squares[coordinate.getX()][coordinate.getY()].setCard(playerCard);

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
        //todo write a function to change square to coordinate
        if (!withinRange(card.getPosition(), newPosition, 2)) {
            //todo ERROR not within range move
        }
        if (!card.isCanMove()) {
            //todo ERROR cannot move
        }
        //todo age vijegish mogheye move anjam mishe

        card.getPosition().removeCardFromSquare();
        card.setPosition(newPosition);
        newPosition.setCard(card);
        //todo age item collactable dasht
    }

    public boolean withinRange(Square square1, Square square2, int range) {
        if (abs(square1.getXCoordinate() - square2.getXCoordinate()) + abs(square1.getYCoordinate() - square2.getYCoordinate()) <= range) {
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
