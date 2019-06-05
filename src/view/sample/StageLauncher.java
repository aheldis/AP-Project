package view.sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.account.Account;
import model.account.Collection;
import model.card.*;
import view.CollectionScene;
import view.GeneralGraphicMethods;
import view.enums.StateType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class StageLauncher extends Application {

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 900;
    private static Stage primaryStage;
    private static HashMap<StateType, Scene> sceneHashMap = new HashMap<>();
    private static final String CURSOR_PATH = "pics/cursor/mouse.png";

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Scene makeScene(StateType stateType) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        sceneHashMap.put(stateType, scene);

        GeneralGraphicMethods.setCursor(scene, CURSOR_PATH);

        return scene;
    }

    public static Scene getScene(StateType stateType) {
        if (sceneHashMap.containsKey(stateType))
            return sceneHashMap.get(stateType);
        return makeScene(stateType);
    }


    private void minionMaker(ArrayList<Card> cards, String path) {
        Minion minion = new Minion();
        minion.setPATH_OF_THE_PICTURE("pics/minion_background.png");
        minion.setPATH_OF_ANIMATION(path);
        minion.setHp(10);
        minion.setAp(10);
        cards.add(minion);
        minion.setDescription("i am minion");
    }

    //@Override
//    public void start(Stage primaryStage) throws Exception {
//        StageLauncher.primaryStage = primaryStage;
//        primaryStage.setScene(makeScene(StateType.BATTLE));
//        new BattleScene("pics/maps/abyssian/midground.png", WIDTH, HEIGHT);
//        primaryStage.show();
//    }


    @Override
    public void start(Stage primaryStage) {
        StageLauncher.primaryStage = primaryStage;

        //todo add "D:\\project_Duelyst1\\pics\\minion_background.png" to PATH_OF_THE_PICTURE of spell and ... to minion
        //todo add animation to  spell and minions

        //make scene with type, can access root with (Group)scene.getRoot
        //all of the scene are in a hashMap with each state we can access to them
        Scene mainMenuScene = makeScene(StateType.MAIN_MENU);
        Scene collectionScene = makeScene(StateType.COLLECTION);
//        File file = new File("D:\\project_Duelyst1\\src\\view\\style.css");
//        URL url = null;
//        try {
//            url = file.toURI().toURL();
//            collectionScene.getStylesheets().add(url.toExternalForm());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


        ArrayList<Card> cards = new ArrayList<>();
        Hero hero = new Hero();
        hero.setPATH_OF_THE_PICTURE("pics/default.png");
        hero.setHp(10);
        hero.setAp(10);
        hero.setDescription("hello girls");
//

        Spell spell = new Spell();
        spell.setPATH_OF_THE_PICTURE("pics/minion_background.png");
        spell.setPATH_OF_ANIMATION("pics/spell/fireBall.png");
        spell.setCountOfAnimation(16);
        spell.setFrameSize(48);


        cards.add(spell);

        minionMaker(cards, "pics/gifMinion/giv.gif");
        minionMaker(cards, "pics/gifMinion/gorg.gif");

        for (int i = 0; i < 6; i++)
            cards.add(hero);
        CollectionScene.showInCollection(cards, new Collection(new Account("zahra", "123")));

        //todo test for deck show inas
//        ArrayList<Deck> decks = new ArrayList<>();
//        Deck deck = new Deck();
//        spell.setDescription("atasssh");
//        spell.setName("atisih");
//        deck.getCardsOfDeck().add(spell);
//        deck.getCardsOfDeck().add(spell);
//        deck.setName("zahra");
//        for (int i = 0; i < 10; i++) {
//            decks.add(deck);
//        }
//        CollectionScene.showDeck(decks,new Collection(new Account("zahra","123")));


        try {
            collectionScene.setCursor(new ImageCursor(new Image(new FileInputStream("pics/cursor/mouse.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(collectionScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
