package view;

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
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static view.GeneralGraphicMethods.*;


public class CollectionScene {
    private static final Scene collectionScene = StageLauncher.getScene(StateType.COLLECTION);
    private static Group root = (Group) collectionScene.getRoot();
    private static int CARD_HEIGHT = 320;
    private static int CARD_WIDTH = 245;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;
    private static List<Node> deletable = new ArrayList<>();
    static int pageNumber = 0;
    static ArrayList<VBox> vBoxes = new ArrayList<>();



    private  static  void deckCardMaker (Parent root,Card card,Group group){


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
        }catch (Exception ignored){

        }
    }

    private static void showEachHero(Card card, HBox hBox, int i, int j) {
        try {
            Image image = new Image(new FileInputStream(card.getPathOfThePicture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(CARD_HEIGHT);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.fitWidthProperty();
            hBox.getChildren().add(imageView);

            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 130, card.getMp());


            imageView.setOnMouseEntered(event -> {

                try {
                    ImageView descView = addImage(root, "pics/desc.png",
                            i * (X_BORDER + CARD_WIDTH) + 70,
                            j * (Y_BORDER + CARD_HEIGHT) + 390, 200, 100);
                    ImageView apView = addImage(root, "pics/ap_show.png",
                            i * (X_BORDER + CARD_WIDTH) - 5,
                            j * (Y_BORDER + CARD_HEIGHT) + 390, 100, 100);
                    ImageView hpView = addImage(root, "pics/hp_show.png",
                            (i + 1) * (X_BORDER + CARD_WIDTH) - 55,
                            j * (Y_BORDER + CARD_HEIGHT) + 390, 110, 100);


                    Text hp = addText(root, card.getHp() + "", (i + 1) * (X_BORDER + CARD_WIDTH) - 10,
                            j * (Y_BORDER + CARD_HEIGHT) + 430, Color.WHITE, 20);
                    Text ap = addText(root, card.getAp() + "", i * (X_BORDER + CARD_WIDTH) - 15 + 50,
                            j * (Y_BORDER + CARD_HEIGHT) + 430, Color.WHITE, 20);
                    Text desc = addText(root, card.getDescription(), i * (X_BORDER + CARD_WIDTH) + 100,
                            j * (Y_BORDER + CARD_HEIGHT) + 430, Color.WHITE, 20);


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
        addImage(root, "pics/icon_mana@2x.png", x, y, 50, 50);
        addText(root, mana + "", x + 20, y + 20, Color.rgb(22, 22, 225, 0.5), 20);
    }

    private static void textForCollection(Card card, int i, int j, ImageView imageView) {
        addText(root, card.getAp() + "",
                i * (X_BORDER + CARD_WIDTH) + 98,
                j * (Y_BORDER + CARD_HEIGHT) + 320, Color.WHITE, 20);

        addText(root, card.getHp() + "",
                i * (X_BORDER + CARD_WIDTH) + 175 + 53,
                j * (Y_BORDER + CARD_HEIGHT) + 320, Color.WHITE, 20);

        try {
            imageView.setOnMouseEntered(event -> {

                ImageView descView = addImage(root, "pics/desc.png", i * (X_BORDER + CARD_WIDTH) + 70,
                        j * (Y_BORDER + CARD_HEIGHT) + 400, 200, 100);

                Text desc = addText(root, card.getDescription(),
                        i * (X_BORDER + CARD_WIDTH) + 100,
                        j * (Y_BORDER + CARD_HEIGHT) + 435, Color.WHITE, 15);

                imageView.setOnMouseExited(event1 -> root.getChildren().removeAll(desc, descView));
            });

        } catch (Exception ignored) {

        }


    }

    private static void showEachMinion(Card card, HBox hBox, int i, int j) {//todo add desc
        try {
            ImageView imageView = addImage(hBox, card.getPathOfThePicture(), 0, 0, CARD_WIDTH, CARD_HEIGHT);
            imageView.fitWidthProperty();

            ImageView animationImageView = addImage(root, card.getPathOfAnimation(),
                    i * (X_BORDER + CARD_WIDTH) + 75 + 50,
                    j * (Y_BORDER + CARD_HEIGHT) + 120, 110, 150);
            animationImageView.fitWidthProperty();

            textForCollection(card, i, j, imageView);
            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 130, card.getMp());

        } catch (Exception ignored) {
        }

    }

    private static void showEachSpell(Card card, HBox hBox, int i, int j) {
        try {
            ImageView imageView = addImage(hBox, card.getPathOfThePicture(), 0, 0, CARD_WIDTH, CARD_HEIGHT);
            imageView.fitWidthProperty();

            SpriteMaker.getInstance().makeSpritePic(card.getPathOfAnimation(),
                    i * (X_BORDER + CARD_WIDTH) + 150,
                    i * (Y_BORDER + CARD_HEIGHT) + 200,
                    root, card.getCountOfAnimation(),
                    card.getAnimationRow(), 4000,
                    48, 48, 256);

            textForCollection(card, i, j, imageView);

            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 130, card.getMp());

        } catch (Exception ignored) {
        }
    }

    public static void showInCollection(ArrayList<Card> cards, Collection collection) {
        root.getChildren().clear();

        ScrollPane scroller = new ScrollPane(root);

        setBackground(root, "pics/collectionBackground.jpg", true, 20, 20);

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

            ImageView magnifierView = new ImageView(new Image(new FileInputStream(
                    "pics/collection/magnifier_icon.png")));
            magnifierView.setFitHeight(40);
            magnifierView.setFitWidth(40);

            ImageView rightBar = new ImageView(new Image(new FileInputStream(
                    "pics/collection/left.png")));
            rightBar.setFitWidth(100);
            rightBar.setFitHeight(100);

            stackPane.getChildren().add(rightBar);
            stackPane.getChildren().add(magnifierView);

            TextField textArea = new TextField();
            textArea.setPrefHeight(100);
            textArea.relocate(10, 10);
            textArea.positionCaret(1);
            textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 25px; -fx-font-weight: bold;");
            textArea.setBackground(new Background(new BackgroundFill(Color.rgb(5, 5, 5, 0.1),
                    CornerRadii.EMPTY, Insets.EMPTY)));

            hBox.getChildren().addAll(textArea, stackPane);
            hBox.setBackground(new Background(
                    new BackgroundFill(Color.rgb(10, 10, 10, 0.1),
                            CornerRadii.EMPTY, Insets.EMPTY)));
            vBox.getChildren().add(hBox);

            magnifierView.setOnMouseClicked(event -> {
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
                StackPane group = new StackPane();
                addImage(group, "pics/menu/button_cancel.png",
                        500, 10, 200, 80);

                addText(group, "BACK", 567, 40, Color.WHITE, 30);
                vBox.getChildren().add(group);
                group.setOnMouseClicked(event -> {
                    //todo go to menu for haniyeh
                });


            } catch (Exception ignored) {
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        collectionScene.setRoot(scroller);
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

    private static ArrayList<VBox> dragAndDropCard(Collection collection,int pageNumber,VBox target) {
        int NUMBER_IN_EACH_ROW =5;
        ArrayList<VBox> vBoxes = new ArrayList<>();
        int spacing = 13;
        ArrayList<Card> cards = collection.getAllCards();
        VBox vBox = new VBox();
        int startingBound = 2*NUMBER_IN_EACH_ROW*pageNumber;
        Group group;
        for (int i = startingBound; i < startingBound+2*NUMBER_IN_EACH_ROW; i++) {
            if(i>=cards.size())
                break;
            if (i % NUMBER_IN_EACH_ROW == 0) {
                vBox = new VBox();
                vBox.relocate(370 * ((i /(float) NUMBER_IN_EACH_ROW)%2 + 1), 160);
                vBox.setSpacing(spacing);
                root.getChildren().addAll(vBox);
                vBoxes.add(vBox);
            }
            group = new Group();

            deckCardMaker(vBox,cards.get(i),group);
            System.out.println(group);
            DragAndDropClass.dragAndDrop(group,target,collectionScene,vBox);
        }
        return vBoxes;
    }

    private static void makeDeck(VBox vBox, int i, Deck deck, Collection collection) throws Exception {
        Random random = new Random();
        int a = random.nextInt(6);
        ImageView plateImageView = addImage(root, "pics/collection/deck-select/back-" + a + ".png",
                350 + (300 * (i / 5.0)), 20 + (i % 5) * (150), 230, 200);

        addText(root, (i + 1) + "", 350 + (300 * (i / 5.0)) + 20,
                20 + (i % 5) * (150) + 150,
                Color.rgb(225, 225, 225, 0.5), 30);

        addText(root, deck.getName(), 350 + (300 * (i / 5.0)) + 80, 20 + (i % 5) * (150) + 150,
                Color.rgb(211, 100, 225, 0.5), 40);


        plateImageView.setOnMouseClicked(event -> {
            root.getChildren().removeAll(deletable);
            deletable.clear();
            vBox.getChildren().clear();
            ImageView deckImageView;
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


                    vBoxes .addAll( dragAndDropCard(collection,pageNumber,vBox));

                    ImageView hero_icon = addImage(root, "pics/collection/deck-select/icon-" + a + ".png",
                            360, 50, 120, 100);

                    ImageView close = addImage(root,
                            "pics/collection/button_close@2x.png", 1100, 50, 50, 50);
                    ImageView backCircle = addImage(root,"pics/circle.png",400,730,70,70);
                    ImageView back = addImage(root,"pics/back.png",415,750-5,40,40);

                    ImageView nextCircle = addImage(root,"pics/circle.png",1000,730,70,70);
                    ImageView next = addImage(root,"pics/next.png",1015,750-5,40,40);

                    back.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            pageNumber--;
                            if(pageNumber<0)
                                pageNumber = 0;
                            root.getChildren().removeAll(vBoxes);
                            vBoxes.addAll( dragAndDropCard(collection,pageNumber,vBox));
                        }
                    });
                    next.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            pageNumber++;
                            root.getChildren().removeAll(vBoxes);
                            vBoxes .addAll( dragAndDropCard(collection,pageNumber,vBox));
                        }
                    });


                    close.setOnMouseClicked(event17 ->
                    {
                        root.getChildren().removeAll(close, rectangle, hero_icon,
                                back,backCircle,next,nextCircle);
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
                    deckCardMaker(vBox,card,group);
                }

        });


    }

    public static void showDeck(ArrayList<Deck> decks, Collection collection) {
        //collectionScene.setRoot(root);
        root.getChildren().clear();

        setBackground(root, "pics/collection/background@2x.jpg", true, 20, 20);

        try {
            VBox vBox = new VBox();//vobxe baqale safhe
            vBox.relocate(0, 0);
            vBox.setSpacing(5);
            vBox.setPrefSize(200, collectionScene.getHeight());
            vBox.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 10, 10, 0.5),
                    CornerRadii.EMPTY, Insets.EMPTY)));


            for (int i = 0; i < decks.size(); i++) {
                try {
                    makeDeck(vBox, i, decks.get(i), collection);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            ImageView add_deck = addImage(root, "pics/collection/plate@2x.png",
                    collectionScene.getWidth() - 110, collectionScene.getHeight() - 110,
                    100, 100);


            ImageView plus = addImage(root, "pics/collection/add.png",
                    collectionScene.getWidth() - 67, collectionScene.getHeight() - 67,
                    15, 15);
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
