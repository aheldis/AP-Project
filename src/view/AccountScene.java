package view;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AccountScene {
    private static final AccountScene instance = new AccountScene();
    private static final Scene accountScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) accountScene.getRoot();
    private static final HashMap<Node, Bounds> movables = new HashMap<>();
    private static final ArrayList<Node> movableNodes = new ArrayList<>();

    private AccountScene() {
    }

    public static AccountScene getInstance() {
        return instance;
    }

    public void makeBackground() {
        String backgroundPath = "pics/menu/background@2x.jpg";
        ImageView background = GeneralGraphicMethods.setBackground(root, backgroundPath, true, 0, 0);
        addLanterns();
        addMovables(background);
    }

    private void addLanterns() {
        String lanternPath = "pics/menu/lantern_large_3.png";
        int count = 50;
        ImageView[] lanterns = new ImageView[count];
        int[] xv = new int[count];
        int[] yv = new int[count];
        double ratioX = GeneralGraphicMethods.getRatioX();
        double ratioY = GeneralGraphicMethods.getRatioY();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            lanterns[i] = GeneralGraphicMethods.addImage(root, lanternPath,
                    380 + random.nextInt(450), 100 + random.nextInt(70),
                    random.nextInt(10) + 15, random.nextInt(20) + 10);
            xv[i] = random.nextInt(2) + 2;
            yv[i] = random.nextInt(4) - 4;
        }

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < count; i++) {
                    ImageView lantern = lanterns[i];
                    lantern.setX(((lantern.getX() + (double) xv[i] / 10) % 850) * ratioX);
                    if (lantern.getX() < 380 * ratioX) {
                        lantern.setX((380 + random.nextInt(400)) * ratioX);
                        lantern.setFitWidth((random.nextInt(10) + 15) * ratioX);
                    }
                    lantern.setY(lantern.getY() + ((double) yv[i] / 10) * ratioY);
                    if ((lantern.getY()) < -200) {
                        lantern.setY((100 + random.nextInt(70)) * ratioY);
                        lantern.setFitHeight((random.nextInt(20) + 10) * ratioY);
                    }
                    lantern.setFitWidth(lantern.getFitHeight() + 0.01 * ratioX);
                    lantern.setFitHeight(lantern.getFitHeight() + 0.01 * ratioY);
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
                230, 2676 / 1.9, 910 / 1.9);
        movables.put(imageView, imageView.getBoundsInLocal());
        movableNodes.add(imageView);
        pillarsPath = "pics/menu/pillars_near@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -70,
                40, 2167 / 1.8, 1843 / 1.8);
        movables.put(imageView, imageView.getBoundsInLocal());
        movableNodes.add(imageView);
        addMugs(background);
        String foregroundPath = "pics/menu/foreground@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, foregroundPath,
                380, 320, 2676 / 1.8, 810 / 1.8);
        movables.put(imageView, imageView.getBoundsInLocal());
        movableNodes.add(imageView);
        moveWithMouse(background, width / 2, height / 2);
    }

    private void addMugs(ImageView background) {
        String mugPath = "pics/menu/vignette.png";
        int count = 50;
        ImageView[] mugs = new ImageView[count];
        double xv = -0.5;
        double ratioX = GeneralGraphicMethods.getRatioX();
        double ratioY = GeneralGraphicMethods.getRatioY();
        Random random = new Random();
        for (int i = 0; i < count / 2; i++) {
            double x = 800 - i * random.nextInt(200);
            double y = 400 + random.nextInt(200);
            mugs[i * 2] = GeneralGraphicMethods.addImage(root, mugPath, x, y, 1103 / 5, 1080 / 5);
            mugs[i * 2 + 1] = GeneralGraphicMethods.addImage(root, mugPath, x + 110,
                    y, 1103 / 5, 1080 / 5);
            mugs[i * 2].setRotationAxis(Rotate.Y_AXIS);
            mugs[i * 2].setRotate(180);
        }
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < count / 2; i++) {
                    mugs[i * 2].setX(mugs[i * 2].getX() + xv * ratioX);
                    mugs[i * 2 + 1].setX(mugs[i * 2 + 1].getX() + xv * ratioX);
                    if (mugs[i * 2 + 1].getX() < -600) {
                        double x = (900 - random.nextInt(200)) * ratioX;
                        double y = (350 + random.nextInt(200)) * ratioY;
                        mugs[i * 2].setX(x);
                        mugs[i * 2].setY(y);
                        mugs[i * 2 + 1].setX(x + 110 * ratioX);
                        mugs[i * 2 + 1].setY(y);
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
                firstDistanceX[0] = distanceX;
                firstDistanceY[0] = distanceY;
                for (Node node : AccountScene.movables.keySet()) {
                    double finalDistanceX;
                    double finalDistanceY;
                    if (movableNodes.get(2) == node) {
                        finalDistanceX = distanceX * 4 * GeneralGraphicMethods.getRatioX();
                        finalDistanceY = distanceY * 2 * GeneralGraphicMethods.getRatioY();
                    } else {
                        finalDistanceX = distanceX * GeneralGraphicMethods.getRatioX();
                        finalDistanceY = distanceY * GeneralGraphicMethods.getRatioX();
                    }
                    Bounds bounds = AccountScene.movables.get(node);
                    new Thread(() -> Platform.runLater(() -> {
                        KeyValue xValue = new KeyValue(node.layoutXProperty(), bounds.getMinX() + finalDistanceX);
                        KeyValue yValue = new KeyValue(node.layoutYProperty(), bounds.getMinY() + finalDistanceY);
                        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), xValue, yValue);
                        Timeline timeline = new Timeline(keyFrame);
                        timeline.play();
                    })).start();
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