package view.Graphic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.account.Account;
import model.card.Card;
import model.card.Minion;
import model.card.Spell;
import view.enums.Cursor;
import view.enums.StateType;

import javax.swing.plaf.ScrollBarUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static view.Graphic.GeneralGraphicMethods.*;

public class StageLauncher extends Application {
    private static Account account;

    public static void setAccount(Account account) {
        StageLauncher.account = account;
    }

    private static Stage primaryStage;
    private static HashMap<StateType, Scene> sceneHashMap = new HashMap<>();
    private static double HEIGHT;
    private static double WIDTH;

    static double getWidth() {
        return WIDTH;
    }

    static double getHeight() {
        return HEIGHT;
    }

    static void decorateScene(StateType stateType) {
        switch (stateType) {
            case MAIN_MENU:
                Platform.runLater(() -> {
                    primaryStage.setScene(StageLauncher.getScene(StateType.ACCOUNT_MENU));
                    playMusic("resource/music/main_menu.m4a",
                            true, StageLauncher.getScene(StateType.ACCOUNT_MENU));
                    primaryStage.show();
                });
                break;
            case SHOP:
                ShopScene.makeShopScene(account);
                setScene(StateType.SHOP);
                break;
            case COLLECTION:
                CollectionScene.showInCollection(account.getCollection());
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
                SelectGameScene.selectGame(account);
                break;
            case BATTLE:
                Platform.runLater(() -> {
                    primaryStage.setScene(StageLauncher.getScene(StateType.BATTLE));
                    GeneralGraphicMethods.playMusic("resource/music/battle_music/" +
                            BattleScene.getSingleInstance().getNumberOfMap() +
                            ".m4a", true,StageLauncher.getScene(StateType.BATTLE));
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
     //   Platform.runLater(() -> GeneralGraphicMethods.setCursor(scene, cursor));
        return scene;
    }

    public static Scene getScene(StateType stateType) {
        if (sceneHashMap.containsKey(stateType))
            return sceneHashMap.get(stateType);
        return null;
        //return makeScene(stateType);
    }


    private static void minionMaker(ArrayList<Card> cards, String path) {
        Minion minion = new Minion();
        minion.setPathOfThePicture("pics/other/minion_background.png");
        minion.setPathOfAnimation(path);
        minion.setHp(10);
        minion.setAp(10);
        cards.add(minion);
        minion.setDescription("i am minion");
    }
    public static void graveYardTest() {
        ArrayList<Card> cards = new ArrayList<>();

        minionMaker(cards, "pics/gifMinion/giv.gif");
        minionMaker(cards, "pics/gifMinion/giv.gif");

        Spell spell = new Spell();
        spell.setPathOfThePicture("pics/other/minion_background.png");
        spell.setPathOfAnimation("pics/Spell/fireBall.png");
        spell.setCountOfAnimation(16);
        spell.setName("Fireball");
        spell.setFrameSize(48);
        spell.setMp(10);
        spell.setHp(10);
        for (int i = 0; i < 13; i++)
            cards.add(spell);
        GraveYard.makeYard(cards);
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

        //todo add "D:\\project_Duelyst1\\pics\\minion_background.png" to PATH_OF_THE_PICTURE of Spell and ... to minion
        //todo add animation to  Spell and minions

/*/
        zahraTestDeck();
        getPrimaryStage().setScene(collectionScene);
//*/

//*/

//        primaryStage.setScene(selectGameScene);


/*

        BattleScene battleScene1 = BattleScene.getSingleInstance();
        battleScene1.setBattleScene(7); //from 1 to 12
        battleScene1.test();
        primaryStage.setScene(battleScene);
//*/


        AccountScene.getInstance().makeBackground();
        primaryStage.setScene(accountScene);
/*
        MainMenuScene mainMenuScene1 = MainMenuScene.getInstance();
        mainMenuScene1.makeMenu(new Account("2","2"));
        NewCardGraphic.makeCardForm(mainMenuScene, new Account("2", "2"));/*/
//*/
 //       AccountScene.getInstance().makeBackground();
 //       primaryStage.setScene(accountScene);


//       graveYardTest();
//        primaryStage.setScene(graveyardScene);


/*/
//        zahraTestDeck();
//        primaryStage.setScene(collectionScene);
//*/


//        zahraTestShop();
//        primaryStage.setScene(shopScene);
//        Raining.rain(shopScene);
//        uncomment init shop

//        collectionScene.setFill(Color.BLACK);
//        testzahraFooter((Group)collectionScene.getRoot());
//        primaryStage.setScene(collectionScene);
//        zahraTestDeck();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
