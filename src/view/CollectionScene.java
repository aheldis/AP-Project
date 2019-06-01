package view;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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


    public static void showEachCard(Card card, HBox hBox, int i, int j) {
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            imageView.setOnMouseClicked(event -> {
                Text text = new Text("hi");
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.rgb(0, 0, 0, 0.6));
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                rectangle.relocate(X_BORDER * (i + 1) + i * CARD_WIDTH, Y_BORDER * (j + 1) + j * CARD_HEIGHT + 200);
                rectangle.setWidth(210);
                rectangle.setHeight(50);
                text.setFont(Font.font(20));
                text.setFill(Color.WHITE);
                text.relocate(X_BORDER * (i + 1) + i * CARD_WIDTH + 10, Y_BORDER * (j + 1) + j * CARD_HEIGHT + 200);
                root.getChildren().add(rectangle);
                root.getChildren().add(text);
                imageView.setOnMouseClicked(event1 -> {
                    root.getChildren().remove(rectangle);
                    root.getChildren().remove(text);
                });
            });

        } catch (Exception e) {

        }

    }

    public static void showInCollection(ArrayList<Card> cards) {
        root.getChildren().clear();

        ScrollPane scroller = new ScrollPane(root);
        //for background of collection

        try {
            Image image = new Image(new FileInputStream("pics/collectionBackground.jpg"));
            ImageView imageView = new ImageView(image);
//            layout.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT,
//                    BackgroundRepeat.REPEAT,
//                    BackgroundPosition.CENTER,
//                    new BackgroundSize(WIDTH,HEIGHT,true,true,true,true))));
            // scroller.setBackground(new Background(new BackgroundImage(image, null, null, null, null)));
            root.getChildren().add(imageView);
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
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        vBox.setSpacing(Y_BORDER);

        scroller.setFitToWidth(true);
        HBox hBox = new HBox();
        int j = -1;
        for (int i = 0; i < cards.size(); i++) {
            if (i % 5 == 0) {
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(X_BORDER);
                vBox.getChildren().add(hBox);
                j++;
            }
            showEachCard(cards.get(i), hBox, i % 5, j);
        }

        collectionScene.setRoot(scroller);


    }

}
