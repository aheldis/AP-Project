package view;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view.enums.Cursor;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.util.ArrayList;
import java.util.Random;

public class AccountScene {
    private static final AccountScene instance = new AccountScene();
    private static final Scene accountScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) accountScene.getRoot();
    private static final ArrayList<Node> movables = new ArrayList<>();
    private static final ArrayList<Node> windows = new ArrayList<>();
    private static ArrayList<Node> changes = new ArrayList<>();

    private AccountScene() {
    }

    public static AccountScene getInstance() {
        return instance;
    }

    public void makeBackground() {
        String backgroundPath = "pics/menu/background@2x.jpg";
        ImageView background = GeneralGraphicMethods.setBackground(root, backgroundPath, true, 0, 0);
        background.setOnMouseClicked(event -> System.out.println(event.getX() + " " + event.getY()));
        addLanterns();
        addMovables(background);
        addWindows();
    }

    private void addWindows() {
        double centerX = StageLauncher.getWidth() / 2;
        double centerY = StageLauncher.getHeight() / 2;
        double sizeX = 500;
        double sizeY = 600;
        double ratioX = GeneralGraphicMethods.getRatioX();
        double ratioY = GeneralGraphicMethods.getRatioY();
        Rectangle rectangle = new Rectangle(centerX - sizeX / 2, centerY - sizeY / 2, sizeX, sizeY - 100);
        rectangle.setFill(Color.rgb(40, 40, 36, 0.95));
        root.getChildren().add(rectangle);
        windows.add(rectangle);
        ImageView brand = GeneralGraphicMethods.addImage(root, "pics/login_pics/brand_duelyst@2x.png",
                centerX - 250 / ratioX, centerY - (sizeY / 2 - 120) / ratioY, 1000 / 2, 216 / 2);
        windows.add(brand);
        Label logIn = new Label("LOG IN");
        logIn.relocate(centerX - 100, centerY - sizeY / 2 + 45);
        root.getChildren().add(logIn);
        windows.add(logIn);
        Label signUp = new Label("SIGN UP");
        signUp.relocate(centerX + 20, centerY - sizeY / 2 + 45);
        root.getChildren().add(signUp);
        windows.add(signUp);
        commonTextFields(sizeY);
        Button enterButton = newButton("LOG IN", sizeY);
        enter("LOG IN", enterButton, logIn, signUp);
        logIn.setOnMouseClicked(event -> enter("LOG IN", enterButton, logIn, signUp));
        signUp.setOnMouseClicked(event -> enter("SIGN UP", enterButton, logIn, signUp));
    }

    private void commonTextFields(double sizeY) {
        double centerX = StageLauncher.getWidth() / 2;
        double centerY = StageLauncher.getHeight() / 2;
        TextField userName = new TextField();
        userName.setPromptText("Username");
        userName.setPrefSize(350, 75);
        userName.relocate(centerX - 175, centerY - sizeY / 2 + 100);
        userName.setStyle("-fx-background-color: rgba(100,100,100,0.4); -fx-font-size: 20px");
        root.getChildren().add(userName);
        windows.add(userName);
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setPrefSize(350, 75);
        password.relocate(centerX - 175, centerY - sizeY / 2 + 200);
        password.setStyle("-fx-background-color: rgba(100,100,100,0.4); -fx-font-size: 20px");
        root.getChildren().add(password);
        windows.add(password);
    }

    private void enter(String enter, Button enterButton, Label logIn, Label signUp) {
        root.getChildren().removeAll(changes);
        windows.removeAll(changes);
        changes = new ArrayList<>();
        double ratioX = GeneralGraphicMethods.getRatioX();
        double ratioY = GeneralGraphicMethods.getRatioY();
        if (enter.equals("LOG IN")) {
            enterButton.setText("LOG IN");
            logIn.setStyle("-fx-text-fill: white; -fx-font-size: 20px");
            signUp.setStyle("-fx-text-fill: gray; -fx-font-size: 20px");
            ImageView triangle = GeneralGraphicMethods.addImage(root, "pics/login_pics/bnea-triangle@2x.png",
                    (logIn.getLayoutX() + 25) / ratioX, (logIn.getLayoutY() - 20) / ratioY, 15, 10);
            windows.add(triangle);
            changes.add(triangle);
        } else {
            enterButton.setText("SIGN UP");
            signUp.setStyle("-fx-text-fill: white; -fx-font-size: 20px");
            logIn.setStyle("-fx-text-fill: gray; -fx-font-size: 20px");
            ImageView triangle = GeneralGraphicMethods.addImage(root, "pics/login_pics/bnea-triangle@2x.png",
                    (signUp.getLayoutX() + 30) / ratioX, (signUp.getLayoutY() - 20) / ratioY, 15, 10);
            windows.add(triangle);
            changes.add(triangle);
        }

    }

    private Button newButton(String name, double sizeY) {
        double centerX = accountScene.getWidth() / 2;
        double centerY = accountScene.getHeight() / 2;
        String noChange = "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20px";
        Button button = new Button(name);
        button.setPrefSize(350, 75);
        button.relocate(centerX - 175, centerY - sizeY / 2 + 350);
        button.setStyle("-fx-background-color: darkorange;" + noChange);
        root.getChildren().add(button);
        button.setOnMouseEntered(event -> {
            GeneralGraphicMethods.setCursor(accountScene, Cursor.LIGHTEN);
            button.setStyle("-fx-background-color: red;" + noChange);
        });
        button.setOnMouseExited(event -> {
            GeneralGraphicMethods.setCursor(accountScene, Cursor.AUTO);
            button.setStyle("-fx-background-color: darkorange;" + noChange);
        });
        return button;
    }

    private void addLanterns() {
        ArrayList<String> lanternPaths = new ArrayList<>();
        lanternPaths.add("pics/menu/lantern_large_3.png");
        lanternPaths.add("pics/menu/lantern_large_2.png");
        lanternPaths.add("pics/menu/lantern_large_3.png");
        lanternPaths.add("pics/menu/lantern_small.png");
        int count = 80;
        ImageView[] lanterns = new ImageView[count];
        int[] xv = new int[count];
        int[] yv = new int[count];
        boolean[] faded = new boolean[count];
        double ratioX = GeneralGraphicMethods.getRatioX();
        double ratioY = GeneralGraphicMethods.getRatioY();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            lanterns[i] = GeneralGraphicMethods.addImage(root, lanternPaths.get(random.nextInt(4)),
                    1050 + random.nextInt(550), 50 + random.nextInt(250),
                    20, 27);
            xv[i] = random.nextInt(2) + 2;
            yv[i] = random.nextInt(4) - 4;
        }

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long second = (long) Math.pow(10, 9);

            @Override
            public void handle(long now) {
                for (int i = 0; i < count; i++) {
                    ImageView lantern = lanterns[i];
                    lantern.setLayoutX(lantern.getLayoutX() + (double) xv[i] / 10 * ratioX);
                    lantern.setLayoutY(lantern.getLayoutY() + (double) yv[i] / 10 * ratioY);
                    if (faded[i]) {
                        if (lastTime == 0) {
                            lastTime = now;
                        }
                        if (now > lastTime + second / 2 + second / 4) {
                            lastTime = now;
                            lantern.setLayoutX((1050 + random.nextInt(450)) * ratioX);
                            lantern.setLayoutY((270 + random.nextInt(70)) * ratioY);
                            lantern.setFitWidth(20 * ratioX);
                            lantern.setFitHeight(27 * ratioY);
                            FadeTransition fade = new FadeTransition();
                            fade.setDuration(Duration.millis(500));
                            fade.setFromValue(0);
                            fade.setToValue(10);
                            fade.setNode(lantern);
                            fade.play();
                            faded[i] = false;
                        }
                    } else if (lantern.getLayoutX() > 1700 * ratioX || lantern.getLayoutY() < -50) {
                        FadeTransition fade = new FadeTransition();
                        fade.setDuration(Duration.millis(500));
                        fade.setFromValue(10);
                        fade.setToValue(0);
                        fade.setNode(lantern);
                        fade.play();
                        faded[i] = true;
                    }
                    lantern.setFitWidth(lantern.getFitWidth() + 0.03 * ratioX);
                    lantern.setFitHeight(lantern.getFitHeight() + 0.03 * ratioY);
                }
            }
        };
        animationTimer.start();

    }

    private void addMovables(ImageView background) {
        double width = StageLauncher.getWidth();
        double height = StageLauncher.getHeight();
        String pillarsPath = "pics/menu/pillars_far@2x.png";
        ImageView imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -300,
                250, 2676 / 1.9, 910 / 1.9);
        movables.add(imageView);
        pillarsPath = "pics/menu/pillars_near@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -70,
                60, 2167 / 1.8, 1843 / 1.8);
        movables.add(imageView);
        addMugs();
        String foregroundPath = "pics/menu/foreground@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, foregroundPath,
                780, 640, 2676 / 1.8, 810 / 1.8);
        movables.add(imageView);
        moveWithMouse(background, width / 2, height / 2);
    }

    private void addMugs() {
        ArrayList<String> cloudPaths = new ArrayList<>();
        for (int i = 1; i < 8; i++)
            cloudPaths.add("pics/particles/cloud_00" + i + "@2x.png");
        int count = 40;
        ImageView[] mugs = new ImageView[count];
        double xv = -0.5;
        double ratioX = GeneralGraphicMethods.getRatioX();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int randNumber = random.nextInt(2) + 3;
            double x = 1400 - i * random.nextInt(100);
            double y = 730 + random.nextInt(200);
            mugs[i] = GeneralGraphicMethods.addImage(root, cloudPaths.get(random.nextInt(7)), x, y,
                    100 * randNumber, 50 * randNumber);
            Lighting lighting = new Lighting();
            lighting.setDiffuseConstant(1.0);
            lighting.setSpecularConstant(0.0);
            lighting.setSpecularExponent(0.0);
            lighting.setSurfaceScale(0.0);
            lighting.setLight(new Light.Distant(45, 45,
                    Color.rgb(121, 149, 255, 1)));
            mugs[i].setEffect(lighting);
        }
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < count; i++) {
                    mugs[i].setX(mugs[i].getX() + xv * ratioX);
                    if (mugs[i].getX() + mugs[i].getFitWidth() < -20) {
                        double x = 1300 * ratioX;
                        mugs[i].setX(x);
                    }
                }
            }
        };
        animationTimer.start();
    }

    private void moveWithMouse(ImageView imageView, double centerX, double centerY) {
        final double[] firstDistanceX = {0};
        final double[] firstDistanceY = {0};


        imageView.setOnMouseMoved(event -> {
            double distanceX = event.getX() - centerX;
            double distanceY = event.getY() - centerY;
            distanceX = -sign(distanceX) * 10;
            distanceY = -sign(distanceY) * 2;
            if (firstDistanceX[0] != distanceX || firstDistanceY[0] != distanceY) {
                if (firstDistanceX[0] != distanceX)
                    firstDistanceX[0] = distanceX;
                else distanceX = 0;
                if (firstDistanceY[0] != distanceY)
                    firstDistanceY[0] = distanceY;
                else distanceY = 0;
                for (Node node : AccountScene.movables) {
                    double finalDistanceX;
                    double finalDistanceY;
                    if (movables.get(2) == node) {
                        finalDistanceX = distanceX * 4 * GeneralGraphicMethods.getRatioX();
                        finalDistanceY = distanceY * 2 * GeneralGraphicMethods.getRatioY();
                    } else {
                        finalDistanceX = distanceX * GeneralGraphicMethods.getRatioX();
                        finalDistanceY = distanceY * GeneralGraphicMethods.getRatioY();
                    }
                    KeyValue xValue = new KeyValue(node.layoutXProperty(), node.getLayoutX() + finalDistanceX);
                    KeyValue yValue = new KeyValue(node.layoutYProperty(), node.getLayoutY() + finalDistanceY);
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), xValue, yValue);
                    Timeline timeline = new Timeline(keyFrame);
                    timeline.play();
                }
            }

        });
    }

    private int sign(double number) {
        if (number > 10) return 1;
        if (number > -10 && number <= 10) return 0;
        return -1;
    }
}