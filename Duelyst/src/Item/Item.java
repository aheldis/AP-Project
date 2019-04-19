package Item;

public abstract class Item {
    private String name;
    private String description;
    private int itemID;

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
