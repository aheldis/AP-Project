package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import model.account.Collection;
import model.item.Collectible;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

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
        System.out.println("numberOfMap = " + numberOfMap);
        String pathOfFile = "pics/maps_categorized/map" + numberOfMap + "/background";
        File file = new File(pathOfFile);
        File[] files = file.listFiles();
        if(files != null) {
            Arrays.sort(files);
            for (File file1 : files) {
                System.out.println("file1.getName() = " + file1.getName());
                GeneralGraphicMethods.setBackground(root, file1.getPath(), false, 0, 0);
            }
        }
    }

}
