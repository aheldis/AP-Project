package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class BattleScene {
    private static BattleScene singleInstance = null;
    private Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
    private Group root = (Group) battleScene.getRoot();
    private double width = StageLauncher.getWidth();
    private double height = StageLauncher.getHeight();

    private BattleScene() {

    }

    public static BattleScene getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new BattleScene();
        return singleInstance;
    }

    public void setBattleScene(int numberOfMap) {
        root.getChildren().clear();
        setMapBackground(numberOfMap);
    }

    private void setMapBackground(int numberOfMap) {
        //System.out.println("numberOfMap = " + numberOfMap);
        String pathOfFile = "pics/maps_categorized/map" + numberOfMap + "/background";
        File file = new File(pathOfFile);
        File[] files = file.listFiles();
        if (files != null) {
            Arrays.sort(files);
            for (File file1 : files) {
                //System.out.println("file1.getName() = " + file1.getName());
                ImageView imageView = GeneralGraphicMethods.setBackground(root, file1.getPath(), false, 0, 0);
                if (file1.getName().contains("middleground") || file1.getName().contains("midground")) {
                    moveBackgrounds(imageView, true, false);
                }
                if (file1.getName().contains("foreground")) {
                    moveBackgrounds(imageView, false, true);
                }
            }
        }
    }

    private void moveBackgrounds(ImageView imageView, boolean horizontal, boolean vertical) {
        int randomNumber = (new Random().nextInt(3)) - 1;
        if (randomNumber == 0) randomNumber = 1;
        if (vertical)
            randomNumber *= 15;
        else
            randomNumber *= 10;
        if (vertical && (imageView.getX() - randomNumber > width || imageView.getX() - randomNumber < 0))
            randomNumber *= -1;
        if (horizontal && (imageView.getY() - randomNumber > height || imageView.getY() - randomNumber < 0))
            randomNumber *= -1;

        final int moveDistance = randomNumber;

        imageView.setOnMouseEntered(event -> {
            double primaryX = imageView.getX();
            double primaryY = imageView.getY();
            if (vertical)
                imageView.setX(primaryX + moveDistance);
            if (horizontal) {
                imageView.setY(primaryY + moveDistance);
            }
        });
        imageView.setOnMouseExited(event -> {
            double primaryX = imageView.getX();
            double primaryY = imageView.getY();
            if (vertical)
                imageView.setX(primaryX - moveDistance);
            if (horizontal)
                imageView.setY(primaryY - moveDistance);
        });
    }
}
