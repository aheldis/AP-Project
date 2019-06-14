package view.Graphic;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import model.battle.Player;

public class BattleHeaderGraphic {
    private Group root;
    private Group rightHeader = new Group();
    private Group leftHeader = new Group();
    private BattleScene battleScene;
    private ImageView[] imageViews = new ImageView[2];

    BattleHeaderGraphic(BattleScene battleScene, Group root) {
        this.battleScene = battleScene;
        this.root = root;
        root.getChildren().addAll(rightHeader, leftHeader);
        initHeader();
        imageViews[0] = null;
        imageViews[1] = null;
    }

    //TODO Mana ke avas mishe avas kone -> listener
    //TODO TODO TODO TODO SABAAAAA


    private void addPortraitBorder(double x, double y, Group group, boolean turnOfThisPlayer, String avatarPath) {
        if (avatarPath != null)
            GeneralGraphicMethods.addImage(group, avatarPath, x + 13, y + 13, 100, 100);
        if (turnOfThisPlayer) {
            GeneralGraphicMethods.addImage(group, "pics/battle_categorized/general_portrait_border_highlight@2x.png", x, y, 130, 130);
        } else {
            GeneralGraphicMethods.addImage(group, "pics/battle_categorized/general_portrait_border@2x.png", x, y, 130, 130);
        }

        /*
        ImageView imageView1 = GeneralGraphicMethods.addImage(group, "pics/battle_categorized/general_portrait_border@2x.png", x, y, 130, 130);
        ImageView imageView2 = GeneralGraphicMethods.addImage(group, "pics/battle_categorized/general_portrait_border_highlight@2x.png", x, y, 130, 130);
        group.getChildren().remove(imageView2);
        imageView2.setOnMouseExited(event -> {
            group.getChildren().add(imageView1);
            group.getChildren().remove(imageView2);
        });
        imageView1.setOnMouseEntered(event -> {
            group.getChildren().add(imageView2);
            group.getChildren().remove(imageView1);
        });
        */
    }


    private void addMana(double x, double y, int numberOfMana, Group group) {
        for (int i = 0; i < 9; i++) {
            if (i < numberOfMana)
                GeneralGraphicMethods.addImage(group,
                        "pics/other/icon_mana@2x.png", x + i * 28, y, 25, 25);
            else
                GeneralGraphicMethods.addImage(group,
                        "pics/battle_categorized/icon_mana_inactive@2x.png",
                        x + i * 28, y, 25, 25);
        }
    }

    private void initHeader() {
        makeLeftHeader(battleScene.getMatch().getPlayers()[0]);
        makeRightHeader(battleScene.getMatch().getPlayers()[1]);

        /*
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
        perspectiveTransform.setUlx(115);
        perspectiveTransform.setUly(30);
        perspectiveTransform.setUrx(546);
        perspectiveTransform.setUry(21);
        perspectiveTransform.setLlx(115);
        perspectiveTransform.setLly(200);
        perspectiveTransform.setLrx(546);
        perspectiveTransform.setLry(177);

        leftHeader.setEffect(perspectiveTransform);
*/
        /*
        addManaLeft(248, 96, 9);
        addManaRight(920, 96, 9);
        */
    }

    private void makeLeftHeader(Player player) {
        leftHeader.getChildren().clear();
        GeneralGraphicMethods.addTextWithShadow(player.getUserName(), 248, 78, leftHeader, "Arial", 27);
        addMana(245, 100, player.getMana(), leftHeader);
        addPortraitBorder(120, 25, leftHeader, true, player.getAvatarPath());
        addPortraitBorder(1165, 25, rightHeader, false, null);
    }

    private void makeRightHeader(Player player) {
        rightHeader.getChildren().clear();
        GeneralGraphicMethods.addTextWithShadow(player.getUserName(), 1010, 78, leftHeader, "Arial", 27);
        addMana(911, 100, player.getMana(), rightHeader);
        addPortraitBorder(1165, 25, rightHeader, true, player.getAvatarPath());
        addPortraitBorder(120, 25, leftHeader, false, null);
    }

    public void makeHeaderEachTurn(int numberOfPlayer, Player player) {
        if (numberOfPlayer == 0) {
            makeLeftHeader(player);
        } else {
            makeRightHeader(player);
        }
    }
}
