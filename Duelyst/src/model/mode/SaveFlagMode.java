package model.mode;

import model.item.Flag;
import model.battle.Match;

public class SaveFlagMode extends Mode {
    Flag flag;

    SaveFlagMode() {
        flag = new Flag();
    }

    public boolean checkForEndOfTheGame(Match match) {
        return false;
    }
}
