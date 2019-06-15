package view.Graphic;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.account.FilesType;
import model.battle.Player;
import model.card.Card;
import model.card.Minion;
import model.card.Spell;
import view.enums.StateType;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;

public class BattleFooterGraphic {
    private Group root;
    private Player player;
    private Group circlesGroup = new Group();
    private Scene scene;
    private BattleScene battleScene;

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
        Card nextCard = player.getMainDeck().passNextCard();
        addGif(group, nextCardGroup, nextCard, 10, 10);
    }

    private ImageView addGif(Group group, Group circleGroup, Card card, int dx, int dy) {
        ImageView gif;
        if (card instanceof Spell) {
            SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                    card.getName(), FilesType.SPELL, card.getCountOfAnimation());
            gif = SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                    45 + dx, 40 + dy, group, sprite.count, sprite.rows, card.getMillis(),
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
            addText(handCardGroup, card.getMp() + "", 72, 115,
                    Color.rgb(0, 0, 0, 0.5), 15);
            makeCircleRotation(backgroundCircle, 70, 70);
            ImageView gif = addGif(group, handCardGroup, card, 0, 0);
            if (card instanceof Minion) {
                DragAndDrop dragAndDrop = new DragAndDrop();
                dragAndDrop.dragAndDropForGame(gif, card, player.getHand(), handCardGroup, root,
                        gif.getFitWidth() / 2 - 10, gif.getFitHeight() / 2 + 20, 15, -21);
            }
        }

    }

    private void addButtons(Scene scene, Group group) {
        Button endTurn = imageButton(scene, group, "pics/battle/end_turn_yellow.png",
                "END TURN", 1000, 0, 200, 80);
        Button graveYard = imageButton(scene, group, "pics/battle/graveYard.png",
                "GRAVE YARD", 1000 - 80, 75, 150, 70);
        Button cancel = imageButton(scene, group, "pics/battle/help.png",
                "CANCEL", 1000 + 90, 75, 150, 70);
        Button a = imageButton(scene,group,"pics\\collection\\close-deck.png","save",1000 +90,75 ,30,30);
        group.getChildren().remove(a);

        endTurn.setOnMouseClicked(event -> BattleScene.getSingleInstance().getMatch().changeTurn());
        cancel.setOnMouseClicked(event -> {
            BattleScene.getSingleInstance().getMatch().setLoser(player);
            BattleScene.getSingleInstance().getMatch().setWinner(player.getOpponent());
            BattleScene.getSingleInstance().getMatch().endGame();
            StageLauncher.decorateScene(StateType.MAIN_MENU);
        });

        graveYard.setOnMouseClicked(event -> {
            Platform.runLater(() -> StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.GRAVE_YARD)));
            GraveYard.makeYard(player.getGraveYard().getCards());
        });
        a.setOnMouseClicked(event -> {
            BattleScene battleScene = BattleScene.getSingleInstance();
                new Thread(() -> {
                    try {
                        String path = "PausedGames/" + battleScene.getMatch().getMatchNumber() + ".json";
                        File file = new File(path);
                        if (file.exists())
                            file.delete();
                        YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
                        FileWriter fileWriter = new FileWriter(file);
                        altMapper.toJson(battleScene, fileWriter);
                        fileWriter.close();
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
        addButtons(scene, circlesGroup);
    }

    public void changeFooterEachTurn() {
        circlesGroup.getChildren().clear();
        addNextCard(circlesGroup);
        addCardsOfHand((Group) scene.getRoot(), circlesGroup);
        addButtons(scene, circlesGroup);
    }

}
