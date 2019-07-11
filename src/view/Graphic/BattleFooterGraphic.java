package view.Graphic;

import controller.RequestEnum;
import controller.Transmitter;
import controller.client.TransferController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.account.FilesType;
import model.battle.Player;
import model.card.Card;
import model.card.Minion;
import model.card.Spell;
import model.requirment.GeneralLogicMethods;
import view.enums.StateType;

import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;

public class BattleFooterGraphic {
    private Group root;
    private Player player;
    private Group circlesGroup = new Group();
    private Scene scene;
    private BattleScene battleScene;
    private Button endTurnButton;

    BattleFooterGraphic(BattleScene battleScene, Group root, Player player, Scene scene) {
        this.battleScene = battleScene;
        this.root = root;
        this.player = player;
        this.scene = scene;
        initFooter();
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
        Card nextCard = player.getMainDeck().passNextCard(true);
        addGif(group, nextCardGroup, nextCard, 10, 10);
    }

    private ImageView addGif(Group group, Group circleGroup, Card card, int dx, int dy) {
        ImageView gif;
        if (card instanceof Spell) {
            SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                    card.getName(), FilesType.SPELL, card.getCountOfAnimation());
            gif = SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                    45 + dx, 40 + dy, group, sprite.count, sprite.rows, sprite.millis,
                    (int) sprite.widthOfEachFrame, (int) sprite.heightOfEachFrame);
        } else {
            gif = addImage(group, "pics/Minion/" + card.getName() + ".gif",
                    15 + dx, -21 + dy, 110, 150);
        }
        circleGroup.getChildren().add(gif);
        return gif;
    }

    private void addCardsOfHand(Group root, Group group) {
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
            addText(handCardGroup, 72, 115, card.getMp() + "",
                    Color.rgb(0, 0, 0, 0.5), 15);
            makeCircleRotation(backgroundCircle, 70, 70);
            ImageView gif = addGif(group, handCardGroup, card, 0, 0);
            if (card instanceof Minion) {
                if (BattleScene.getSingleInstance().isImPlayer1()) {
                    gif.setRotationAxis(Rotate.Y_AXIS);
                    gif.setRotate(180);
                }
                DragAndDrop dragAndDrop = new DragAndDrop();
                dragAndDrop.dragAndDropForGame(gif, card, player.getHand(), handCardGroup, root,
                        gif.getFitWidth() / 2 - 10, gif.getFitHeight() / 2 + 20, 15, -21);
            } else {
                DragAndDrop dragAndDrop = new DragAndDrop();
                dragAndDrop.dragAndDropForGame(gif, card, player.getHand(), handCardGroup, root,
                        gif.getFitWidth() / 2 + 20, gif.getFitHeight() / 2 + 20, 45, 40);
            }
            setOnMouseEntered(gif, scene, false);
        }

    }

    private void addTimer(Group group) {
        addImage(group, "pics/battle_categorized/timer_background@2x.png",
                200, 0, 650, 10);
        ImageView progress = addImage(group,
                "pics/battle_categorized/unit_stats_instructional_bg@2x.png",
                207, 1, 50, 8);
        Rectangle rectangle = addRectangle(group, 207, 0
                , 6, 10, 0, 0, Color.rgb(81, 89, 102, 0.4));

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            private double change = 0.5;
            BattleScene battleScene = BattleScene.getSingleInstance();
            private long time = battleScene.getFastForward() ? 120_000_0 : 120_000_000;

            @Override
            public void handle(long now) {

                for (int i = 0; i < 20; i++) {
                    if (now - lastUpdate >= time) {
                        progress.setX(progress.getX() + change);
                        lastUpdate = now;
                        rectangle.setWidth(rectangle.getWidth() + change);
                        if (progress.getX() >= 633 - 30) {
                            this.stop();
                            if (BattleScene.getSingleInstance().getMatch() != null)
                                BattleScene.getSingleInstance().getMatch().changeTurn(false, true);
                        }
                    }
                }

            }
        };
        animationTimer.start();
    }

    private void addButtons(Scene scene, Group group) {
        Button endTurn = imageButton(scene, group, "pics/battle/end_turn_yellow.png",
                "END TURN", 1000, 0, 200, 80);
        endTurnButton = endTurn;
        Button graveYard = imageButton(scene, group, "pics/battle/graveYard.png",
                "GRAVE YARD", 1000 - 80, 75, 150, 70);
        Button cancel = imageButton(scene, group, "pics/battle/help.png",
                "CANCEL", 1000 + 90, 75, 150, 70);
        Button a = imageButton(scene, group, "pics/collection/close-deck.png", "save", 1000 + 90, 75, 30, 30);
        group.getChildren().remove(a);

        endTurn.setOnMouseClicked(event -> BattleScene.getSingleInstance().getMatch().changeTurn(false, true));
        cancel.setOnMouseClicked(event -> {
            BattleScene.getSingleInstance().getMatch().setLoser(player);
            BattleScene.getSingleInstance().getMatch().setWinner(player.getOpponent());
            BattleScene.getSingleInstance().getMatch().endGame();
            if (battleScene.getMatch().passComputerPlayer() == -1)
                TransferController.main(RequestEnum.GAME_CANCEL, new Transmitter());
        });

        graveYard.setOnMouseClicked(event -> {
            Platform.runLater(() -> StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.GRAVE_YARD)));
            GraveYardScene.makeYard(player.getGraveYard().getCards());
        });
        a.setOnMouseClicked(event -> {
            BattleScene battleScene = BattleScene.getSingleInstance();
            new Thread(() -> {
                try {
                    String path = "PausedGames/" + battleScene.getMatch().getMatchNumber() + ".json";
                    GeneralLogicMethods.saveInFile(path, battleScene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

    }

    private void initFooter() {
//        Group circlesGroup = new Group();
        circlesGroup.relocate(50, 680);
        root.getChildren().addAll(circlesGroup);
        addNextCard(circlesGroup);
        addCardsOfHand((Group) scene.getRoot(), circlesGroup);
        addTimer(circlesGroup);
        addButtons(scene, circlesGroup);
    }

    public void changeFooterEachTurn() {
        circlesGroup.getChildren().clear();
        addNextCard(circlesGroup);
        addCardsOfHand((Group) scene.getRoot(), circlesGroup);
        addButtons(scene, circlesGroup);
        addTimer(circlesGroup);
        battleScene.backToDefault();
    }

    public Scene getScene() {
        return scene;
    }

    public Group getRoot() {
        return root;
    }

    public Button getEndTurnButton() {
        return endTurnButton;
    }

    public Group getCirclesGroup() {
        return circlesGroup;
    }
}
