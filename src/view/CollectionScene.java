package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Box;
import model.card.Card;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static view.sample.StageLauncher.HEIGHT;
import static view.sample.StageLauncher.WIDTH;


public class CollectionScene {
    private static Scene collectionScene = StageLauncher.sceneHashMap.get(StateType.COLLECTION);
    private static Group root = (Group) collectionScene.getRoot();
    private static int CARD_HEIGHT = 320;
    private static int CARD_WIDTH = 245;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;


    public static void showEachCard(Card card, HBox hBox) {
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

        } catch (Exception e) {

        }

    }

    public static void showInCollection(ArrayList<Card> cards) {
        root.getChildren().clear();

        VBox vBox = new VBox();
        vBox.setSpacing(Y_BORDER);
        ScrollPane scroller = new ScrollPane(vBox);
        scroller.setFitToWidth(true);
        //for background of collection

        try {
            Image image = new Image(new FileInputStream("D:\\project_Duelyst1\\pics\\collectionBackground.jpg"));
            ImageView imageView = new ImageView(image);
//            vBox.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT,
//                    BackgroundRepeat.REPEAT,
//                    BackgroundPosition.CENTER,
//                    new BackgroundSize(WIDTH,HEIGHT,true,true,true,true))));

            imageView.relocate(0, 0);
            imageView.setFitHeight(HEIGHT);
            imageView.setFitWidth(WIDTH);
            BoxBlur boxblur = new BoxBlur();
            boxblur.setWidth(2.0f);
            boxblur.setHeight(3.0f);
            boxblur.setIterations(3);
            imageView.setEffect(boxblur);
            imageView.fitWidthProperty();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HBox hBox = new HBox();
        for (int i = 0; i < cards.size(); i++) {
            if (i % 5 == 0) {
                hBox = new HBox();
                hBox.setSpacing(X_BORDER);
                vBox.getChildren().add(hBox);
            }
            showEachCard(cards.get(i), hBox);
        }

        collectionScene.setRoot(scroller);

    }

}
