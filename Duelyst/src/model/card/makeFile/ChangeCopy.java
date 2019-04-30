package model.card.makeFile;

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
    public HashMap<String, Integer> buffs; //in az har baff yedoone toosh mitoone dashte bashe ke okeye fekr konam age nist begin
    //public ArrayList<Buff> untiBuffs;
    public boolean unaffactBuffs; //bara nirooye khodi bada ro az bein mibare bara doshman khoobaro

    public ChangeCopy() {
    }
}
