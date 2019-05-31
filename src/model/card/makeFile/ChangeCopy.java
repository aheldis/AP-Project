package model.card.makeFile;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeCopy {

    public String targetType;
    public boolean opponentCanMove = true; //Disarm buff
    public boolean opponentCanAttack = true;
    public boolean opponentCanCounterAttack = true;
    public int turnOfCanNotMoveForOpponent = 0;
    public int turnOfCanNotAttackForOpponent = 0;
    public int turnOfCanNotCounterAttackForOpponent = 0;
    public int hpChange = 0;
    public int apChange = 0;
    public boolean continuous = false;
    public HashMap<String, ArrayList<Integer>> buffs = new HashMap<>();
    public boolean unaffectBuffs; //bara nirooye khodi bada ro az bein mibare bara doshman khoobaro
    public String apOrHpForWeakness = null; // ap/hp -> faghat faghat age weakness buff dasht
    public int changeInApOrHpForWeakness = 0; //meghdare Taghir age weakness dasht
    public String apOrHpForPower = null; // ap/hp -> faghat faghat age power buff dasht
    public int changeInApOrHpForPower = 0;
    public int changeInManaForMana = 0;
    public int changeInHpForDecreaserHp = 0;

    public ChangeCopy() {
    }
}
