package model.battle;

import model.account.Account;
import model.land.LandOfGame;

import java.util.Date;

public class Match {
    private Player[] players;
    private String mode;
    //private Time startTime;
    //private Time endTime;
    private Account winner;
    private Account loser;
    private int reward;
    private LandOfGame land;
    private int whichPlayer = 0; //0--> player 1 /1--> player 2
    private Date date;

    public void startMatch() {
        date = new Date();

//        players[0].initPlayers();
//        players[1].initPlayers();
        initGame();
        players[0].initPerTurn();
        players[1].initPerTurn();

        while (true) {
            players[whichPlayer].playTurn();
            if (gameEnded()) {
                break;
            }
            whichPlayer = 1 - whichPlayer;
        }
    }


    public Match(Player[] players, String mode) {//when we make a match we should have players
        this.players = players;
        land = new LandOfGame();
        this.mode = mode;
    }

    public void initGame() {

        //set reward

        players[0].mainDeck.setRandomOrderForDeck();
        players[0].getHand().setCards();
        players[1].mainDeck.setRandomOrderForDeck();
        players[1].getHand().setCards();

        players[0].addToCardsOfLand(players[0].getMainDeck().getHero());
        players[1].addToCardsOfLand(players[1].getMainDeck().getHero());

    }

    public boolean gameEnded() {
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.winner = this.winner;
        matchInfo.loser = this.loser;
        matchInfo.date = date;
        winner.addToWins();
        winner.addMatchInfo(matchInfo);
        loser.addMatchInfo(matchInfo);

        //todo
    }

    public Player[] getPlayers() {
        return players;
    }

    public String getMode() {
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


}
