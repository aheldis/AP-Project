package model.battle;

import model.Item.Flag;
import model.land.LandOfGame;

import java.util.ArrayList;
import java.util.Date;

public class Match {
    private Player[] players;
    private String mode;//DeathMode - SaveFlagMode - CollectFlagMode
    private int numberOfFlags;
    private ArrayList<Flag> flags;
    private Player winner;
    private Player loser;
    private int reward;
    private LandOfGame land;
    private int whichPlayer = 0; //0--> player 1 /1--> player 2
    private Date date;


    public void setFlagsRandomly(){
        //todo
    }
    public ArrayList<Flag> getFlags(){
        return flags;
    }

    public Match(Player[] players, String mode, int numberOfFlags, int reward) {
        //when we make a match we should have players
        this.players = players;
        this.mode = mode;
        this.numberOfFlags = numberOfFlags;
        this.reward = reward;
        land = new LandOfGame();
    }

    public Player passPlayerWithTurn() {
        if (whichPlayer == 0)
            return players[0];
        else
            return players[1];
    }

    public Player passAnotherPlayerWithOutTurn() {//midonam esmesh cherte (zahra)
        if (whichPlayer == 0)
            return players[1];
        else
            return players[0];
    }

    public void changeTurn() {
        if (whichPlayer == 0)
            whichPlayer = 1;
        else
            whichPlayer = 0;
    }

    public void startMatch() {
        date = new Date();

        initGame();
        players[0].initPerTurn();
        players[1].initPerTurn();

        while (true) {
            players[whichPlayer].playTurn();
            if (gameEnded()) {
                endGame();
                break;
            }
            whichPlayer = 1 - whichPlayer;
        }
    }


    public void initGame() {

        /* ina to khode player anjam mishe:
        players[0].mainDeck.setRandomOrderForDeck();
        players[0].getHand().setCards();
        players[1].mainDeck.setRandomOrderForDeck();
        players[1].getHand().setCards();
        */

        players[0].addToCardsOfLand(players[0].getMainDeck().getHero());
        players[1].addToCardsOfLand(players[1].getMainDeck().getHero());

    }

    private boolean gameEnded() {
        //todo
        switch (mode) {
            case "DeathMode": {
                if (players[0].getHero().getHp() == 0) {
                    setWinnerAndLoser(players[1], players[0]);
                    return true;
                }
                if (players[1].getHero().getHp() == 0) {
                    setWinnerAndLoser(players[0], players[1]);
                    return true;
                }
                break;
            }
            case "SaveFlagMode": {
                if (players[0].getTurnForSavingFlag() == 6) {
                    setWinnerAndLoser(players[0], players[1]);
                    return true;
                }
                if (players[1].getTurnForSavingFlag() == 6) {
                    setWinnerAndLoser(players[1], players[0]);
                    return true;
                }
                break;
            }
            case "CollectFlagMode": {
                if (players[0].getNumberOfFlagsSaved() >= numberOfFlags / 2) {
                    setWinnerAndLoser(players[0], players[1]);
                    return true;
                }
                if (players[1].getNumberOfFlagsSaved() >= numberOfFlags / 2) {
                    setWinnerAndLoser(players[1], players[2]);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private void endGame() {
        MatchInfo matchInfo = new MatchInfo();
        if (this.winner instanceof OrdinaryPlayer)
            matchInfo.winner = this.winner.getAccount();
        if (this.loser instanceof OrdinaryPlayer)
            matchInfo.loser = this.loser.getAccount();
        matchInfo.date = date;

        winner.addToAccountWins();
        winner.addMatchInfo(matchInfo);
        loser.addMatchInfo(matchInfo);

        //todo ye chizi print kone ke bazi tamoom shode
    }

    private void setWinnerAndLoser(Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;
    }

    public Player[] getPlayers() {
        return players;
    }

    public String getMode() {
        return mode;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public LandOfGame getLand() {
        return land;
    }

    public void setLand(LandOfGame land) {
        this.land = land;
    }


}
