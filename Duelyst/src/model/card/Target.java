package model.card;

import model.battle.Player;
import model.land.LandOfGame;
import model.land.Square;

import java.util.ArrayList;

public class Target {
    private Player player;
    private LandOfGame land;
    private ArrayList<Square> targets = new ArrayList<>();
    private String counterAttackType; //ranged hybrid melee
    private String cardType; //minion hero spell
    //    private String number; // 0 <=
    private boolean one;
    private boolean row;
    private boolean column;
    private boolean all;
    private boolean random;
    //distance


    public ArrayList<Square> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Square> targets) {
        this.targets = targets;
    }

    public Target() {

    }

    public Target(Player player, LandOfGame land) {
        this.player = player;
        this.land = land;
    }

    public boolean checkIfAttackedCardIsValid(Card attacked) {
        //check beshe ba sharayet target mikhoone ya na todo kamel nistaa
        String counterAttackName = null;
        if (attacked instanceof Minion) {
            if (!cardType.equals("minion")) {
                return false;
            }
            counterAttackName = ((Minion) attacked).getCounterAttack().getName();
        }
        if (attacked instanceof Hero) {
            if (!cardType.equals("hero")) {
                return false;
            }
            counterAttackName = ((Hero) attacked).getCounterAttack().getName();
        }

        if (counterAttackName != null) {
            if(!counterAttackName.equals(counterAttackType)){
                return false;
            }
            return true;

        }
        return false;
    }

//    public void setTargets(Card attacker, Card attacked) {
//        if (attacked == null) {
//
//        } else {
//
//        }
//    }

    public ArrayList<Square> setTarget(Square square /*squari e ke seda mikone */) {
        //todo
        return null;
    }


}
