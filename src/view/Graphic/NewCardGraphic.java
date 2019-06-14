package view.Graphic;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.account.Account;
import model.account.FilesType;
import model.card.makeFile.NewFileAsli;

import java.util.ArrayList;
import java.util.HashMap;

import static view.Graphic.GeneralGraphicMethods.*;
import static view.Graphic.GeneralGraphicMethods.setOnMouseEntered;

public class NewCardGraphic {
    private static HashMap<String, TextField> hashMap = new HashMap();
    private static StackPane enter;
    private static Scene scene;
    private static Group group;

    private static TextField textField;
    private static Boolean textFieldReady = false;
    private static Text fieldName;
    private static Text description;
    private static Text error;

    //TODO _____________ DONT TOUCH PLEASEEEE

   /* public static void changeText(String text) {
        System.out.println("changeText " + text);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fieldName.setText(text);
                textField.clear();
                textFieldReady = false;
            }
        });
    }

    public static String getTextOfTextField() {
        while (!textFieldReady)
        {}
        return textField.getText();
    }

*/
    private static HBox makeOneRowOfForm(String input, int x, int y) {
        HBox hbox = new HBox();
        fieldName = addText(hbox, input, 20, 0, Color.rgb(250, 250, 250, 0.8), 20);
        StackPane stackPane = new StackPane();
        addRectangle(stackPane, 0, 0, 200, 30, 5, 5, Color.rgb(225, 225, 225, 0.3));
        textField = new TextField();
        textField.setPrefHeight(30);
        textField.setPrefWidth(200);
        textField.positionCaret(0);
        textField.setStyle("-fx-text-fill: #a3b2cc; -fx-font-size: 20px; -fx-font-weight: bold;");
        textField.setBackground(new Background(
                new BackgroundFill(Color.TRANSPARENT,
                        CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.getChildren().add(textField);

        hbox.getChildren().add(stackPane);
        ImageView imageView = addImage(hbox, "pics/menu/Confirm-512.png", 0, 0, 30, 30);
        setOnMouseEntered(imageView, scene, true);
        //imageView.setOnMouseClicked(event -> {textFieldReady = true; });

        hbox.relocate(x, y);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().forEach(node -> HBox.setMargin(node, new Insets(5, 5, 5, 5)));
        group.getChildren().add(hbox);
        return hbox;
    }


    private static void f(Account account, String type) {
        makeOneRowOfForm("blah blah", 150, 100);
        NewFileAsli.makeNewCard(account, FilesType.getEnum(type));
    }

    private static VBox addImageAndText(String name, double x, double y) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        addImage(vBox, "pics/other/" + name + ".png", x, y, 200, 200);
        addTextWithShadow(name, x, y, vBox, "Beyond Wonderland", 40);
        group.getChildren().add(vBox);
        vBox.relocate(x, y);
        setOnMouseEntered(vBox, scene, true);
        return vBox;
    }

    static void makeCardForm(Scene scene, Account account) {
        account = null;
        NewCardGraphic.scene = scene;
        group = new Group();
        ((Group) scene.getRoot()).getChildren().add(group);

        Rectangle rectangle = addRectangle(group, 80, 80, (int) StageLauncher.getWidth() - 160, (int) StageLauncher.getHeight() - 160
                , 50, 50, Color.rgb(0, 0, 0, 0.85));
        rectangle.setStroke(Color.rgb(40, 100, 250));
        rectangle.setStrokeWidth(7);

        ImageView close = addImage(group, "pics/menu/button_close@2x.png",
                1270, 50, 60, 60);
        close.setOnMouseClicked(event -> ((Group) scene.getRoot()).getChildren().remove(group));


        VBox vBox = new VBox();
        group.getChildren().addAll(vBox);
        vBox.relocate(200, 100);

        //f(account, "hero");

        VBox hero = addImageAndText("hero", 240, 300);
        VBox spell = addImageAndText("spell", 580, 300);
        VBox minion = addImageAndText("minion", 920, 300);

        hero.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);
            ArrayList<String> fieldNames =  NewFileAsli.getFieldNames(FilesType.getEnum("hero"));
            fieldNames.forEach(name -> {
                vBox.getChildren().add(makeOneRowOfForm(name, 0, 0));

            });

            /*
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
                    */
            enter(group, scene);

        });
        minion.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);
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
            enter(group, scene);
        });
        spell.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);

            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("type", 200, 270 - 20),
                    makeOneRowOfForm("Mp", 200, 340 - 20),
                    makeOneRowOfForm("target", 200, 410 - 20),
                    makeOneRowOfForm("buff", 200, 480 - 20),
                    makeOneRowOfForm("cost", 200, 550 - 20));
            enter(group, scene);
        });
        /*
        buff.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);
            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("buff type", 200, 270 - 20),
                    makeOneRowOfForm("effect value", 200, 340 - 20),
                    makeOneRowOfForm("delay", 200, 410 - 20),
                    makeOneRowOfForm("last", 200, 480 - 20),
                    makeOneRowOfForm("friend or enemy", 200, 550 - 20));
            enter(group, scene);
        });
*/

        if (enter != null) {
            enter.setOnMouseClicked(event -> {
                //todo get hash map


                enter = null;
            });
        }


    }

    private static void enter(Group root, Scene scene) {
        enter = new StackPane();
        enter.setAlignment(Pos.CENTER);
        enter.relocate(1130, 720);
        root.getChildren().addAll(enter);
        addImage(enter, "pics/menu/quit.png", 0, 0, 200, 80);
        Text text = addText(enter, "ENTER", 70, 30,
                Color.rgb(225, 225, 225, 0.7), 30);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        text.setStroke(Color.rgb(225, 225, 225, 0.3));
        setOnMouseEntered(enter, scene, true);
    }

}

