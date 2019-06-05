package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.util.ArrayList;

public class AccountScene {
    private static final AccountScene instance = new AccountScene();
    private static final Scene accountScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) accountScene.getRoot();
    private static final ArrayList<Node> movable = new ArrayList<>();

    private AccountScene() {}

    public static AccountScene getInstance() {
        return instance;
    }

    public void makeBackground() {
        String backgroundPath = "pics/menu/background@2x.jpg";
        ImageView background = GeneralGraphicMethods.setBackground(root, backgroundPath, true, 0, 0);
        double width = StageLauncher.getWidth();
        double height = StageLauncher.getHeight();
        String pillarsPath = "pics/menu/pillars_far@2x.png";
        ImageView imageView = GeneralGraphicMethods.addImage(root, pillarsPath, -100,
                400, 2676 / 3, 910 / 2);
        movable.add(imageView);
        pillarsPath = "pics/menu/pillars_near@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, pillarsPath, 0,
                100, 2167 / 2, 1843 / 1.8);
        movable.add(imageView);
        String foregroundPath = "pics/menu/foreground@2x.png";
        imageView = GeneralGraphicMethods.addImage(root, foregroundPath,
                width - 1300, height - 380, 2676 / 1.8, 810 / 1.8);
        movable.add(imageView);
        moveWithMouse(background);
    }

    private void moveWithMouse(ImageView imageView) {
        imageView.setOnMouseMoved(event -> {
            System.out.println(event.getX() + " " + event.getY());
        });
    }


}
