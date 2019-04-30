package model.card;

import model.land.Square;

import java.util.ArrayList;
import java.util.HashMap;

public class Change {

    // todo setter hasho to card gozashtam ke dastresish rahat tar she :D (zahra)

    private String targetType;
    private boolean opponentCanMove = true; //Disarm buff
    private boolean opponentCanAttack = true;
    private boolean opponentCanCounterAttack = true;
    private int turnOfCanNotMoveForOpponent = 0;
    private int turnOfCanNotAttackForOpponent = 0;
    private int turnOfCanNotCounterAttackForOpponent = 0;
    private int hpChange = 0;
    private int apChange = 0;
    private boolean continuous = false;
    private HashMap<Buff, Integer> buffs; //in az har baff yedoone toosh mitoone dashte bashe ke okeye fekr konam age nist begin
    private ArrayList<Buff> untiBuffs;

    private void makeChangeInTargetCard(Card targetCard) {//change e hamle konnande ro roye opponent seda mikonm
        if (!this.opponentCanMove)
            targetCard.setCanMove(false, this.turnOfCanNotMoveForOpponent));

        if (!this.opponentCanCounterAttack)
            targetCard.setCanCounterAttack(false, this.turnOfCanNotCounterAttackForOpponent);

        if (!this.opponentCanAttack)
            targetCard.setCanAttack(false, this.turnOfCanNotAttackForOpponent);

        //ina ro to khod seta dadam
//        targetCard.setTurnOfCanNotMove(Math.max(targetCard.getTurnOfCanNotMove(), this.turnOfCanNotMoveForOpponent));
//        targetCard.setTurnOfCanNotAttack(Math.max(targetCard.getTurnOfCanNotAttack(), this.turnOfCanNotAttackForOpponent));
//        targetCard.setTurnOfCanNotCounterAttack(Math.max(targetCard.getTurnOfCanNotCounterAttack(), this.turnOfCanNotCounterAttackForOpponent));
        targetCard.changeAp(apChange);
        targetCard.changeHp(hpChange);
        for (Buff buff : buffs.keySet()) {
            buff.affect(targetCard);
            //buff.affect(targetCard, buffs.get(buff));
            //todo inja nabayad bere buff ro add kone be buffuye carde bad affect ro to init per turn seda konim?
        }
        for (Buff buff : untiBuffs) {
            buff.unAffect(targetCard);
        }
    }

    public void affect(ArrayList<Square> targets) {
        if (targetType.equals("Square")) {
            for (Square square : targets) {
                for (Buff buff : buffs.keySet())
                    square.addBuffToSquare(buff);
            }
        }
        if (targetType.equals("Card")) {
            for (Square square : targets) {
                makeChangeInTargetCard((Card) square.getObject());
            }
        }
    }


    public void destroyPositiveEffects() {

    }

}
