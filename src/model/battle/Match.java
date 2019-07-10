package model.battle;

import controller.RequestEnum;
import controller.Transmitter;
import controller.client.TransferController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.account.Shop;
import model.card.Card;
import model.card.Hero;
import model.item.*;
import model.land.LandOfGame;
import model.land.Square;
import view.Graphic.*;
import view.enums.StateType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

import static view.Graphic.GeneralGraphicMethods.*;

public class Match {
    private Player[] players;
    private String mode;//DeathMode - SaveFlagMode - CollectFlagMode
    private int numberOfFlags;
    private ArrayList<Flag> flags = new ArrayList<>();
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
    public int numberOfMap;

    Match(Player[] players, String mode, int numberOfFlags, int reward) {
//        addToPausedGames();

        land = new LandOfGame();
        for (int i = 0; i < 2; i++) {
            if (players[i] instanceof OrdinaryPlayer)
                players[i].getAccount().setCurrentlyPlaying(true);
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

        Hero firstHero = players[0].getMainDeck().getHero();
        square[2][0].setObject(players[0].getMainDeck().getHero());
        firstHero.setPosition(square[2][0]);

        Hero secondHero = players[1].getMainDeck().getHero();
        square[2][8].setObject(players[1].getMainDeck().getHero());
        secondHero.setPosition(square[2][8]);

        date = new Date();

        initGame();
    }


    public void setBattleScene(BattleScene battleScene) {
        this.battleScene = battleScene;
    }

    private void initGame() {
        players[0].addToCardsOfLand(players[0].getMainDeck().getHero());
        players[1].addToCardsOfLand(players[1].getMainDeck().getHero());
    }

    public void addToPausedGames() {
        new Thread(() -> {
            try {
                File file = new File("PausedGames/NumberOfMap");
                Scanner fileReader = new Scanner(file);
                FileWriter fileWriter = new FileWriter(file, true);
                int line = 0;
                while (fileReader.hasNextLine()) {
                    fileReader.nextLine();
                    line++;
                }
                if (line != 0)
                    line--;//we add \n at each writing


                fileWriter.write(line + "\n");
                matchNumber = line;
                fileWriter.close();
                fileReader.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();

    }

    public void addToGameFlags(Flag flag) {
        this.flags.add(flag);
    }

    private void addCard() {
        Square[][] squares = land.getSquares();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (squares[i][j].getObject() instanceof Card) {
                    try {
                        BattleScene.getSingleInstance().addCardToBoard(i, j, (Card) squares[i][j].getObject(),
                                "Breathing", new ImageView(new Image(new
                                        FileInputStream(((Card) squares[i][j].getObject()).getPathOfAnimation()))),
                                true, true, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void initPause(boolean imPlayer0) {
        this.battleScene = BattleScene.getSingleInstance();
        addCard();
        initCheck(imPlayer0);
    }

    private void initCheck(boolean imPlayer0) {
        setFlagsGraphic();
        setCollectiblesGraphic();
        if (imPlayer0 && players[0].getMainDeck().getItem() != null) {
            Usable item = players[0].getMainDeck().getItem();
            battleScene.showAlert(item.getName() + ": " + item.getDescription());
        } else if (!imPlayer0 && players[1].getMainDeck().getItem() != null) {
            Usable item = players[1].getMainDeck().getItem();
            battleScene.showAlert(item.getName() + ": " + item.getDescription());
        }
    }

    public void initGraphic(boolean imPlayer0) {
        this.battleScene = BattleScene.getSingleInstance();
        Hero firstHero = players[0].getMainDeck().getHero();
        Hero secondHero = players[1].getMainDeck().getHero();
        battleScene.addCardToBoard(2, 0, firstHero, "Breathing",
                null, imPlayer0, false, false);
        battleScene.addCardToBoard(2, 8, secondHero, "Breathing",
                null, !imPlayer0, true, false);
        initCheck(imPlayer0);
    }


    public void setFlagsRandomly(int mode) {
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
                    || squares[randomX][randomY].getFlags().size() != 0) {

                randomX = random.nextInt(LandOfGame.getNumberOfRows());
                randomY = random.nextInt(LandOfGame.getNumberOfColumns());
            }
            flag = new Flag(squares[randomX][randomY]);
            flags.add(flag);
            squares[randomX][randomY].addToFlags(flag);
        }
    }

    private void setFlagsGraphic() {
        for (Flag flag : flags) {
            ImageView flagView = GeneralGraphicMethods.createImage("pics/battle_categorized/flag.gif", 10, 10);
            BattleScene.getSingleInstance().setImageViewForFlag(flag, flagView);
            BattleScene.getSingleInstance().addNodeToBoard(flag.getSquare().getXCoordinate(),
                    flag.getSquare().getYCoordinate(), flagView, false);
        }
    }

    public void setCollectiblesRandomly() {
        Random random = new Random();
        int numberOfCollectiblesOnLand = random.nextInt(BOUND_FOR_COLLECTIBLES);
        int randomX, randomY, randomItem;
        ArrayList<Item> collectibles = new ArrayList<>(Shop.getInstance().getCollectibles());
        Square[][] squares = land.getSquares();
        for (int i = 0; i < numberOfCollectiblesOnLand; i++) {
            Collectible collectible;
            randomX = random.nextInt(4);
            randomY = random.nextInt(8);
            if (collectibles.size() != 0) {
                randomItem = random.nextInt(collectibles.size());
                if (squares[randomX][randomY].getObject() != null || squares[randomX][randomY].getFlags().size() != 0) {
                    i--;
                    continue;
                }
                collectible = (Collectible) collectibles.get(randomItem);
                Shop.getInstance().removeCollectible(collectible);
                squares[randomX][randomY].setObject(collectible);
                collectible.setSquare(squares[randomX][randomY]);
                this.collectibles.add(collectible);
                System.out.println(collectible.getCollectibleId().getCollectibleIdAsString());
            }
        }

        HashMap<String, Integer> collectibleNames = new HashMap<>();
        for (Item collectible : collectibles) {
            if (!collectibleNames.containsKey(collectible.getName()))
                collectibleNames.put(collectible.getName(), 0);
            int number = collectibleNames.get(collectible.getName()) + 1;
            collectibleNames.remove(collectible.getName());
            collectibleNames.put(collectible.getName(), number);
            new CollectibleId((Collectible) collectible, number);
        }
    }


    private void setCollectiblesGraphic() {
        for (Collectible collectible : collectibles) {
            ImageView collectibleImage = GeneralGraphicMethods.createImage(
                    "pics/collectibles/" + collectible.getName() + ".png", 20, 20);
            BattleScene.getSingleInstance().addNodeToBoard(collectible.getSquare().getXCoordinate(),
                    collectible.getSquare().getYCoordinate(), collectibleImage, false);
            BattleScene.getSingleInstance().setImageViewForCollectible(collectible, collectibleImage);
        }
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

    public void changeTurn(boolean server) {//age bazi ba computer bashe turn avaz nemishe

        int computerPlayer = passComputerPlayer();

        if (gameEnded() && computerPlayer != -1) {
            endGame();
            controller.MenuController.state = StateType.ACCOUNT_MENU;
            return;
        }
        if (!server) {
            Transmitter transmitter = new Transmitter();
            TransferController.main(RequestEnum.CHANGE_TURN, transmitter);
            waitGraphic(computerPlayer);
        }
        players[whichPlayer].initPerTurn(whichPlayer, server);
        if (computerPlayer == -1)
            whichPlayer = 1 - whichPlayer;
        else if (!server) {
            players[passComputerPlayer()].playTurnForComputer();
            players[1 - whichPlayer].initPerTurn(1 - whichPlayer, false);//init for computer
        }

        if (!server)
            waitGraphic(computerPlayer);

    }

    public void waitGraphic(int computerPlayer) {

        DragAndDrop.setWait(true);
        if (BattleScene.getSingleInstance().getBattleHeader() != null)
            BattleScene.getSingleInstance().getBattleHeader().deactiveSpecialPower();
        //your turn notification
        Platform.runLater(() -> {
            BattleFooterGraphic battleFooterGraphic = battleScene.getBattleFooter();
            battleFooterGraphic.getEndTurnButton().setOpacity(0);
            Button endTurn = imageButton(battleFooterGraphic.getScene(), battleFooterGraphic.getCirclesGroup(),
                    "pics/battle/end_turn.png", "END TURN", 1000, 0, 200, 80);
            yourTurnAnimation(computerPlayer);
            BattleScene.getSingleInstance().addToWaitNodes(endTurn);
        });
    }

    public void yourTurnAnimation(int computerPlayer) {
        Group root = (Group) Objects.requireNonNull(StageLauncher.getScene(StateType.BATTLE)).getRoot();
        BattleFooterGraphic battleFooterGraphic = battleScene.getBattleFooter();
        ImageView image = GeneralGraphicMethods.addImage(root,
                "pics/battle/notification_go@2x.png", 300, 400 - 50, 800, 200);
        Text text = GeneralGraphicMethods.addText(root, 550, 460, "YOUR TURN",
                Color.rgb(225, 225, 225, 0.7), 60);
        root.getChildren().remove(image);
        root.getChildren().remove(text);

        long currentTime = System.currentTimeMillis();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if ((computerPlayer == -1 || System.currentTimeMillis() - currentTime >= 4000) &&
                        !root.getChildren().contains(image)) {
                    root.getChildren().addAll(image, text);
                }
                if ((computerPlayer != -1 && System.currentTimeMillis() - currentTime >= 5000) ||
                        (computerPlayer == -1 && System.currentTimeMillis() - currentTime >= 1000)) {
                    root.getChildren().removeAll(image, text);
                    battleFooterGraphic.getCirclesGroup().getChildren().removeAll(BattleScene.getSingleInstance().getWaitNodes());
                    battleFooterGraphic.getEndTurnButton().setOpacity(1);
                    DragAndDrop.setWait(false);
                    this.stop();
                }
            }
        };
        animationTimer.start();
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
        new Thread(() -> {

            MatchInfo matchInfo = new MatchInfo();
            for (int i = 0; i < 2; i++) {
                players[i].getAccount().setCurrentlyPlaying(false);
            }
            if (winner instanceof OrdinaryPlayer)
                matchInfo.winner = winner.getAccount().getUserName();
            else
                matchInfo.winner = "Computer";

            if (loser instanceof OrdinaryPlayer)
                matchInfo.loser = loser.getAccount().getUserName();
            else
                matchInfo.loser = "Computer";
            matchInfo.date = date;

            winner.endGame(matchInfo, reward);
            loser.endGame(matchInfo, 0);


        }).start();

        if (winner instanceof ComputerPlayer) {
            loss();
        } else {
            win();
        }

//        BattleView battleView = BattleView.getInstance();
//        battleView.endGameView(this);
    }

    public int passComputerPlayer() {
        if (players[0] instanceof ComputerPlayer)
            return 0;
        if (players[1] instanceof ComputerPlayer)
            return 1;
        else
            return -1;
    }

    private void setWinnerAndLoser(Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;
    }


    private static void loss() {
        setWinAndLossBackGround("resource/music/defeat.m4a",
                "pics/battle/general_f4@2x.png",
                "pics/battle/scene_diamonds_background_defeat@2x.png",
                "pics/battle/scene_diamonds_middleground_defeat@2x.png",
                "pics/battle/highlight_red.png",
                "DEFEAT");
    }

    private static void win() {
        setWinAndLossBackGround("resource/music/sfx_victory_match_w_vo.m4a",
                "pics/battle/general_f1@2x.png",
                "pics/battle/scene_diamonds_background_victory@2x.png",
                "pics/battle/scene_diamonds_middleground_victory@2x.png",
                "pics/battle/highlight_white.png",
                "VICTORY");
    }


    /**
     * Graphic:
     **/
    private static void setWinAndLossBackGround(String musicPath, String generalPath, String backGgoundPath,
                                                String middlegroundPath, String highlightPath, String label) {
        Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
        assert battleScene != null;
        Group root = (Group) battleScene.getRoot();
        root.getChildren().clear();
        setBackground(root,
                "pics/battle/back.png", true, 20, 20);

        playMusic(musicPath, false, battleScene);

        ImageView hero = addImage(root, generalPath, -200, -100, 1800, 1200);
        addImage(root, backGgoundPath, 0, 0,
                (int) StageLauncher.getWidth(), (int) StageLauncher.getHeight());
        addImage(root, backGgoundPath, 300, -300,
                1000, 1000);
        addImage(root, middlegroundPath,
                0, 0, StageLauncher.getWidth(), StageLauncher.getHeight());

        Text text = addText(root, 600, 100, label,
                Color.rgb(225, 225, 225, 0.8), 70);
        addImage(root, highlightPath, 300, -35, 800, 250);
        addText(root, 630, 130, "click on hero to continue", Color.WHITE, 20);
        Glow glow = new Glow();
        glow.setLevel(20);
        text.setEffect(glow);
        BattleScene.getSingleInstance().changeSingleInstance(null);
        hero.setOnMouseClicked(event -> StageLauncher.decorateScene(StateType.MAIN_MENU));
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

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

    public Player getLoser() {
        return loser;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public LandOfGame getLand() {
        return land;
    }


    public ArrayList<Collectible> getCollectibles() {
        return collectibles;
    }
}
