package view.Graphic;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.battle.Player;
import model.card.Card;

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
        addImage(group, "pics/battle/next_card.png",
                0, 0, 160, 160);
        addImage(group, "pics/battle/inner_glow.png",
                0, 0, 170, 170);
    }
    private void addCardsOfHand(Player player, Group group){
        ArrayList<Card> gameCards = player.getHand().getGameCards();
        for(int i=0;i<gameCards.size();i++){
            addImage(group,"pics/battle/hand_card.png",30+130*(i+1),20,140,120);
        }
    }

    private void addButtons(Group group){
        addImage(group,"pics/battle/end_turn_yellow.png",1000,10,200,100);
        addImage(group,"pics/battle/graveYard.png",1000-80,95,150,70);
        addImage(group,"pics/battle/help.png",1000+90,95,150,70);
        Text text = addText(group,"End Turn",1030,53,Color.rgb(225,225,225,0.7),30);
        text.setFont(Font.font("Andele Mono", FontWeight.BOLD,25));
        Text graveYard = addText(group,"Graveyard",1000-60,122,
                Color.rgb(225,225,225,0.7),30);
        graveYard.setFont(Font.font("Andele Mono", FontWeight.BOLD,25));
        Text help = addText(group,"Help",1115,122,Color.rgb(225,225,225,0.7),30);
        help.setFont(Font.font("Andele Mono", FontWeight.BOLD,25));
    }

    public void makeFooter(Player player){
        Group circlesGroup = new Group();
        circlesGroup.relocate(50,680);
        root.getChildren().addAll(circlesGroup);
        addNextCard(circlesGroup);
        addCardsOfHand(player,circlesGroup);
    }

}
