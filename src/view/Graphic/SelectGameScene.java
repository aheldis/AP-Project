package view.Graphic;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.enums.StateType;
import view.Graphic.StageLauncher;

import static view.Graphic.GeneralGraphicMethods.*;

public class SelectGameScene {
    private static Scene selectGameScene = StageLauncher.getScene(StateType.SELECT_GAME);
    private static Group selectGameRoot = (Group) selectGameScene.getRoot();
    private static Scene selectModeScene = StageLauncher.getScene(StateType.SELECT_MODE);
    private static Group selectModeRoot = (Group) selectModeScene.getRoot();

    private static void changeScene(){
        Platform.runLater(() ->
                StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.SELECT_MODE)));
    }

    public static void selectGame(){
        playMusic("resource/music/select_mode.m4a",true,selectGameScene);
        setBackground(selectGameRoot,
                "pics/battle/select_mode/background.jpg",true,10.0f,10.0f);

        ImageView multiPlayerImage = addImage(selectGameRoot,
                "pics/battle/select_mode/multi_player.jpg",300,200,250,400);
        ImageView customImage = addImage(selectGameRoot,
                "pics/battle/select_mode/single_player_custom.jpg",600,200,250,400);
        ImageView storyImage = addImage(selectGameRoot,
                "pics/battle/select_mode/single_player_story.jpg",900,200,250,400);

        addImage(selectGameRoot,
                "pics/battle/select_mode/line.png", 340,254,200,1);

        addImage(selectGameRoot,
                "pics/battle/select_mode/line.png", 640,300,200,1);
        addImage(selectGameRoot,
                "pics/battle/select_mode/line.png", 940,300,200,1);

        addImage(selectGameRoot,
                "pics/battle/select_mode/panel.png", 300,580,250,50);
        addImage(selectGameRoot,
                "pics/battle/select_mode/panel.png", 600,580,250,50);
        addImage(selectGameRoot,
                "pics/battle/select_mode/panel.png", 900,580,250,50);

        multiPlayerImage.setOnMouseClicked(event -> {
            changeScene();
            selectMode();
        });


        customImage.setOnMouseClicked(event -> {
            changeScene();
            selectMode();
        });

        storyImage.setOnMouseClicked(event -> {
            changeScene();
            selectMode();
        });

        Text multiPlayer  = addText(selectGameRoot,"Multi Player",
                350-5,230, Color.rgb(0, 0, 0,0.6),30);
        multiPlayer.setStyle("-fx-font-weight: bold");

       Text customGame = addText(selectGameRoot , "Single Player\nCustom Game",
                650-5,230,Color.rgb(0,0,0,0.6),30);
       customGame.setStyle("-fx-font-weight: bold");

       Text storyGame = addText(selectGameRoot,"Single Player\nStory  Game"
       ,950-5,230,Color.rgb(0,0,0,0.6),30);
        storyGame.setFont(Font.font("Lato-Light", FontWeight.BOLD,30));

       log(selectGameRoot,"select modes\nback",StageLauncher.getScene(StateType.MAIN_MENU),200);

    }

    public static void selectMode(){

        playMusic("resource/music/shop.m4a",true,selectModeScene);

        setBackground(selectModeRoot,
                "pics/battle/select_mode/select_mode_background.jpg",true,10.0f,10.0f);


        ImageView collectFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/collect_flag.jpg",300,200,250,400);

        Text collectFlagText =addText(selectModeRoot,"Collect Flags",350,220,
                Color.rgb(5,100,225,0.6),30);
        collectFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD,30));

        ImageView saveFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/save_flag_mode.jpg",600,200,250,400);
        Text saveFlagText =addText(selectModeRoot,"Save Flag",650,220,
                Color.rgb(25,205,225,0.6),30);
        saveFlagText.setFont(Font.font("Lato-bold", FontWeight.BOLD,30));

        ImageView deathImage = addImage(selectModeRoot,
                "pics/battle/select_mode/Death_mode.jpg",900,200,250,400);
        Text deathModeText =addText(selectModeRoot,"Death Mode",950,220,
                Color.rgb(25,205,225,0.6),30);
        deathModeText.setFont(Font.font("Lato-bold", FontWeight.BOLD,30));

        collectFlagImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        saveFlagImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        deathImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        log(selectModeRoot,"select mode\nback",selectGameScene,200);

    }

}