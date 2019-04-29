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

    public void makeChangeInCard(Card opponentCard) {//change e hamle konnande ro roye opponent seda mikonm
        opponentCard.setCanMove(this.opponentCanMove);
        opponentCard.setCanCounterAttack(this.opponentCanCounterAttack);
        opponentCard.setCanAttack(this.opponentCanAttack);
        opponentCard.setTurnOfCanNotMove(Math.max(opponentCard.getTurnOfCanNotMove(), this.turnOfCanNotMoveForOpponent));
        opponentCard.setTurnOfCanNotAttack(Math.max(opponentCard.getTurnOfCanNotAttack(), this.turnOfCanNotAttackForOpponent));
        opponentCard.setTurnofCanNotCounterAttack(Math.max(opponentCard.getTurnOfCanNotCounterAttack(), this.turnOfCanNotCounterAttackForOpponent));
        opponentCard.changeAp(apChange);
        opponentCard.changeAp(hpChange);
        for(Buff buff : buffs){
            buff.affect(opponentCard);
        }
        for(Buff buff : untiBuffs){
            buff.unAffect(opponentCard);
        }
    }
    public void affect(ArrayList <Square> targets){
        //todo target ha ghablan bayad salahiateshoon check shode bashe
        //todo hame chizaee ke inja hast ro bere ro targeta asar bede
    }


    public void destroyPositiveEffects() {

    }

}
