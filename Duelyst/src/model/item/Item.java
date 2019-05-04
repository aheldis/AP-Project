package model.item;

import model.card.Change;
import model.card.Target;

public abstract class Item {
    private String name;
    private String type; //usable Collectible
    private String description;
    private int cost;
    private int itemNumber;
    protected Target target = new Target();
    protected Change change = new Change();
    //todo if you wanna add player set player in match constructor

    public Item(){

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
