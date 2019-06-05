package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import view.enums.StateType;
import view.sample.StageLauncher;

public class BattleScene {
    private Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
    private StackPane root = (StackPane) battleScene.getRoot();
    private String backGroundImagePath;
    private int width;
    private int height;

    public BattleScene(String backGroundImagePath, int width, int height) {
        root.getChildren().clear();
        this.backGroundImagePath = backGroundImagePath;
        this.width = width;
        this.height = height;
        setBackground();
    }

    public void setBackground() {
        GeneralGraphicMethods.setBackground(root, backGroundImagePath, false, 0, 0);
    }

}
