package model.Item;

import model.land.Square;

import java.util.ArrayList;

public class Usable extends Item {
    private UsableId usableId;
    private ArrayList<Square> target;

    public UsableId getUsableId() {
        return usableId;
    }

    public void setUsableId(UsableId usableId) {
        this.usableId = usableId;
    }

    public void addTotarget(Square square) {
        target.add(square);
    }

    public void affect(){
        //todo
    }

    public boolean equals(String UsableId){
        if(this.usableId.getUsableIdAsString().equals(usableId))
            return true;
        return false;
    }
}
