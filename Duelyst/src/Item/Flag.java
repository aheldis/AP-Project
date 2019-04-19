package Item;

import land.Square;

public class Flag extends Item {
    private boolean isOnTheGround = true;
    private Square square;

    public void setSquare(Square square) {
        this.square = square;
    }

    public void setOnTheGround(boolean onTheGround) {
        isOnTheGround = onTheGround;
    }

    public Square getSquare() {
        return square;
    }

    public boolean isOnTheGround() {
        return isOnTheGround;
    }
}
