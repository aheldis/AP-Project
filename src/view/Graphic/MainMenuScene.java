package view.Graphic;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.account.Account;
import model.account.AllAccount;
import model.battle.MatchInfo;
import view.enums.Cursor;
import view.enums.StateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static view.Graphic.GeneralGraphicMethods.*;

public class MainMenuScene {
    private static final MainMenuScene instance = new MainMenuScene();
    private static final Scene mainMenuScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) Objects.requireNonNull(mainMenuScene).getRoot();
    private static ArrayList<Node> menuNodes = new ArrayList<>();
    private static ImageView playGraph = null;
    private static ImageView collectionGraph = null;
    private static ImageView matchHistoryGraph = null;
    private static HashMap<ImageView, Integer> graphs = new HashMap<>();
    private Account account = null;

    private MainMenuScene() {
    }

    public static MainMenuScene getInstance() {
        return instance;
    }

    void makeMenu(Account account) {
        this.account = account;
        ImageView brand = addImage(root, "pics/login_pics/brand_duelyst@2x.png",
                130, 130, 1000 / 4.0, 216 / 4.0);
        menuNodes.add(brand);
        addGraphsAndLabelButtons();
        addImageButtons();
    }

    private void addGraphsAndLabelButtons() {
        Random random = new Random();
        int randomNumber = random.nextInt(16) + 1;
        playGraph = addImage(root, "pics/menu/" + randomNumber + ".png", 180, 240, 70, 70);
        playGraph.setOpacity(0.5);
        menuNodes.add(playGraph);
        graphs.put(playGraph, 0);
        randomNumber = random.nextInt(16) + 1;
        collectionGraph = addImage(root, "pics/menu/" + randomNumber + ".png", 180, 310, 70, 70);
        collectionGraph.setOpacity(0.5);
        menuNodes.add(collectionGraph);
        graphs.put(collectionGraph, 0);
        randomNumber = random.nextInt(16) + 1;
        matchHistoryGraph = addImage(root, "pics/menu/" + randomNumber + ".png", 180, 380, 70, 70);
        matchHistoryGraph.setOpacity(0.5);
        menuNodes.add(matchHistoryGraph);
        graphs.put(matchHistoryGraph, 0);
        addLabelButtons();
    }

    private void addImageButtons() {
        double x = StageLauncher.getWidth() - 410;
        double y = StageLauncher.getHeight() - 200;
        newImageButton("pics/menu/profile@2x.png", " PROFILE", x, y);
        x += 100;
        newImageButton("pics/menu/armory@2x.png", "    SHOP", x, y);
        x += 100;
        newImageButton("pics/menu/settings@2x.png", "SETTINGS", x, y);
        x += 100;
        newImageButton("pics/menu/quests@2x.png", "NEW CARD", x, y);
    }

    private void addLabelButtons() {
        Label play = newLabelButton("PLAY", 250);
        Label playShadow = addShadow(play);
        fadeAnimation(playShadow);
        AnimationTimer playAnimation = graphAnimation(playGraph);
        play.setOnMouseEntered(event -> {
            setCursor(mainMenuScene, Cursor.LIGHTEN);
            playAnimation.start();
        });
        play.setOnMouseExited(event -> {
            setCursor(mainMenuScene, Cursor.AUTO);
            playAnimation.stop();
            playGraph.setOpacity(0.5);
        });
        play.setOnMouseClicked(event -> {
            SelectGameScene.selectGame(account);
            playAnimation.stop();
            setScene(StateType.SELECT_GAME);
        });
        Label collection = newLabelButton("COLLECTION", 320);
        AnimationTimer collectionAnimation = graphAnimation(collectionGraph);
        Label collectionShadow = shadowAnimation(collection, collectionAnimation);
        collectionShadow.setOnMouseClicked(event -> {
            CollectionScene.showInCollection(account.getCollection());
            collectionAnimation.stop();
            setScene(StateType.COLLECTION);
        });

        Label matchHistory = newLabelButton("MATCH HISTORY", 390);
        AnimationTimer matchHistoryAnimation = graphAnimation(matchHistoryGraph);
        Label matchHistoryShadow = shadowAnimation(matchHistory, matchHistoryAnimation);
        matchHistoryShadow.setOnMouseClicked(event -> {
            SelectGameScene.selectGame(account);
            matchHistoryAnimation.stop();
            showMatchHistory();
        });
    }

    private Label shadowAnimation(Label label, AnimationTimer animation) {
        Label labelShadow = addShadow(label);
        root.getChildren().remove(labelShadow);
        label.setOnMouseEntered(event -> {
            root.getChildren().add(labelShadow);
            setCursor(mainMenuScene, Cursor.LIGHTEN);
            animation.start();
        });
        labelShadow.setOnMouseExited(event -> {
            root.getChildren().remove(labelShadow);
            setCursor(mainMenuScene, Cursor.AUTO);
            animation.stop();
            if (label.getText().equals("COLLECTION"))
                collectionGraph.setOpacity(0.5);
            else
                matchHistoryGraph.setOpacity(0.5);
        });
        return labelShadow;
    }

    private void newImageButton(String path, String name, double x, double y) {
        ImageView image = addImage(root, path, x, y, 70, 70);
        menuNodes.add(image);
        Glow glow = new Glow(0);
        image.setEffect(glow);
        Label label = new Label(name);
        label.relocate(x - 2, y + 80);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(17));
        label.setEffect(glow);
        addToNodes(label);
        workWithMouse(image, glow, name);
        workWithMouse(label, glow, name);
    }

    private Label newLabelButton(String name, double y) {
        Label button = new Label(name);
        button.relocate((double) 250, y);
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

    private void fadeAnimation(Label playShadow) {
        AnimationTimer animationTimer = new AnimationTimer() {
            FadeTransition fade = new FadeTransition();
            private long lastTime = 0;
            private long second = (long) Math.pow(10, 9);

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

    private AnimationTimer graphAnimation(ImageView firstGraph) {
        return new AnimationTimer() {
            private long lastTime = 0;
            private long second = (long) Math.pow(10, 9);
            private ImageView graph = firstGraph;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (now > lastTime + second / 6) {
                    lastTime = now;
                    root.getChildren().remove(graph);
                    menuNodes.remove(graph);
                    ImageView newGraph = addImage(root,
                            "pics/menu/" + ((graphs.get(graph) + 1) % 16 + 1) + ".png",
                            graph.getLayoutX(), graph.getLayoutY(), graph.getFitWidth(), graph.getFitHeight());
                    menuNodes.add(newGraph);
                    graphs.put(newGraph, (graphs.get(graph) + 1) % 16);
                    graphs.remove(graph);
                    if (graph.equals(playGraph))
                        playGraph = newGraph;
                    else if (graph.equals(collectionGraph))
                        collectionGraph = newGraph;
                    else
                        matchHistoryGraph = newGraph;
                    graph = newGraph;
                }
            }
        };
    }

    public void showMatchHistory() {
        Group matchHistoryGroup = new Group();
        root.getChildren().add(matchHistoryGroup);
        NewCardGraphic.addRectangleStroke(matchHistoryGroup);


        Text header = new Text("MATCH HISTORY");
        header.setFill(Color.rgb(40, 100, 250, 1));
        header.setStroke(Color.WHITE);
        header.setStrokeWidth(0.25);
        header.setFont(Font.font("Chalkduster", 50));
        header.relocate(490, 100);
        matchHistoryGroup.getChildren().add(header);

        GridPane gridPane = new GridPane();
        addNodeToGridPane(gridPane, 0, 0, "Winner", true);
        addNodeToGridPane(gridPane, 0, 1, "Loser", true);
        addNodeToGridPane(gridPane, 0, 2, "Date", true);

        ArrayList<MatchInfo> matchHistory = account.getMatchHistory();
        int index = 1;
        for (MatchInfo matchInfo : matchHistory) {

            addNodeToGridPane(gridPane, index, 0, matchInfo.winner, false);
            addNodeToGridPane(gridPane, index, 1, matchInfo.loser, false);
            addNodeToGridPane(gridPane, index, 2, matchInfo.date.toString(), false);
            index++;
        }

        matchHistoryGroup.getChildren().add(gridPane);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.relocate(300, 180);


        ImageView close = addImage(matchHistoryGroup, "pics/menu/button_close@2x.png",
                1270, 50, 60, 60);
        close.setOnMouseClicked(event -> {
            root.getChildren().remove(matchHistoryGroup);
        });

    }

    private void addToNodes(Node node) {
        root.getChildren().add(node);
        menuNodes.add(node);
    }

    private void workWithMouse(Node node, Glow glow, String name) {
        node.setOnMouseEntered(event -> {
            setCursor(mainMenuScene, Cursor.LIGHTEN);
            glow.setLevel(3);
        });
        node.setOnMouseExited(event -> {
            setCursor(mainMenuScene, Cursor.AUTO);
            glow.setLevel(0);
        });
        node.setOnMouseClicked(event -> buttonAction(name));

    }

    private void addNodeToGridPane(GridPane gridPane, int row, int column, String textString, boolean headerRow) {
        StackPane stackPane = new StackPane();
        Text text;
        if (headerRow)
            text = addTextWithShadow(textString, 0, 0, new Group(),
                    "Chalkduster", 30);
        else {

            text = new Text(textString);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Herculanum", 25));
        }

        double rectangleWidth = 220;
        if (column == 2)
            rectangleWidth = 370;
        Rectangle rectangle1 = new Rectangle(rectangleWidth, 50);
        rectangle1.setFill(Color.WHITE);
        Rectangle rectangle2 = new Rectangle(rectangleWidth - 2, 48);
        rectangle2.setFill(Color.gray(0, 0.95));
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(rectangle1, rectangle2, text);
        gridPane.add(stackPane, column, row);
    }

    private void buttonAction(String name) {
        switch (name) {
            case " PROFILE":
                break;
            case "    SHOP":
                ShopScene.makeShopScene(account);
                setScene(StateType.SHOP);
                break;
            case "SETTINGS":
                root.getChildren().removeAll(menuNodes);
                AccountScene.getInstance().addWindows();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AllAccount.getInstance().saveAccount(account);
                        account = null;
                    }
                }).start();
                break;
            case "NEW CARD":
                NewCardGraphic.makeCardForm(mainMenuScene, account);
                break;
        }
    }
}
