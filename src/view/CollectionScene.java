package view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.account.Collection;
import model.battle.Deck;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.ErrorType;
import view.enums.StateType;
import view.sample.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static view.sample.StageLauncher.*;


public class CollectionScene {
    private static Scene collectionScene = StageLauncher.getScene(StateType.COLLECTION);
    private static Group root = (Group) collectionScene.getRoot();
    private static int CARD_HEIGHT = 320;
    private static int CARD_WIDTH = 245;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;
    private static List<Object> deletable = new ArrayList<>();


    private static void makeBackground(String path) {
        GeneralGraphicMethods.setBackground(root, path, true);/*
        try {

            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);
            root.getChildren().add(imageView);
            imageView.relocate(0, 0);
            imageView.setFitHeight(StageLauncher.getHEIGHT());
            imageView.setFitWidth(StageLauncher.getWIDTH());
            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(20.0f);
            boxBlur.setHeight(20.0f);
            boxBlur.setIterations(1);
            imageView.setEffect(boxBlur);
            imageView.fitWidthProperty();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
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
        } catch (Exception ignored) {

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
                            j * (Y_BORDER + CARD_HEIGHT) + 390, "pics/desc.png", 200, 100);
                    ImageView apView = makeImage(i * (X_BORDER + CARD_WIDTH) - 5,
                            j * (Y_BORDER + CARD_HEIGHT) + 390, "pics/ap_show.png", 100, 100);
                    ImageView hpView = makeImage((i + 1) * (X_BORDER + CARD_WIDTH) - 55,
                            j * (Y_BORDER + CARD_HEIGHT) + 390, "pics/hp_show.png", 110, 100);


                    Text hp = textView((i + 1) * (X_BORDER + CARD_WIDTH) - 10,
                            j * (Y_BORDER + CARD_HEIGHT) + 420, card.getHp() + "");
                    Text ap = textView(i * (X_BORDER + CARD_WIDTH) - 15 + 50,
                            j * (Y_BORDER + CARD_HEIGHT) + 420, card.getAp() + "");
                    Text desc = textView(i * (X_BORDER + CARD_WIDTH) + 100,
                            j * (Y_BORDER + CARD_HEIGHT) + 420, card.getDescription());

                    imageView.setOnMouseExited(event1 -> root.getChildren().removeAll(descView, hpView, apView, hp, ap, desc));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception ignored) { }

    }


    private static void textForCollection(Card card, int i, int j, ImageView imageView) {
        Text ap = new Text(card.getAp() + "");
        ap.setFont(Font.font(20));
        ap.relocate(i * (X_BORDER + CARD_WIDTH) + 98, j * (Y_BORDER + CARD_HEIGHT) + 310);
        root.getChildren().add(ap);
        ap.setFill(Color.WHITE);

        Text hp = new Text(card.getHp() + "");
        hp.setFont(Font.font(20));
        hp.relocate(i * (X_BORDER + CARD_WIDTH) + 175 + 53, j * (Y_BORDER + CARD_HEIGHT) + 310);
        root.getChildren().add(hp);
        hp.setFill(Color.WHITE);


        try {
            imageView.setOnMouseEntered(event -> {

                Text desc = new Text(card.getDescription());
                desc.setFill(Color.WHITE);
                desc.setFont(Font.font(15));
                desc.relocate(i * (X_BORDER + CARD_WIDTH) + 100, j * (Y_BORDER + CARD_HEIGHT) + 435);


                ImageView descView = makeImage(i * (X_BORDER + CARD_WIDTH) + 70,
                        j * (Y_BORDER + CARD_HEIGHT) + 400, "pics/desc.png", 200, 100);
                root.getChildren().add(desc);

                imageView.setOnMouseExited(event1 -> root.getChildren().removeAll(desc, descView));
            });

        } catch (Exception ignored) {

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
            animationImageView.relocate(i * (X_BORDER + CARD_WIDTH) + 75 + 50, j * (Y_BORDER + CARD_HEIGHT) + 120);
            animationImageView.setFitHeight(150);
            animationImageView.setFitWidth(110);
            animationImageView.fitWidthProperty();
            root.getChildren().add(animationImageView);

            textForCollection(card, i, j, imageView);


        } catch (Exception ignored) { }

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
                    i * (Y_BORDER + CARD_HEIGHT) + 200,
                    root, card.getCountOfAnimation(), card.getAnimationRow(), 5000,
                    48, 48, 256);

            textForCollection(card, i, j, imageView);
        } catch (Exception ignored) { }
    }

    public static void showInCollection(ArrayList<Card> cards,Collection collection) {
        root.getChildren().clear();

        ScrollPane scroller = new ScrollPane(root);

        makeBackground("pics/collectionBackground.jpg");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        vBox.setSpacing(Y_BORDER);

        try {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.TOP_RIGHT);
            hBox.setSpacing(1);

            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);

            Image searchBarImageView = new Image(new FileInputStream(
                    "pics/collection/searchbar.png"));

            ImageView magnifierView = new ImageView(new Image(new FileInputStream(
                    "pics\\collection\\magnifier_icon.png")));
            magnifierView.setFitHeight(40);
            magnifierView.setFitWidth(40);

            ImageView rightBar = new ImageView(new Image(new FileInputStream(
                    "pics\\collection\\left.png")));
            rightBar.setFitWidth(100);
            rightBar.setFitHeight(100);



            stackPane.getChildren().add(rightBar);
            stackPane.getChildren().add(magnifierView);

            TextField textArea = new TextField();
            textArea.setPrefHeight(100);
            textArea.relocate(10, 10);
            textArea.positionCaret(1);
            textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 25px; -fx-font-weight: bold;");
            //textArea.setStyle("-fx-text-inner-color: red;");
            textArea.setBackground(new Background(new BackgroundFill(Color.rgb(5, 5, 5, 0.1),
                    CornerRadii.EMPTY, Insets.EMPTY)));

            hBox.getChildren().addAll( textArea,stackPane);
            hBox.setBackground(new Background(new BackgroundFill(Color.rgb(10, 10, 10, 0.1),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            vBox.getChildren().add(hBox);

            magnifierView.setOnMouseClicked(event -> {
                System.out.println(textArea.getText());
                textArea.clear();
                if (!collection.searchCardName(textArea.getText())) {
                     ErrorType.HAVE_NOT_CARD_IN_COLLECTION.printMessage();
                     //todo show cards
                }
            });

            scroller.setFitToWidth(true);

            int j = -1;
            for (int i = 0; i < cards.size(); i++) {
                if (i % 5 == 0) {

                    hBox = new HBox();
                    //hBox.setAlignment(Pos.CENTER);
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
                Image back = new Image(new FileInputStream("pics/menu/button_cancel.png"));
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


            } catch (Exception ignored) { }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    private static void makeDeck(VBox vBox, int i, Deck deck, Collection collection) throws Exception {

        Random random = new Random();
        int a = random.nextInt(6);
        Image plate = new Image(new FileInputStream(
                "pics/collection/deck-select/back-" + a + ".png"));
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
            root.getChildren().removeAll(deletable);
            deletable.clear();
            vBox.getChildren().clear();
            ImageView deckImageView;
            try {
                HBox ribbonHBox = new HBox();
                deckImageView = new ImageView(new Image(new FileInputStream(
                        "pics/collection/deck_ribbons/ribbon-" + a + ".png")));
                deckImageView.setFitHeight(150);
                deckImageView.setFitWidth(324);
                ribbonHBox.getChildren().add(deckImageView);
                vBox.getChildren().add(ribbonHBox);

                final ImageView delete_deck = new ImageView(new Image(new FileInputStream(
                        "D:\\project_Duelyst1\\pics\\collection\\button_close@2x.png")));
                delete_deck.relocate(300, 10);
                delete_deck.setFitHeight(20);
                delete_deck.setFitWidth(20);
                root.getChildren().add(delete_deck);
                deletable.add(delete_deck);

                delete_deck.setOnMouseClicked(event14 -> {
                    try {
                        collection.deleteDeck(deck.getName());
                        System.out.println(deletable);
                        root.getChildren().removeAll(deletable);
                        deletable.clear();
                        vBox.getChildren().clear();

                    } catch (Exception ignored) { }

                });


                ArrayList<Card> cards = deck.getCardsOfDeck();


                for (Card card : cards) {
                    Group group = new Group();

                    ImageView backPicView = new ImageView(new Image(new FileInputStream(
                            "pics/collection/deck-select/deck_back.png")));
                    backPicView.setFitHeight(70);
                    backPicView.setFitWidth(300);
                    group.getChildren().add(backPicView);

                    SpriteMaker.getInstance().makeSpritePic(card.getPATH_OF_ANIMATION(), 0, 10,
                            group, card.getCountOfAnimation(), card.getAnimationRow(), 4000,
                            card.getFrameSize(), card.getFrameSize(), 256);

                    ImageView garbage = new ImageView(new Image(new FileInputStream(
                            "pics/collection/delete-button.png")));
                    garbage.setFitHeight(20);
                    garbage.setFitWidth(20);
                    garbage.relocate(230, 40);
                    group.getChildren().add(garbage);

                    vBox.getChildren().add(group);

                    garbage.setOnMouseClicked(event13 -> {
                        //collection.removeCardFromDeck(card,deck.getName());
                        root.getChildren().remove(group);
                        group.getChildren().clear();

                    });

                    Text text = new Text(card.getName() + "\n" + card.getDescription());
                    text.setY(25);
                    text.setX(70);
                    text.setFill(Color.rgb(200, 200, 225, 0.5));
                    text.setFont(Font.font(20));
                    text.setOnMouseEntered(event1 -> {
                        text.setUnderline(true);
                        text.setFill(Color.rgb(49, 255, 245, 0.5));
                    });
                    text.setOnMouseExited(event12 -> {
                        text.setUnderline(false);
                        text.setFill(Color.rgb(200, 200, 225, 0.5));
                    });
                    group.getChildren().add(text);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


    }

    public static void showDeck(ArrayList<Deck> decks, Collection collection) {
        collectionScene.setRoot(root);
        root.getChildren().clear();

        makeBackground("pics\\collection\\background@2x.jpg");

        try {
            VBox vBox = new VBox();

//            ScrollPane scroller = new ScrollPane(vBox);
//            scroller.setPrefViewportWidth(316);
//            scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//            scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            vBox.relocate(0, 0);
            vBox.setSpacing(5);
            vBox.setPrefSize(200, collectionScene.getHeight());
            vBox.setBackground(new Background(new BackgroundFill(Color.rgb(10, 10, 10, 0.5),
                    CornerRadii.EMPTY, Insets.EMPTY)));


            for (int i = 0; i < decks.size(); i++) {
                try {
                    makeDeck(vBox, i, decks.get(i), collection);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            ImageView add_deck = new ImageView(new Image(new FileInputStream(
                    "pics\\collection\\plate@2x.png")));
            add_deck.relocate(collectionScene.getWidth() - 110, collectionScene.getHeight() - 110);
            add_deck.setFitWidth(100);
            add_deck.setFitHeight(100);
            ImageView plus = new ImageView(new Image(new FileInputStream(
                    "pics\\collection\\add.png")));
            plus.relocate(collectionScene.getWidth() - 67, collectionScene.getHeight() - 67);
            plus.setFitHeight(15);
            plus.setFitWidth(15);
            root.getChildren().add(add_deck);
            root.getChildren().add(plus);
            //todo mizane ro plus ye kofti beshe

//            plus.setOnMouseClicked(event -> {
//                AnimationTimer animationTimer = new AnimationTimer() {
//                    @Override
//                    public void handle(long now) {
//                        for(int i=0;i<50;i++){
//                            plus.setFitWidth(plus.getFitWidth()+);
//                        }
//                    }
//                };
//                animationTimer.start();
//
//            });

            root.getChildren().add(vBox);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
