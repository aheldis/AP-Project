package model.Item;

public abstract class Item {
    private String name;
    private String description;
    private int cost;
    private int itemNumber; //todo tozih to card

    //    TODO print
    public void printDescription() {

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
