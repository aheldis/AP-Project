package view.Graphic;

import com.gilecode.yagson.YaGson;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.account.Account;
import model.battle.Deck;
import model.battle.Game;
import model.battle.Match;
import view.enums.StateType;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static view.Graphic.GeneralGraphicMethods.*;

class SelectGameScene {
    private static Scene selectGameScene = StageLauncher.getScene(StateType.SELECT_GAME);
    private static Group selectGameRoot = (Group) Objects.requireNonNull(selectGameScene).getRoot();
    private static Scene selectModeScene = StageLauncher.getScene(StateType.SELECT_MODE);
    private static Group selectModeRoot = (Group) Objects.requireNonNull(selectModeScene).getRoot();
    private static Account account;
    private static int numberOfDeck;
    private static ArrayList<Node> groupOfDeck = new ArrayList<>();
    private static Match match;
    private static Game game = new Game();
    private static String mode;
    private static String deckName;

    private static void changeScene() {
        Platform.runLater(() ->
                StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.SELECT_MODE)));
    }

    private static void makeDeck() {
        Group group;
        int column = -1;
        ArrayList<Deck> decks = account.getDecks();
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            if (i % 7 == 0) {
                column++;
            }
            if (deck.validate()) {
                group = new Group();
                group.relocate(210 * (column) + 10, 110 * (i % 7) + 10);
                addImage(group, "pics/battle/select_mode/notification_quest_small@2x.png"
                        , 0, 0, 200, 100);
                addText(group, 40, 50, decks.get(i).getName(),
                        Color.rgb(225, 225, 225, 0.5), 20);

                selectGameRoot.getChildren().addAll(group);

                group.setOnMouseClicked(event -> {
                    //playMusic("resource/music/choose_button.m4a",false,selectGameScene);
                    changeScene();
                    deckName = deck.getName();
                    mode = "custom";
                    selectMode();
                });
            }
        }

    }

    private static Text showDescForStoryGame(String input, int x) {
        Text text = addText(selectGameRoot, 600, x - 150, input,
                Color.rgb(225, 225, 225, 0.8), 30);
        text.setStroke(Color.rgb(0, 0, 0, 0.5));

        return text;
    }

    private static ImageView makeHeroPic(String path, int x, int y) {
        ImageView imageView = addImage(selectGameRoot,
                path, x, y, 500, 500);
        imageView.setOnMouseEntered(event -> {
            imageView.setScaleX(1.5);
            imageView.setScaleY(1.5);
        });
        imageView.setOnMouseExited(event -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);
        });
        return imageView;
    }

    static void selectGame(Account account) {
        SelectGameScene.account = account;
        if (!stopper) {
            playMusic("resource/music/select_mode.m4a", true, selectGameScene);
            stopper = false;
        }
        setBackground(selectGameRoot,
                "pics/battle/select_mode/background.jpg", true, 10.0f, 10.0f);

        Button button= imageButton(selectGameScene,selectGameRoot,"pics/battle/select_mode/multi_player.jpg",
                "hello",100, 100, 100, 100);
        selectGameRoot.getChildren().remove(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                        try {
                            InputStream input = new FileInputStream("D:/project_Duelyst1/PausedGames/1.json" );
                            Reader reader = new InputStreamReader(input);
                            YaGson mapper = new YaGson();
                            BattleScene battleScene = mapper.fromJson(reader, BattleScene.class);//load the deck
                            Platform.runLater(() ->
                                    StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.BATTLE)));

                            BattleScene.changeSingleInstance(battleScene);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
        });

        ImageView multiPlayerImage = addImage(selectGameRoot,
                "pics/battle/select_mode/multi_player.jpg", 300, 200, 250, 400);
        ImageView customImage = addImage(selectGameRoot,
                "pics/battle/select_mode/single_player_custom.jpg", 600, 200, 250, 400);
        ImageView storyImage = addImage(selectGameRoot,
                "pics/battle/select_mode/single_player_story.jpg", 900, 200, 250, 400);

        addImage(selectGameRoot,
                "pics/battle/select_mode/line.png", 340, 254, 200, 1);

        addImage(selectGameRoot,
                "pics/battle/select_mode/line.png", 640, 300, 200, 1);
        addImage(selectGameRoot,
                "pics/battle/select_mode/line.png", 940, 300, 200, 1);

        addImage(selectGameRoot,
                "pics/battle/select_mode/panel.png", 300, 580, 250, 50);
        addImage(selectGameRoot,
                "pics/battle/select_mode/panel.png", 600, 580, 250, 50);
        addImage(selectGameRoot,
                "pics/battle/select_mode/panel.png", 900, 580, 250, 50);

        multiPlayerImage.setOnMouseClicked(event -> {
            //playMusic("resource/music/choose_button.m4a",false,selectGameScene);
            changeScene();
            selectMode();
        });


        customImage.setOnMouseClicked(event -> {
            selectGameRoot.getChildren().clear();
            setBackground(selectGameRoot,
                    "pics/battle/select_mode/background.jpg", true, 10.0f, 10.0f);

            makeDeck();
            log(selectGameRoot, "select modes\nback", StateType.SELECT_GAME, 200);

        });

        storyImage.setOnMouseClicked(event -> {
            selectGameRoot.getChildren().clear();
            setBackground(selectGameRoot,
                    "pics/battle/select_mode/background.jpg", true, 20.0f, 20.0f);


            ImageView arash = makeHeroPic(
                    "pics/battle/select_mode/arash_mode.png", 60, 100);

            ImageView zahhak = makeHeroPic("pics/battle/select_mode/zahak_mode.png",
                    400, 131);
            ImageView diveSepid = makeHeroPic("pics/battle/select_mode/dive_sepid_mode.png",
                    800 - 30, 125);

            showDescForStoryGame("Save Flag", 740);
            showDescForStoryGame("Collect Flags", 410);
            showDescForStoryGame("Death Mode", 1120);

            Random random = new Random();

            diveSepid.setOnMouseClicked(event12 -> {//story game-1
                game = new Game();
                if (game.checkPlayerDeck(account, 1)) {

                    match = game.makeNewStoryGame(1);
                    startGame(game, match);
                }
                //game started
            });
            zahhak.setOnMouseClicked(event1 -> {//story game -2
                game = new Game();
                if (game.checkPlayerDeck(account, 1)) {


                    match = game.makeNewStoryGame(2);
                    startGame(game, match);
                }
                //game started
            });

            arash.setOnMouseClicked(event13 -> {//story game -3
                game = new Game();
                if (game.checkPlayerDeck(account, 1)) {

                    match = game.makeNewStoryGame(3);
                    startGame(game, match);
                }
            });
            //getNumberOfFlagPage(arash, selectGameRoot, selectGameScene);

            log(selectGameRoot, "select modes\nback", StateType.SELECT_GAME, 200);

        });

        Text multiPlayer = addText(selectGameRoot, 230, 350 - 5, "Multi Player",
                Color.rgb(0, 0, 0, 0.6), 30);
        multiPlayer.setStyle("-fx-font-weight: bold");

        Text customGame = addText(selectGameRoot, 230, 650 - 5, "Single Player\nCustom Game",
                Color.rgb(0, 0, 0, 0.6), 30);
        customGame.setStyle("-fx-font-weight: bold");

        Text storyGame = addText(selectGameRoot, 230, 950 - 5, "Single Player\nStory  Game"
                , Color.rgb(0, 0, 0, 0.6), 30);
        storyGame.setFont(Font.font("Lato-Light", FontWeight.BOLD, 30));

        log(selectGameRoot, "select modes\nback", StateType.MAIN_MENU, 200);

    }

    private static void selectMode() {

        playMusic("resource/music/shop.m4a", true, selectModeScene);

        setBackground(selectModeRoot,
                "pics/battle/select_mode/select_mode_background.jpg", true, 10.0f, 10.0f);


        ImageView collectFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/collect_flag.jpg", 300, 200, 250, 400);

        Text collectFlagText = addText(selectModeRoot, 220, 350, "Collect Flags",
                Color.rgb(5, 100, 225, 0.6), 30);
        collectFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        ImageView saveFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/save_flag_mode.jpg", 600, 200, 250, 400);
        Text saveFlagText = addText(selectModeRoot, 220, 650, "Save Flag",
                Color.rgb(25, 205, 225, 0.6), 30);
        saveFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        ImageView deathImage = addImage(selectModeRoot,
                "pics/battle/select_mode/Death_mode.jpg", 900, 200, 250, 400);
        Text deathModeText = addText(selectModeRoot, 220, 950, "Death Mode",
                Color.rgb(25, 205, 225, 0.6), 30);
        deathModeText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        Random random = new Random();

        getNumberOfFlagPage(collectFlagImage, selectModeRoot, selectModeScene);
        saveFlagImage.setOnMouseClicked(event -> {
            if (mode.equals("custom")) {

                game = new Game();
                if (game.checkPlayerDeck(account, 1)) {


                    match = game.makeNewCustomGame(account, deckName, 2, 0);
                    startGame(game, match);


                }
            }
        });

        deathImage.setOnMouseClicked(event -> {
            if (mode.equals("custom")) {
                game = new Game();
                if (game.checkPlayerDeck(account, 1)) {

                    match = game.makeNewCustomGame(account, deckName, 1, 0);
                    startGame(game, match);
                }
            }
        });

        log(selectModeRoot, "select mode\nback", StateType.SELECT_GAME, 200);

    }

    private static void getNumberOfFlagPage(ImageView imageView, Group root, Scene scene) {
        try {
            imageView.setOnMouseClicked(event -> {
                root.getChildren().clear();
                setBackground(root,
                        "pics/battle/select_mode/select_mode_background.jpg",
                        true, 10.0f, 10.0f);
                Text enterNumbersOfFlag = addText(root, 200, 500, "Enter Numbers Of Flags",
                        Color.rgb(0, 25, 225, 0.8), 40);
                enterNumbersOfFlag.setFont(Font.font("Luminari", 30));
                enterNumbersOfFlag.setStrokeWidth(1);
                enterNumbersOfFlag.setStroke(Color.rgb(0, 0, 0, 0.2));


                ImageView text = addImage(root, "pics/collection/card_silenced@2x.png",
                        600 - 5 - 20, 240, 200, 100);

                TextField number = new TextField();
                number.setPrefHeight(50);
                number.relocate(605, 255);
                number.positionCaret(1);
                number.setStyle("-fx-text-fill: #80ffff; -fx-font-size: 25px; -fx-font-weight: bold;");
                number.setFont(Font.font("Luminari", 30));
                number.setBackground(new Background(
                        new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                                CornerRadii.EMPTY, Insets.EMPTY)));
                root.getChildren().add(number);
                scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                    if (key.getCode() == KeyCode.ENTER) {
                        game = new Game();
                        if (game.checkPlayerDeck(account, 1)) {
                            if (number.getText().matches("\\d+")) {


                                match = game.makeNewCustomGame(account, deckName,
                                        3, Integer.parseInt(number.getText()));
                                startGame(game, match);
                            }
                        }
                    }
                });

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        log(selectGameRoot, "select modes\nback", StateType.SELECT_GAME, 200);

    }


    private static void startGame(Game game, Match match) {
        Random random = new Random();
        Platform.runLater(() ->{
                StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.BATTLE));
        BattleScene battleScene = BattleScene.getSingleInstance();
        battleScene.setGame(game);
        battleScene.setMatch(match);
        battleScene.setBattleScene(random.nextInt(12) + 1);
        match.initGraphic();
        });
    }
}

