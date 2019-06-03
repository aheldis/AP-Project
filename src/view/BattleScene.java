package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleScene {
    private Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
    private Group root = (Group) battleScene.getRoot();
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
       GeneralGraphicMethods.setBackground(root, backGroundImagePath, false);
    }

}
