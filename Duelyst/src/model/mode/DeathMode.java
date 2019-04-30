package model.mode;

import model.battle.Match;
import model.land.Square;

public class DeathMode extends Mode {

    @Override
    public boolean playerGotTheFlag(Square squareOfPlayer, Square SquareOfFlag) {
        return false;
    }

    public boolean checkForEndOfTheGame(Match match) {
        return false;
    }
}
