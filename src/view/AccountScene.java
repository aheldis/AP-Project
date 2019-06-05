package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.util.ArrayList;

public class AccountScene {
    private static final Scene accountScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) accountScene.getRoot();
    private static final ArrayList<Node> movable = new ArrayList<>();

    public static void makeBackground() {
        String backgroundPath = "pics/menu/background@2x.jpg";
        GeneralGraphicMethods.setBackground(root, backgroundPath, true, 0, 0);

        String foregroundPath = "pics/menu/foreground@2x.png";
        ImageView imageView = GeneralGraphicMethods.addImage(root, foregroundPath,
                500, 500, 2676 / 2, 810 / 2);
        movable.add(imageView);
        /*
        String pillarsPath = "pics/menu/pillars_far@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -100,
                accountScene.getHeight() / 2, 2676 / 2, 910 / 2);
        movable.add(imageView);
        pillarsPath = "pics/menu/pillars_near@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -100,
                accountScene.getHeight() / 2, 2167 / 2, 1843 / 2);
        movable.add(imageView);*/
    }


}
