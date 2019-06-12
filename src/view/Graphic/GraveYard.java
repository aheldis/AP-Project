package view.Graphic;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.account.FilesType;
import model.card.Card;
import model.card.Spell;
import view.enums.StateType;

import java.util.ArrayList;
import java.util.Random;

import static view.Graphic.GeneralGraphicMethods.*;

public class GraveYard {
    private static Scene graveYardScene = StageLauncher.getScene(StateType.GRAVE_YARD);
    private static Group root = (Group) graveYardScene.getRoot();
    private static final int column = 4;
    private static int pageNumber = 0;
    private static ArrayList<Node> deleteable = new ArrayList<>();

    private static Group makeEachCardGroup(Card card) {
        Group group = new Group();
        addImage(group, "pics/battle/graveyard/card_glow_line@2x.png",
                0, 0, 300, 350);
        Random random = new Random();
        int a = random.nextInt(6);
        addImage(group, "pics/battle/graveyard/runes-" + a + ".png", 20, 20, 80, 80);
        if (card instanceof Spell) {
            SpriteAnimationProperties sprite = new SpriteAnimationProperties(
                    ((Spell) card).getName(), FilesType.SPELL,((Spell) card).getCountOfAnimation());
            SpriteMaker.getInstance().makeSpritePic(sprite.spriteSheetPath,
                    94,58,
                    group, sprite.count,
                    sprite.rows, 4000,
                    (int)sprite.widthOfEachFrame, (int)sprite.heightOfEachFrame);
        }
        else {
            addImage(group, card.getPathOfAnimation(), 85, 100, 150, 150);
        }
        Text name = addText(group, card.getName(), 85, 250,
                Color.rgb(0, 225, 225, 0.7), 30);
        name.setFont(Font.font("Luminari", 30));
        name.setTextAlignment(TextAlignment.CENTER);
        name.setStroke(Color.BLACK);
        return group;
    }

    private static void pageMaker(ArrayList<Card> cards, VBox pageVbox) {
        HBox rowHBox = new HBox();
        int startingBound = 2 * column * pageNumber;
        System.out.println(startingBound);
        for (int i = startingBound; i < 2 * column + startingBound; i++) {

            if (i >= cards.size())
                break;
            if (i % column == 0) {
                rowHBox = new HBox();
                rowHBox.setAlignment(Pos.BASELINE_CENTER);
                rowHBox.setSpacing(20);
                pageVbox.getChildren().addAll(rowHBox);
                deleteable.add(rowHBox);
            }

            rowHBox.getChildren().add(makeEachCardGroup(cards.get(i)));

        }

    }

    public static void makeYard(ArrayList<Card> cards) {

        playMusic("resource/music/graveyard.m4a", true, graveYardScene);
        setBackground(root, "pics/battle/graveyard/graveyard_background.jpg", true, 5, 5);

        VBox pageVbox = new VBox();
        pageVbox.setSpacing(10);
        root.getChildren().addAll(pageVbox);
        HBox rowHBox = new HBox();


        pageMaker(cards, pageVbox);

        ImageView backCircle = addImage(root, "pics/other/circle.png", 100, 750, 70, 70);
        ImageView back = addImage(root, "pics/other/back.png", 115, 765, 40, 40);

        ImageView nextCircle = addImage(root, "pics/other/circle.png", 1200, 750, 70, 70);
        ImageView next = addImage(root, "pics/other/next.png", 1215, 765, 40, 40);

        back.setOnMouseClicked(event -> {
            pageNumber--;
            if (pageNumber < 0)
                pageNumber = 0;
            pageVbox.getChildren().removeAll(deleteable);
            deleteable.clear();
            pageMaker(cards, pageVbox);
        });

        next.setOnMouseClicked(event -> {
            System.out.println("hi");
            pageNumber++;
            pageVbox.getChildren().removeAll(deleteable);
            deleteable.clear();
            pageMaker(cards, pageVbox);
        });

        log(root, "See Death Cards", StateType.BATTLE, 100);


    }
}
