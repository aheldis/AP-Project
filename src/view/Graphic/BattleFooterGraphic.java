package view.Graphic;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.battle.Player;
import model.card.Card;
import view.enums.StateType;

import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;

public class BattleFooterGraphic {
    Group root;
    Player player;

    public BattleFooterGraphic(Group root,Player player) {
        this.root = root;
        this.player = player;
    }

    private void addNextCard(Group group){

        Group nextCardGroup = new Group();
        group.relocate(50,660);
        group.getChildren().addAll(nextCardGroup);
        nextCardGroup.relocate(0, 0);

        addImage(nextCardGroup, "pics/battle/next_card.png",
                0, 0, 170, 170);
        addImage(nextCardGroup, "pics/battle/inner_glow.png",
                0, 0, 170, 170);
        addImage(nextCardGroup,"pics/battle/outer_ring.png",
                0, 0, 170, 170);

    }
    private void addCardsOfHand(Player player, Group group){
        ArrayList<Card> gameCards = player.getHand().getGameCards();
        Group handCardGroup;
        for (int i = 0; i < gameCards.size(); i++) {
            handCardGroup = new Group();
            handCardGroup.relocate(30 + 140 * (i + 1), 20);
            group.getChildren().addAll(handCardGroup);
            ImageView backgroudCircle = addImage(handCardGroup,
                    "pics/battle/hand_card.png", 0, 0, 140, 140);
            addImage(handCardGroup, "pics/other/icon_mana@2x.png", 60, 107, 30, 30);
            makeCircleRotation(backgroudCircle,70,70);
        }

    }

    private void addButtons(Group group){
        addImage(group,"pics/battle/end_turn_yellow.png",1000,10,200,100);
        addImage(group,"pics/battle/graveYard.png",1000-80,95,150,70);
        addImage(group,"pics/battle/help.png",1000+90,95,150,70);

        Text text = addText(group,"End Turn",1040,53,Color.rgb(225,225,225,0.7),30);
        text.setFont(Font.font("Andele Mono", FontWeight.BOLD,25));


        Text graveYard = addText(group,"Graveyard",1000-60,122,
                Color.rgb(225,225,225,0.7),30);

        graveYard.setOnMouseClicked(event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.GRAVE_YARD));
                }
            });
            GraveYard.makeYard(player.getGraveYard().getCards());
        });

        graveYard.setFont(Font.font("Andele Mono", FontWeight.BOLD,25));
        Text help = addText(group,"Help",1115,122,Color.rgb(225,225,225,0.7),30);
        help.setFont(Font.font("Andele Mono", FontWeight.BOLD,25));

        text.setStroke(Color.rgb(251,225,60,0.5));
        graveYard.setStroke(Color.rgb(0,225,225,0.5));
        help.setStroke(Color.rgb(0,225,225,0.5));
    }

    public void makeFooter(){
        Group circlesGroup = new Group();
        circlesGroup.relocate(50,680);
        root.getChildren().addAll(circlesGroup);
        addNextCard(circlesGroup);
        addCardsOfHand(player,circlesGroup);
        addButtons(circlesGroup);
    }

}
