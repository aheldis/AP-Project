package model.card;

import model.card.Buff;

import java.util.ArrayList;

public class Change {

    // todo setter hasho to card gozashtam ke dastresish rahat tar she :D (zahra)

    protected boolean canMove = true;
    protected boolean canCounterAttack = true; //Disarm buff
    protected boolean canAttack = true;
    protected int turnOfCanNotMove = 0;
    protected int turnOfCanNotAttack = 0;
    protected int turnOfCanNotCounterAttack = 0;
    private int hpChange = 0;
    private int apChange = 0;
    private int turnOfAffect = 0;
    private int changeHpInNextTurn = 0;
    private boolean addToHpAfterCounterAttack = false; //Holy buff
    private boolean holyBuffDoesNotAffect = false;///shire darande :|
    private boolean decreaseHpAfterCounterAttack = false;//mare qol peykar
    private boolean canMoveAtFirst = false;
    private boolean continuous = false;
    private ArrayList<Buff> buffs;


    public void destroyPositiveEffects() {

    }
}
