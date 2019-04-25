package model.battle;

import model.account.Account;
import model.land.LandOfGame;
import model.mode.Mode;

public class Match {
    private Player[] players = new Player[2];
    private Mode mode;
    //private Time startTime;
    //private Time endTime;
    private Account winner;
    private Account loser;
    private int reward;
    private LandOfGame land;
    private int turn = 0;

    public void startGame(){
        //init game
    }


    public Player[] getPlayers() {
        return players;
    }

    public Mode getMode() {
        return mode;
    }

    public Account getWinner() {
        return winner;
    }

    public Account getLoser() {
        return loser;
    }

    public int getReward() {
        return reward;
    }

    public LandOfGame getLand() {
        return land;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setWinner(Account winner) {
        this.winner = winner;
    }

    public void setLoser(Account loser) {
        this.loser = loser;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setLand(LandOfGame land) {
        this.land = land;
    }

    public void newMatch(){
        //todo
    }

    public void startMatch() {
        players[0].init();
        players[1].init();

        while (true) {
            players[turn].playTurn();
            if (gameEnded()) {
                break;
            }
            turn = 1 - turn;
        }
    }

    public boolean gameEnded() {
        //todo
    }
}
