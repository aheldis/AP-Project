package model.card;

import model.land.Square;
import model.requirment.Coordinate;

import java.util.ArrayList;
import java.util.Objects;

public class Change {

    // todo setter hasho to card gozashtam ke dastresish rahat tar she :D (zahra)

    protected boolean canCounterAttack = true; //Disarm buff
    protected boolean canAttack = true;
    protected boolean canMove = true;
    protected int turnOfCanNotMove = 0;
    protected int turnOfCanNotAttack = 0;
    protected int turnOfCanNotCounterAttack = 0;
    private int hpChange = 0;
    private int apChange = 0;
    private boolean continuous = false;
    private ArrayList<Buff> buffs;
    private ArrayList<Buff> untiBuff;
    //private int changeHpInNextTurn = 0;//todo nemidonam chiye line 15 inforForMakeCard tozih bede
    // private boolean addToHpAfterCounterAttack = false; //Holy buff //todo dar buff handle mikonm(zahra)
    //private int turnOfAffect = 0;

    public void destroyPositiveEffects() {

    }

}
