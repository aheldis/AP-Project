package model.card.makeFile;

import model.card.Change;
import model.card.Target;

public class ItemCopy {
    public String name;
    public String type;
    public String description;
    public int cost;
    public int itemNumber;
    private Target target;
    private Change change;

    public ItemCopy() {
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setChange(Change change) {
        this.change = change;
    }
}
