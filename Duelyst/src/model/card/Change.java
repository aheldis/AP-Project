package model.card;

import model.land.Square;

import java.util.ArrayList;

public class Change {

    // todo setter hasho to card gozashtam ke dastresish rahat tar she :D (zahra)

    private boolean opponentCanMove = true; //Disarm buff
    private boolean opponentCanAttack = true;
    private boolean opponentCanCounterAttack = true;
    private int turnOfCanNotMoveForOpponent = 0;
    private int turnOfCanNotAttackForOpponent = 0;
    private int turnOfCanNotCounterAttackForOpponent = 0;
    private int hpChange = 0;
    private int apChange = 0;
    private boolean continuous = false;
    private ArrayList<Buff> buffs;
    private ArrayList<Buff> untiBuffs;

    public void makeChangeInTargetCard(Card targetCard) {//change e hamle konnande ro roye opponent seda mikonm
        targetCard.setCanMove(this.opponentCanMove);
        targetCard.setCanCounterAttack(this.opponentCanCounterAttack);
        targetCard.setCanAttack(this.opponentCanAttack);
        targetCard.setTurnOfCanNotMove(Math.max(targetCard.getTurnOfCanNotMove(), this.turnOfCanNotMoveForOpponent));
        targetCard.setTurnOfCanNotAttack(Math.max(targetCard.getTurnOfCanNotAttack(), this.turnOfCanNotAttackForOpponent));
        targetCard.setTurnofCanNotCounterAttack(Math.max(targetCard.getTurnOfCanNotCounterAttack(), this.turnOfCanNotCounterAttackForOpponent));
        targetCard.changeAp(apChange);
        targetCard.changeAp(hpChange);
        for(Buff buff : buffs){
            buff.affect(targetCard);
        }
        for(Buff buff : untiBuffs){
            buff.unAffect(targetCard);
        }
    }
    public void affect(ArrayList <Square> targets){
        //todo target ha ghablan bayad salahiateshoon check shode bashe
        //todo hame chizaee ke inja hast ro bere ro targeta asar bede
    }


    public void destroyPositiveEffects() {

    }

}
