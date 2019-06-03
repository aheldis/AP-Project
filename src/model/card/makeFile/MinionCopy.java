package model.card.makeFile;

import model.card.ActivationTimeOfSpecialPower;
import model.card.Card;
import model.land.Square;

public class MinionCopy extends Card {
    public String name;
    public int cost;
    public int mp;
    public int hp;
    public int ap;
    public String counterAttack;
    public int attackRange;
    public String ActivationTimeOfSpecialPower;
    public String description;
    protected ChangeCopy change = new ChangeCopy();//HAS-A
    protected TargetCopy target = new TargetCopy();

    private String specialPowerInfo;
    private int numberOfBeingAttacked;
    private int numberOfAttack;
    private String playerName;
    private int turn;
    private int forWhichPlayer;
    private Square square;
    private int price;
    private String activationTimeOfSpecialPowerString = "-";
    private ActivationTimeOfSpecialPower activationTimeOfSpecialPower;
    private boolean comboAbility;
    private boolean haveSpecialPower;

}
