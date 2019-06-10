package view.Graphic;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.account.Account;
import model.account.Shop;
import model.card.Card;
import view.Graphic.CollectionScene;
import view.enums.StateType;
import view.Graphic.StageLauncher;


import javax.swing.*;
import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;

public class ShopScene {
    private static Scene shopScene = StageLauncher.getScene(StateType.SHOP);
    private static Group root = (Group) shopScene.getRoot();
    private static ArrayList<HBox> hboxes = new ArrayList<>();
    private static int pageNumberCards =0 ;


    private static Group makeShopIconBar(String path, String input, int i) {
        Group group = new Group();
        group.relocate(20, (i + 1) * 100 - 50);

        addImage(group, path, 0, 0, 100, 100);

        Text text = addText(group, input, 120, 50,
                Color.rgb(225, 225, 225, 0.5), 30);

        text.setOnMouseEntered(event -> {
            Glow glow = new Glow();
            glow.setLevel(10);
            text.setEffect(glow);
        });
        text.setOnMouseExited(event -> text.setEffect(null));

        return group;


    }

    private static HBox sellCard(Text daric, Account account){
        HBox hbox = new HBox();
        hbox.setSpacing(4);
        hbox.relocate(540, 10);
        Group groupText = new Group();

        addRectangle(groupText, 0, 0, 400, 90, 50, 50
                , Color.rgb(0, 0, 0, 0.7));
        hbox.getChildren().addAll(groupText);

        TextField textArea = new TextField();
        textArea.setPrefHeight(100);
        textArea.relocate(0, 0);
        textArea.positionCaret(1);
        textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 20px; -fx-font-weight: bold;");
        textArea.setBackground(new Background(new BackgroundFill(
                Color.rgb(5, 5, 5, 0.0001),
                CornerRadii.EMPTY, Insets.EMPTY)));
        groupText.getChildren().add(textArea);

        Group group = new Group();
        groupText.relocate(400, 100);
        addRectangle(group,0,0,50,90,
                50,50,Color.rgb(5, 5, 5, 0.7));
        hbox.getChildren().addAll(group);

        addImage(group,"pics/shop/tag.png",3,0,50,90-5);


        hboxes.add(hbox);
        root.getChildren().addAll(hbox);

        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Shop.getInstance().sell(account, textArea.getText());
                textArea.clear();
                daric.setText("Daric :"+account.getDaric());
            }
        });
        return hbox;
    }

    public static void buyCard(HBox hBox,Text daric,Account account){
        ArrayList<Card> cards =Shop.getInstance().getCards();
        VBox vBox = new VBox();//todo remember to delete
        for(int i=0;i<10;i++){
            if(i>=cards.size()) {
                break;
            }
            makeHBoxForCards(4,pageNumberCards,cards,daric,account);
        }


        ImageView backCircle = addImage(root, "pics/circle.png", 400, 750, 70, 70);
        ImageView back = addImage(root, "pics/back.png", 415, 765, 40, 40);

        ImageView nextCircle = addImage(root, "pics/circle.png", 1100, 750, 70, 70);
        ImageView next = addImage(root, "pics/next.png", 1115, 765, 40, 40);

        root.getChildren().addAll(vBox);

        back.setOnMouseClicked(event -> {
            pageNumberCards--;
            if (pageNumberCards < 0)
                pageNumberCards = 0;
            root.getChildren().removeAll(hboxes);
            root.getChildren().addAll(hBox);
         makeHBoxForCards(4,pageNumberCards,cards,daric,account);
        });
        next.setOnMouseClicked(event -> {
            pageNumberCards++;
            root.getChildren().removeAll(hboxes);
            root.getChildren().addAll(hBox);
            makeHBoxForCards(4,pageNumberCards,cards,daric,account);
        });

    }

    private static void makeHBoxForCards(int column, int pageNumber, ArrayList<Card> cards, Text daric, Account account){
        HBox hBox = new HBox();
        int startingBound = 2 * column * pageNumber;
        int j = -1;
        for (int i = startingBound; i < startingBound + 2 * column; i++) {
            Card card = cards.get(i);
            if (i >= cards.size())
                break;
            if (i % column == 0) {
                j++;
                hBox = new HBox();
                hBox.relocate(350,100+(j)*335);
                root.getChildren().addAll(hBox);
                hboxes.add(hBox);
                hBox.setSpacing(10);
            }
            Group group =CollectionScene.makeCardGroup(0,0,cards.get(i));
            hBox.getChildren().addAll(group);
            Text text = addText(group,card.getCost()+"",90,215,Color.WHITE,20);
            group.setOnMouseClicked(event -> {//todo set font
//                Shop.getInstance().sell(account,card.getCardId().getCardIdAsString());
                daric.setFont(Font.font("Chalkduster",30));
                daric.setText("Daric :"+account.getDaric());
            });
        }
    }

    private static void makeRectIcon(int width, int height, int numberOfRows,
                                     String iconName, int numberOfCulonm,
                                     int numberOfIcons, String typeOfFile, int upperNumber) {
        HBox hBox;
        int j = 0;
        ImageView imageIcon;
        for (int i = 0; i < numberOfRows; i++) {
            hBox = new HBox();
            hboxes.add(hBox);
            hBox.setSpacing(10);
            hBox.relocate(350, height * (i + 1) + 10 * i - upperNumber);
            root.getChildren().add(hBox);
            for (int k = 0; k < numberOfCulonm; k++) {
                j++;
                if (j > numberOfIcons)
                    return;
                imageIcon = addImage(hBox,
                        "pics/shop/" + iconName + "-" + j + typeOfFile, 0, 0, width, height);
            }
        }
    }

    public static void makeShopScene(Account account) {
        playMusic("resource/music/shop.m4a",true,shopScene);

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

        Rectangle rectangle = new Rectangle(20, 750, 220, 60);
        rectangle.setFill(Color.rgb(10, 10, 10));
        rectangle.setArcHeight(100);
        rectangle.setArcWidth(100);
        root.getChildren().add(rectangle);

        addImage(root, "pics/shop/diamond.png", 25, 750, 50, 50);
        Text daric=addText(root, "Daric: " + account.getDaric(),
                80, 775, Color.rgb(225, 225, 225, 0.5), 20);


        emote.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();
            makeRectIcon(200, 150, 4,
                    "emotes", 5, 20, ".png", 90);

        });
        orbs.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();
            makeRectIcon(200, 200, 2,
                    "orbs", 4, 7, ".png", 90);
        });


        battle_maps.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();
            makeRectIcon(200, 350, 2,
                    "battle_map", 4,
                    8, ".jpg", 300);
        });

        bundles.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();
            makeRectIcon(190, 150, 4,
                    "bundles", 5,
                    17, ".png", 90);
        });

        profile.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();
            makeRectIcon(190, 160, 4,
                    "profile", 5,
                    20, ".jpg", 90);
        });


        buyAndSellCard.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();

            HBox hBox= sellCard(daric,account);

            buyCard(hBox,daric,account);



        });

        search.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hboxes);
            hboxes.clear();
            HBox hBox = new HBox();

            Group groupText = new Group();

            addRectangle(groupText, 0, 0, 400, 90, 50, 50
                    , Color.rgb(0, 0, 0, 0.7));

            TextField textArea = new TextField();
            textArea.setPrefHeight(100);
            textArea.relocate(0, 0);
            textArea.positionCaret(1);
            textArea.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 20px; -fx-font-weight: bold;");
            textArea.setBackground(new Background(new BackgroundFill(
                    Color.rgb(5, 5, 5, 0.0001),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            groupText.getChildren().add(textArea);

            hBox.getChildren().addAll(groupText);
            hBox.relocate(600, 200);
            Group group = new Group();
            addRectangle(group, 0, 0, 80, 90, 50, 50
                    , Color.rgb(0, 0, 0, 0.7));


            ImageView magnifier = addImage(group,
                    "pics/shop/research.png", 16, 23, 50, 50);
            hBox.getChildren().addAll(group);

            magnifier.setOnMouseClicked(event1 -> {
                Group group1;
                String cardName = textArea.getText();
                textArea.clear();
                Object object = Shop.getInstance().search(account, cardName);
                if (object != null) {
                    if (object instanceof Card) {
                        group1 = CollectionScene.makeCardGroup(
                                500, 400, object);
                        root.getChildren().addAll(group1);
                        HBox hBox1 = new HBox();
                        hBox1.relocate(410, 350);
                        hBox1.getChildren().addAll(group1);
                        hboxes.add(hBox1);
                        root.getChildren().addAll(hBox1);
                        addText(group1, account.getUserName() + "_" + cardName + "_" +
                                        account.getCollection().getNumberOfCardId((Card) object),
                                50, 230, Color.WHITE, 20);
                    }
                }
            });


            root.getChildren().addAll(hBox);
            hboxes.add(hBox);
        });

        log(root,Shop.getInstance().help(),StageLauncher.getScene(StateType.MAIN_MENU),450);
    }



}
