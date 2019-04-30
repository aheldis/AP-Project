package model.Item;

import model.land.Square;

public class Collectable extends Item {
    private CollectableId collectableId;
    private Square square;

    void affect() {

    }

    public CollectableId getCollectableId() {
        return collectableId;
    }

    public void setCollectableId(CollectableId collectableId) {
        this.collectableId = collectableId;
    }

    Square getSquare() {
        return square;
    }

    void setSquare(Square square) {
        this.square = square;
    }
}
