package view.Graphic;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.account.Account;
import view.enums.Cursor;
import view.enums.StateType;

import java.util.ArrayList;
import java.util.Objects;

import static view.Graphic.GeneralGraphicMethods.setCursor;

public class MainMenuScene {
    private static final MainMenuScene instance = new MainMenuScene();
    private static final Scene mainMenuScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) Objects.requireNonNull(mainMenuScene).getRoot();
    private static ArrayList<Node> menuNodes = new ArrayList<>();
    private Account account = null;

    private MainMenuScene() {}

    public static MainMenuScene getInstance() {
        return instance;
    }

    void makeMenu(Account account) {
        this.account = account;
        ImageView brand = GeneralGraphicMethods.addImage(root, "pics/login_pics/brand_duelyst@2x.png",
                130, 130, 1000 / 4, 216 / 4);
        menuNodes.add(brand);
        addLabelButtons();
    }

    private void addLabelButtons() {
        Label play = newLabelButton("PLAY", 250, 250);
        Label playShadow = addShadow(play);
        fadeAnimation(playShadow);
        play.setOnMouseEntered(event -> setCursor(mainMenuScene, Cursor.LIGHTEN));
        play.setOnMouseExited(event -> setCursor(mainMenuScene, Cursor.AUTO));
        Label collection = newLabelButton("COLLECTION", 250, 320);
        Label collectionShadow = addShadow(collection);
        root.getChildren().remove(collectionShadow);
        collection.setOnMouseEntered(event -> {
            root.getChildren().add(collectionShadow);
            setCursor(mainMenuScene, Cursor.LIGHTEN);
        });
        collectionShadow.setOnMouseExited(event -> {
            root.getChildren().remove(collectionShadow);
            setCursor(mainMenuScene, Cursor.AUTO);
        });
//        collection.setOnMouseClicked(event -> CollectionScene.);

    }

    private void fadeAnimation(Label playShadow) {
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long second = (long) Math.pow(10, 9);
            FadeTransition fade = new FadeTransition();

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (now > lastTime + second) {
                    lastTime = now;
                    fade.setDuration(Duration.millis(1000));
                    fade.setFromValue(10);
                    fade.setToValue(0.5);
                    fade.setAutoReverse(true);
                    fade.setNode(playShadow);
                    fade.play();
                }
            }
        };
        animationTimer.start();
    }

    private Label newLabelButton(String name, double x, double y) {
        Label button = new Label(name);
        button.relocate(x, y);
        button.setFont(Font.font("Lato Light", 35));
        button.setTextFill(Color.WHITE);
        addToNodes(button);
        return button;
    }

    private Label addShadow(Label label) {
        Label labelCopy = new Label(label.getText());
        labelCopy.relocate(label.getLayoutX(), label.getLayoutY());
        labelCopy.setFont(Font.font("Lato Light", 35));
        labelCopy.setTextFill(Color.WHITE);
        root.getChildren().remove(label);
        addToNodes(labelCopy);
        root.getChildren().add(label);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.WHITE);
        dropShadow.setOffsetX(0f);
        dropShadow.setOffsetY(0f);
        dropShadow.setHeight(50);
        dropShadow.setWidth(50);
        dropShadow.setSpread(0.6);
        labelCopy.setEffect(dropShadow);
        return labelCopy;
    }

    private void addToNodes(Node node) {
        root.getChildren().add(node);
        menuNodes.add(node);
    }
}
