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
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.StateType;
import view.sample.SpriteMaker;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.Format;
import java.util.ArrayList;

import static view.sample.StageLauncher.*;


public class CollectionScene {
    private static Scene collectionScene = StageLauncher.sceneHashMap.get(StateType.COLLECTION);
    private static Group root = (Group) collectionScene.getRoot();
    private static int CARD_HEIGHT = 320;
    private static int CARD_WIDTH = 245;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;


    private static ImageView makeImage(int x, int y, String path, int width, int height) {
        try {
            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);
            imageView.relocate(x, y);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            root.getChildren().add(imageView);
            return imageView;
        } catch (Exception e) {

        }
        return null;
    }

    private static Text textView(int x, int y, String input) {
        Text text = new Text(input);
        text.setFont(Font.font(20));
        text.relocate(x, y);
        text.setFill(Color.WHITE);
        root.getChildren().add(text);
        return text;
    }

    private static void showEachHero(Card card, HBox hBox, int i, int j) {
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            imageView.setOnMouseEntered(event -> {

                try {
                    ImageView descView = makeImage(i * (X_BORDER + CARD_WIDTH) + 70,
                            j * (Y_BORDER + CARD_HEIGHT) + 260, "pics/desc.png", 200, 100);
                    ImageView apView = makeImage(i * (X_BORDER + CARD_WIDTH) - 5,
                            j * (Y_BORDER + CARD_HEIGHT) + 260, "pics/ap_show.png", 100, 100);
                    ImageView hpView = makeImage((i + 1) * (X_BORDER + CARD_WIDTH) - 55,
                            j * (Y_BORDER + CARD_HEIGHT) + 260, "pics/hp_show.png", 110, 100);



                    Text hp = textView((i + 1) * (X_BORDER + CARD_WIDTH) - 10,
                            j * (Y_BORDER + CARD_HEIGHT) + 295, card.getHp() + "");
                    Text ap = textView(i * (X_BORDER + CARD_WIDTH) - 15+50,
                            j * (Y_BORDER + CARD_HEIGHT) + 295, card.getAp() + "");
                    Text desc = textView(i * (X_BORDER + CARD_WIDTH) + 100,
                            j * (Y_BORDER + CARD_HEIGHT) + 280, card.getDescription());

                    imageView.setOnMouseExited(event1 -> {
                        root.getChildren().removeAll(descView, hpView, apView, hp, ap, desc);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {

        }

    }

    private static void textForCollection(Card card ,int i,int j,ImageView imageView){
        Text ap = new Text(card.getAp()+"");
        ap.setFont(Font.font(20));
        ap.relocate(i * (X_BORDER + CARD_WIDTH)+98,j * (Y_BORDER + CARD_HEIGHT)+180);
        root.getChildren().add(ap);
        ap.setFill(Color.WHITE);

        Text hp = new Text(card.getHp()+"");
        hp.setFont(Font.font(20));
        hp.relocate(i*(X_BORDER + CARD_WIDTH)+175+53,j * (Y_BORDER + CARD_HEIGHT)+180);
        root.getChildren().add(hp);
        hp.setFill(Color.WHITE);


        try{
            imageView.setOnMouseEntered(event -> {

                Text desc = new Text(card.getDescription());
                desc.setFill(Color.WHITE);
                desc.setFont(Font.font(15));
                desc.relocate(i * (X_BORDER + CARD_WIDTH)+100,j * (Y_BORDER + CARD_HEIGHT)+285);


                ImageView descView = makeImage(i * (X_BORDER + CARD_WIDTH) + 70,
                        j * (Y_BORDER + CARD_HEIGHT) + 260, "pics/desc.png", 200, 100);
                root.getChildren().add(desc);

                imageView.setOnMouseExited(event1 -> root.getChildren().removeAll(desc,descView));
            });

        }catch (Exception e){

        }


    }

    private static void showEachMinion(Card card, HBox hBox, int i,int j){//todo add desc
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            Image animationImage = new Image(new FileInputStream(card.getPATH_OF_ANIMATION()));
            ImageView animationImageView = new ImageView(animationImage);
            animationImageView.relocate(i * (X_BORDER + CARD_WIDTH)+75+50,j * (Y_BORDER + CARD_HEIGHT)+20);
            animationImageView.setFitHeight(150);
            animationImageView.setFitWidth(110);
            animationImageView.fitWidthProperty();
            root.getChildren().add(animationImageView);

            textForCollection(card,i,j,imageView);


        }catch (Exception e){

        }

    }

    private static void showEachSpell(Card card,HBox hBox,int i,int j){
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            SpriteMaker.getInstance().makeSpritePic(card.getPATH_OF_ANIMATION(),i * (X_BORDER + CARD_WIDTH)+150,
                    i * (Y_BORDER + CARD_HEIGHT)+75,
                    root,card.getCountOfAnimation(),card.getAnimationRow(),5000,
                    48,48,256);

            textForCollection(card,i,j,imageView);


        }catch (Exception e){

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
        //vBox.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().add(vBox);
        vBox.setSpacing(Y_BORDER);

        scroller.setFitToWidth(true);
        HBox hBox = new HBox();
        int j = -1;
        for (int i = 0; i < cards.size(); i++) {
            if (i % 5 == 0) {

                hBox = new HBox();
                Text helper =new Text("hiii");
                helper.relocate(0,0);
                helper.setFont(Font.font(5));
                helper.setFill(Color.TRANSPARENT);
                hBox.getChildren().add(helper);
                //hBox.setAlignment(Pos.CENTER);
               // hBox.setAlignment(Pos.BASELINE_RIGHT);
                hBox.setSpacing(X_BORDER);
                vBox.getChildren().add(hBox);
                j++;
            }
            if (cards.get(i) instanceof Hero)
                showEachHero(cards.get(i), hBox, i % 5, j);
            if(cards.get(i) instanceof Minion)
                showEachMinion(cards.get(i),hBox,i%5,j);
            if (cards.get(i) instanceof Spell)
                showEachSpell(cards.get(i),hBox,i%5,j);
        }

        collectionScene.setRoot(scroller);


    }

}
