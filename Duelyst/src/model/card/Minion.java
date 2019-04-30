package model.card;

import model.land.Square;


//todo // healthPower _> hp    attackPower -> ap to card darimeshon

public class Minion extends Card {
    String specialPowerInfo;
    private int numberOfBeingAttacked;
    private int numberOfAttack;
    private String playerName;
    private int turn;
    private int forWhichPlayer;
    private Square square;
    private int price;
    private ActivationTimeOfSpecialPower activationTimeOfSpecialPower;
    private boolean comboAbility;
    private boolean haveSpecialPower;

    void setTarget(Square square) {
        this.square = square;
    }

    public ActivationTimeOfSpecialPower getActivationTimeOfSpecialPower() {
        return activationTimeOfSpecialPower;
    }

    public void setActivationTimeOfSpecialPower(ActivationTimeOfSpecialPower activationTimeOfSpecialPower) {
        this.activationTimeOfSpecialPower = activationTimeOfSpecialPower;
    }

    public boolean getHaveSpecialPower() {
        return haveSpecialPower;
    }

    public void setHaveSpecialPower(boolean haveSpecialPower) {
        this.haveSpecialPower = haveSpecialPower;
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

    public String getSpecialPowerInfo() {
        return specialPowerInfo;
    }

}
