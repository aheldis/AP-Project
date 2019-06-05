package view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.enums.StateType;
import view.sample.StageLauncher;

import static view.GeneralGraphicMethods.*;

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
        setBackground(selectGameRoot,
                "pics/battle/select_mode/background.jpg",true,10.0f,10.0f);

        ImageView multiPlayerImage = addImage(selectGameRoot,
                "pics/battle/select_mode/multi_player.jpg",300,200,250,400);
        ImageView customImage = addImage(selectGameRoot,
                "pics/battle/select_mode/single_player_custom.jpg",600,200,250,400);
        ImageView storyImage = addImage(selectGameRoot,
                "pics/battle/select_mode/single_player_story.jpg",900,200,250,400);


        multiPlayerImage.setOnMouseClicked(event -> {
            selectMode();
            changeScene();
        });


        customImage.setOnMouseClicked(event -> {
            selectMode();
            changeScene();
        });

        storyImage.setOnMouseClicked(event -> {
            selectMode();
            changeScene();
        });

        Text multiPlayer  = addText(selectGameRoot,"Multi Player",
                350,230, Color.rgb(0, 0, 0,0.6),30);
        multiPlayer.setStyle("-fx-font-weight: bold");

       Text customGame = addText(selectGameRoot , "Single Player\nCustom Game",
                650,230,Color.rgb(0,0,0,0.6),30);
       customGame.setStyle("-fx-font-weight: bold");

       Text storyGame = addText(selectGameRoot,"Single Player\nStory  Game"
       ,950,230,Color.rgb(0,0,0,0.6),30);
       storyGame.setStyle("-fx-font-weight: bold");



    }

    public static void selectMode(){

        setBackground(selectModeRoot,
                "pics/battle/select_mode/select_mode_background.jpg",true,10.0f,10.0f);

        ImageView collectFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/collect_flag.jpg",300,200,250,400);
        ImageView saveFlagImage = addImage(selectModeRoot,
                "pics/battle/select_mode/save_flag_mode.jpg",600,200,250,400);
        ImageView deathImage = addImage(selectModeRoot,
                "pics/battle/select_mode/Death_mode.jpg",900,200,250,400);


        collectFlagImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        saveFlagImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });

        deathImage.setOnMouseClicked(event -> {

            //todo go to game ^__^
        });


    }

}
