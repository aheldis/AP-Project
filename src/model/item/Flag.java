package model.item;

import model.card.Card;
import model.land.Square;

import java.io.Serializable;


public class Flag extends Item implements Serializable {
    private Square square;
    private Card OwnerCard;

    public Flag(Square square){
        this.square = square;
    }

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
