package model.item;

public class Usable extends Item {
    private UsableId usableId;
    private int countOfAnimation ;


    public int getCountOfAnimation() {
        return countOfAnimation;
    }

    public boolean equals(String usableId) {
        return this.usableId.getUsableIdAsString().equals(usableId);
    }

    public UsableId getUsableId() {
        return usableId;
    }

    public void setUsableId(UsableId usableId) {
        this.usableId = usableId;
    }
}
