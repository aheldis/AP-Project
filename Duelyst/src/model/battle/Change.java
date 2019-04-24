package model.battle;

import java.util.PrimitiveIterator;

public class Change {

    private int hpChange=0;
    private int apChange=0;
    private int turnOfAffect=0;
    private int changeHpInNextTurn=0;
    private boolean canMove=true;
    private boolean canCounterAttack=true; //Disarm buff
    private boolean addToHpAfterCounterAttack=false; //Holy buff
    private boolean holyBuffDoesNotAffect=false;///shire darande :|
    private boolean decreaseHpafterCounterAttack=false;//mare qol peykar
    private boolean canMoveAtFirst=false;
    private boolean continuous=false;
    public void destroyPositiveEffects(){

    }
}
