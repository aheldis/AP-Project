package model.card;

import model.counterAttack.CounterAttack;
import model.land.Square;

import java.util.ArrayList;

public class Minion extends Card {
    private int numberOfBeingAttacked;
    private int numberOfAttack;
    private String playerName;
    private ArrayList<Buff> buffs;
    private int turn;
    private int forWhichPlayer;
    private Square square;
    private int price;
    private int manaPower;
    private int healthPower;
    private int attackPower;
    private CounterAttack counterAttack;
    private int attackRange;
    private ActivationTimeOfSpecialPower activationTimeOfSpecialPower;
    String specialPowerInfo;

    int getNumberOfBeingAttacked() {
        return numberOfBeingAttacked;
    }

    int getNumberOfAttack() {
        return numberOfAttack;
    }

    void addToBuffsOfHero(Buff buff) {

    }

    public int getMp() {
        return manaPower;
    }

    void decreaseHP(int healthPower) {
        this.healthPower -= healthPower;
    }

    void increaseHP(int healthPower) {
        this.healthPower += healthPower;
    }

    public int getHp() {
        return healthPower;
    }

    void decreaseAP(int attackPower) {
        this.attackPower -= attackPower;
    }

    void increaseAP(int attackPower) {
        this.attackPower += attackPower;
    }

    public int getAp() {
        return attackPower;
    }

    void attack(Square square) {

    }

    void counterAttack(Square square) {

    }

    public CounterAttack getCounterAttack() {
        return counterAttack;
    }

    void move(Square square) {

    }

    void setTarget(Square square) {

    }

    void decreaseHPOfTarget() {

    }

    public String getSpecialPowerInfo() {
        return specialPowerInfo;
    }

    void useSpecialPower() {

    }

}
