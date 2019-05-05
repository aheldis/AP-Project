package model.item;

import model.card.Card;
import model.land.Square;

public class Flag extends Item {
    private Square square;
    private Card OwnerCard;

    public void setOwnerCard(Card ownerCard) {
        OwnerCard = ownerCard;
    }

    public Card getOwnerCard() {
        return OwnerCard;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

}
