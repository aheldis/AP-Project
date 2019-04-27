package model.card;

import model.counterAttack.CounterAttack;
import model.land.Square;

import java.util.ArrayList;


//todo healthPower _> hp    attackPower -> ap to card darimeshon

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

    void changeHp(int healthPower) {
        this.hp += healthPower;
    }

    public int getHp() {
        return hp;
    }

    void changeAp(int attackPower) {
        this.ap -= attackPower;
    }

    public int getAp() {
        return ap;
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
