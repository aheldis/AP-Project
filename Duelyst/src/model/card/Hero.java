package model.card;

import model.counterAttack.CounterAttack;
import model.requirment.Coordinate;

import java.util.ArrayList;

public class Hero extends Card {
    private String counterAttackclassName;
    private String specialPowerInfo;
    private ArrayList<Buff> buffs = new ArrayList<Buff>();
    private CounterAttack counterAttack;
    private Coordinate Square;
    private int hp;
    private int ap;
    private Spell spell;
    private int mpRequiredForSpell;
    private int coolDown;
    private int lastTimeSpellUsed;
    private int attackRange;
    private int numberOfAttacks;
    private int numberOfBeingAttacked;

    public void setCounterAttack(CounterAttack counterAttack) {
        this.counterAttack = counterAttack;
        this.counterAttackclassName = counterAttack.getClassName();
    }

    public String getCounterAttackclassName() {
        return counterAttackclassName;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public CounterAttack getCounterAttack() {
        return counterAttack;
    }

    public Coordinate getSquare() {
        return Square;
    }

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }

    public Spell getSpell() {
        return spell;
    }

    public int getMpRequiredForSpell() {
        return mpRequiredForSpell;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public int getLastTimeSpellUsed() {
        return lastTimeSpellUsed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public String getSpecialPowerInfo() {
        return specialPowerInfo;
    }

    public void addToBuffsOfHero(Buff buff) {
        buffs.add(buff);
    }

    public void increaseHp(int number) {
        hp += number;
    }

    public void decreaseHp(int number) {
        hp -= number;
    }

    public void increaseAp(int number) {
        ap += number;
    }

    public void decreaseAp(int number) {
        ap -= number;
    }

    public void useSpecialPower() {
        //TODO
    }

    public int getNumberOfAttacks() {
        return numberOfAttacks;
    }

    public int getNumberOfBeingAttacked() {
        return numberOfBeingAttacked;
    }

    public void increaseNumberOfAttacks() {
        numberOfAttacks++;
    }

    public void increaseNumberOfBeingAttacked() {
        numberOfBeingAttacked++;
    }

    public void setSpecialPowerInfo(String specialPowerInfo) {
        this.specialPowerInfo = specialPowerInfo;
    }

    public void addNewHeroCard() {
        //TODO
    }


}
