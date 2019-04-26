package model.Item;

import model.land.Square;

public class Collectable extends Item {
    private CollectableId collectableId;
    private Square square;

    public CollectableId getCollectableId() {
        return collectableId;
    }

    public void setCollectableId(CollectableId collectableId) {
        this.collectableId = collectableId;
    }

    void setSquare(Square square) {
        this.square = square;
    }

    Square getSquare() {
        return square;
    }

    void affect() {

    }
}
