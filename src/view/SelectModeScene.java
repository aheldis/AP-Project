package view;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.enums.StateType;
import view.sample.StageLauncher;


import static view.GeneralGraphicMethods.*;


public class SelectModeScene {
    private static Scene gameScene = StageLauncher.getScene(StateType.SELECT_MODE);
    private static Group root = (Group) gameScene.getRoot();

    public static void selectMode(){
        setBackground(root,
                "pics/battle/select_mode/background.jpg",true,10.0f,10.0f);

        ImageView multiPlayerImage = addImage(root,"pics/battle/select_mode/multi_player.jpg",300,200,250,400);
        ImageView customImage = addImage(root,"pics/battle/select_mode/single_player_custom.jpg",600,200,250,400);
        ImageView storyImage = addImage(root,"pics/battle/select_mode/single_player_story.jpg",900,200,250,400);

        multiPlayerImage.setOnMouseClicked(event -> {

        });

        customImage.setOnMouseClicked(event -> {

        });

        storyImage.setOnMouseClicked(event -> {

        });

        Text multiPlayer  = addText(root,"Multi Player",
                350,230, Color.rgb(0, 0, 0,0.6),30);
        multiPlayer.setStyle("-fx-font-weight: bold");

       Text customGame = addText(root , "Single Player\nCustom Game",
                650,230,Color.rgb(0,0,0,0.6),30);
       customGame.setStyle("-fx-font-weight: bold");

       Text storyGame = addText(root,"Single Player\nStory  Game"
       ,950,230,Color.rgb(0,0,0,0.6),30);
       storyGame.setStyle("-fx-font-weight: bold");



    }

}
