package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.enums.StateType;
import view.sample.StageLauncher;


import static view.GeneralGraphicMethods.*;

public class ShopScene {
    private static Scene shopScene = StageLauncher.getScene(StateType.SHOP);
    private static Group root = (Group) shopScene.getRoot();

    private static Group makeShopIconBar(String path, String input) {
        Group group = new Group();
        ImageView icon = addImage(group, path, 20, 20, 100, 100);

        Text text = addText(group, input, 100, 60,
                Color.rgb(225, 225, 225, 0.5), 40);

        text.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Glow glow = new Glow();
                glow.setLevel(10);
                text.setEffect(glow);
            }
        });
        text.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setEffect(null);
            }
        });

        return group;


    }

    public static void makeShopScene() {
        setBackground(root, "pics/shop/shop_background.jpg", false, 15, 15);
        VBox sideVBox = new VBox();
        sideVBox.relocate(0, 0);

        sideVBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(10, 10, 10, 0.1),
                CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(sideVBox);

        root.getChildren().add(makeShopIconBar(
                "pics/shop/menu_icon_card_backs@2x.png", "hello"));


    }


}
