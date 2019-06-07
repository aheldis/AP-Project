package model.card.makeFile;


public class CardCopy {
    public String name;
    public String type;
    public int cost;
    public int mp;
    public int hp;
    public int ap;
    public String counterAttack;
    public int attackRange;
    public String ActivationTimeOfSpecialPower;
    public int coolDown;

    private ChangeCopy change = new ChangeCopy();
    private TargetCopy target = new TargetCopy();

    public void setChange(ChangeCopy change) {
        this.change = change;
    }
    public void setTarget(TargetCopy target) {
        this.target = target;
    }
}
