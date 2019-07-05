package model.item;

import java.io.Serializable;

public class Usable extends Item implements Serializable {
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
