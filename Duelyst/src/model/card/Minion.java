package model.card;

import model.battle.Player;
import model.counterAttack.CounterAttack;
import model.land.Square;
import model.requirment.Coordinate;
import view.Request;
import view.enums.ErrorType;
import view.enums.RequestSuccessionType;

import java.util.ArrayList;


//todo healthPower _> hp    attackPower -> ap to card darimeshon

public class Minion extends Card {
    private int numberOfBeingAttacked;
    private int numberOfAttack;
    private String playerName;
    private int turn;
    private int forWhichPlayer;
    private Square square;
    private int price;
    private CounterAttack counterAttack;
    private int attackRange;
    private ActivationTimeOfSpecialPower activationTimeOfSpecialPower;
    private boolean comboAbility;
    String specialPowerInfo;

    public boolean isComboAbility() {
        return comboAbility;
    }

    int getNumberOfBeingAttacked() {
        return numberOfBeingAttacked;
    }

    int getNumberOfAttack() {
        return numberOfAttack;
    }

    // todo momkene buff tekrari bashe?
    void addToBuffsOfMinion(Buff buff) {
        getBuffsOnThisCard().add(buff);
    }

    void attack(Player opponent, String cardId) {
        Card card = getCardById(cardId, opponent.getCardsOnLand());
        if (card == null) {
            ErrorType.INVALID_CARD_ID.printMessage();
            return;
        }
        if (!withinRange(card.getPosition().getCoordinate())) {
            ErrorType.UNAVAILABLE_OPPONENT.printMessage();
            return;
        }
        Minion minion = card.getPosition().squareHasMinionAndPassIt();
        if (minion != null) {
            //todo
            return;
        }
        Hero hero = card.getPosition().squareHasHeroAndPassIt();
        if (hero != null) {
            //todo
        }
    }

    void counterAttack(Square square) {
        //todo
    }

    public CounterAttack getCounterAttack() {
        return counterAttack;
    }

    void setTarget(Square square) {
        this.square = square;
    }

    void decreaseHPOfTarget() {
        //todo
    }

    public String getSpecialPowerInfo() {
        return specialPowerInfo;
    }

    void useSpecialPower() {

    }

}
