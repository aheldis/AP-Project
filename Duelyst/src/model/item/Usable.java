package model.item;

public class Usable extends Item {
    private UsableId usableId;

    /*
    public void affect() {
        //todo
    }
*/
    public boolean equals(String UsableId) {
        return this.usableId.getUsableIdAsString().equals(usableId);
    }

    public UsableId getUsableId() {
        return usableId;
    }

    public void setUsableId(UsableId usableId) {
        this.usableId = usableId;
    }
}
