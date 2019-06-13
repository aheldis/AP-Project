package view.Graphic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.account.Collection;
import model.account.FilesType;
import model.battle.Deck;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import model.item.Usable;
import view.enums.Cursor;
import view.enums.ErrorType;
import view.enums.StateType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static view.Graphic.GeneralGraphicMethods.*;


class CollectionScene {
    private static final Scene collectionScene = StageLauncher.getScene(StateType.COLLECTION);
    private static Group root = (Group) Objects.requireNonNull(collectionScene).getRoot();
    private static int CARD_HEIGHT = 315;
    private static int CARD_WIDTH = 220;
    private static int X_BORDER = 45;
    private static int Y_BORDER = 35;
    private static List<Node> deletable = new ArrayList<>();
    private static int pageNumber = 0;
    private static int pageNumberCards = 0;
    private static ArrayList<VBox> vBoxes = new ArrayList<>();
    private static ArrayList<Node> cardsIcon = new ArrayList<>();
    private static int numberOfDeck = 0;
    private static ArrayList<Node> groupOfDeck = new ArrayList<>();


    private static void deckLittleCardMaker(Parent root, Object card, Group group,
                                            Collection collection, Deck deck) {

        try {
            ImageView backPicView = new ImageView(new Image(new FileInputStream(
                    "pics/collection/deck-select/deck_back.png")));
            backPicView.setFitHeight(70);
            backPicView.setFitWidth(300);
            group.getChildren().add(backPicView);

            String name = "";
            if (card instanceof Spell) {
                System.out.println("here");
                name = ((Spell) card).getName();
                SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                        name, FilesType.SPELL, ((Spell) card).getCountOfAnimation());
                cardsIcon.add(SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                        25, 10,
                        group, sprite.count,
                        sprite.rows, ((Spell) card).getMillis(),
                        (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame));
            }
            if (card instanceof Hero) {
                name = ((Hero) card).getName();
                addImage(group, "pics/Hero/" + name + ".gif", 0, 0, 80, 80);
            }
            if (card instanceof Minion) {
                name = ((Minion) card).getName();
                addImage(group, "pics/Minion/" + name + ".gif", 0, 0, 80, 80);
            }
            if (card instanceof Usable) {
                name = ((Usable) card).getName();
                SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                        name, FilesType.USABLE,
                        ((Usable) card).getCountOfAnimation());
                cardsIcon.add(SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                        25, 10,
                        group, sprite.count,
                        sprite.rows, ((Usable) card).getMillis(),
                        (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame));

            }


            Text text = addText(group, name + "\n",
                    60 + 25, 10, Color.rgb(200, 200, 225, 0.5), 20);
            text.setOnMouseEntered(event1 -> {
                text.setUnderline(true);
                text.setFill(Color.rgb(49, 255, 245, 0.5));
            });
            text.setOnMouseExited(event12 -> {
                text.setUnderline(false);
                text.setFill(Color.rgb(200, 200, 225, 0.5));
            });

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
                if (card instanceof Card)
                    collection.removeCardFromDeck((Card) card, deck.getName());
                if (card instanceof Usable)
                    collection.removeItemFromDeck((Usable) card, deck.getName());
                if (root instanceof VBox)
                    ((VBox) root).getChildren().removeAll(group);
                if (root instanceof Group)
                    ((Group) root).getChildren().removeAll(group);

