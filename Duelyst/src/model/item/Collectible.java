package model.item;

import model.land.Square;

public class Collectible extends Item {
    private CollectibleId collectibleId;
    private Square square;

    void affect() {

    }

    public CollectibleId getCollectibleId() {
        return collectibleId;
    }

    public void setCollectibleId(CollectibleId collectibleId) {
        this.collectibleId = collectibleId;
    }

    Square getSquare() {
        return square;
    }

    void setSquare(Square square) {
        this.square = square;
    }
}
