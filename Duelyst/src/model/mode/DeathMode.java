package model.mode;

import model.land.Square;

public abstract class DeathMode extends Mode {
    @Override
    public boolean checkForEndOfTheGame() {
        return false;
    }

    @Override
    public boolean playerGotTheFlag(Square squareOfPlayer, Square SquareOfFlag) {
        return false;
    }
}