                group.getChildren().clear();

            });

        } catch (Exception ignored) {

        }
    }

    static Group makeCardGroup(int x, int y, Object card, Scene scene) {
        Group group = new Group();
        group.relocate(x, y);
        if (card instanceof Card) {
            ImageView imageView = addImage(group, "pics/other/minion_background.png",
                    0, 0, CARD_WIDTH, CARD_HEIGHT);

            showMana(group, 0, 0, ((Card) card).getMp());


            addText(group, ((Card) card).getAp() + ""
                    , 50 - 10,
                    200 - 17, Color.WHITE, 20);

            addText(group, ((Card) card).getHp() + "",
                    180 - 20,
                    200 - 17, Color.WHITE, 20);
            if (card instanceof Spell) {
                SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                        ((Spell) card).getName(), FilesType.SPELL, ((Spell) card).getCountOfAnimation());
                cardsIcon.add(SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                        94, 58,
                        group, sprite.count,
                        sprite.rows, ((Spell) card).getMillis(),
                        (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame));
            }
            if (card instanceof Minion) {
                addImage(group, "pics\\Minion\\" + ((Minion) card).getName() + ".gif",
                        64,
                        28, 110, 150);

            }
            if (card instanceof Hero) {
                addImage(group, "pics\\Hero\\" + ((Hero) card).getName() + ".gif",
                        64,
                        28, 110, 150);
            }

            try {
                imageView.setOnMouseEntered(event -> {
                    ImageView descView = addImage(group, "pics/other/desc.png", 10,
                            303 - 50, 200, 100);
                    Text desc = addText(group, ((Card) card).getDescription(),
                            40,
                            338 - 50 - 8, Color.WHITE, 15);
                    setCursor(scene, Cursor.LIGHTEN);
                    imageView.setOnMouseExited(event1 -> {
                        group.getChildren().removeAll(desc, descView);
                        setCursor(scene, Cursor.AUTO);
                    });
                });

            } catch (Exception ignored) {

            }

        }
        return group;
    }


    private static void showMana(Group root, int x, int y, int mana) {
        cardsIcon.add(addImage(root, "pics/other/icon_mana@2x.png", x, y, 50, 50));
        cardsIcon.add(addText(root, mana + "", x + 16, y + 20,
                Color.rgb(22, 22, 225, 0.5), 20));
    }


    private static void showEachHero(Card card, HBox hBox, int i, int j) {
        try {

            ImageView imageView = addImage(hBox, "pics\\heroCard\\" + card.getName() + ".png",
                    0, 0, CARD_WIDTH, CARD_HEIGHT);

            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 65, card.getMp());


            imageView.setOnMouseEntered(event -> {

                try {
                    ImageView descView = addImage(root, "pics/other/desc.png",
                            i * (X_BORDER + CARD_WIDTH) + 70,
                            j * (Y_BORDER + CARD_HEIGHT) + 390 - 55, 200, 100);
                    cardsIcon.add(descView);
                    ImageView apView = addImage(root, "pics/other/ap_show.png",
                            i * (X_BORDER + CARD_WIDTH) - 5,
                            j * (Y_BORDER + CARD_HEIGHT) + 390 - 55, 100, 100);
                    cardsIcon.add(apView);
                    ImageView hpView = addImage(root, "pics/other/hp_show.png",
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
                            j * (Y_BORDER + CARD_HEIGHT) + 430 - 60, Color.WHITE, 13);
                    cardsIcon.add(desc);


                    imageView.setOnMouseExited(event1 ->
                            root.getChildren().removeAll(descView, hpView, apView, hp, ap, desc));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

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

                ImageView descView = addImage(root, "pics/other/desc.png", i * (X_BORDER + CARD_WIDTH) + 70,
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

    private static void showEachMinion(Card card, HBox hBox, int i, int j) {
        try {
            ImageView imageView = addImage(hBox,
                    "pics/other/minion_background.png", 0, 0, CARD_WIDTH, CARD_HEIGHT);
            imageView.fitWidthProperty();

            ImageView animationImageView = addImage(root, "pics\\Minion\\" + card.getName() + ".gif",
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

    private static void showEachSpell(Card card, HBox hBox, int i, int j) {
        try {
            ImageView imageView = addImage(hBox, "pics/other/minion_background.png",
                    0, 0, CARD_WIDTH, CARD_HEIGHT);
            imageView.fitWidthProperty();

            SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                    card.getName(), FilesType.SPELL, card.getCountOfAnimation());
            cardsIcon.add(SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                    i * (X_BORDER + CARD_WIDTH) + 140,
                    j * (Y_BORDER + CARD_HEIGHT) + 200 - 55,
                    root, sprite.count,
                    sprite.rows, card.getMillis(),
                    (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame));

            textForCollection(card, i, j, imageView);

            showMana(root, i * (X_BORDER + CARD_WIDTH) + 40,
                    j * (Y_BORDER + CARD_HEIGHT) + 130 - 65, card.getMp());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hBoxCardMaker(VBox vBox, int pageNumber, int NUMBER_IN_EACH_ROW,
                                      ArrayList<Card> cards, int spacing) {
        HBox hBox = new HBox();
        int startingBound = 2 * NUMBER_IN_EACH_ROW * pageNumber;
        int j = -1;
        for (int i = startingBound; i < startingBound + 2 * NUMBER_IN_EACH_ROW; i++) {
            if (i >= cards.size())
                break;
            if (i % NUMBER_IN_EACH_ROW == 0) {
                hBox = new HBox();
                vBox.getChildren().addAll(hBox);
                cardsIcon.add(hBox);
                Text helper = new Text("hiii");
                helper.relocate(0, 0);
                helper.setFont(Font.font(5));
                helper.setFill(Color.TRANSPARENT);
                hBox.getChildren().add(helper);
                hBox.setSpacing(X_BORDER);
                cardsIcon.add(hBox);
                j++;
            }
            if (cards.get(i) instanceof Hero)
                showEachHero(cards.get(i), hBox, i % 5, j);
            if (cards.get(i) instanceof Minion)
                showEachMinion(cards.get(i), hBox, i % 5, j);
            if (cards.get(i) instanceof Spell)
                showEachSpell(cards.get(i), hBox, i % 5, j);
        }
    }

    private static void searchBar(HBox hBox, VBox vBox, Collection collection) {
        Group groupText = new Group();
        groupText.relocate(300, 20);

        addRectangleForCollection(groupText, 0, 25, 400, 40, 50, 50
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
        addRectangleForCollection(group1, 0, 20, 50, 40, 60, 60
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

                ImageView background = setBackground(root, "pics/other/collectionBackground.jpg",
                        true, 20, 20);
                root.getChildren().addAll(group);

                addRectangleForCollection(group, 0, 0, 400, 400
                        , 50, 50, Color.rgb(0, 0, 0, 0.9));
                ImageView close = addImage(group,
                        "pics/collection/button_close@2x.png", 350, 0, 50, 50);
                VBox vBox1 = new VBox();
                vBox1.relocate(30, 30);
                vBox1.setSpacing(5);
                group.getChildren().addAll(vBox1);
                for (String id : ids)
                    addText(vBox1, id, 0, 0, Color.rgb(225, 225, 225), 20);

                close.setOnMouseClicked(event1 -> root.getChildren().removeAll(background, group));
            } else {
                ErrorType.HAVE_NOT_CARD_IN_COLLECTION.printMessage();
            }
        });
    }

    public static Group makeItemCard(Usable item) {
        Group group = new Group();
        ImageView imageView = addImage(group, "pics/other/spell_background.png",
                0, 0, CARD_WIDTH, CARD_HEIGHT);

        SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                item.getName(), FilesType.USABLE, item.getCountOfAnimation());
        cardsIcon.add(SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                80, 75,
                group, sprite.count,
                sprite.rows, item.getMillis(),
                (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame));


        imageView.setOnMouseEntered(event -> {
            ImageView descView = addImage(group, "pics/other/desc.png", 10,
                    303 - 50, 200, 100);

            Text desc = addText(group, item.getDescription(),
                    40,
                    338 - 50 - 8, Color.WHITE, 15);
            imageView.setOnMouseExited(event1 -> group.getChildren().removeAll(desc, descView));
        });

        return group;
    }

    private static void addItemCard(Usable[] items, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(1);
        vBox.getChildren().addAll(hBox);
        cardsIcon.add(hBox);
        Group group;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null)
                continue;
            group = makeItemCard(items[i]);
            hBox.getChildren().addAll(group);
        }
    }

    static void showInCollection(Collection collection) {
        ArrayList<Card> cards = collection.getAllCards();
        Usable[] items = collection.getItems();
        root.getChildren().clear();

        playMusic("resource/music/collection.m4a", true, collectionScene);

        setBackground(root, "pics/other/collectionBackground.jpg", true, 20, 20);

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


        searchBar(hBox, vBox, collection);

        ImageView backCircle = addImage(root, "pics/other/circle.png", 100, 750, 70, 70);
        ImageView back = addImage(root, "pics/other/back.png", 115, 765, 40, 40);

        ImageView nextCircle = addImage(root, "pics/other/circle.png", 1200, 750, 70, 70);
        ImageView next = addImage(root, "pics/other/next.png", 1215, 765, 40, 40);
        ImageView deckSceneButton = addImage(root, "pics/other/desc.png", 600, 770, 100, 50);
        Text deckScene = addText(root, "Decks", 618, 785, Color.rgb(225, 225, 225,
                0.8), 20);
        deckScene.setOnMouseClicked(event -> Platform.runLater(() -> showDeck(collection.getDecks(), collection)));

        back.setOnMouseClicked(event -> {
            pageNumberCards--;
            if (pageNumberCards < 0)
                pageNumberCards = 0;
            vBox.getChildren().removeAll(cardsIcon);
            root.getChildren().removeAll(cardsIcon);
            cardsIcon.clear();

            if (pageNumberCards == Math.ceil(cards.size() / 10.0)) {
                addItemCard(items, vBox);
            }
            hBoxCardMaker(vBox, pageNumberCards, 5, cards, 10);
        });
        next.setOnMouseClicked(event -> {
            pageNumberCards++;
            vBox.getChildren().removeAll(cardsIcon);
            root.getChildren().removeAll(cardsIcon);
            cardsIcon.clear();
            if (pageNumberCards == Math.ceil(cards.size() / 10.0)) {
                addItemCard(items, vBox);
            }
            hBoxCardMaker(vBox, pageNumberCards, 5, cards, 10);
        });

        if (pageNumberCards == Math.ceil(cards.size() / 10.0)) {
            addItemCard(items, vBox);
        }
        int j = -1;
        for (int i = 0; i < 10; i++) {
            if (i >= cards.size())
                break;
            if (i % 5 == 0) {

                hBox = new HBox();
                cardsIcon.add(hBox);
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


        log(root, collection.helpOfCollection(), StateType.MAIN_MENU, 600);

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
        } catch (Exception ignored) {
        }
        return deck;
    }

    private static ArrayList<VBox> dragAndDropCard(Collection collection, int pageNumber, VBox target, Deck deck) {
        final int NUMBER_IN_EACH_ROW = 5;
        final int SPACING = 13;
        ArrayList<VBox> vBoxes = new ArrayList<>();
        ArrayList<Card> cards = collection.getAllCards();
        Usable[] items = collection.getItems();
        VBox vBox = new VBox();
        int startingBound = 2 * NUMBER_IN_EACH_ROW * pageNumber;
        for (int i = startingBound; i < startingBound + 2 * NUMBER_IN_EACH_ROW; i++) {
            if (i >= cards.size()) {
                for (int h = i - cards.size(); h < items.length; h++) {
                    if (items[h] == null)
                        continue;
                    if (i % NUMBER_IN_EACH_ROW == 0) {
                        vBox = new VBox();
                        vBox.relocate(370 * ((i / (float) NUMBER_IN_EACH_ROW) % 2 + 1), 160);
                        vBox.setSpacing(SPACING);
                        root.getChildren().addAll(vBox);
                        vBoxes.add(vBox);
                    }
                    Group group = new Group();
                    deckLittleCardMaker(vBox, items[h], group, collection, deck);
                    DragAndDropClass.dragAndDrop(group, target, deck, items[h]
                            , vBox, root, 150, 35);
                    i++;
                }
                break;
            }
            if (i % NUMBER_IN_EACH_ROW == 0) {
                vBox = new VBox();
                vBox.relocate(370 * ((i / (float) NUMBER_IN_EACH_ROW) % 2 + 1), 160);
                vBox.setSpacing(SPACING);
                root.getChildren().addAll(vBox);
                vBoxes.add(vBox);
            }
            Group group = new Group();
            deckLittleCardMaker(vBox, cards.get(i), group, collection, deck);
            DragAndDropClass.dragAndDrop(group, target, deck, cards.get(i), vBox
                    , root, 150, 35);
        }
        return vBoxes;
    }

    private static void makeDeckSide(VBox vBox, int i, Deck deck, Collection collection, Group group) {
        int a = i % 6;
        group.setOnMouseClicked(event -> {
            root.getChildren().removeAll(deletable);
            deletable.clear();
            vBox.getChildren().clear();
            addImage(vBox,
                    "pics/collection/deck_ribbons/ribbon-" + a + ".png", 0, 0,
                    324, 150);

            ImageView addCardToDeck = addImage(root,
                    "pics\\collection\\plus (2).png", 40, 10, 20, 20);


            ImageView delete_deck = addImage(root,
                    "pics/collection/close-deck.png", 70, 10, 20, 20);

            ImageView checkMainDeck;
            if (deck.getName().equals(collection.getAccount().getMainDeck().getName())) {
                checkMainDeck = addImage(root,
                        "pics/collection/checkmark-green.png", 10, 10, 20, 20);
            } else {
                checkMainDeck = addImage(root,
                        "pics/collection/checkmark-blue.png", 10, 10, 20, 20);
            }


            addCardToDeck.setOnMouseClicked(event15 -> {
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
                ImageView backCircle = addImage(root, "pics/other/circle.png", 400, 730, 70, 70);
                ImageView back = addImage(root, "pics/other/back.png", 415, 750 - 5, 40, 40);

                ImageView nextCircle = addImage(root, "pics/other/circle.png", 1000, 730, 70, 70);
                ImageView next = addImage(root, "pics/other/next.png", 1015, 750 - 5, 40, 40);

                back.setOnMouseClicked(event1 -> {
                    pageNumber--;
                    if (pageNumber < 0)
                        pageNumber = 0;
                    root.getChildren().removeAll(vBoxes);
                    vBoxes.addAll(dragAndDropCard(collection, pageNumber, vBox, deck));
                });
                next.setOnMouseClicked(event12 -> {
                    pageNumber++;
                    root.getChildren().removeAll(vBoxes);
                    vBoxes.addAll(dragAndDropCard(collection, pageNumber, vBox, deck));
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
                System.out.println("why");
                if (collection.validateDeck(deck.getName())) {
                    collection.selectADeckAsMainDeck(deck.getName());
                    root.getChildren().remove(checkMainDeck);
                    makeIConBarForDeck("pics/collection/checkmark-green.png",
                            10, 10);
                }
            });

            delete_deck.setOnMouseClicked(event14 -> {
                System.out.println("hi");
                try {
                    collection.deleteDeck(deck.getName());
                    root.getChildren().removeAll(group,
                            checkMainDeck, delete_deck, addCardToDeck);
                    root.getChildren().removeAll(groupOfDeck);
                    root.getChildren().removeAll(deletable);
                    deletable.clear();
                    vBox.getChildren().clear();
                    addImage(root, "pics/collection/shadow.png",
                            650, 130, 300, 400);

                } catch (Exception ignored) {
                }

            });

            ArrayList<Card> cards = deck.getCardsOfDeck();

            Group group1;
            for (Card card : cards) {
                group1 = new Group();
                deckLittleCardMaker(vBox, card, group1, collection, deck);
            }
            if (deck.getItem() != null) {
                group1 = new Group();
                deckLittleCardMaker(vBox, deck.getItem(), group1, collection, deck);
            }

        });


    }

    private static Group makeDeckMainCard(Deck deck, int i, VBox vBox, Collection collection) {
        Group group = new Group();
        group.relocate(650, 130);

        addImage(group, "pics/collection/deck-select/back-" + i % 6 + ".png",
                0, 0, 320, 420);

        Text name = addText(group, deck.getName(), 85, 300,
                Color.rgb(225, 225, 225, 0.5), 40);
        name.setStroke(Color.rgb(0, 225, 225, 0.1));
        name.setStrokeWidth(1);

        groupOfDeck.add(group);

        makeDeckSide(vBox, i, deck, collection, group);

        return group;
    }

    private static void addDeck(Collection collection, VBox sideVBox) {
        ImageView add_deck = addImage(root, "pics/collection/plate@2x.png",
                StageLauncher.getWidth() - 130, StageLauncher.getHeight() - 130,
                100, 100);


        ImageView plus = addImage(root, "pics/collection/add.png",
                StageLauncher.getWidth() - 67 - 20, StageLauncher.getHeight() - 87,
                15, 15);
        plus.setOnMouseClicked(event -> {
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
                            makeDeckSide(sideVBox, i, collection.getDecks().get(i),
                                    collection, makeDeckMainCard(collection.getDecks().get(i), i, sideVBox, collection));
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
                    Group group = makeDeckMainCard(collection.getDecks().get(i), i, sideVBox, collection);
                    root.getChildren().removeAll(groupOfDeck);
                    root.getChildren().addAll(group);
                    makeDeckSide(sideVBox, i, collection.getDecks().get(i)
                            , collection, group);
                    root.getChildren().addAll(rectangle, newDeck, newDeckText,
                            importDeck, importText, close, text, deckName, exportDeck, exportText);
                }

            });

            close.setOnMouseClicked(event14 -> root.getChildren().removeAll(newDeck, newDeckText,
                    importDeck, importText, rectangle, close, text,
                    deckName, exportDeck, exportText));

        });
    }

    private static Group makeDynamicDeck(ArrayList<Deck> decks, VBox sideBar, Collection collection) {
        Group group = new Group();
        group.relocate(650, 130);
        root.getChildren().addAll(group);
        groupOfDeck.add(group);
        addImage(root, "pics/collection/last.png",
                580 + 5, 400 + 2, 35, 35);
        ImageView back = addImage(root, "pics/collection/last.png",
                580, 400, 40, 40);
        addImage(group, "pics/collection/shadow.png",
                0, 0, 300, 400);
        addImage(root, "pics/collection/next.png",
                1000, 400 + 3, 35, 35);
        ImageView next = addImage(root, "pics/collection/next.png",
                1000, 400, 40, 40);

        if (decks.size() != 0) {
            makeDeckSide(sideBar, numberOfDeck, decks.get(numberOfDeck), collection, group);
            root.getChildren().removeAll(groupOfDeck);
            root.getChildren().addAll(makeDeckMainCard(decks.get(0), 0, sideBar, collection));
        }

        back.setOnMouseClicked(event -> {
            numberOfDeck--;
            if (numberOfDeck < 0)
                numberOfDeck = 0;
            root.getChildren().removeAll(groupOfDeck);
            if (decks.size() != 0)
                root.getChildren().addAll(makeDeckMainCard(
                        decks.get(numberOfDeck), numberOfDeck, sideBar, collection));
        });
        next.setOnMouseClicked(event -> {
            numberOfDeck++;
            if (numberOfDeck >= decks.size())
                numberOfDeck = decks.size() - 1;
            root.getChildren().removeAll(groupOfDeck);
            if (decks.size() != 0)
                root.getChildren().addAll(makeDeckMainCard(decks.get(numberOfDeck), numberOfDeck, sideBar, collection));
        });


        return group;
    }

    static void showDeck(ArrayList<Deck> decks, Collection collection) {
        root.getChildren().clear();
        setBackground(root, "pics/collection/background@2x.jpg", true, 20, 20);
        try {
            VBox sideVBox = new VBox();//vobxe baqale safhe
            ScrollBar sc = new ScrollBar();
            sc.relocate(0, 135);
            sc.setBackground(new Background(
                    new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                            CornerRadii.EMPTY, Insets.EMPTY)));
            sc.setPrefHeight(StageLauncher.getHeight() - 170);
            sc.setVisibleAmount(5);
            sc.setMin(0);
            sc.setOrientation(Orientation.VERTICAL);
            //set other properties
            //add childrens to Vbox and properties
            root.getChildren().addAll(sideVBox);
            root.getChildren().addAll(sc);
            sc.valueProperty().addListener((ov, old_val, new_val) ->
                    sideVBox.setLayoutY(-new_val.doubleValue()));


            sideVBox.relocate(0, 0);
            sideVBox.setSpacing(5);
            assert collectionScene != null;
            sideVBox.setPrefSize(200, collectionScene.getHeight());
            sideVBox.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 10, 10, 0.5),
                    CornerRadii.EMPTY, Insets.EMPTY)));


            makeDynamicDeck(decks, sideVBox, collection);

            addDeck(collection, sideVBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log(root, collection.helpOfCollection(), StateType.MAIN_MENU, 600);

    }


}
