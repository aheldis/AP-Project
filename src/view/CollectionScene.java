package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
import model.battle.Deck;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.StateType;
import view.sample.SpriteMaker;
import view.sample.StageLauncher;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.text.Format;
import java.util.ArrayList;
import java.util.Random;

import static view.sample.StageLauncher.*;


public class CollectionScene {
    private static Scene collectionScene = StageLauncher.sceneHashMap.get(StateType.COLLECTION);
    private static Group root = (Group) collectionScene.getRoot();
    private static int CARD_HEIGHT = 320;
    private static int CARD_WIDTH = 245;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;
    private static ArrayList<Text> texts = new ArrayList<>();


    private static void makeBackground(String path) {
        try {

            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);
            root.getChildren().add(imageView);
            imageView.relocate(0, 0);
            imageView.setFitHeight(HEIGHT);
            imageView.setFitWidth(WIDTH);
            BoxBlur boxblur = new BoxBlur();
            boxblur.setWidth(10.0f);
            boxblur.setHeight(10.0f);
            boxblur.setIterations(3);
            imageView.setEffect(boxblur);
            imageView.fitWidthProperty();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
                    Text ap = textView(i * (X_BORDER + CARD_WIDTH) - 15 + 50,
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

    private static void textForCollection(Card card, int i, int j, ImageView imageView) {
        Text ap = new Text(card.getAp() + "");
        ap.setFont(Font.font(20));
        ap.relocate(i * (X_BORDER + CARD_WIDTH) + 98, j * (Y_BORDER + CARD_HEIGHT) + 180);
        root.getChildren().add(ap);
        ap.setFill(Color.WHITE);

        Text hp = new Text(card.getHp() + "");
        hp.setFont(Font.font(20));
        hp.relocate(i * (X_BORDER + CARD_WIDTH) + 175 + 53, j * (Y_BORDER + CARD_HEIGHT) + 180);
        root.getChildren().add(hp);
        hp.setFill(Color.WHITE);


        try {
            imageView.setOnMouseEntered(event -> {

                Text desc = new Text(card.getDescription());
                desc.setFill(Color.WHITE);
                desc.setFont(Font.font(15));
                desc.relocate(i * (X_BORDER + CARD_WIDTH) + 100, j * (Y_BORDER + CARD_HEIGHT) + 285);


                ImageView descView = makeImage(i * (X_BORDER + CARD_WIDTH) + 70,
                        j * (Y_BORDER + CARD_HEIGHT) + 260, "pics/desc.png", 200, 100);
                root.getChildren().add(desc);

                imageView.setOnMouseExited(event1 -> root.getChildren().removeAll(desc, descView));
            });

        } catch (Exception e) {

        }


    }

    private static void showEachMinion(Card card, HBox hBox, int i, int j) {//todo add desc
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            Image animationImage = new Image(new FileInputStream(card.getPATH_OF_ANIMATION()));
            ImageView animationImageView = new ImageView(animationImage);
            animationImageView.relocate(i * (X_BORDER + CARD_WIDTH) + 75 + 50, j * (Y_BORDER + CARD_HEIGHT) + 20);
            animationImageView.setFitHeight(150);
            animationImageView.setFitWidth(110);
            animationImageView.fitWidthProperty();
            root.getChildren().add(animationImageView);

            textForCollection(card, i, j, imageView);


        } catch (Exception e) {

        }

    }

    private static void showEachSpell(Card card, HBox hBox, int i, int j) {
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            SpriteMaker.getInstance().makeSpritePic(card.getPATH_OF_ANIMATION(), i * (X_BORDER + CARD_WIDTH) + 150,
                    i * (Y_BORDER + CARD_HEIGHT) + 75,
                    root, card.getCountOfAnimation(), card.getAnimationRow(), 5000,
                    48, 48, 256);

            textForCollection(card, i, j, imageView);


        } catch (Exception e) {

        }
    }

    public static void showInCollection(ArrayList<Card> cards) {
        root.getChildren().clear();

        ScrollPane scroller = new ScrollPane(root);

        makeBackground("pics/collectionBackground.jpg");

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
                Text helper = new Text("hiii");
                helper.relocate(0, 0);
                helper.setFont(Font.font(5));
                helper.setFill(Color.TRANSPARENT);
                hBox.getChildren().add(helper);
                hBox.setSpacing(X_BORDER);
                vBox.getChildren().add(hBox);
                j++;
            }
            if (cards.get(i) instanceof Hero)
                showEachHero(cards.get(i), hBox, i % 5, j);
            if (cards.get(i) instanceof Minion)
                showEachMinion(cards.get(i), hBox, i % 5, j);
            if (cards.get(i) instanceof Spell)
                showEachSpell(cards.get(i), hBox, i % 5, j);
        }
        try {
            Image back = new Image(new FileInputStream("D:\\project_Duelyst1\\pics\\menu\\button_cancel.png"));
            ImageView backView = new ImageView(back);
            backView.relocate(500, 10);
            backView.setFitWidth(200);
            backView.setFitHeight(80);
            Group group = new Group();
            Text text = new Text("BACK");
            text.setFont(Font.font(30));
            text.setFill(Color.WHITE);
            group.getChildren().addAll(backView, text);
            text.relocate(567, 23);
            vBox.getChildren().add(group);
            group.setOnMouseClicked(event -> {
                //todo go to menu for haniyeh
            });


        } catch (Exception e) {

        }
        collectionScene.setRoot(scroller);


    }

