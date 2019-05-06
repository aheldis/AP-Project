package model.battle;

import model.account.Shop;
import model.card.Card;
import model.card.Hero;
import model.item.Collectible;
import model.item.CollectibleId;
import model.item.Flag;
import model.item.Item;
import model.land.LandOfGame;
import model.land.Square;
import view.BattleView;
import view.enums.StateType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

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
    private int BOUND_FOR_COLLECTIBLES = 6;
    private ArrayList<Collectible> collectibles = new ArrayList<>();


    public int getReward() {
        return reward;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public void addToGameFlags(Flag flag) {
        this.flags.add(flag);
    }

    private void setFlagsRandomly(int mode) {
        flags = new ArrayList<>();
        Flag flag;
        Random random = new Random();
        Square[][] squares = land.getSquares();
        int randomX, randomY;
        if (mode == 2) {
            randomX = random.nextInt(4);
            randomY = random.nextInt(8);
            while (squares[randomX][randomY].getObject() != null) {
                randomX = random.nextInt(4);
                randomY = random.nextInt(8);
            }
            flag = new Flag(squares[randomX][randomY]);
            flags.add(flag);
            squares[randomX][randomY].setObject(flag);
            return;
        }
        for (int i = 0; i < numberOfFlags; i++) {
            randomX = random.nextInt(land.getNumberOfRows());
            randomY = random.nextInt(land.getNumberOfColumns());
            if (squares[randomX][randomY].getObject() != null) {
                i--;
                continue;
            }
            flag = new Flag(squares[randomX][randomY]);
            flags.add(flag);
            squares[randomX][randomY].setObject(flag);
        }
    }

    private void setCollectiblesRandomly() {
        Random random = new Random();
        int numberOfCollectiblesOnLand = random.nextInt(BOUND_FOR_COLLECTIBLES);
        int randomX, randomY, randomItem;
        ArrayList<Item> collectibles = new ArrayList<>(Shop.getInstance().getCollectibles());
        Square[][] squares = land.getSquares();
        for (int i = 0; i < numberOfCollectiblesOnLand; i++) {
            Collectible collectible;
            randomX = random.nextInt(4);
            randomY = random.nextInt(8);
            randomItem = random.nextInt(collectibles.size() - 1);
            if (squares[randomX][randomY].getObject() != null) {
                i--;
                continue;
            }
            collectible = (Collectible) collectibles.get(randomItem);
            Shop.getInstance().removeCollectible(collectible);
            this.collectibles.add(collectible);
            squares[randomX][randomY].setObject(collectible);

        }

        HashMap<String, Integer> collectibleNames = new HashMap<>();
        for (Item collectible : collectibles) {
            if (!collectibleNames.containsKey(collectible))
                collectibleNames.put(collectible.getName(), 0);
            int number = collectibleNames.get(collectible.getName()) + 1;
            collectibleNames.remove(collectible.getName());
            collectibleNames.put(collectible.getName(), number);
            new CollectibleId((Collectible) collectible, number);
        }
    }

    public Match(Player[] players, String mode, int numberOfFlags, int reward) {
        land = new LandOfGame();
        ArrayList<Card> cards = players[0].getMainDeck().getCardsOfDeck();
        Hero firstHero = players[0].getMainDeck().getHero();
        Hero secondHero = players[1].getMainDeck().getHero();
        players[0].setMatch(this);
        players[1].setMatch(this);
        for (Card card : cards) {
            card.setPlayer(players[0]);
            card.setLandOfGame(land);
        }
        firstHero.setPlayer(players[0]);
        firstHero.setLandOfGame(land);
        firstHero.setCanMove(true, 1);
        cards = players[1].getMainDeck().getCardsOfDeck();
        for (Card card : cards) {
            card.setPlayer(players[1]);
            card.setLandOfGame(land);
        }
        secondHero.setPlayer(players[1]);
        secondHero.setLandOfGame(land);
        secondHero.setCanMove(true, 1);
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);
        this.players = players;
        this.mode = mode;
        this.numberOfFlags = numberOfFlags;
        this.reward = reward;

        Square[][] square = land.getSquares();
        square[2][0].setObject(players[0].getMainDeck().getHero());
        firstHero.setPosition(square[2][0]);
        square[2][8].setObject(players[1].getMainDeck().getHero());
        secondHero.setPosition(square[2][8]);
        players[0].addToCardsOfLand(players[0].getMainDeck().getHero());
        players[1].addToCardsOfLand(players[1].getMainDeck().getHero());
        if (mode.equals(Game.getModeAsString(3))) {
            setFlagsRandomly(3);
        }
        if (mode.equals(Game.getModeAsString(2))) {
            setFlagsRandomly(2);
        }
        setCollectiblesRandomly();
        date = new Date();
        //set mana : meqdare avaliye mana baraye player inline behesh 2 dadam
        players[0].setMana(2);
        players[1].setMana(2);
        initGame();
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

    public void changeTurn() {//age bazi ba computer bashe turn avaz nemishe
        if (gameEnded()) {
            endGame();
            Controller.MenuController.state = StateType.ACCOUNT_MENU;
            return;
        }
        if (passComputerPlayer() == -1) {
            players[whichPlayer].initPerTurn();
            whichPlayer = 1 - whichPlayer;
        } else {
            players[whichPlayer].initPerTurn();
            players[passComputerPlayer()].playTurnForComputer();
            players[1 - whichPlayer].initPerTurn();//init for computer
            if (gameEnded()) {
                endGame();
                Controller.MenuController.state = StateType.ACCOUNT_MENU;
                return;
            }
        }
    }

//    public void startMatch() {
//        date = new Date();
//
//        initGame();
//        players[0].initPerTurn();
//        players[1].initPerTurn();
//
//        while (true) {
//            players[whichPlayer].playTurn();
//            if (gameEnded()) {
//                endGame();
//                break;
//            }
//            whichPlayer = 1 - whichPlayer;
//        }
//    }

    private int passComputerPlayer() {
        if (players[0] instanceof ComputerPlayer)
            return 0;
        if (players[1] instanceof ComputerPlayer)
            return 1;
        else
            return -1;
    }

    public void initGame() {
        players[0].addToCardsOfLand(players[0].getMainDeck().getHero());
        players[1].addToCardsOfLand(players[1].getMainDeck().getHero());
    }

    private boolean gameEnded() {
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
        winner.getAccount().changeValueOfDaric(reward);

        BattleView battleView = BattleView.getInstance();
        battleView.endGameView(this);
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

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public Player getLoser() {
        return loser;
    }

    public LandOfGame getLand() {
        return land;
    }

}
