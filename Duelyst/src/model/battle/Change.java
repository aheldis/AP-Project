package model.battle;

import model.card.Buff;

import java.util.ArrayList;

public class Change {

    private int hpChange = 0;
    private int apChange = 0;
    private int turnOfAffect = 0;
    private int changeHpInNextTurn = 0;
    private boolean canMove = true;
    private boolean canCounterAttack = true; //Disarm buff
    private boolean addToHpAfterCounterAttack = false; //Holy buff
    private boolean holyBuffDoesNotAffect = false;///shire darande :|
    private boolean decreaseHpAfterCounterAttack = false;//mare qol peykar
    private boolean canMoveAtFirst = false;
    private boolean continuous = false;
    private ArrayList <Buff> buffs;

    public void destroyPositiveEffects() {

    }
}
