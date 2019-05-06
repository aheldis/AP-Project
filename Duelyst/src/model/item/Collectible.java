package model.item;

import model.card.Card;
import model.land.Square;

public class Collectible extends model.item.Item {
    private model.item.CollectibleId collectibleId;
    private Square square;
    private Card theOneWhoCollects;

    public model.item.CollectibleId getCollectibleId() {
        return collectibleId;
    }

    public void setCollectibleId(model.item.CollectibleId collectibleId) {
        this.collectibleId = collectibleId;
    }

    Square getSquare() {
        return square;
    }

    void setSquare(Square square) {
        this.square = square;
    }

    public void setTheOneWhoCollects(Card theOneWhoCollects) {
        this.theOneWhoCollects = theOneWhoCollects;
    }

    public Card getTheOneWhoCollects() {
        return theOneWhoCollects;
    }


}
