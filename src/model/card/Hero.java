package model.card;

import java.util.ArrayList;

public class Hero extends Card {
    private String specialPowerInfo;
    private ArrayList<Buff> buffs = new ArrayList<Buff>();
    private Spell spell;
    private int mpRequiredForSpell;
    private int coolDown;
    private int turnNotUsedSpecialPower = 0;
    private int numberOfAttacks;
    private int numberOfBeingAttacked;
    private boolean haveSpecialPower;

    public void addToBuffsOfHero(Buff buff) {
        buffs.add(buff);
    }

    public void increaseNumberOfAttacks() {
        numberOfAttacks++;
    }

    public void increaseNumberOfBeingAttacked() {
        numberOfBeingAttacked++;
    }

    public void addNewHeroCard() {
        //TODO add new other Card and also items (to removed: Make NewHero and MakeNewUsable item)
    }

    public boolean getHaveSpecialPower() {
        return haveSpecialPower;
    }


    public ArrayList<Buff> getBuffs() {
        return buffs;
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

    public int getTurnNotUsedSpecialPower() {
        return turnNotUsedSpecialPower;
    }

    public void addToTurnNotUsedSpecialPower(int a) {
        turnNotUsedSpecialPower += a;
    }

    public void setTurnNotUsedSpecialPower(int turnNotUsedSpell) {
        this.turnNotUsedSpecialPower = turnNotUsedSpell;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public String getSpecialPowerInfo() {
        return specialPowerInfo;
    }

    public void setSpecialPowerInfo(String specialPowerInfo) {
        this.specialPowerInfo = specialPowerInfo;
    }

    public int getNumberOfAttacks() {
        return numberOfAttacks;
    }

    public int getNumberOfBeingAttacked() {
        return numberOfBeingAttacked;
    }


}
