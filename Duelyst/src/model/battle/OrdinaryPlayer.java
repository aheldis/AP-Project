package model.battle;

import model.account.Account;
import model.card.Card;
import model.land.Square;
import view.BattleView;
import view.enums.ErrorType;

import static java.lang.Math.abs;

public class OrdinaryPlayer extends Player {


    public OrdinaryPlayer(Account account, Deck deck) {
        this.setAccount(account);
        this.setMainDeck(deck);
        this.setType("OrdinaryPlayer");
        mainDeck.setRandomOrderForDeck();
        setHand();
    }

    public void addToAccountWins() {
        getAccount().addToWins();
    }

    public static OrdinaryPlayer makeNewPlayer(Account account, Deck mainDeck) {
        if (mainDeck == null) {
            ErrorType error = ErrorType.DONT_HAVE_MAIN_DECK;
            BattleView.getInstance().printError(error);
            return null;
        }
        if (!mainDeck.validate()) {
            ErrorType error = ErrorType.INVALID_DECK_FOR_GAME;
            BattleView.getInstance().printError(error);
            return null;
        }

        OrdinaryPlayer player = new OrdinaryPlayer(account, mainDeck);
        return player;
    }

    public void playTurn() {

        //bere request begire

    }

    public void useSpecialPowerHero() {

    }

    public void putCardFromHandInLand(Card card, Square square) {

        //check mana
        //check
    }

    public void attack(Card card, Square target) {
        if (/*check range*/) {
            //todo ERROR not within range attack
        }
        if (!card.isCanAttack()) {
            //todo ERROR cannot attack
        }
        if (target.getObject() == null || !(target.getObject() instanceof Card) {
            //todo ERROR cannot attack to this
        }

        Card attacked = (Card) target.getObject();

        //todo age vijegish mogheye attack anjam mishe

        //todo change in ap hp

        opponent.counterAttack(attacked, card);
    }

    public void move(Card card, Square newPosition) {
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
}
