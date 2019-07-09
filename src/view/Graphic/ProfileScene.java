package view.Graphic;


import com.gilecode.yagson.YaGson;
import controller.Transmitter;
import controller.client.TransferController;
import controller.RequestEnum;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.account.Account;
import model.battle.Game;
import model.battle.Match;
import model.battle.MatchInfo;
import view.enums.StateType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

import static view.Graphic.GeneralGraphicMethods.*;

public class ProfileScene {
    private static ProfileScene singleInstance = new ProfileScene();
    private Scene scene = StageLauncher.getScene(StateType.PROFILE);
    private Group root = (Group) Objects.requireNonNull(scene).getRoot();
    private Group matchHistoryGroup = new Group();
    private Group leaderBoardGroup = new Group();
    private Transmitter transmitter = new Transmitter();
   private String name;
    private ProfileScene() {
    }

    public static ProfileScene getSingleInstance() {
        return singleInstance;
    }

    void initProfileScene() {
        playMusic("resource/music/profile.m4a",true,scene);
        GeneralGraphicMethods.setBackground(root, "pics/other/profileBackground.jpg", false, StageLauncher.getWidth(), StageLauncher.getHeight());
        addSidebar();
        log(root, "", StateType.MAIN_MENU, 200);
    }

    private void addTextToScene(VBox vBox,int x,int y,String input){
        Group matchHistoryButtonGroup = new Group();
        Text matchHistoryButton1 = addText(matchHistoryButtonGroup, x,y, input, Color.WHITE, 25);
        Text matchHistoryButton2 = addText(new Group(), x,y, input, Color.WHITE, 25);
        setOnMouseEntered(matchHistoryButton1, scene, true);
        setOnMouseEntered(matchHistoryButton2, scene, true);
        matchHistoryButton1.setOnMouseClicked(event -> {
            if(input.equals("Match History"))
                showMatchHistory();
            if(input.equals("Leader Board"))
               showLeaderBoard();
            if(input.equals("Paused game"))
                goToGame();
            matchHistoryButtonGroup.getChildren().remove(matchHistoryButton1);
            matchHistoryButtonGroup.getChildren().addAll(matchHistoryButton2);
        });
        matchHistoryButton2.setOnMouseClicked(event -> {
            if(input.equals("Match History"))
                hideMatchHistory();
            if(input.equals("Leader Board"))
                hideLeaderBoard();
            matchHistoryButtonGroup.getChildren().remove(matchHistoryButton2);
            matchHistoryButtonGroup.getChildren().addAll(matchHistoryButton1);
        });
        vBox.getChildren().add(matchHistoryButtonGroup);
    }
    private void addSidebar() {
        addRectangle(root, 0, 0, 300, (int) StageLauncher.getHeight(), 0, 0, Color.gray(0, 0.7));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        transmitter = TransferController.main(RequestEnum.PROFILE, transmitter);
        addImage(vBox, transmitter.path, 50, 50, 200, 200);
        name = transmitter.name;
        addTextWithShadow(vBox, 50, 280, transmitter.name, "Luminari", 30);

        addTextToScene(vBox,53,350,"Match History");
        addTextToScene(vBox,53,400,"Leader Board");
        addTextToScene(vBox,53,450,"Paused game");
//        Group matchHistoryButtonGroup = new Group();
//        Text matchHistoryButton1 = addText(matchHistoryButtonGroup, 53, 350, "Match History", Color.WHITE, 25);
//        Text matchHistoryButton2 = addText(new Group(), 53, 350, "Match History", Color.WHITE, 25);
//        setOnMouseEntered(matchHistoryButton1, scene, true);
//        setOnMouseEntered(matchHistoryButton2, scene, true);
//        matchHistoryButton1.setOnMouseClicked(event -> {
//            showMatchHistory();
//            matchHistoryButtonGroup.getChildren().remove(matchHistoryButton1);
//            matchHistoryButtonGroup.getChildren().addAll(matchHistoryButton2);
//        });
//        matchHistoryButton2.setOnMouseClicked(event -> {
//            hideMatchHistory();
//            matchHistoryButtonGroup.getChildren().remove(matchHistoryButton2);
//            matchHistoryButtonGroup.getChildren().addAll(matchHistoryButton1);
//        });

//        vBox.getChildren().add(matchHistoryButtonGroup);

        vBox.getChildren().forEach(node -> VBox.setMargin(node, new Insets(5, 5, 10, 5)));
        vBox.relocate(45, 50);
        root.getChildren().addAll(vBox);
    }
    private void showMatchHistory() {
        System.out.println("ProfileScene.showMatchHistory");
        matchHistoryGroup = new Group();
        root.getChildren().add(matchHistoryGroup);

        NewCardGraphic.addRectangleStroke(matchHistoryGroup, 925, (int) StageLauncher.getHeight() - 160, false,
                Color.rgb(51, 51, 255, 0.9));


        Text header = new Text("MATCH HISTORY");
        header.setFill(Color.rgb(153, 0, 51));
        header.setStroke(Color.WHITE);
        header.setStrokeWidth(0.25);
        header.setFont(Font.font("Chalkduster", 50));
        header.relocate(490, 100);
        matchHistoryGroup.getChildren().add(header);

        GridPane gridPane = new GridPane();
        addNodeToGridPane(gridPane, 0, 0, "Winner", true);
        addNodeToGridPane(gridPane, 0, 1, "Loser", true);
        addNodeToGridPane(gridPane, 0, 2, "Date", true);


        ArrayList<MatchInfo> matchHistory = transmitter.matchInfos;
        Collections.reverse(matchHistory);

        int index = 1;
        for (MatchInfo matchInfo : matchHistory) {

            addNodeToGridPane(gridPane, index, 0, matchInfo.winner, false);
            addNodeToGridPane(gridPane, index, 1, matchInfo.loser, false);
            addNodeToGridPane(gridPane, index, 2, matchInfo.date.toString(), false);
            index++;
            if(index > 9)
                break;
        }

        matchHistoryGroup.getChildren().add(gridPane);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.relocate(300, 180);

        matchHistoryGroup.relocate(350, 60);
    }