/*

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
        Group group = (Group) scene.getRoot();
        Rectangle rectangle = addRectangle(group, 0, 0, (int) StageLauncher.getWidth(), (int) StageLauncher.getHeight()
                , 0, 0, Color.rgb(0, 0, 0, 0.85));
        VBox vBox = new VBox();
        group.getChildren().addAll(vBox);
        vBox.relocate(200, 100);

        ImageView hero = addImage(group, "pics/other/hero.png", 150, 300, 200, 200);
        ImageView spell = addImage(group, "pics/other/spell.png", 450, 300, 200, 200);
        ImageView minion = addImage(group, "pics/other/minion.png", 750, 300, 200, 200);
        ImageView buff = addImage(group, "pics/other/buff.png", 1050, 300, 200, 200);
        setOnMouseEntered(hero, scene, true);
        setOnMouseEntered(spell, scene, true);
        setOnMouseEntered(minion, scene, true);
        setOnMouseEntered(buff, scene, true);

        hero.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion, buff);
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
            enter(group, scene);
        });
        minion.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion, buff);
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
            enter(group, scene);
        });
        spell.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion, buff);

            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("type", 200, 270 - 20),
                    makeOneRowOfForm("Mp", 200, 340 - 20),
                    makeOneRowOfForm("target", 200, 410 - 20),
                    makeOneRowOfForm("buff", 200, 480 - 20),
                    makeOneRowOfForm("cost", 200, 550 - 20));
            enter(group, scene);
        });
        buff.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion, buff);
            vBox.getChildren().addAll(
                    makeOneRowOfForm("name", 200, 200 - 20),
                    makeOneRowOfForm("buff type", 200, 270 - 20),
                    makeOneRowOfForm("effect value", 200, 340 - 20),
                    makeOneRowOfForm("delay", 200, 410 - 20),
                    makeOneRowOfForm("last", 200, 480 - 20),
                    makeOneRowOfForm("friend or enemy", 200, 550 - 20));
            enter(group, scene);
        });


        if (enter != null) {
            enter.setOnMouseClicked(event -> {
                //todo get hash map


                enter = null;
            });
        }

        ImageView close = addImage(group, "pics/menu/button_close@2x.png",
                StageLauncher.getWidth() - 70, 0, 60, 60);
        close.setOnMouseClicked(event -> group.getChildren().removeAll(rectangle, vBox,
                close, buff, hero, spell, minion, enter));


    }

    private static void enter(Group group, Scene scene) {
        enter = new Group();
        enter.relocate(1100, 700);
        group.getChildren().addAll(enter);
        addImage(enter, "pics/menu/quit.png", 0, 0, 200, 80);
        Text text = addText(enter, "ENTER", 70, 30,
                Color.rgb(225, 225, 225, 0.7), 30);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        text.setStroke(Color.rgb(225, 225, 225, 0.3));
        setOnMouseEntered(enter, scene, true);
    }

}*/
