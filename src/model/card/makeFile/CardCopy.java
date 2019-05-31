package model.card.makeFile;


public class CardCopy {
    public String name;
    public int CardNumber;
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

    public void setChange(ChangeCopy change) {
        this.change = change;
    }
    public void setTarget(TargetCopy target) {
        this.target = target;
    }
}
