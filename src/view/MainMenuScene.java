package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import model.account.Account;
import view.enums.StateType;
import view.sample.StageLauncher;

import java.util.ArrayList;
import java.util.Objects;

public class MainMenuScene {
    private static final MainMenuScene instance = new MainMenuScene();
    private static final Scene mainMenuScene = StageLauncher.getScene(StateType.ACCOUNT_MENU);
    private static Group root = (Group) Objects.requireNonNull(mainMenuScene).getRoot();
    private static ArrayList<Node> menuNodes = new ArrayList<>();
    private Account account = null;

    private MainMenuScene() {}

    public static MainMenuScene getInstance() {
        return instance;
    }

    void makeMenu(Account account) {
        this.account = account;
        ImageView brand = GeneralGraphicMethods.addImage(root, "pics/login_pics/brand_duelyst@2x.png",
                150, 150, 1000 / 3, 216 / 3);
        menuNodes.add(brand);
    }

    private void addToNodes(Node node) {
        root.getChildren().add(node);
        menuNodes.add(node);
    }
}
