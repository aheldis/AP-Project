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
    private ActivationTimeOfSpecialPower activationTimeOfSpecialPower;
    private boolean comboAbility;
    String specialPowerInfo;
    private boolean haveSpecialPower;

    public void setActivationTimeOfSpecialPower(ActivationTimeOfSpecialPower activationTimeOfSpecialPower) {
        this.activationTimeOfSpecialPower = activationTimeOfSpecialPower;
    }

    public ActivationTimeOfSpecialPower getActivationTimeOfSpecialPower() {
        return activationTimeOfSpecialPower;
    }

    public void setHaveSpecialPower(boolean haveSpecialPower) {
        this.haveSpecialPower = haveSpecialPower;
    }
    public boolean getHaveSpecialPower(){
        return haveSpecialPower;
    }

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

    public CounterAttack getCounterAttack() {
        return counterAttack;
    }

    void setTarget(Square square) {
        this.square = square;
    }

    public String getSpecialPowerInfo() {
        return specialPowerInfo;
    }

    void useSpecialPower() {

    }

}
