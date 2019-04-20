package model.mode;

import model.Item.Flag;

public class SaveFlagMode extends Mode {
    Flag flag;

    SaveFlagMode() {
        flag = new Flag();
    }

    @Override
    public boolean checkForEndOfTheGame() {
        return false;
    }
}
