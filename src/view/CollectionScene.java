package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
import view.DragAndDropClass;
import view.sample.SpriteMaker;
import view.sample.StageLauncher;

import java.io.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static view.GeneralGraphicMethods.*;


public class CollectionScene {
    private static final Scene collectionScene = StageLauncher.getScene(StateType.COLLECTION);
    private static Group root = (Group) collectionScene.getRoot();
    private static int CARD_HEIGHT = 315;
    private static int CARD_WIDTH = 220;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;
    private static List<Node> deletable = new ArrayList<>();
    private static int pageNumber = 0;
    private static int pageNumberCards = 0;
    private static ArrayList<VBox> vBoxes = new ArrayList<>();
    private static ArrayList<Node> cardsIcon = new ArrayList<>();


    private static void deckCardMaker(Parent root, Card card, Group group) {


        try {
            ImageView backPicView = new ImageView(new Image(new FileInputStream(
                    "pics/collection/deck-select/deck_back.png")));
            backPicView.setFitHeight(70);
            backPicView.setFitWidth(300);
            group.getChildren().add(backPicView);

            SpriteMaker.getInstance().makeSpritePic(card.getPathOfAnimation(), 0, 10,
                    group, card.getCountOfAnimation(), card.getAnimationRow(), 4000,
                    card.getFrameSize(), card.getFrameSize(), 256);

            ImageView garbage = new ImageView(new Image(new FileInputStream(
                    "pics/collection/delete-button.png")));
            garbage.setFitHeight(20);
            garbage.setFitWidth(20);
            garbage.relocate(230, 40);
            group.getChildren().add(garbage);

            if (root instanceof VBox)
                ((VBox) root).getChildren().addAll(group);
            if (root instanceof Group)
                ((Group) root).getChildren().addAll(group);


            garbage.setOnMouseClicked(event16 -> {
                //todo az commenti to baziye asli dar biyad
                //collection.removeCardFromDeck(card,deck.getName());
                if (root instanceof VBox)
                    ((VBox) root).getChildren().removeAll(group);
                if (root instanceof Group)
                    ((Group) root).getChildren().removeAll(group);

                group.getChildren().clear();

            });

            Text text = addText(group, card.getName() + "\n" + card.getDescription(),
                    60, 10, Color.rgb(200, 200, 225, 0.5), 20);
            text.setOnMouseEntered(event1 -> {
                text.setUnderline(true);
                text.setFill(Color.rgb(49, 255, 245, 0.5));
            });
            text.setOnMouseExited(event12 -> {
                text.setUnderline(false);
                text.setFill(Color.rgb(200, 200, 225, 0.5));
            });
        } catch (Exception ignored) {

        }
    }

    static Group makeCardGroup(int x, int y, Object card) {
        Group group = new Group();
        group.relocate(x, y);
        if (card instanceof Card) {
            ImageView imageView = addImage(group, ((Card) card).getPathOfThePicture(),
                    0, 0, CARD_WIDTH, CARD_HEIGHT);

            showMana(group, 0, 0, ((Card) card).getMp());



            if(!(card instanceof Hero)) {
                addText(group, ((Card) card).getAp() + ""
                        , 50 - 10,
                        200 - 17, Color.WHITE, 20);

                addText(group, ((Card) card).getHp() + "",
                        180 - 20,
                        200 - 17, Color.WHITE, 20);
                SpriteMaker.getInstance().makeSpritePic(((Card) card).getPathOfAnimation(),
                        94,
                        58,
                        group, ((Card) card).getCountOfAnimation(),
                        ((Card) card).getAnimationRow(), 4000,
                        48, 48, 256);

            }
            try {
                imageView.setOnMouseEntered(event -> {
                    ImageView descView = addImage(group, "pics/desc.png", 10,
                            303 - 50, 200, 100);

                    Text desc = addText(group, ((Card) card).getDescription(),
                            40,
                            338 - 50, Color.WHITE, 15);
                    imageView.setOnMouseExited(event1 -> group.getChildren().removeAll(desc, descView));
                });

            } catch (Exception ignored) {

            }

        }
        return group;
    }

