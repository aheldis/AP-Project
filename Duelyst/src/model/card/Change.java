package model.card;

import model.battle.Player;
import model.land.Square;

import java.util.ArrayList;
import java.util.HashMap;

public class Change {
    //TODO OOOO continuesssss

    //TODO oon on_move o folan ro too kard borde gharar shod chi beshe va if yes be fileha bas ezafe she. ~ e engar ezafe shode hichi pas :) (check)

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
    private HashMap<String, ArrayList<Integer>> buffs = new HashMap<>();
    private boolean unaffactBuffs; //bara nirooye khodi bada ro az bein mibare bara doshman khoobaro
    private String apOrHpForWeakness = null; // ap/hp -> faghat faghat age weakness buff dasht
    private int changeInApOrHpForWeakness = 0; //meghdare Taghir age weakness dasht
    private String apOrHpForPower = null; // ap/hp -> faghat faghat age power buff dasht
    private int changeInApOrHpForPower = 0; //meghdare Taghir age power dasht
    //todo ina ro dar nazar begiram too in paeen :> DONE :-?

    public void affect(Player player, ArrayList<Square> targets) {
        if (targetType.equals("Square")) {
            for (Square square : targets) {
                for (String buffName : buffs.keySet()) {
                    square.addBuffToSquare(getBuff(buffName));
                }
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

        targetCard.changeAp(apChange);
        targetCard.changeHp(hpChange);

        if (!unaffactBuffs) {
            for (String buffName : buffs.keySet()) {
                for (int forHowManyTurn : buffs.get(buffName)) {
                    targetCard.addBuff(getBuff(buffName), forHowManyTurn);
                    //todo ye check bokonam hamin aval nabayad affect dade beshse ya na
                }
            }
        }

        //todo age size arraylist buff sefr nabashe faghat oon buffe ro bayad hazf kone DONE :-?
        if (unaffactBuffs) {
            if (buffs.size() == 0) {
                if (targetCard.getPlayer().equals(player)) {
                    targetCard.removeBuffs(true);
                } else
                    targetCard.removeBuffs(false);
            } else {
                for (String buffName : buffs.keySet()) {
                    targetCard.removeBuff(buffName);
                }
            }
        }
    }

    private Buff getBuff(String buffName) {

        Buff buff = Buff.getNewBuffByName(buffName);
        if (buffName.equals("power")) {
            if (apOrHpForPower.equals("ap"))
                buff.setApChange(changeInApOrHpForPower);
            else
                buff.setHpChange(changeInApOrHpForPower);
        }
        if (buffName.equals("weakness")) {
            if (apOrHpForWeakness.equals("ap"))
                buff.setApChange(changeInApOrHpForWeakness);
            else
                buff.setHpChange(changeInApOrHpForWeakness);
        }
        return buff;
    }
}
