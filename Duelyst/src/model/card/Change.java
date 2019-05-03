package model.card;

import model.battle.Player;
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
    private HashMap<String, Integer> buffs; //in az har baff yedoone toosh mitoone dashte bashe ke okeye fekr konam age nist begin
    //private ArrayList<Buff> untiBuffs;
    private boolean unaffactBuffs; //bara nirooye khodi bada ro az bein mibare bara doshman khoobaro

    public void affect(Player player, ArrayList<Square> targets) {
        if (targetType.equals("Square")) {
            for (Square square : targets) {
                for (String buffName : buffs.keySet())
                    square.addBuffToSquare(Buff.getByName(buffName));
            }
        }
        if (targetType.equals("Card")) {
            for (Square square : targets) {
                makeChangeInTargetCard(player, (Card) square.getObject());
            }
        }
    }

    private void makeChangeInTargetCard(Player player, Card targetCard) {//change e hamle konnande ro roye opponent seda mikonm
        if (!this.opponentCanMove)
            targetCard.setCanMove(false, this.turnOfCanNotMoveForOpponent);

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
        for (String buffName : buffs.keySet()) {
            targetCard.addBuff(Buff.getByName(buffName), buffs.get(buffName));
        }
        if (unaffactBuffs) {
            if (targetCard.getPlayer().equals(player)) {
                targetCard.removeBuffs(true);
            } else
                targetCard.removeBuffs(false);
        }
    }

    public void destroyPositiveEffects() {

    }

    public String getTargetType() {
        return targetType;
    }
}
