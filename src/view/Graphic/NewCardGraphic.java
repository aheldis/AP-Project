package view.Graphic;

import controller.RequestEnum;
import controller.Transmitter;
import controller.client.TransferController;
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
import model.account.FilesType;
import view.enums.StateType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static view.Graphic.GeneralGraphicMethods.*;

public class NewCardGraphic {
    private static ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
    private static int numberOfBuffs;
    private static boolean firstTimeIndEquals3 = true;
    private static ArrayList<HashMap<String, TextField>> hashMapsWithTextField = new ArrayList<>(); //Be tartib: 0: khod card - 1: change - 2: target - 3 be bad buffHa
    private static ArrayList<HashMap<String, String>> hashMapsWithStrings = new ArrayList<>(); //Be tartib: 0: khod card - 1: change - 2: target - 3 be bad buffHa
    private static StackPane enter;
    private static Scene scene;
    private static Group group;
    private static int numberOfHashMap;
    private static Text error = new Text();
    private static int spriteNumber = 0;
    private static int spriteNumberCount = 10;

    public static void setError(String text) {
        error.setText(text);
    }

    private static BorderPane makeOneRowOfForm(String input, int x, int y, int numberOfHashMap) {
        if (hashMapsWithTextField.size() == numberOfHashMap) {
            hashMapsWithTextField.add(new HashMap<>());
        }

        Text fieldName = addText(new Group(), 20, 0, input, Color.rgb(250, 250, 250, 0.8), 20);
        BorderPane.setMargin(fieldName, new Insets(4, 5, 2, 5));

        StackPane stackPane = new StackPane();
        addRectangle(stackPane, 0, 0, 200, 30, 5, 5, Color.rgb(225, 225, 225, 0.3));
        TextField textField = new TextField();
        textField.setPrefHeight(30);
        textField.setPrefWidth(200);
        textField.positionCaret(0);
        textField.setStyle("-fx-text-fill: #a3b2cc; -fx-font-size: 20px; -fx-font-weight: bold;");
        textField.setBackground(new Background(
                new BackgroundFill(Color.TRANSPARENT,
                        CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.getChildren().add(textField);
        stackPane.setAlignment(Pos.CENTER);
        BorderPane.setMargin(stackPane, new Insets(2, 5, 2, 5));

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(stackPane);
        borderPane.setLeft(fieldName);
        BorderPane.setAlignment(textField, Pos.CENTER);

        hashMapsWithTextField.get(numberOfHashMap).put(input, textField);
        group.getChildren().add(borderPane);
        return borderPane;
    }

    private static VBox addImageAndText(String name, double x, double y) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        addImage(vBox, "pics/other/" + name + ".png", x, y, 200, 200);
        addTextWithShadow(vBox, x, y, name, "Beyond Wonderland", 40);
        group.getChildren().add(vBox);
        vBox.relocate(x, y);
        setOnMouseEntered(vBox, scene, true);
        return vBox;
    }

    private static void chooseSprite(VBox vBox, String type) {
        vBox.getChildren().clear();
        group.getChildren().remove(enter);

        String folderPath = "pics/" + Objects.requireNonNull(FilesType.getEnum(type)).getName() + "/defaults/";

        Group images = new Group();
        group.getChildren().add(images);
        int x = 200, y = 250;
        for (int i = 0; i < 4; i++) {
            if (type.equals("spell")) {
                y = 300;
                x += 47;
            }
            addPicture(images, type, folderPath, i, x, y);

            x += 250;
        }
    }

    private static int getCount(String folderPath, int number) {
        try {
            FileReader fileReader = new FileReader(folderPath + "counts.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                int index = (int) line.charAt(0) - (int) '0';
                if (index == number)
                    return Integer.parseInt(line.substring(2));
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void addPicture(Group images, String type, String folderPath, int number, int x, int y) {
        ImageView imageView;
        if (FilesType.getEnum(type) == FilesType.SPELL) {
            int count = getCount(folderPath, number);
            imageView = SpriteMaker.getInstance().makeSpritePic(folderPath + number + ".png",
                    x, y, images, count,
                    5, 3000,
                    48, 48);


        } else {
            imageView = addImage(images, folderPath + number + ".gif", x, y, 250, 250);
        }
        setOnMouseEntered(imageView, scene, true);
        imageView.setOnMouseClicked(event -> {
            spriteNumber = number;
            spriteNumberCount = getCount(folderPath, number);
            done(images, type);
        });
    }

    private static void fillHashMapsWithString() {
        for (HashMap<String, TextField> hashMap : hashMapsWithTextField) {
            HashMap<String, String> newHashMap = new HashMap<>();
            for (String name : hashMap.keySet()) {
                newHashMap.put(name, hashMap.get(name).getText());
            }
            hashMapsWithStrings.add(newHashMap);
        }
    }

    private static void done(Group images, String type) {
        images.getChildren().clear();
        group.getChildren().remove(enter);
        fillHashMapsWithString();
        Transmitter transmitter = new Transmitter();
        transmitter.spriteNumber = spriteNumber;
        transmitter.spriteNumberCount = spriteNumberCount;
        transmitter.hashMapsWithStrings = hashMapsWithStrings;
        transmitter.type = type;
        transmitter = TransferController.main(RequestEnum.MAKE_NEW_CARD, transmitter);

//        if (error.getText() != null && !error.getText().equals("")) {
//            error = addText(group, 120, 130, error.getText(), Color.RED, 30);
//        } else {
        if (transmitter.errorType == null) {
            addText(group, 120, 130, "Card successfully created.", Color.WHITE, 30);
        }
    }

    private static void changeVBox(VBox vBox, ArrayList<String> arrayList, int ind, String type) {
        vBox.getChildren().clear();
        arrayList.forEach(name -> {
            if (name.contains("__")) {
                Text text = addTextWithShadow(vBox, 0, 0, name.substring(2), "Arial", 20);
                VBox.setMargin(text, new Insets(2, 5, 10, 5));
            } else {
                vBox.getChildren().add(makeOneRowOfForm(name, 0, 0, numberOfHashMap));
            }
        });
        numberOfHashMap++;
        enter.setOnMouseClicked(event -> {
            if (ind == 3) {
                if (firstTimeIndEquals3) {
                    try {
                        numberOfBuffs = Integer.parseInt(hashMapsWithTextField.get(1).get("number of buffs").getText());
                    } catch (Exception e) {
                        numberOfBuffs = 0;
                    }
                    firstTimeIndEquals3 = false;
                }
                if (numberOfBuffs > 0) {
                    numberOfBuffs--;
                    changeVBox(vBox, arrayLists.get(ind), ind, type);
                } else {
                    chooseSprite(vBox, type);
                }
            } else {
                if (ind == 1 && arrayLists.get(1) == null || arrayLists.get(1).size() == 0)
                    chooseSprite(vBox, type);
                else
                    changeVBox(vBox, arrayLists.get(ind), ind + 1, type);
            }
        });
    }

    private static void setVBox(VBox vBox, String type) {
        Transmitter transmitter = new Transmitter();
        transmitter.type = type;
        transmitter = TransferController.main(RequestEnum.NEW_CARD_ARRAYLISTS, transmitter);
        arrayLists.clear();
        arrayLists.add(transmitter.fieldNames);
        arrayLists.add(transmitter.changeFieldNames);
        arrayLists.add(transmitter.targetFieldNames);
        arrayLists.add(transmitter.buffFieldNames);
        changeVBox(vBox, arrayLists.get(0), 1, type);
    }

    static void makeCardForm() {
        firstTimeIndEquals3 = true;
        arrayLists.clear();
        hashMapsWithTextField.clear();
        numberOfHashMap = 0;
        Scene scene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
        NewCardGraphic.scene = scene;
        group = new Group();
        assert scene != null;
        ((Group) scene.getRoot()).getChildren().add(group);

        addRectangleStroke(group, (int) StageLauncher.getWidth() - 160, (int) StageLauncher.getHeight() - 160,
                true, Color.rgb(40, 100, 250, 0.5));

        ImageView close = addImage(group, "pics/menu/button_close@2x.png",
                1270, 50, 60, 60);
        close.setOnMouseClicked(event -> ((Group) scene.getRoot()).getChildren().remove(group));


        VBox vBox = new VBox();
        vBox.relocate(150, 125);
        vBox.setAlignment(Pos.CENTER_LEFT);

        group.getChildren().add(vBox);

        VBox hero = addImageAndText("hero", 240, 300);
        VBox spell = addImageAndText("spell", 580, 300);
        VBox minion = addImageAndText("minion", 920, 300);

        hero.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);
            enter(group, scene);
            setVBox(vBox, "hero");
        });
        minion.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);
            enter(group, scene);
            setVBox(vBox, "minion");

        });
        spell.setOnMouseClicked(event -> {
            group.getChildren().removeAll(hero, spell, minion);
            enter(group, scene);
            setVBox(vBox, "spell");

        });
    }

    static void addRectangleStroke(Group group, int width, int height, boolean whiteBackground, Color strokeColor) {
        if (whiteBackground)
            addRectangle(group, 0, 0, (int) StageLauncher.getWidth(), (int) StageLauncher.getHeight(),
                    0, 0, Color.rgb(255, 255, 255, 0.2));
        Rectangle rectangle = addRectangle(group, ((int) StageLauncher.getWidth() - width) / 2, ((int) StageLauncher.getHeight() - height) / 2,
                width, height
                , 50, 50, Color.rgb(0, 0, 0, 0.85));
        rectangle.setStroke(strokeColor);
        rectangle.setStrokeWidth(7);
    }

    private static void enter(Group root, Scene scene) {
        enter = new StackPane();
        enter.setAlignment(Pos.CENTER);
        enter.relocate(1130, 720);
        root.getChildren().addAll(enter);
        addImage(enter, "pics/menu/quit.png", 0, 0, 200, 80);
        Text text = addText(enter, 70, 30, "ENTER",
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
