package model.Item;

import model.land.Square;

public class Flag extends Item {
    private boolean isOnTheGround = true;
    private Square square;

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public boolean isOnTheGround() {
        return isOnTheGround;
    }

    public void setOnTheGround(boolean onTheGround) {
        isOnTheGround = onTheGround;
    }
}
