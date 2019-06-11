package view.Graphic;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
import view.enums.StateType;
import view.Graphic.StageLauncher;

import java.util.ArrayList;

import static view.Graphic.CollectionScene.*;
import static view.Graphic.GeneralGraphicMethods.*;

public class SelectGameScene {
    private static Scene selectGameScene = StageLauncher.getScene(StateType.SELECT_GAME);
    private static Group selectGameRoot = (Group) selectGameScene.getRoot();
    private static Scene selectModeScene = StageLauncher.getScene(StateType.SELECT_MODE);
    private static Group selectModeRoot = (Group) selectModeScene.getRoot();
    private static Account account;
    private static int numberOfDeck;
    private static ArrayList<Node> groupOfDeck = new ArrayList<>();

    private static void changeScene() {
        Platform.runLater(() ->
                StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.SELECT_MODE)));
    }

    private static void makeDeck() {
        Group group;
        int column = -1;
        ArrayList<Deck> decks = account.getDecks();
        for (int i = 0; i < decks.size(); i++) {
            if (i % 7 == 0) {
                column++;
            }
            if (decks.get(i).validate()) {
                group = new Group();
                group.relocate(210 * (column) + 10, 110 * (i % 7) + 10);
                addImage(group, "pics/battle/select_mode/notification_quest_small@2x.png", 0, 0, 200, 100);
                addText(group, decks.get(i).getName(), 50, 40,
                        Color.rgb(225, 225, 225, 0.5), 20);

                selectGameRoot.getChildren().addAll(group);

                group.setOnMouseClicked(event -> {
                    //playMusic("resource\\music\\choose_button.m4a",false,selectGameScene);
                    changeScene();
                    selectMode();
                });
            }
        }

    }

    public static void selectGame(Account account) {
        SelectGameScene.account = account;
        playMusic("resource/music/select_mode.m4a", true, selectGameScene);
        setBackground(selectGameRoot,
                "pics/battle/select_mode/background.jpg", true, 10.0f, 10.0f);

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
            changeScene();
            selectMode();
        });


        customImage.setOnMouseClicked(event -> {
            selectGameRoot.getChildren().clear();
            setBackground(selectGameRoot,
                    "pics/battle/select_mode/background.jpg", true, 10.0f, 10.0f);

            makeDeck();
            // changeScene();
            // selectMode();
        });

        storyImage.setOnMouseClicked(event -> {
            changeScene();
            selectMode();
        });

        Text multiPlayer = addText(selectGameRoot, "Multi Player",
                350 - 5, 230, Color.rgb(0, 0, 0, 0.6), 30);
        multiPlayer.setStyle("-fx-font-weight: bold");

        Text customGame = addText(selectGameRoot, "Single Player\nCustom Game",
                650 - 5, 230, Color.rgb(0, 0, 0, 0.6), 30);
        customGame.setStyle("-fx-font-weight: bold");

        Text storyGame = addText(selectGameRoot, "Single Player\nStory  Game"
                , 950 - 5, 230, Color.rgb(0, 0, 0, 0.6), 30);
        storyGame.setFont(Font.font("Lato-Light", FontWeight.BOLD, 30));

        log(selectGameRoot, "select modes\nback", StageLauncher.getScene(StateType.MAIN_MENU), 200);

    }

    public static void selectMode() {

//        {
//            int mode = 0;
//            int numberOfFLags = 0;
//            boolean valid = false;
//            String deckName = null;
//            String command;
//            menuView.showDecksAndModes(account);
//            do {
//
//                mode = Integer.parseInt(command.split(" ")[3]);
//                if (command.split(" ").length > 4)
//                    numberOfFLags = Integer.parseInt(command.split(" ")[4]);
//                if (mode != 0 && deckName != null)
//                    valid = true;
//
//            } while (!valid);
//            match = game.makeNewCustomGame(account, deckName, mode, numberOfFLags);
//            if (match == null) {
//                state = StateType.ACCOUNT_MENU;
//            }
//            state = StateType.BATTLE;
//            break;
//        }
        playMusic("resource/music/shop.m4a", true, selectModeScene);

        setBackground(selectModeRoot,
                "pics/battle/select_mode/select_mode_background.jpg", true, 10.0f, 10.0f);


        ImageView collectFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/collect_flag.jpg", 300, 200, 250, 400);

        Text collectFlagText = addText(selectModeRoot, "Collect Flags", 350, 220,
                Color.rgb(5, 100, 225, 0.6), 30);
        collectFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        ImageView saveFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/save_flag_mode.jpg", 600, 200, 250, 400);
        Text saveFlagText = addText(selectModeRoot, "Save Flag", 650, 220,
                Color.rgb(25, 205, 225, 0.6), 30);
        saveFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        ImageView deathImage = addImage(selectModeRoot,
                "pics/battle/select_mode/Death_mode.jpg", 900, 200, 250, 400);
        Text deathModeText = addText(selectModeRoot, "Death Mode", 950, 220,
                Color.rgb(25, 205, 225, 0.6), 30);
        deathModeText.setFont(Font.font("Lato-bold", FontWeight.BOLD, 30));

        collectFlagImage.setOnMouseClicked(event -> {
            selectModeRoot.getChildren().clear();
            setBackground(selectModeRoot,
                    "pics/battle/select_mode/select_mode_background.jpg",
                    true, 10.0f, 10.0f);
            Text enterNumbersOfFlag= addText(selectModeRoot,"Enter Numbers Of Flags",500,200,
                    Color.rgb(0,25,225,0.8),40);
            enterNumbersOfFlag.setFont(Font.font("Luminari",30));
            enterNumbersOfFlag.setStrokeWidth(1);
            enterNumbersOfFlag.setStroke(Color.rgb(0,0,0,0.2));



            ImageView text = addImage(selectModeRoot, "pics/collection/card_silenced@2x.png",
                    600-5-20,240, 200, 100);

            TextField deckName = new TextField();
            deckName.setPrefHeight(50);
            deckName.relocate(605, 255);
            deckName.positionCaret(1);
            deckName.setStyle("-fx-text-fill: #80ffff; -fx-font-size: 25px; -fx-font-weight: bold;");
            deckName.setFont(Font.font("Luminari",30));
            deckName.setBackground(new Background(
                    new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                            CornerRadii.EMPTY, Insets.EMPTY)));
            selectModeRoot.getChildren().add(deckName);

            //todo go to game ^__^
        });

        saveFlagImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        deathImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        log(selectModeRoot, "select mode\nback", selectGameScene, 200);

    }

}
