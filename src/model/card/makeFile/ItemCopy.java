package model.card.makeFile;

public class ItemCopy {
    public String name;
    public String type;
    public String description;
    public int cost;
    public int itemNumber;
    private TargetCopy target;
    private ChangeCopy change;

    public ItemCopy() {
    }

    public void setTarget(TargetCopy target) {
        this.target = target;
    }

    public void setChange(ChangeCopy change) {
        this.change = change;
    }
}