//    private Text textmaker(String input, int x, int y, double fontSize){
//        Text text = new Text(input);
//        text.relocate(x,y);
//        text.setFont(Font.font(fontSize));
//        return text;
//    }

    /**
     * //todo code for image background
     * Image image = new Image(new FileInputStream("D:\\project_Duelyst1\\pics\\minion_background.png"));
     * vBox.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,
     * BackgroundRepeat.REPEAT,
     * BackgroundPosition.DEFAULT,
     * BackgroundSize.DEFAULT)));
     */

    private static void makeDeck(VBox vBox, int i, Deck deck) throws Exception {
        Random random = new Random();
        int a = random.nextInt(6);
        Image plate = new Image(new FileInputStream(
                "D:\\project_Duelyst1\\pics\\collection\\deck-select\\back-"+a+".png"));
        ImageView plateImageView = new ImageView(plate);
        plateImageView.setFitHeight(200);
        plateImageView.setFitWidth(230);
        plateImageView.relocate(350 + (300 * (i / 5.0)), 20 + (i % 5) * (150));
        root.getChildren().add(plateImageView);

        Text number = new Text((i + 1) + "");
        number.setFont(Font.font(30));
        number.setFill(Color.rgb(225, 225, 225, 0.5));
        number.relocate(350 + (300 * (i / 5.0)) + 20, 20 + (i % 5) * (150) + 120);
        root.getChildren().add(number);

        Text deckName = new Text(deck.getName());
        deckName.setFont(Font.font(40));
        deckName.setFill(Color.rgb(211, 100, 225, 0.5));
        deckName.relocate(350 + (300 * (i / 5.0)) + 80, 20 + (i % 5) * (150) + 120);
        root.getChildren().add(deckName);


        plateImageView.setOnMouseClicked(event -> {
            root.getChildren().removeAll(texts);
            vBox.getChildren().clear();
            Image deckImage,backPic;
            ImageView deckImageView,backPicView;
            try {
                HBox hBox = new HBox();
                deckImage = new Image(new FileInputStream(
                        "D:\\project_Duelyst1\\pics\\collection\\deck_ribbons\\ribbon-" + a + ".png"));
                deckImageView = new ImageView(deckImage);
                deckImageView.setFitHeight(150);
                deckImageView.setFitWidth(300);
                hBox.getChildren().add(deckImageView);
                vBox.getChildren().add(hBox);

                ArrayList<Card> cards = deck.getCardsOfDeck();
                for (int j = 0; j < cards.size(); j++) {
                    hBox = new HBox();
                    backPic = new Image(new FileInputStream(
                            "D:\\project_Duelyst1\\pics\\collection\\deck-select\\cardback-"+a+".jpg"));
                    backPicView = new ImageView(backPic);
                    backPicView.setFitHeight(70);
                    backPicView.setFitWidth(250);
                    hBox.getChildren().add(backPicView);

                    SpriteMaker.getInstance().makeSpritePic(cards.get(j).getPATH_OF_ANIMATION(),240,75*j+165,
                            hBox,cards.get(j).getCountOfAnimation(),cards.get(j).getAnimationRow(),4000,
                            cards.get(j).getFrameSize(),cards.get(j).getFrameSize(),256);

                    Text text = new Text(cards.get(j).getName()+"\n"+cards.get(j).getDescription());
                    text.relocate(10,75*j+165);
                    text.setFill(Color.rgb(200,200,225,0.5));
                    text.setFont(Font.font(20));
                    root.getChildren().add(text);
                    texts.add(text);

                    vBox.getChildren().add(hBox);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


    }

    public static void showDeck(ArrayList<Deck> decks) {
        collectionScene.setRoot(root);
        root.getChildren().clear();

        makeBackground("pics/collectionBackground.jpg");

        try {
            VBox vBox = new VBox();
            vBox.relocate(0, 0);
            vBox.setSpacing(5);
            vBox.setPrefSize(200, collectionScene.getHeight());
            vBox.setBackground(new Background(new BackgroundFill(Color.rgb(10, 10, 10, 0.5),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            for (int i = 0; i < decks.size(); i++) {
                try {
                    makeDeck(vBox, i, decks.get(i));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


            root.getChildren().addAll(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