    private void goToGame(){
        new Thread(()->{
            try {
                String matchPath = "PausedGames/" + name + "_match.json";

                YaGson yaGson = new YaGson();
                Match match = yaGson.fromJson(new FileReader(matchPath), Match.class);
//              String gamePath =  "PausedGames/" + name + "_game.json";
//              Game game = yaGson.fromJson(new FileReader(gamePath), Game.class );
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    StageLauncher.getPrimaryStage().setScene(StageLauncher.getScene(StateType.BATTLE));
                    BattleScene.setNewInstance();
                    BattleScene battleScene = BattleScene.getSingleInstance();
                    //battleScene.setGame(game);
                    battleScene.setMatch(match);

                    battleScene.setBattleScene(match.numberOfMap);
                    battleScene.setImPlayer0(true);
                    match.initGraphic(true);
                });
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("have not paused");
            }
        }).start();
    }

    private void showLeaderBoard(){
        leaderBoardGroup = new Group();
        root.getChildren().add(leaderBoardGroup);

        NewCardGraphic.addRectangleStroke(leaderBoardGroup, 925, (int) StageLauncher.getHeight() - 160, false,
                Color.rgb(51, 51, 255, 0.9));

        Text header = new Text("LEADER BOARD");
        header.setFill(Color.rgb(153, 0, 51));
        header.setStroke(Color.WHITE);
        header.setStrokeWidth(0.25);
        header.setFont(Font.font("Chalkduster", 50));
        header.relocate(490, 100);
        leaderBoardGroup.getChildren().add(header);

        GridPane gridPane = new GridPane();
        addNodeToGridPane(gridPane, 0, 0, "Number", true);
        addNodeToGridPane(gridPane, 0, 1, "Username", true);
        addNodeToGridPane(gridPane, 0, 2, "Wins", true);

        ArrayList<Account> accounts = transmitter.accounts;

        int index = 1;
        for (Account account : accounts) {

            addNodeToGridPane(gridPane, index, 0,index+"", false);
            addNodeToGridPane(gridPane, index, 1,account.getUserName() , false);
            addNodeToGridPane(gridPane, index, 2, account.getWins()+"", false);
            index++;
            if(index > 9)
                break;
        }

        leaderBoardGroup.getChildren().add(gridPane);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.relocate(300, 180);

        leaderBoardGroup.relocate(350, 60);




    }

    private void hideLeaderBoard(){
        root.getChildren().remove(leaderBoardGroup);

    }

    private void addNodeToGridPane(GridPane gridPane, int row, int column, String textString, boolean headerRow) {
        StackPane stackPane = new StackPane();
        Text text;
        if (headerRow)
            text = addTextWithShadow(new Group(), 0, 0, textString,
                    "Chalkduster", 30);
        else {

            text = new Text(textString);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Herculanum", 25));
        }

        double rectangleWidth = 220;
        if (column == 2)
            rectangleWidth = 370;
        Rectangle rectangle1 = new Rectangle(rectangleWidth, 50);
        rectangle1.setFill(Color.WHITE);
        Rectangle rectangle2 = new Rectangle(rectangleWidth - 2, 48);
        rectangle2.setFill(Color.gray(0, 0.95));
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(rectangle1, rectangle2, text);
        gridPane.add(stackPane, column, row);
    }

    private void hideMatchHistory() {
        System.out.println("ProfileScene.hideMatchHistory");
        root.getChildren().remove(matchHistoryGroup);
    }
}
