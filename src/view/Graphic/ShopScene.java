package view.Graphic;

import controller.Transmitter;
import controller.client.TransferController;
import controller.RequestEnum;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.card.Card;
import model.item.Item;
import model.item.Usable;
import view.enums.Cursor;
import view.enums.StateType;

import java.util.ArrayList;
import java.util.Objects;

import static controller.RequestEnum.*;
import static view.Graphic.GeneralGraphicMethods.*;

class ShopScene {
    private static Scene shopScene = StageLauncher.getScene(StateType.SHOP);
    private static Group root = (Group) Objects.requireNonNull(shopScene).getRoot();
    private static ArrayList<HBox> hBoxes = new ArrayList<>();
    private static int pageNumberCards = 0;
    private static ArrayList<Node> deletable = new ArrayList<>();

    static void makeShopScene() {
        playMusic("resource/music/shop.m4a", true, shopScene);

        setBackground(root, "pics/shop/shop_background.jpg", false, 15, 15);
        VBox sideVBox = new VBox();
        sideVBox.relocate(0, 0);

        sideVBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(10, 10, 10, 0.1),
                CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(sideVBox);

        Group buyAndSellCard = makeShopIconBar(
                "pics/shop/sellCard.png", "Buy And Sell", 0);
        root.getChildren().add(buyAndSellCard);

        Group search = makeShopIconBar(
                "pics/shop/research.png", "Search", 1
        );
        root.getChildren().add(search);

        Group emote = makeShopIconBar(
                "pics/shop/emotes.png", "Emotes", 2);
        root.getChildren().add(emote);

        Group profile = makeShopIconBar(
                "pics/shop/profile_icon.png", "Profile Icon", 3);
        root.getChildren().add(profile);

        Group battle_maps = makeShopIconBar(
                "pics/shop/battle_map.png", "Battle Maps", 4);
        root.getChildren().add(battle_maps);

        Group bundles = makeShopIconBar(
                "pics/shop/friends.png", "Bundles", 5);
        root.getChildren().add(bundles);

        Group orbs = makeShopIconBar(
                "pics/shop/spirit_orb.png", "Spirit Orbs", 6);
        root.getChildren().add(orbs);

        Rectangle rectangle = new Rectangle(20, 750, 180, 40);
        rectangle.setFill(Color.rgb(10, 10, 10, 0.5));
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        root.getChildren().add(rectangle);

        long daricNumber = TransferController.main(SHOP_DARIC, new Transmitter()).daric;
        addImage(root, "pics/shop/icon_gold@2x.png", 55, 755, 20, 20);
        Text daric = addText(root, 80, 760, "Daric: " + daricNumber,
                Color.rgb(225, 225, 225, 0.5), 15);


        emote.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            root.getChildren().removeAll(deletable);
            hBoxes.clear();
            makeRectIcon(200, 150, 4,
                    "emotes", 5, 20, ".png", 90);

        });
        orbs.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            root.getChildren().removeAll(deletable);
            hBoxes.clear();
            makeRectIcon(200, 200, 2,
                    "orbs", 4, 7, ".png", 90);
        });


        battle_maps.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            root.getChildren().removeAll(deletable);
            hBoxes.clear();
            makeRectIcon(200, 350, 2,
                    "battle_map", 4,
                    8, ".jpg", 300);
        });

        bundles.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            root.getChildren().removeAll(deletable);
            hBoxes.clear();
            makeRectIcon(190, 150, 4,
                    "bundles", 5,
                    17, ".png", 90);
        });

        profile.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            hBoxes.clear();
            makeRectIcon(190, 160, 4,
                    "profile", 5,
                    20, ".jpg", 90);
        });


        buyAndSellCard.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            root.getChildren().removeAll(deletable);
            hBoxes.clear();

            HBox hBox = sellCard(daric);


            Transmitter transmitter = TransferController.main(SHOP_ITEMS, new Transmitter());
            buyCard(hBox, daric, transmitter.items);
        });

        search.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hBoxes);
            root.getChildren().removeAll(deletable);
            hBoxes.clear();
            HBox hBox = new HBox();

            Group groupText = new Group();

            addRectangle(groupText, 0, 0, 400, 90, 50, 50,
                    Color.rgb(0, 0, 0, 0.7));

            TextField textArea = new TextField();
            textArea.setPrefHeight(100);
            textArea.relocate(0, 0);
            textArea.positionCaret(1);
            textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 20px; -fx-font-weight: bold;");
            Group group = CollectionScene.textArea(hBox, groupText, textArea);
            addRectangle(group, 0, 0, 80, 90, 50, 50
                    , Color.rgb(0, 0, 0, 0.7));


            ImageView magnifier = addImage(group,
                    "pics/shop/research.png", 16, 23, 50, 50);
            hBox.getChildren().addAll(group);

            magnifier.setOnMouseClicked(event1 -> {
                Group group1;
                String cardName = textArea.getText();
                textArea.clear();

                Transmitter transmitter = new Transmitter();
                transmitter.name = cardName;
                transmitter = TransferController.main(RequestEnum.SHOP_SEARCH, transmitter);
                Object object = transmitter.object;
                if (object != null) {
                    if (object instanceof Card) {
                        group1 = CollectionScene.makeCardGroup(500, 400, object, shopScene);
                        root.getChildren().addAll(group1);
                        HBox hBox1 = new HBox();
                        hBox1.relocate(410, 350);
                        hBox1.getChildren().addAll(group1);
                        hBoxes.add(hBox1);
                        root.getChildren().addAll(hBox1);
                        addText(group1, 50, 230, transmitter.name + "_" + cardName + "_" +
                                        transmitter.collection.getNumberOfCardId((Card) object),
                                Color.WHITE, 20);
                    }
                }
            });


            root.getChildren().addAll(hBox);
            hBoxes.add(hBox);
        });

        Transmitter transmitter = TransferController.main(SHOP_HELP, new Transmitter());
        log(root, transmitter.string, StateType.MAIN_MENU, 450);
    }

    private static Group makeShopIconBar(String path, String input, int i) {
        Group group = new Group();
        group.relocate(20, (i + 1) * 100 - 50);
        addImage(group, path, 0, 0, 100, 100);
        Text text = addText(group, 120, 50, input,
                Color.rgb(225, 225, 225, 0.5), 30);
        text.setOnMouseEntered(event -> {
            Glow glow = new Glow();
            glow.setLevel(10);
            text.setEffect(glow);
            setCursor(shopScene, Cursor.LIGHTEN);
        });
        text.setOnMouseExited(event -> {
            text.setEffect(null);
            setCursor(shopScene, Cursor.AUTO);
        });
        return group;
    }

    private static void makeRectIcon(int width, int height, int numberOfRows,
                                     String iconName, int numberOfColumn,
                                     int numberOfIcons, String typeOfFile, int upperNumber) {
        HBox hBox;
        int j = 0;
        ImageView imageIcon;
        for (int i = 0; i < numberOfRows; i++) {
            hBox = new HBox();
            hBoxes.add(hBox);
            hBox.setSpacing(10);
            hBox.relocate(350, height * (i + 1) + 10 * i - upperNumber);
            root.getChildren().add(hBox);
            for (int k = 0; k < numberOfColumn; k++) {
                j++;
                if (j > numberOfIcons)
                    return;
                imageIcon = addImage(hBox,
                        "pics/shop/" + iconName + "-" + j + typeOfFile, 0, 0, width, height);
                imageIcon.setOnMouseEntered(event -> GeneralGraphicMethods.setCursor(shopScene, Cursor.LIGHTEN));
                imageIcon.setOnMouseExited(event -> GeneralGraphicMethods.setCursor(shopScene, Cursor.AUTO));
            }
        }
    }

    private static HBox sellCard(Text daric) {
        HBox hbox = new HBox();
        hbox.setSpacing(4);
        hbox.relocate(540, 17);
        Group groupText = new Group();

        addRectangle(groupText, 0, 17, 400, 70, 50, 50,
                Color.rgb(0, 0, 0, 0.7));
        hbox.getChildren().addAll(groupText);

        TextField textArea = new TextField();
        textArea.setPrefHeight(70);
        textArea.relocate(0, 20);
        textArea.positionCaret(1);
        textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 20px; -fx-font-weight: bold;");
        textArea.setBackground(new Background(new BackgroundFill(
                Color.rgb(5, 5, 5, 0.0001),
                CornerRadii.EMPTY, Insets.EMPTY)));
        groupText.getChildren().add(textArea);

        Group group = new Group();
        groupText.relocate(400, 100);
        addRectangle(group, 0, 17, 50, 70,
                50, 50, Color.rgb(5, 5, 5, 0.7));
        hbox.getChildren().addAll(group);

        addImage(group, "pics/shop/tag.png", 0, 17, 50, 90 - 30);


        hBoxes.add(hbox);
        root.getChildren().addAll(hbox);

        group.setOnMouseClicked(event -> {
            Transmitter transmitter = new Transmitter();
            transmitter.name = textArea.getText();
            transmitter = TransferController.main(SHOP_SELL, transmitter);
            textArea.clear();
            daric.setText("Daric :" + transmitter.daric);
        });
        return hbox;
    }

    private static void buyCard(HBox hBox, Text daric, ArrayList<Item> items) {
        Transmitter transmitter = TransferController.main(SHOP_CARDS, new Transmitter());
        ArrayList<Card> cards = transmitter.cards;
        VBox vBox = new VBox();//todo remember to delete
        for (int i = 0; i < 10; i++) {
            if (i >= cards.size()) {
                break;
            }
            makeHBoxForCards(pageNumberCards, cards, daric, items);
        }


        ImageView backCircle = addImage(root, "pics/other/circle.png", 400, 750, 70, 70);
        ImageView back = addImage(root, "pics/other/back.png", 415, 765, 40, 40);

        ImageView nextCircle = addImage(root, "pics/other/circle.png", 1100, 750, 70, 70);
        ImageView next = addImage(root, "pics/other/next.png", 1115, 765, 40, 40);

        deletable.add(backCircle);
        deletable.add(back);
        deletable.add(next);
        deletable.add(nextCircle);


        root.getChildren().addAll(vBox);

        back.setOnMouseClicked(event -> {
            pageNumberCards--;
            if (pageNumberCards < 0)
                pageNumberCards = 0;
            root.getChildren().removeAll(hBoxes);
            root.getChildren().addAll(hBox);
            makeHBoxForCards(pageNumberCards, cards, daric, items);
        });
        next.setOnMouseClicked(event -> {
            pageNumberCards++;
            root.getChildren().removeAll(hBoxes);
            root.getChildren().addAll(hBox);
            makeHBoxForCards(pageNumberCards, cards, daric, items);
        });

    }

    private static void makeHBoxForCards(int pageNumber, ArrayList<Card> cards,
                                         Text daric, ArrayList<Item> items) {
        HBox hBox = new HBox();
        final int column = 4;
        int startingBound = 2 * column * pageNumber;
        int j = -1;
        Group group;
        outer:
        for (int i = startingBound; i < startingBound + 2 * column; i++) {
            if (i % column == 0 && ((i >= cards.size() && i < startingBound + 2 * column) || i < cards.size())) {
                j++;
                hBox = new HBox();
                hBox.relocate(350, 100 + (j) * 335);
                root.getChildren().addAll(hBox);
                hBoxes.add(hBox);
                hBox.setSpacing(10);
            }
            if (i >= cards.size()) {
                int indexOfItem = i - cards.size();
                for (int k = indexOfItem; k < startingBound + 2 * column; k++) {
                    if (i >= startingBound + 2 * column)
                        break outer;
                    if (k >= items.size())
                        break outer;
                    Usable item = (Usable)items.get(k);
                    group = CollectionScene.makeItemCard(item);

                    group.setOnMouseClicked(event -> daric.setText("Daric: " + buyCard(item.getName())));

                    addText(group, 20, 225, items.get(k).getName() + "\n" + items.get(k).getCost()
                            , Color.WHITE, 20);
                    hBox.getChildren().addAll(group);
                    indexOfItem++;
                    i++;
                }

                break;
            }
            Card card = cards.get(i);
            group = CollectionScene.makeCardGroup(0, 0, cards.get(i), shopScene);
            hBox.getChildren().addAll(group);
            addText(group, 20, 225, card.getName(), Color.WHITE, 20);
            addText(group, 20, 245, card.getCost() + "", Color.WHITE, 20);
            group.setOnMouseClicked(event -> daric.setText("Daric: " + buyCard(card.getName())));
        }
    }

    private static long buyCard(String name) {
        Transmitter transmitter = new Transmitter();
        transmitter.name = name;
        transmitter = TransferController.main(RequestEnum.SHOP_BUY, transmitter);
        return transmitter.daric;
    }


}
