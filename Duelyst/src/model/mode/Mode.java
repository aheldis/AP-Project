package model.mode;

import model.battle.Match;
import model.battle.Player;
import model.land.Square;

public abstract class Mode {
    private String modeName;
    private Player[] players = new Player[2];
    private int turn;

    public abstract boolean checkForEndOfTheGame(Match match);

    public void addPlayer(Player player) {
        if (players[0] == null)
            players[0] = player;
        else
            players[1] = player;
    }

    public boolean playerGotTheFlag(Square squareOfPlayer, Square squareOfFlag) {
        return squareOfPlayer.equals(squareOfFlag);
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public Player[] getPlayers() {
        return players;
    }


}
