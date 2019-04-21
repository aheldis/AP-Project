package model.Item;

public abstract class Item {
    private String name;
    private String description;
    private int cost;


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

//    TODO print
    public void printDescription() {

    }

}
