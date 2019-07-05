package model.card;

import model.battle.Player;
import model.land.Square;
import view.Graphic.BattleScene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Change implements Serializable {
    private String targetType;
    private boolean opponentCanMove = true; //Disarm buff
    private boolean opponentCanAttack = true;
    private boolean opponentCanCounterAttack = true;
    private int turnOfCanNotMoveForOpponent = 0;
    private int turnOfCanNotAttackForOpponent = 0;
    private int turnOfCanNotCounterAttackForOpponent = 0;
    private int hpChange = 0;
    private int apChange = 0;

    private HashMap<String, ArrayList<Integer>> buffs = new HashMap<>();
    private boolean unaffectBuffs; //bara nirooye khodi bada ro az bein mibare bara doshman khoobaro
    private String apOrHpForWeakness = null; // ap/hp -> faghat faghat age weakness buff dasht
    private int changeInApOrHpForWeakness = 0; //meghdare Taghir age weakness dasht
    private String apOrHpForPower = null; // ap/hp -> faghat faghat age power buff dasht
    private int changeInApOrHpForPower = 0; //meghdare Taghir age power dasht
    private int changeInManaForMana = 0;
    private int changeInHpForDecreaserHp = 0;

    public void affect(Player player, ArrayList<Square> targets) {

        if (targetType.equals("square")) {
            for (Square square : targets) {
                for (String buffName : buffs.keySet()) {
                    for (int number : buffs.get(buffName)) {
                        square.addBuffToSquare(getBuff(buffName, number));
                        BattleScene.getSingleInstance().showTarget(square, targetType);
                    }
                }
            }
        }

        if (targetType.equals("force")) {
            for (Square square : targets) {
                makeChangeInTargetCard(player, (Card) square.getObject());
                BattleScene.getSingleInstance().showTarget(square, targetType);
            }
        }

        if (targetType.equals("player")) {
            for (String buffName : buffs.keySet()) {
                for (int number : buffs.get(buffName)) {
                    player.addBuffToPlayer(getBuff(buffName, number));
                }
            }
        }

    }

    private Buff getBuff(String buffName, int forHowManyTurn) {
        Buff buff = Buff.getNewBuffByName(buffName, forHowManyTurn);
        if (buff != null) {
            if (buffName.equals("Power")) {
                if (apOrHpForPower.equals("ap"))
                    buff.setApChange(changeInApOrHpForPower);
                else
                    buff.setHpChange(changeInApOrHpForPower);
            }
            if (buffName.equals("Weakness")) {
                if (apOrHpForWeakness.equals("ap"))
                    buff.setApChange(changeInApOrHpForWeakness);
                else
                    buff.setHpChange(changeInApOrHpForWeakness);
            }
            if (buffName.equals("Mana")) {
                buff.setManaChange(changeInManaForMana);
            }
            if (buffName.equals("DecreaserHp")) {
                buff.setHpChange(changeInHpForDecreaserHp);
            }
        }
        return buff;
    }

    private void makeChangeInTargetCard(Player player, Card targetCard) {//change e hamle konnande ro roye opponent seda mikonm
        if (!opponentCanMove)
            targetCard.setCanMove(false, turnOfCanNotMoveForOpponent);

        if (!opponentCanCounterAttack)
            targetCard.setCanCounterAttack(false, turnOfCanNotCounterAttackForOpponent);

        if (!opponentCanAttack)
            targetCard.setCanAttack(false, turnOfCanNotAttackForOpponent);

        targetCard.changeAp(apChange);
        targetCard.changeHp(hpChange);

        if (!unaffectBuffs) {
            for (String buffName : buffs.keySet()) {
                for (int forHowManyTurn : buffs.get(buffName)) {
                    targetCard.addBuff(getBuff(buffName, forHowManyTurn));
                }
            }
        }

        if (unaffectBuffs) {
            if (buffs.size() == 0) {
                if (targetCard.getPlayer().equals(player)) {
                    targetCard.removeBuffs(false);
                } else
                    targetCard.removeBuffs(true);
            } else {
                for (String buffName : buffs.keySet()) {
                    targetCard.removeBuff(buffName);
                }
            }
        }
    }

    public String getTargetType() {
        return targetType;
    }
}
