package model.item;

import model.land.Square;

import java.util.ArrayList;

public class Usable extends Item {
    private UsableId usableId;
    private ArrayList<Square> target;

    public void addTotarget(Square square) {
        target.add(square);
    }

    public void affect() {
        //todo
    }

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
