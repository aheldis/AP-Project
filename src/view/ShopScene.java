package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.account.Account;
import view.enums.StateType;
import view.sample.StageLauncher;


import static view.GeneralGraphicMethods.*;

public class ShopScene {
    private static Scene shopScene = StageLauncher.getScene(StateType.SHOP);
    private static Group root = (Group) shopScene.getRoot();

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

    public static void makeRectIcon(int width,int numberOfRows,String iconName,int numberOfIcons){
        HBox hBox;
        int j=0;
        for(int i=0;i<numberOfRows;i++) {
            hBox = new HBox();
            ImageView imageIcon = addImage(hBox,
                    "pics/shop/"+iconName+"-"+j+".png",0,0,width,width);


        }
    }

    public static void makeShopScene(Account account) {
        setBackground(root, "pics/shop/shop_background.jpg", false, 15, 15);
        VBox sideVBox = new VBox();
        sideVBox.relocate(0, 0);

        sideVBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(10, 10, 10, 0.1),
                CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(sideVBox);

        root.getChildren().add(makeShopIconBar(
                "pics/shop/buy_card.png", "Buy Card", 0));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/sellCard.png", "Sell Card", 1));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/research.png", "Search", 2
        ));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/emotes.png", "Emotes", 3));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/profile_icon.png", "Profile Icon", 4
        ));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/battle_map.png", "Battle Maps", 5));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/friends.png", "Bundles", 6
        ));

        root.getChildren().add(makeShopIconBar(
                "pics/shop/spirit_orb.png", "Spirit Orbs", 7
        ));

        Rectangle rectangle = new Rectangle(20, 840, 220, 60);
        rectangle.setFill(Color.rgb(10, 10, 10));
        rectangle.setArcHeight(100);
        rectangle.setArcWidth(100);
        root.getChildren().add(rectangle);

        addImage(root, "pics/shop/diamond.png", 25, 840, 50, 50);
        addText(root, "Daric: " + account.getDaric(),
                80, 865, Color.rgb(225, 225, 225, 0.5), 20);


    }


}
