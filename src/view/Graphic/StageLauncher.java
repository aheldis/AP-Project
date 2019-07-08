package view.Graphic;

import controller.Transmitter;
import controller.client.TransferController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.enums.Cursor;
import view.enums.StateType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static controller.RequestEnum.*;
import static view.Graphic.GeneralGraphicMethods.*;

public class StageLauncher extends Application {

    private static Stage primaryStage;
    private static HashMap<StateType, Scene> sceneHashMap = new HashMap<>();
    private static double HEIGHT;
    private static double WIDTH;
    private static Group requestGroup;

    public static double getWidth() {
        return WIDTH;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    public static void decorateScene(StateType stateType) {
        switch (stateType) {
            case MAIN_MENU:
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    primaryStage.setScene(StageLauncher.getScene(StateType.ACCOUNT_MENU));
                    playMusic("resource/music/main_menu.m4a",
                            true, StageLauncher.getScene(StateType.ACCOUNT_MENU));
                    primaryStage.show();
                });
                break;
            case SHOP:
                ShopScene.makeShopScene();
                setScene(StateType.SHOP);
                break;
            case COLLECTION:
                Transmitter transmitter = TransferController.main(GET_COLLECTION, new Transmitter());
                CollectionScene.showInCollection(transmitter.collection);
                setScene(StateType.COLLECTION);
                break;
            case SELECT_GAME:
                if (primaryStage.getScene() == StageLauncher.getScene(StateType.SELECT_GAME)) {
                    stopper = true;
                }
                Platform.runLater(() -> {
                    primaryStage.setScene(StageLauncher.getScene(StateType.SELECT_GAME));
                    primaryStage.show();
                });
                SelectGameScene.selectGame();
                break;
            case BATTLE:
                Platform.runLater(() -> {
                    primaryStage.setScene(StageLauncher.getScene(StateType.BATTLE));
                    GeneralGraphicMethods.playMusic("resource/music/battle_music/" +
                            BattleScene.getSingleInstance().getNumberOfMap() +
                            ".m4a", true, StageLauncher.getScene(StateType.BATTLE));
                    primaryStage.show();
                });
                break;

        }
    }

    static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Scene makeScene(StateType stateType, Cursor cursor) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        sceneHashMap.put(stateType, scene);
        GeneralGraphicMethods.setCursor(scene, cursor);
        return scene;
    }

    public static Scene getScene(StateType stateType) {
        if (sceneHashMap.containsKey(stateType))
            return sceneHashMap.get(stateType);
        return null;
        //return makeScene(stateType);
    }

    public static void getNewRequest(Transmitter transmitter) {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {

            Group group = new Group();
            group.relocate(WIDTH / 2 - 100, getHeight() / 2 - 100);
            String message = transmitter.message;
            try {
                addImage(group, "pics/battle/notification_challenge@2x.png", 0, 0, 200, 70);
                addText(group, 50, 30, message, Color.WHITE, 30);
                ImageView accept = addImage(group,
                        "pics/battle/collection_card_rarity_mythron@2x.png", 125, 70, 50, 50);
                ImageView decline = addImage(group,
                        "pics/battle/collection_card_rarity_legendary@2x.png", 25, 70, 50, 50);
                accept.setOnMouseClicked(event -> {
                    ((Group) primaryStage.getScene().getRoot()).getChildren().removeAll(group);
                    TransferController.main(ACCEPT_PLAY, new Transmitter());
                });
                decline.setOnMouseClicked(event -> {
                    ((Group) primaryStage.getScene().getRoot()).getChildren().removeAll(group);
                    TransferController.main(DECLINE_PLAY, new Transmitter());
                });
                ((Group) primaryStage.getScene().getRoot()).getChildren().addAll(group);
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestGroup = group;
        });
    }

    public static void deleteRequestGroup() {
        ((Group) primaryStage.getScene().getRoot()).getChildren().removeAll(requestGroup);
    }


    @Override
    public void start(Stage primaryStage) {

        StageLauncher.primaryStage = primaryStage;
        WIDTH = 1380;
        HEIGHT = 850;
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.setTitle("Duelyst");
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("pics/other/duelyst_icon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scene accountScene = makeScene(StateType.ACCOUNT_MENU, Cursor.AUTO);
        Scene mainMenuScene = makeScene(StateType.MAIN_MENU, Cursor.AUTO);
        Scene collectionScene = makeScene(StateType.COLLECTION, Cursor.AUTO);
        Scene selectModeScene = makeScene(StateType.SELECT_MODE, Cursor.GREEN);
        Scene selectGameScene = makeScene(StateType.SELECT_GAME, Cursor.GREEN);
        Scene battleScene = makeScene(StateType.BATTLE, Cursor.AUTO);
        Scene shopScene = makeScene(StateType.SHOP, Cursor.AUTO);
        Scene graveyardScene = makeScene(StateType.GRAVE_YARD, Cursor.RED);
        Scene profileScene = makeScene(StateType.PROFILE, Cursor.AUTO);
        Scene chatScene = makeScene(StateType.GLOBAL_CHAT, Cursor.GREEN);


        AccountScene.getInstance().makeBackground();
        primaryStage.setScene(accountScene);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("hi");
            TransferController.main(LOGOUT, new Transmitter());
            primaryStage.close();
        });


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
