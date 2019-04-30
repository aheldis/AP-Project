package model.card.makeFile;

import java.util.HashMap;

public class ChangeCopy {

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

}
