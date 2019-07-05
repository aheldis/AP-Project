package model.item;

import javafx.scene.image.ImageView;
import model.card.Card;
import model.land.Square;

import java.io.Serializable;


public class Flag extends Item implements Serializable {
    private Square square;
    private Card OwnerCard;
    private ImageView imageView;

    public Flag(Square square){
        this.square = square;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
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
