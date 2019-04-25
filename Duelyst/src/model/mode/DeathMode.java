package model.mode;

import model.land.Square;

public class DeathMode extends Mode {

    public boolean checkForEndOfTheGame() {
        return false;
    }

    @Override
    public boolean playerGotTheFlag(Square squareOfPlayer, Square SquareOfFlag) {
        return false;
    }
}
