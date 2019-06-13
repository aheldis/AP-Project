package view.Graphic;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

import static view.Graphic.GeneralGraphicMethods.*;


public class NewCardGraphic {
    private static HashMap<String, TextField> hashMap = new HashMap();

    private static TextField textFieldMaker(int x, int y) {
        TextField textField = new javafx.scene.control.TextField();
        textField.setPrefHeight(50);
        textField.relocate(x, y);
        textField.positionCaret(1);
        textField.setStyle("-fx-text-fill: #a3b2cc; -fx-font-size: 25px; -fx-font-weight: bold;");
        textField.setBackground(new Background(
                new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                        CornerRadii.EMPTY, Insets.EMPTY)));
        return textField;
    }

    private static Group makeOneRowOfForm(String input, int x, int y) {
        Group group = new Group();
        group.relocate(x, y);

        addText(group, input, 0, 0, Color.rgb(225, 225, 225, 0.6), 20);
        TextField textField = textFieldMaker(30, 10);
        hashMap.put(input, textField);

        group.getChildren().addAll(textField);
        return group;

    }

    public static void makeCardForm(Scene scene) {
        Group root = new Group();



        ImageView hero = addImage(root, "pics/other/hero.png", 150, 300, 200, 200);
        ImageView spell = addImage(root, "pics/other/spell.png", 450, 300, 200, 200);
        ImageView minion = addImage(root, "pics/other/minion.png", 750, 300, 200, 200);
        ImageView buff = addImage(root, "pics/other/buff.png", 1050, 300, 200, 200);

        hero.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().removeAll(hero, spell, minion);

                VBox vBox = new VBox();

                vBox.getChildren().addAll(
                        makeOneRowOfForm("name",200,200-20 ),
                        makeOneRowOfForm("type", 200,270-20),
                        makeOneRowOfForm("Mp", 200,340-20),
                        makeOneRowOfForm("Ap",200,410 -20),
                        makeOneRowOfForm("Hp",200,480 -20),
                        makeOneRowOfForm("Attack Type",200,550-20 ),
                        makeOneRowOfForm("range",200,620-20 ),
                        makeOneRowOfForm("specialPower", 200,690-20),
                        makeOneRowOfForm("special power cooldown",200,760-20 ),
                        makeOneRowOfForm("cost",200,830 -20));
            }
        });
        minion.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().removeAll(hero, spell, minion);

                VBox vBox = new VBox();
                vBox.getChildren().addAll(
                        makeOneRowOfForm("name", 200,200-20),
                        makeOneRowOfForm("type", 200,270-20),
                        makeOneRowOfForm("Mp",200,340-20 ),
                        makeOneRowOfForm("Ap", 200,410-20),
                        makeOneRowOfForm("Hp",200,480-20 ),
                        makeOneRowOfForm("Attack Type",200,550-20 ),
                        makeOneRowOfForm("range",200,620-20 ),
                        makeOneRowOfForm("specialPower(spell name)",200,690-20 ),
                        makeOneRowOfForm("special power activation",200,760-20 ),
                        makeOneRowOfForm("cost", 200,830-20));
            }
        });
        spell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().removeAll(hero, spell, minion);
                VBox vBox = new VBox();
                vBox.getChildren().addAll(
                        makeOneRowOfForm("name",200,200-20 ),
                        makeOneRowOfForm("type", 200,270-20),
                        makeOneRowOfForm("Mp",200,340-20 ),
                        makeOneRowOfForm("target",200,410-20 ),
                        makeOneRowOfForm("buff",200,480-20 ),
                        makeOneRowOfForm("cost",200,550-20 ));
            }
        });
        buff.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                root.getChildren().removeAll(hero, spell, minion);
                VBox vBox = new VBox();
                vBox.getChildren().addAll(
                        makeOneRowOfForm("name",200,200-20 ),
                        makeOneRowOfForm("buff type",200,270-20 ),
                        makeOneRowOfForm("effect value", 200,340-20),
                        makeOneRowOfForm("delay", 200,410-20),
                        makeOneRowOfForm("last", 200,480-20),
                        makeOneRowOfForm("friend or enemy", 200,550-20));
            }
        });


    }


}
