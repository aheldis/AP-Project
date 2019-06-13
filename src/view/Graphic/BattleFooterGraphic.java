package view.Graphic;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.account.FilesType;
import model.battle.Player;
import model.card.Card;
import model.card.Spell;
import view.enums.StateType;

import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;

class BattleFooterGraphic {
    private Group root;
    private Player player;

    BattleFooterGraphic(Group root, Player player) {
        this.root = root;
        this.player = player;
    }

    private void addNextCard(Group group) {

        Group nextCardGroup = new Group();
        group.relocate(50, 660);
        group.getChildren().addAll(nextCardGroup);
        nextCardGroup.relocate(0, 0);

        addImage(nextCardGroup, "pics/battle/next_card.png",
                0, 0, 170, 160);
        addImage(nextCardGroup, "pics/battle/inner_glow.png",
                0, 0, 170, 160);
        addImage(nextCardGroup, "pics/battle/outer_ring.png",
                0, 0, 170, 160);

    }

    private void addCardsOfHand(Player player, Group group) {
        ArrayList<Card> gameCards = player.getHand().getGameCards();
        Group handCardGroup;
        for (int i = 0; i < gameCards.size(); i++) {
            Card card = gameCards.get(i);
            handCardGroup = new Group();
            handCardGroup.relocate(30 + 140 * (i + 1), 10);
            group.getChildren().addAll(handCardGroup);
            ImageView backgroundCircle = addImage(handCardGroup,
                    "pics/battle/hand_card.png", 0, 0, 140, 140);
            addImage(handCardGroup, "pics/other/icon_mana@2x.png", 60, 107, 30, 30);
            makeCircleRotation(backgroundCircle, 70, 70);
            ImageView gif;
            if (card instanceof Spell) {
                SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                        card.getName(), FilesType.SPELL, card.getCountOfAnimation());
                gif = SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                        45, 40, group, sprite.count, sprite.rows, card.getMillis(),
                        (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame);
            } else {
                gif = addImage(group, "pics\\Minion\\" + card.getName() + ".gif",
                        15, -21, 110, 150);
            }
            handCardGroup.getChildren().add(gif);
        }

    }

    private void addButtons(Scene scene, Group group) {
        Button endTurn = imageButton(scene, group, "pics/battle/end_turn_yellow.png",
                "END TURN", 1000, 0, 200, 80);
        Button graveYard = imageButton(scene, group, "pics/battle/graveYard.png",
                "GRAVE YARD", 1000 - 80, 75, 150, 70);
        Button help = imageButton(scene, group, "pics/battle/help.png",
                "HELP", 1000 + 90, 75, 150, 70);

        graveYard.setOnMouseClicked(event -> {
            Platform.runLater(() -> StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.GRAVE_YARD)));
            GraveYard.makeYard(player.getGraveYard().getCards());
        });

    }

    void makeFooter(Scene scene) {
        Group circlesGroup = new Group();
        circlesGroup.relocate(50, 680);
        root.getChildren().addAll(circlesGroup);
        addNextCard(circlesGroup);
        addCardsOfHand(player, circlesGroup);
        addButtons(scene, circlesGroup);
    }

}
