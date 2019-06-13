package view.Graphic;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.HashMap;

import static view.Graphic.GeneralGraphicMethods.*;


class NewCardGraphic {
    private static HashMap<String, TextField> hashMap = new HashMap();
    private static Group enter;


    private static TextField textFieldMaker(int x, int y) {
        TextField textField = new javafx.scene.control.TextField();
        textField.setPrefHeight(50);
        textField.relocate(x + 100, -10);
        textField.positionCaret(1);
        textField.setStyle("-fx-text-fill: #a3b2cc; -fx-font-size: 20px; -fx-font-weight: bold;");
        textField.setBackground(new Background(
                new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                        CornerRadii.EMPTY, Insets.EMPTY)));
        return textField;
    }

    private static Group makeOneRowOfForm(String input, int x, int y) {
        Group group = new Group();
        group.relocate(x, y);
        addText(group, input, 20, 0, Color.rgb(225, 225, 225, 0.6), 20);
        TextField textField = textFieldMaker(200, 0);
        hashMap.put(input, textField);
        group.getChildren().addAll(textField);
        return group;

    }

    static void makeCardForm(Scene scene) {
        Group root = (Group) scene.getRoot();
        Rectangle rectangle = addRectangle(root, 0, 0, (int) StageLauncher.getWidth(), (int) StageLauncher.getHeight()
                , 0, 0, Color.rgb(0, 0, 0, 0.85));
        VBox vBox = new VBox();
        root.getChildren().addAll(vBox);
        vBox.relocate(200, 100);

        ImageView hero = addImage(root, "pics/other/hero.png", 150, 300, 200, 200);
        ImageView spell = addImage(root, "pics/other/spell.png", 450, 300, 200, 200);
        ImageView minion = addImage(root, "pics/other/minion.png", 750, 300, 200, 200);
        ImageView buff = addImage(root, "pics/other/buff.png", 1050, 300, 200, 200);
        setOnMouseEntered(hero, scene, true);
        setOnMouseEntered(spell, scene, true);
        setOnMouseEntered(minion, scene, true);
        setOnMouseEntered(buff, scene, true);

        hero.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hero, spell, minion, buff);
            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("type", 200, 270 - 20),
                    makeOneRowOfForm("Mp", 200, 340 - 20),
                    makeOneRowOfForm("Ap", 200, 410 - 20),
                    makeOneRowOfForm("Hp", 200, 480 - 20),
                    makeOneRowOfForm("Attack Type", 200, 550 - 20),
                    makeOneRowOfForm("range", 200, 620 - 20),
                    makeOneRowOfForm("specialPower", 200, 690 - 20),
                    makeOneRowOfForm("special power cooldown", 200, 760 - 20),
                    makeOneRowOfForm("cost", 200, 830 - 20));
            enter(root, scene);
        });
        minion.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hero, spell, minion, buff);
            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("type", 200, 270 - 20),
                    makeOneRowOfForm("Mp", 200, 340 - 20),
                    makeOneRowOfForm("Ap", 200, 410 - 20),
                    makeOneRowOfForm("Hp", 200, 480 - 20),
                    makeOneRowOfForm("Attack Type", 200, 550 - 20),
                    makeOneRowOfForm("range", 200, 620 - 20),
                    makeOneRowOfForm("specialPower(spell name)", 200, 690 - 20),
                    makeOneRowOfForm("special power activation", 200, 760 - 20),
                    makeOneRowOfForm("cost", 200, 830 - 20));
            enter(root, scene);
        });
        spell.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hero, spell, minion, buff);

            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("type", 200, 270 - 20),
                    makeOneRowOfForm("Mp", 200, 340 - 20),
                    makeOneRowOfForm("target", 200, 410 - 20),
                    makeOneRowOfForm("buff", 200, 480 - 20),
                    makeOneRowOfForm("cost", 200, 550 - 20));
            enter(root, scene);
        });
        buff.setOnMouseClicked(event -> {
            root.getChildren().removeAll(hero, spell, minion, buff);
            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("buff type", 200, 270 - 20),
                    makeOneRowOfForm("effect value", 200, 340 - 20),
                    makeOneRowOfForm("delay", 200, 410 - 20),
                    makeOneRowOfForm("last", 200, 480 - 20),
                    makeOneRowOfForm("friend or enemy", 200, 550 - 20));
            enter(root, scene);
        });


        if (enter != null) {
            enter.setOnMouseClicked(event -> {
                //todo get hash map


                enter = null;
            });
        }

        ImageView close = addImage(root, "pics/menu/button_close@2x.png",
                StageLauncher.getWidth() - 70, 0, 60, 60);
        close.setOnMouseClicked(event -> root.getChildren().removeAll(rectangle, vBox,
                close, buff, hero, spell, minion, enter));


    }

    private static void enter(Group root, Scene scene) {
        enter = new Group();
        enter.relocate(1100, 700);
        root.getChildren().addAll(enter);
        addImage(enter, "pics/menu/quit.png", 0, 0, 200, 80);
        Text text = addText(enter, "ENTER", 70, 30,
                Color.rgb(225, 225, 225, 0.7), 30);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        text.setStroke(Color.rgb(225, 225, 225, 0.3));
        setOnMouseEntered(enter, scene, true);
    }


}
