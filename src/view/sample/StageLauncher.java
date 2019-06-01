package view.sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.card.Card;
import model.card.Hero;
import view.CollectionScene;
import view.enums.StateType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StageLauncher extends Application {

    public static final int WIDTH =1500;
    public static final int HEIGHT = 900;
    public static Stage primaryStage;
    public static HashMap<StateType, Scene> sceneHashMap = new HashMap<>();

    private static Scene makeScene(StateType stateType) {
        Group root = new Group();
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        sceneHashMap.put(stateType, scene);
        return scene;
    }


    @Override
    public void start(Stage primaryStage) {
        StageLauncher.primaryStage = primaryStage;


        //make scene with type, can access root with (Group)scene.getRoot
        //all of the scene are in a hashMap with each state we can access to them
        Scene mainMenuScene = makeScene(StateType.MAIN_MENU);
        Scene collectionScene = makeScene(StateType.COLLECTION);

        final Random rng = new Random();
        VBox content = new VBox(5);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);

        ArrayList<Card> cards = new ArrayList<>();
        Hero hero = new Hero();
        hero.setPATH_OF_THE_PICTURE("D:\\project_Duelyst1\\pics\\collectionBackground.jpg");
        cards.add(hero);
        CollectionScene.showInCollection(cards);

        primaryStage.setScene(collectionScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
