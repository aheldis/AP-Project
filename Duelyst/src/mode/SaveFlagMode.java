package mode;

import Item.Flag;
import land.Square;

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
