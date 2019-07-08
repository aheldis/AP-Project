package model.item;

import javafx.scene.image.ImageView;
import model.card.Card;
import model.land.Square;

import java.io.Serializable;


public class Collectible extends Item implements Serializable {
    private model.item.CollectibleId collectibleId;
    private Square square;
    private Card theOneWhoCollects;
    private ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public model.item.CollectibleId getCollectibleId() {
        return collectibleId;
    }

    public void setCollectibleId(model.item.CollectibleId collectibleId) {
        this.collectibleId = collectibleId;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public void setTheOneWhoCollects(Card theOneWhoCollects) {
        this.theOneWhoCollects = theOneWhoCollects;
    }

    public Card getTheOneWhoCollects() {
        return theOneWhoCollects;
    }


}
