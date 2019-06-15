package model.battle;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
import view.Graphic.BattleScene;
import view.Graphic.GeneralGraphicMethods;
import view.Graphic.StageLauncher;
import view.enums.StateType;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

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
    private BattleScene battleScene;
    private int matchNumber = 0;

    BattleScene getBattleScene() {
        return battleScene;
    }


    public int getReward() {
        return reward;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public void addToGameFlags(Flag flag) {
        this.flags.add(flag);
    }

    private void setFlagsRandomly(int mode) {
        if (mode == 2)
            numberOfFlags = 1;
        flags = new ArrayList<>();
        Flag flag;
        Random random = new Random();
        Square[][] squares = land.getSquares();
        int randomX, randomY;
        for (int i = 0; i < numberOfFlags; i++) {
            randomX = random.nextInt(LandOfGame.getNumberOfRows());
            randomY = random.nextInt(LandOfGame.getNumberOfColumns());
            while (squares[randomX][randomY].getObject() != null
                    && squares[randomX][randomY].getFlags().size() != 0) {

                randomX = random.nextInt(LandOfGame.getNumberOfRows());
                randomY = random.nextInt(LandOfGame.getNumberOfColumns());
            }
            //todo aval bazi momkene chandta flag ro add kone to ye khoone. okeye; vali age nemikhaim to
            // oon while balaee e ino ezafe kon: || squares[randomX][randomY].getFlags().size() != 0
            flag = new Flag(squares[randomX][randomY]);
            flags.add(flag);
            squares[randomX][randomY].addToFlags(flag);
            ImageView flagView = GeneralGraphicMethods.createImage("pics/battle_categorized/flag.gif", 10, 10);
            flag.setImageView(flagView);
            BattleScene.getSingleInstance().addNodeToBoard(randomX, randomY, flagView);
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
            if (squares[randomX][randomY].getObject() != null || squares[randomX][randomY].getFlags().size() != 0) {
                i--;
                continue;
            }
            collectible = (Collectible) collectibles.get(randomItem);
            Shop.getInstance().removeCollectible(collectible);
            this.collectibles.add(collectible);
            squares[randomX][randomY].setObject(collectible);
            ImageView collectibleImage = GeneralGraphicMethods.createImage(
                    "pics/collectibles/" + collectible.getName() + ".png", 20, 20);
            BattleScene.getSingleInstance().addNodeToBoard(randomX, randomY, collectibleImage);
            collectible.setImageView(collectibleImage);

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

    public void initGraphic() {
        this.battleScene = BattleScene.getSingleInstance();
        Hero firstHero = players[0].getMainDeck().getHero();
        Hero secondHero = players[1].getMainDeck().getHero();
        battleScene.addCardToBoard(2, 0, firstHero, "Breathing", null, true, false);
        battleScene.addCardToBoard(2, 8, secondHero, "Breathing", null, false, true);
        if (mode.equals(Game.getModeAsString(3))) {
            setFlagsRandomly(3);
        }
        if (mode.equals(Game.getModeAsString(2))) {
            setFlagsRandomly(2);
        }
        setCollectiblesRandomly();
    }

    Match(Player[] players, String mode, int numberOfFlags, int reward) {
        new Thread(() -> {
            System.out.println("hi");
            try {
                File file = new File("PausedGames/NumberOfMap");
                Scanner fileReader = new Scanner(file);
                FileWriter fileWriter = new FileWriter(file, true);
                int line = 1;
                while (fileReader.hasNextLine()) {
                    fileReader.nextLine();
                    line++;
                }
                if (line != 1)
                    line--;//we add \n at each writing


                fileWriter.write(line + "\n");
                matchNumber = line;
                fileWriter.close();
                fileReader.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
        land = new LandOfGame();
        Hero firstHero = players[0].getMainDeck().getHero();
        Hero secondHero = players[1].getMainDeck().getHero();
        for (int i = 0; i < 2; i++) {
            ArrayList<Card> cards = players[i].getMainDeck().getCardsOfDeck();
            Hero hero = players[i].getMainDeck().getHero();
            players[i].setMatch(this);
            for (Card card : cards) {
                card.setPlayer(players[i]);
                card.setLandOfGame(land);
            }
            hero.setPlayer(players[0]);
            hero.setLandOfGame(land);
            hero.setCanMove(true, 1);
            hero.setCanAttack(true, 1);
            players[i].setOpponent(players[1 - i]);
            players[i].setMana(2);
        }

        this.players = players;
        this.mode = mode;
        this.numberOfFlags = numberOfFlags;
        this.reward = reward;

        Square[][] square = land.getSquares();

        square[2][0].setObject(players[0].getMainDeck().getHero());
        firstHero.setPosition(square[2][0]);

        square[2][8].setObject(players[1].getMainDeck().getHero());
        secondHero.setPosition(square[2][8]);

//        players[0].addToCardsOfLand(players[0].getMainDeck().getHero());
//        players[1].addToCardsOfLand(players[1].getMainDeck().getHero());

        date = new Date();
        //set mana : meqdare avaliye mana baraye player inline behesh 2 dadam

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
            controller.MenuController.state = StateType.ACCOUNT_MENU;
            return;
        }
        if (passComputerPlayer() == -1) {
            players[whichPlayer].initPerTurn(whichPlayer);
            whichPlayer = 1 - whichPlayer;
        } else {
            players[whichPlayer].initPerTurn(whichPlayer);
            players[passComputerPlayer()].playTurnForComputer();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Group root = (Group) StageLauncher.getScene(StateType.BATTLE).getRoot();
                    ImageView image = GeneralGraphicMethods.addImage(root,
                            "pics/battle/notification_go@2x.png", 300, 400 - 50, 800, 200);
                    Text text = GeneralGraphicMethods.addText(root, "YOUR TURN", 550, 460
                            , Color.rgb(225, 225, 225, 0.7), 60);
                    long currentTime = System.currentTimeMillis();

                    AnimationTimer animationTimer = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            if (System.currentTimeMillis() - currentTime >= 1000) {
                                root.getChildren().removeAll(text, image);
                                this.stop();
                            }
                        }
                    };
                    animationTimer.start();

                }
            });
        }

        players[1 - whichPlayer].initPerTurn(1 - whichPlayer);//init for computer
        if (gameEnded()) {
            endGame();
            controller.MenuController.state = StateType.ACCOUNT_MENU;
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

    private void initGame() {
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
                if (players[0].getNumberOfFlagsSaved() * 2 >= numberOfFlags) {
                    setWinnerAndLoser(players[0], players[1]);
                    return true;
                }
                if (players[1].getNumberOfFlagsSaved() * 2 >= numberOfFlags) {
                    setWinnerAndLoser(players[1], players[0]);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public void endGame() {
        MatchInfo matchInfo = new MatchInfo();
        if (this.winner instanceof OrdinaryPlayer)
            matchInfo.winner = this.winner.getAccount().getUserName();
        if (this.loser instanceof OrdinaryPlayer)
            matchInfo.loser = this.loser.getAccount().getUserName();
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
