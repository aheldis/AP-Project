package model.mode;

import model.item.Flag;
import model.battle.Match;

import java.util.ArrayList;

public class CollectFlagMode extends Mode {
    private static final int NUMBER_OF_PLAYERS = 2;
    private ArrayList<Flag> flagsOnTheGround;
    private ArrayList<ArrayList<Flag>> flagsOwnedByPlayers = new ArrayList<>(NUMBER_OF_PLAYERS);

    {
        flagsOwnedByPlayers.set(0, new ArrayList<>());
        flagsOwnedByPlayers.set(1, new ArrayList<>());
    }

    CollectFlagMode(int numberOfFlags) {
        flagsOnTheGround = new ArrayList<>(numberOfFlags);
        for (int i = 0; i < numberOfFlags; i++) {
            flagsOnTheGround.add(new Flag());
        }
    }

    /*public void checkEncountersOfAllTheFlagsAndPlayers() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++)
            for (Flag flag : flagsOnTheGround)
                if (playerGotTheFlag(getPlayers()[i],)) {

                }
    }*/

    public boolean checkForEndOfTheGame(Match match) {

        return flagsOnTheGround.size() == 0;
    }
}
