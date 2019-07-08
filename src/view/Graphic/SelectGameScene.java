package view.Graphic;

import com.gilecode.yagson.YaGson;
import controller.RequestEnum;
import controller.Transmitter;
import controller.client.TransferController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
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

public class SelectGameScene {
    private static Scene selectGameScene = StageLauncher.getScene(StateType.SELECT_GAME);
    private static Group selectGameRoot = (Group) Objects.requireNonNull(selectGameScene).getRoot();
    private static Scene selectModeScene = StageLauncher.getScene(StateType.SELECT_MODE);
    private static Group selectModeRoot = (Group) Objects.requireNonNull(selectModeScene).getRoot();
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
        Transmitter transmitter = TransferController.main(RequestEnum.DECKS, new Transmitter());
        ArrayList<Deck> decks = transmitter.decks;
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
                addText(group, 50, 40, decks.get(i).getName(),
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

    private static void showDescForStoryGame(String input, int x) {
        Text text = addText(selectGameRoot, x - 150, 600, input,
                Color.rgb(225, 225, 225, 0.8), 30);
        text.setStroke(Color.rgb(0, 0, 0, 0.5));

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

    static void selectGame() {
        if (!stopper) {
            playMusic("resource/music/select_mode.m4a", true, selectGameScene);
            stopper = false;
        }
        setBackground(selectGameRoot,
                "pics/battle/select_mode/background.jpg", true, 10.0f, 10.0f);

        Button button = imageButton(selectGameScene, selectGameRoot, "pics/battle/select_mode/multi_player.jpg",
                "hello", 100, 100, 100, 100);
        selectGameRoot.getChildren().remove(button);
        //todo what is this?
        button.setOnMouseClicked(event -> {
            try {
                InputStream input = new FileInputStream("D:/project_Duelyst1/PausedGames/1.json");
                Reader reader = new InputStreamReader(input);
                YaGson mapper = new YaGson();
                BattleScene battleScene = mapper.fromJson(reader, BattleScene.class);//load the deck
                Platform.runLater(() ->
                        StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.BATTLE)));

                BattleScene.getSingleInstance().changeSingleInstance(battleScene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
            mode = "multiplayer";
            changeScene();
            selectMode();
        });


        customImage.setOnMouseClicked(event -> {
            selectGameRoot.getChildren().clear();
            setBackground(selectGameRoot,
                    "pics/battle/select_mode/background.jpg", true, 10.0f, 10.0f);

            mode = "custom";
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

            diveSepid.setOnMouseClicked(event12 -> {//story game-1
                startStoryGame(1);
                //game started
            });
            zahhak.setOnMouseClicked(event1 -> {//story game -2
                startStoryGame(2);
                //game started
            });

            arash.setOnMouseClicked(event13 -> {//story game -3
                startStoryGame(3);
            });
            //getNumberOfFlagPage(arash, selectGameRoot, selectGameScene);

            log(selectGameRoot, "select modes\nback", StateType.SELECT_GAME, 200);

        });

        Text multiPlayer = addText(selectGameRoot, 350 - 5, 230, "Multi Player",
                Color.rgb(0, 0, 0, 0.6), 30);
        multiPlayer.setStyle("-fx-font-weight: bold");

        Text customGame = addText(selectGameRoot, 650 - 5, 230, "Single Player\nCustom Game",
                Color.rgb(0, 0, 0, 0.6), 30);
        customGame.setStyle("-fx-font-weight: bold");

        Text storyGame = addText(selectGameRoot, 950 - 5, 230, "Single Player\nStory  Game"
                , Color.rgb(0, 0, 0, 0.6), 30);
        storyGame.setFont(Font.font("Lato-Light", FontWeight.BOLD, 30));

        log(selectGameRoot, "select modes\nback", StateType.MAIN_MENU, 200);

    }

    private static void startStoryGame(int level) {
        Transmitter transmitter = new Transmitter();
        transmitter.level = level;
        transmitter.playerNumber = 1;
        transmitter = TransferController.main(RequestEnum.START_STORY_GAME, transmitter);
        if (transmitter.errorType == null)
            startGame(transmitter.game, transmitter.match, 0, true);
    }

    private static void selectMode() {

        System.out.println("SelectGameScene.selectMode");

        playMusic("resource/music/shop.m4a", true, selectModeScene);

        setBackground(selectModeRoot,
                "pics/battle/select_mode/select_mode_background.jpg", true, 10.0f, 10.0f);


        ImageView collectFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/collect_flag.jpg", 300, 200, 250, 400);

        Text collectFlagText = addText(selectModeRoot, 350, 220, "Collect Flags",
                Color.rgb(5, 100, 225, 0.6), 30);
        collectFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        ImageView saveFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/save_flag_mode.jpg", 600, 200, 250, 400);
        Text saveFlagText = addText(selectModeRoot, 650, 220, "Save Flag",
                Color.rgb(25, 205, 225, 0.6), 30);
        saveFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        ImageView deathImage = addImage(selectModeRoot,
                "pics/battle/select_mode/Death_mode.jpg", 900, 200, 250, 400);
        Text deathModeText = addText(selectModeRoot, 950, 220, "Death Mode",
                Color.rgb(25, 205, 225, 0.6), 30);
        deathModeText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        TextField reward = new TextField();
        reward.setPrefHeight(50);
        reward.relocate(605, 100);
        textFieldProperties(reward, selectModeRoot);

        getNumberOfFlagPage(collectFlagImage, selectModeRoot, selectModeScene, reward);

        saveFlagImage.setOnMouseClicked(event -> {
            if (mode.equals("custom"))
                startCustomGame(2, 1);
            else {
                //multiplayer
                multiPlayerPage(selectModeRoot, selectModeScene, 2, 1, Integer.parseInt(reward.getText()));
            }

        });

        deathImage.setOnMouseClicked(event -> {
            if (mode.equals("custom"))
                startCustomGame(1, 0);
            else {
                //multiplayer
                multiPlayerPage(selectModeRoot, selectModeScene, 1, 0, Integer.parseInt(reward.getText()));
            }
        });

        log(selectModeRoot, "select mode\nback", StateType.SELECT_GAME, 200);

    }

    private static void textFieldProperties(TextField textField, Group root) {
        textField.positionCaret(1);
        textField.setStyle("-fx-text-fill: #80ffff; -fx-font-size: 25px; -fx-font-weight: bold;");
        textField.setFont(Font.font("Luminari", 30));
        textField.setBackground(new Background(
                new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                        CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(textField);
    }

    private static void startCustomGame(int mode, int numberOfFlag) {
        Transmitter transmitter = new Transmitter();
        transmitter.playerNumber = 1;
        transmitter.name = deckName;
        transmitter.mode = mode;
        transmitter.numberOfFlag = numberOfFlag;
        transmitter = TransferController.main(RequestEnum.START_CUSTOM_GAME, transmitter);
        if (transmitter.errorType == null)
            startGame(game, match, 0, true);
    }

    private static Group makeOpponent(String name, int mode, int numberOfFlags, int reward) {
        Group group = new Group();
        addImage(group, "pics/battle/diamond_main_menu_container@2x.png", 15, 0, 200, 50);
        ImageView imageView = addImage(group, "pics/battle/collection_card_rarity_common@2x.png",
                0, 0, 50, 50);
        addText(group, 40, 20, name, Color.WHITE, 20);
        ImageView glow = addImage(group, "pics/battle/collection_card_rarity_rare@2x.png",
                0, 0, 50, 50);
        group.getChildren().removeAll(glow);
        group.setOnMouseEntered(event -> {
            group.getChildren().removeAll(imageView);
            group.getChildren().addAll(glow);
            group.setOnMouseExited(event1 -> {
                group.getChildren().removeAll(glow);
                group.getChildren().addAll(imageView);
            });
        });
        group.setOnMouseClicked(event -> {
            Transmitter transmitter = new Transmitter();
            transmitter.name = name;
            transmitter.mode = mode;
            transmitter.numberOfFlag = numberOfFlags;
            transmitter.reward = reward;
            TransferController.main(RequestEnum.START_MATCH, transmitter);
        });
        return group;
    }

    public static void decline() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> StageLauncher.decorateScene(StateType.MAIN_MENU));
    }

    private static boolean valid(ArrayList<Account> accounts, int i) {
        if (accounts.get(i).getAuthToken() == null || accounts.get(i).getMainDeck() == null)
            return false;
        return accounts.get(i).getMainDeck().validate() && !accounts.get(i).isCurrentlyPlaying();
    }

    private static void multiPlayerPage(Group root, Scene scene, int mode, int numberOfFlags, int reward) {
        Transmitter transmitter = TransferController.main(RequestEnum.MAIN_DECK, new Transmitter());
        System.out.println("SelectGameScene.multiPlayerPage");
        if (transmitter.errorType == null) {
            root.getChildren().clear();
            setBackground(root, "pics/battle_categorized/multiplayer_backGround.jpg", true, 10.0f, 10.0f);
            addTextWithShadow(root, 100, 100, "Waiting for player...", "Andale Mono", 40);

            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.relocate(100, 150);
            root.getChildren().addAll(vBox);
            transmitter = TransferController.main(RequestEnum.ALL_ACCOUNT, new Transmitter());
            ArrayList<Account> accounts = transmitter.accounts;
            for (int i = 0; i < accounts.size(); i++) {
                if (valid(accounts, i))
                    vBox.getChildren().add(makeOpponent(accounts.get(i).getUserName(), mode, numberOfFlags, reward));
            }

            //todo -> bere be server request bede

            Button cancel = imageButton(scene, root, "pics/battle/help.png",
                    "CANCEL", 1000 + 90, 75, 150, 70);
            cancel.setOnMouseClicked(event -> {
                root.getChildren().clear();
                StageLauncher.decorateScene(StateType.SELECT_GAME);
                TransferController.main(RequestEnum.CANCEL_START_MATCH, new Transmitter());
            });
        }
    }

    private static void getNumberOfFlagPage(ImageView imageView, Group root, Scene scene, TextField reward) {

        try {

            imageView.setOnMouseClicked(event -> {

                root.getChildren().clear();
                setBackground(root,
                        "pics/battle/select_mode/select_mode_background.jpg",
                        true, 10.0f, 10.0f);
                Text enterNumbersOfFlag = addText(root, 500, 200, "Enter Numbers Of Flags",
                        Color.rgb(0, 25, 225, 0.8), 40);
                enterNumbersOfFlag.setFont(Font.font("Luminari", 30));
                enterNumbersOfFlag.setStrokeWidth(1);
                enterNumbersOfFlag.setStroke(Color.rgb(0, 0, 0, 0.2));


                addImage(root, "pics/collection/card_silenced@2x.png",
                        600 - 5 - 20, 240, 200, 100);

                TextField number = new TextField();
                number.setPrefHeight(50);
                number.relocate(605, 255);
                textFieldProperties(number, root);

                if (mode.equals("custom")) {
                    scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                        if (key.getCode() == KeyCode.ENTER)
                            startCustomGame(3, Integer.parseInt(number.getText()));
                    });
                } else {
                    //multiplayer
                    multiPlayerPage(selectModeRoot, selectModeScene, 3, Integer.parseInt(number.getText()), Integer.parseInt(reward.getText()));
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        log(selectGameRoot, "choose flag numbers\nback", StateType.SELECT_GAME, 200);

    }


    public static void startGame(Game game, Match match, int numberOfMap, boolean imPlayer0) {
        Random random = new Random();
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.BATTLE));
            BattleScene.setNewInstance();
            BattleScene battleScene = BattleScene.getSingleInstance();
            battleScene.setGame(game);
            battleScene.setMatch(match);
            if (numberOfMap != 0)
                battleScene.setBattleScene(numberOfMap);
            else
                battleScene.setBattleScene(random.nextInt(12) + 1);
            match.initGraphic(imPlayer0);
        });
    }
}

