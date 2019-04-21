package model.mode;

import model.battle.Player;
import model.land.Square;

abstract class Mode {
    private String modeName;
    private Player[] players = new Player[2];
    private int turn;

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getModeName() {
        return modeName;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players[0] == null)
            players[0] = player;
        else
            players[1] = player;
    }

    public boolean playerGotTheFlag(Square squareOfPlayer, Square squareOfFlag) {
        return squareOfPlayer.equals(squareOfFlag);
    }

    public abstract boolean checkForEndOfTheGame();
}