    public static void showEachHero(Card card, HBox hBox, int i, int j) {
        try {
            ImageView imageView = addImage(hBox, card.getPathOfThePicture(),
                    0, 0, CARD_WIDTH, CARD_HEIGHT);

            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 65, card.getMp());


            imageView.setOnMouseEntered(event -> {

                try {
                    ImageView descView = addImage(root, "pics/desc.png",
                            i * (X_BORDER + CARD_WIDTH) + 70,
                            j * (Y_BORDER + CARD_HEIGHT) + 390 - 55, 200, 100);
                    cardsIcon.add(descView);
                    ImageView apView = addImage(root, "pics/ap_show.png",
                            i * (X_BORDER + CARD_WIDTH) - 5,
                            j * (Y_BORDER + CARD_HEIGHT) + 390 - 55, 100, 100);
                    cardsIcon.add(apView);
                    ImageView hpView = addImage(root, "pics/hp_show.png",
                            (i + 1) * (X_BORDER + CARD_WIDTH) - 55,
                            j * (Y_BORDER + CARD_HEIGHT) + 390 - 55, 110, 100);
                    cardsIcon.add(hpView);

                    Text hp = addText(root, card.getHp() + "", (i + 1) * (X_BORDER + CARD_WIDTH) - 10,
                            j * (Y_BORDER + CARD_HEIGHT) + 430 - 55, Color.WHITE, 20);
                    cardsIcon.add(hp);
                    Text ap = addText(root, card.getAp() + "", i * (X_BORDER + CARD_WIDTH) - 15 + 50,
                            j * (Y_BORDER + CARD_HEIGHT) + 430 - 55, Color.WHITE, 20);
                    cardsIcon.add(ap);
                    Text desc = addText(root, card.getDescription(), i * (X_BORDER + CARD_WIDTH) + 100,
                            j * (Y_BORDER + CARD_HEIGHT) + 430 - 55, Color.WHITE, 20);
                    cardsIcon.add(desc);


                    imageView.setOnMouseExited(event1 ->
                            root.getChildren().removeAll(descView, hpView, apView, hp, ap, desc));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception ignored) {
        }

    }

    private static void showMana(Group root, int x, int y, int mana) {
        cardsIcon.add(addImage(root, "pics/icon_mana@2x.png", x, y, 50, 50));
        cardsIcon.add(addText(root, mana + "", x + 16, y + 20,
                Color.rgb(22, 22, 225, 0.5), 20));
    }

    private static void textForCollection(Card card, int i, int j, ImageView imageView) {
        cardsIcon.add(addText(root, card.getAp() + "",
                i * (X_BORDER + CARD_WIDTH) + 90,
                j * (Y_BORDER + CARD_HEIGHT) + 320 - 60, Color.WHITE, 20));

        cardsIcon.add(addText(root, card.getHp() + "",
                i * (X_BORDER + CARD_WIDTH) + 175 + 33,
                j * (Y_BORDER + CARD_HEIGHT) + 320 - 60, Color.WHITE, 20));

        try {
            imageView.setOnMouseEntered(event -> {

                ImageView descView = addImage(root, "pics/desc.png", i * (X_BORDER + CARD_WIDTH) + 70,
                        j * (Y_BORDER + CARD_HEIGHT) + 400 - 65, 200, 100);
                cardsIcon.add(descView);
                Text desc = addText(root, card.getDescription(),
                        i * (X_BORDER + CARD_WIDTH) + 100,
                        j * (Y_BORDER + CARD_HEIGHT) + 435 - 65, Color.WHITE, 15);
                cardsIcon.add(desc);
                imageView.setOnMouseExited(event1 -> root.getChildren().removeAll(desc, descView));
            });

        } catch (Exception ignored) {

        }


    }

    public static void showEachMinion(Card card, HBox hBox, int i, int j) {//todo add desc
        try {
            ImageView imageView = addImage(hBox,
                    card.getPathOfThePicture(), 0, 0, CARD_WIDTH, CARD_HEIGHT);
            imageView.fitWidthProperty();

            ImageView animationImageView = addImage(root, card.getPathOfAnimation(),
                    i * (X_BORDER + CARD_WIDTH) + 75 + 37,
                    j * (Y_BORDER + CARD_HEIGHT) + 120 - 55, 110, 150);
            cardsIcon.add(animationImageView);
            animationImageView.fitWidthProperty();

            textForCollection(card, i, j, imageView);
            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 130 - 65, card.getMp());

        } catch (Exception ignored) {
        }

    }

    public static void showEachSpell(Card card, HBox hBox, int i, int j) {
        try {
            ImageView imageView = addImage(hBox, card.getPathOfThePicture(), 0, 0, CARD_WIDTH, CARD_HEIGHT);
            imageView.fitWidthProperty();

            cardsIcon.add(SpriteMaker.getInstance().makeSpritePic(card.getPathOfAnimation(),
                    i * (X_BORDER + CARD_WIDTH) + 140,
                    i * (Y_BORDER + CARD_HEIGHT) + 200 - 55,
                    root, card.getCountOfAnimation(),
                    card.getAnimationRow(), 4000,
                    48, 48, 256));

            textForCollection(card, i, j, imageView);

            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 130 - 65, card.getMp());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hBoxCardMaker(VBox vBox, int pageNumber, int NUMBER_IN_EACH_ROW, ArrayList<Card> cards, int spacing) {
        HBox hBox = new HBox();
        int startingBound = 2 * NUMBER_IN_EACH_ROW * pageNumber;
        int j = -1;
        for (int i = startingBound; i < startingBound + 2 * NUMBER_IN_EACH_ROW; i++) {
            if (i >= cards.size())
                break;
            if (i % NUMBER_IN_EACH_ROW == 0) {
                hBox = new HBox();
                vBox.getChildren().removeAll(cardsIcon);
                hBox.relocate(350,100+(j)*335);
                hBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(hBox);
                cardsIcon.add(hBox);
                hBox.setSpacing(spacing);
                j++;
            }
            Group group = makeCardGroup(0,0,cards.get(i));
            hBox.getChildren().addAll(group);

        }
    }

    public static void searchBar(HBox hBox,VBox vBox,Collection collection){
        Group groupText = new Group();
        groupText.relocate(300, 20);

        addRectangle(groupText, 0, 25, 400, 40, 50, 50
                , Color.rgb(0, 0, 0, 0.7));

        TextField textArea = new TextField();
        textArea.relocate(3, 5);
        textArea.positionCaret(1);
        textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 15px; -fx-font-weight: bold;");
        textArea.setBackground(new Background(new BackgroundFill(
                Color.rgb(5, 5, 5, 0.0001),
                CornerRadii.EMPTY, Insets.EMPTY)));
        groupText.getChildren().add(textArea);

        hBox.getChildren().addAll(groupText);
        hBox.relocate(600, 200);
        Group group1 = new Group();
        addRectangle(group1, 0, 20, 50, 40, 60, 60
                , Color.rgb(0, 0, 0, 0.7));

        ImageView magnifier = addImage(group1,
                "pics/collection/magnifier_icon.png", 16, 13, 20, 20);
        hBox.getChildren().addAll(group1);

        vBox.getChildren().add(hBox);

        group1.setOnMouseClicked(event -> {
            ArrayList<String> ids = new ArrayList<>();
            ids.addAll(collection.searchCardName(textArea.getText()));
            ids.addAll(collection.searchItemName(textArea.getText()));
            textArea.clear();
            if (ids.size() != 0) {
                Group group = new Group();
                group.relocate(440, 150);

                ImageView background = setBackground(root, "pics/collectionBackground.jpg",
                        true, 20, 20);
                root.getChildren().addAll(group);

                addRectangle(group, 0, 0, 400, 400
                        , 50, 50, Color.rgb(0, 0, 0, 0.9));
                ImageView close = addImage(group,
                        "pics/collection/button_close@2x.png", 350, 0, 50, 50);
                VBox vBox1 = new VBox();
                vBox1.relocate(30, 30);
                vBox1.setSpacing(5);
                group.getChildren().addAll(vBox1);
                for (int i = 0; i < ids.size(); i++) {
                    addText(vBox1, ids.get(i), 0,
                            0, Color.rgb(225, 225, 225), 20);

                }

                close.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().removeAll(background, group);
                    }
                });
            } else {
                ErrorType.HAVE_NOT_CARD_IN_COLLECTION.printMessage();
            }
        });
    }

    public static void showInCollection(ArrayList<Card> cards, Collection collection) {
        root.getChildren().clear();

        playMusic("resource/music/collection.m4a",true,collectionScene);

        setBackground(root, "pics/collectionBackground.jpg", true, 20, 20);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        vBox.setSpacing(Y_BORDER);
//todo ooo
        HBox goToCollection = new HBox();
        Text text = new Text("hi");
        goToCollection.setPrefHeight(20);
        text.setFont(Font.font(10));
        goToCollection.getChildren().addAll(text);
        //vBox.getChildren().addAll(goToCollection);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(1);
        hBox.setPrefWidth(StageLauncher.getWidth());


        searchBar(hBox,vBox,collection);

        ImageView backCircle = addImage(root, "pics/circle.png", 100, 750, 70, 70);
        ImageView back = addImage(root, "pics/back.png", 115, 765, 40, 40);

        ImageView nextCircle = addImage(root, "pics/circle.png", 1200, 750, 70, 70);
        ImageView next = addImage(root, "pics/next.png", 1215, 765, 40, 40);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageNumberCards--;
                if (pageNumberCards < 0)
                    pageNumberCards = 0;
                vBox.getChildren().removeAll(cardsIcon);
                root.getChildren().removeAll(cardsIcon);
                cardsIcon.clear();
                hBoxCardMaker(vBox, pageNumberCards, 5, cards, 10);
            }
        });
        next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageNumberCards++;
                vBox.getChildren().removeAll(cardsIcon);
                root.getChildren().removeAll(cardsIcon);
                cardsIcon.clear();
                hBoxCardMaker(vBox,pageNumberCards,5,cards,10);
            }
        });

        int j = -1;
        for(int i=0;i<10;i++){
            if(i>=cards.size()) {
                break;
            }
            hBoxCardMaker(vBox,pageNumberCards,5,cards,10);
        }
