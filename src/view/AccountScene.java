package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.util.ArrayList;
import java.util.HashMap;

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
        double width = StageLauncher.getWidth();
        double height = StageLauncher.getHeight();
        String pillarsPath = "pics/menu/pillars_far@2x.png";
        ImageView imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -50,
                230, 2676 / 3.3, 910 / 2);
        movables.put(imageView, imageView.getBoundsInLocal());
        movableNodes.add(imageView);
        pillarsPath = "pics/menu/pillars_near@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -50,
                80, 2167 / 2, 1843 / 1.8);
        movables.put(imageView, imageView.getBoundsInLocal());
        movableNodes.add(imageView);
        String foregroundPath = "pics/menu/foreground@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, foregroundPath,
                380, 330, 2676 / 1.8, 810 / 1.8);
        movables.put(imageView, imageView.getBoundsInLocal());
        movableNodes.add(imageView);
        moveWithMouse(background, width / 2, height / 2);
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
                        finalDistanceX = distanceX * 4;
                        finalDistanceY = distanceY * 2;
                    } else {
                        finalDistanceX = distanceX;
                         finalDistanceY = distanceY;
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