//        try {
////            StackPane group = new StackPane();
////            addImage(group, "pics/menu/button_cancel.png",
////                    500, 10, 200, 80);
////
////            addText(group, "BACK", 567, 40, Color.WHITE, 30);
////            vBox.getChildren().add(group);
////            group.setOnMouseClicked(event -> {
////                //todo go to menu for haniyeh
////            });
//
//
//        } catch (Exception ignored) {
//        }

    }

    /**
     * //todo code for image background
     * Image image = new Image(new FileInputStream("D:\\project_Duelyst1\\pics\\minion_background.png"));
     * vBox.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,
     * BackgroundRepeat.REPEAT,
     * BackgroundPosition.DEFAULT,
     * BackgroundSize.DEFAULT)));
     */

    private static ImageView makeIConBarForDeck(String path, int x, int y) {
        ImageView deck = null;
        try {
            deck = new ImageView(new Image(new FileInputStream(path)));
            deck.relocate(x, y);
            deck.setFitHeight(20);
            deck.setFitWidth(20);
            root.getChildren().add(deck);
            deletable.add(deck);
        } catch (Exception e) {

        }
        return deck;
    }

    private static ArrayList<VBox> dragAndDropCard(Collection collection, int pageNumber, VBox target, Deck deck) {
        int NUMBER_IN_EACH_ROW = 5;
        ArrayList<VBox> vBoxes = new ArrayList<>();
        int spacing = 13;
        ArrayList<Card> cards =
                collection.getAllCards();
        VBox vBox = new VBox();
        int startingBound = 2 * NUMBER_IN_EACH_ROW * pageNumber;
        Group group;
        for (int i = startingBound; i < startingBound + 2 * NUMBER_IN_EACH_ROW; i++) {
            if (i >= cards.size())
                break;
            if (i % NUMBER_IN_EACH_ROW == 0) {
                vBox = new VBox();
                vBox.relocate(370 * ((i / (float) NUMBER_IN_EACH_ROW) % 2 + 1), 160);
                vBox.setSpacing(spacing);
                root.getChildren().addAll(vBox);
                vBoxes.add(vBox);
            }
            group = new Group();

            deckCardMaker(vBox, cards.get(i), group);
            System.out.println(group);

            DragAndDropClass.dragAndDrop(group, target, deck, cards.get(i));
        }
        return vBoxes;
    }

    private static void makeDeck(VBox vBox, int i, Deck deck, Collection collection) {
        Random random = new Random();
        int a = random.nextInt(6);
        ImageView plateImageView = addImage(root, "pics/collection/deck-select/back-" + a + ".png",
                350 + (300 * (i / 5.0)), 10 + (i % 5) * (150), 230, 200);

        addText(root, (i + 1) + "", 350 + (300 * (i / 5.0)) + 20,
                10 + (i % 5) * (150) + 150,
                Color.rgb(225, 225, 225, 0.5), 30);

        addText(root, deck.getName(), 350 + (300 * (i / 5.0)) + 80, 10 + (i % 5) * (150) + 150,
                Color.rgb(211, 100, 225, 0.5), 40);


        plateImageView.setOnMouseClicked(event -> {
            root.getChildren().removeAll(deletable);
            deletable.clear();
            vBox.getChildren().clear();
            addImage(vBox,
                    "pics/collection/deck_ribbons/ribbon-" + a + ".png", 0, 0,
                    324, 150);

            final ImageView addCardToDeck = makeIConBarForDeck(
                    "pics/collection/add-blue.png", 40, 10);

            final ImageView delete_deck = makeIConBarForDeck(
                    "pics/collection/close-deck.png", 70, 10);

            final ImageView checkMainDeck;
            if (deck.getName().equals(collection.getAccount().getMainDeck().getName())) {
                checkMainDeck = makeIConBarForDeck(
                        "pics/collection/checkmark-green.png", 10, 10);
            } else {
                checkMainDeck = makeIConBarForDeck(
                        "pics/collection/checkmark-blue.png", 10, 10);
            }


            addCardToDeck.setOnMouseEntered(event15 -> {

                System.out.println("hi");
                Rectangle rectangle = new Rectangle(350, 50, 800, 750);
                rectangle.setFill(Color.rgb(0, 0, 0, 0.7));
                rectangle.setArcHeight(50);
                rectangle.setArcWidth(50);
                root.getChildren().add(rectangle);


                vBoxes.addAll(dragAndDropCard(collection, pageNumber, vBox, deck));

                ImageView hero_icon = addImage(root, "pics/collection/deck-select/icon-" + a + ".png",
                        360, 50, 120, 100);

                ImageView close = addImage(root,
                        "pics/collection/button_close@2x.png", 1100, 50, 50, 50);
                ImageView backCircle = addImage(root, "pics/circle.png", 400, 730, 70, 70);
                ImageView back = addImage(root, "pics/back.png", 415, 750 - 5, 40, 40);

                ImageView nextCircle = addImage(root, "pics/circle.png", 1000, 730, 70, 70);
                ImageView next = addImage(root, "pics/next.png", 1015, 750 - 5, 40, 40);

                back.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pageNumber--;
                        if (pageNumber < 0)
                            pageNumber = 0;
                        root.getChildren().removeAll(vBoxes);
                        vBoxes.addAll(dragAndDropCard(collection, pageNumber, vBox, deck));
                    }
                });
                next.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pageNumber++;
                        root.getChildren().removeAll(vBoxes);
                        vBoxes.addAll(dragAndDropCard(collection, pageNumber, vBox, deck));
                    }
                });


                close.setOnMouseClicked(event17 ->
                {
                    root.getChildren().removeAll(close, rectangle, hero_icon,
                            back, backCircle, next, nextCircle);
                    root.getChildren().removeAll(vBoxes);
                    vBoxes.clear();
                });
            });

            checkMainDeck.setOnMouseClicked(event13 -> {
                if (collection.validateDeck(deck.getName())) {
                    collection.selectADeckAsMainDeck(deck.getName());
                    root.getChildren().remove(checkMainDeck);
                    makeIConBarForDeck("pics/collection/checkmark-green.png",
                            20, 10);
                }
            });

            delete_deck.setOnMouseClicked(event14 -> {
                System.out.println("hi");
                try {
                    collection.deleteDeck(deck.getName());
                    root.getChildren().removeAll(deletable);
                    deletable.clear();
                    vBox.getChildren().clear();

                } catch (Exception ignored) {
                }

            });


            ArrayList<Card> cards = deck.getCardsOfDeck();

            Group group;
            for (Card card : cards) {
                group = new Group();
                deckCardMaker(vBox, card, group);
            }

        });



    }

    public static void showDeck(ArrayList<Deck> decks, Collection collection) {
        //collectionScene.setRoot(root);
        root.getChildren().clear();

        setBackground(root, "pics/collection/background@2x.jpg", true, 20, 20);

        try {
            VBox sideVBox = new VBox();//vobxe baqale safhe
            sideVBox.relocate(0, 0);
            sideVBox.setSpacing(5);
            sideVBox.setPrefSize(200, collectionScene.getHeight());
            sideVBox.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 10, 10, 0.5),
                    CornerRadii.EMPTY, Insets.EMPTY)));


            for (int i = 0; i < decks.size(); i++) {
                makeDeck(sideVBox, i, decks.get(i), collection);
            }

            ImageView add_deck = addImage(root, "pics/collection/plate@2x.png",
                    collectionScene.getWidth() - 130, collectionScene.getHeight() - 130,
                    100, 100);


            ImageView plus = addImage(root, "pics/collection/add.png",
                    collectionScene.getWidth() - 67 - 20, collectionScene.getHeight() - 87,
                    15, 15);
            plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Rectangle rectangle = new Rectangle(800, 200
                            , Color.rgb(0, 0, 0, 0.8));
                    rectangle.relocate(350, 100);
                    root.getChildren().add(rectangle);
                    rectangle.setArcWidth(50);
                    rectangle.setArcHeight(50);

                    ImageView close = addImage(root, "pics/collection/button_close@2x.png",
                            1100, 100, 50, 50);

                    ImageView newDeck = addImage(root, "pics/collection/new_deck.png",
                            400, 200, 250, 80);
                    ImageView importDeck = addImage(root, "pics/collection/import.png",
                            860, 200, 250, 80);

                    ImageView exportDeck = addImage(root, "pics/collection/export_deck.png",
                            660, 200, 200, 80);

                    Text newDeckText = addText(root, "New Deck", 500 - 50, 235,
                            Color.rgb(225, 225, 225, 0.6), 30);
                    newDeckText.setStyle("-fx-font-weight: bold");

                    Text exportText = addText(root, "Export", 690, 235,
                            Color.rgb(225, 225, 225, 0.6), 30);
                    exportText.setStyle("-fx-font-weight: bold");

                    Text importText = addText(root, "Import Deck", 890, 235
                            , Color.rgb(225, 225, 225, 0.6), 30);
                    importText.setStyle("-fx-font-weight: bold");

                    ImageView text = addImage(root, "pics/collection/card_silenced@2x.png",
                            600 - 5, 100, 300, 100);

                    TextField deckName = new TextField();
                    deckName.setPrefHeight(50);
                    deckName.relocate(600, 120);
                    deckName.positionCaret(1);
                    deckName.setStyle("-fx-text-fill: #a3b2cc; -fx-font-size: 25px; -fx-font-weight: bold;");
                    deckName.setBackground(new Background(
                            new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                                    CornerRadii.EMPTY, Insets.EMPTY)));
                    root.getChildren().add(deckName);

                    importText.setOnMouseClicked(event12 -> {
                        try {
                            FileReader fr = new FileReader("exportedDeck/" + deckName.getText() + ".txt");
                            Gson gson = new GsonBuilder().create();
                            Deck deck = gson.fromJson(fr, Deck.class);//load the deck
                            if (!collection.getAccount().getCollection().checkTheDeckForImport(deck)) {
                                ErrorType.HAVE_NOT_CARDS_IN_COLLECTION_FOR_IMPORT.printMessage();
                            }
                            {
                                if (collection.passTheDeckIfHaveBeenExist(deck.getName()) == null) {
                                    collection.getDecks().add(deck);
                                    int i = collection.getDecks().size() - 1;
                                    root.getChildren().removeAll(newDeck, newDeckText,
                                            importDeck, importText, rectangle, close, text,
                                            deckName, exportDeck, exportText);
                                    makeDeck(sideVBox, i, collection.getDecks().get(i), collection);
                                    root.getChildren().addAll(rectangle, newDeck, newDeckText,
                                            importDeck, importText, close, text, deckName, exportDeck, exportText);
                                }
                            }

                        } catch (Exception e) {
                            ErrorType.INVALID_NAME_FOR_IMPORTED_DECK.printMessage();
                        } finally {
                            deckName.clear();
                        }
                    });

                    exportText.setOnMouseClicked(event13 -> {
                        Deck deck = collection.passTheDeckIfHaveBeenExist(deckName.getText());
                        System.out.println(collection.getDecks());
                        if (deck == null) {
                            ErrorType.HAVE_NOT_DECK.printMessage();
                        } else {
                            try {
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                File file = new File("exportedDeck/" + deckName.getText() + ".txt");
                                FileWriter fileWriter = new FileWriter(file);
                                fileWriter.write(gson.toJson(deck));
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        deckName.clear();
                    });

                    newDeckText.setOnMouseClicked(event1 -> {
                        if (collection.createDeck(deckName.getText())) {
                            int i = collection.getDecks().size() - 1;
                            deckName.clear();
                            root.getChildren().removeAll(newDeck, newDeckText,
                                    importDeck, importText, rectangle, close, text,
                                    deckName, exportDeck, exportText);
                            makeDeck(sideVBox, i, collection.getDecks().get(i), collection);
                            root.getChildren().addAll(rectangle, newDeck, newDeckText,
                                    importDeck, importText, close, text, deckName, exportDeck, exportText);
                        }

                    });

                    close.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            root.getChildren().removeAll(newDeck, newDeckText,
                                    importDeck, importText, rectangle, close, text, deckName, exportDeck, exportText);
                        }
                    });

                }
            });
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
            root.getChildren().add(sideVBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log(root,collection.helpOfCollection(),StageLauncher.getScene(StateType.MAIN_MENU),600);


    }


}
