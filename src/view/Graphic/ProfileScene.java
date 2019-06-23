package view.Graphic;

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
import model.battle.MatchInfo;
import view.enums.StateType;

import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;

public class ProfileScene {
    private static ProfileScene singleInstanse = new ProfileScene();
    private Scene scene = StageLauncher.getScene(StateType.PROFILE);
    private Group root = (Group) scene.getRoot();
    private Group matchHistoryGroup = new Group();
    private Account account;

    private ProfileScene() {
    }

    public static ProfileScene getSingleInstance() {
        return singleInstanse;
    }

    public void initProfileScene(Account account) {
        playMusic("resource/music/profile.m4a",true,scene);
        this.account = account;
        GeneralGraphicMethods.setBackground(root, "pics/other/profileBackground.jpg", false, StageLauncher.getWidth(), StageLauncher.getHeight());
        addSidebar();
        log(root, "", StateType.MAIN_MENU, 200);
    }

    private void addSidebar() {
        addRectangle(root, 0, 0, 300, (int) StageLauncher.getHeight(), 0, 0, Color.gray(0, 0.7));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        addImage(vBox, account.getAccountImagePath(), 50, 50, 200, 200);
        addTextWithShadow(vBox, 50, 280, account.getUserName(), "Luminari", 30);

        Group matchHistoryButtonGroup = new Group();
        Text matchHistoryButton1 = addText(matchHistoryButtonGroup, 53, 350, "Match History", Color.WHITE, 25);
        Text matchHistoryButton2 = addText(new Group(), 53, 350, "Match History", Color.WHITE, 25);
        setOnMouseEntered(matchHistoryButton1, scene, true);
        setOnMouseEntered(matchHistoryButton2, scene, true);
        matchHistoryButton1.setOnMouseClicked(event -> {
            showMatchHistory();
            matchHistoryButtonGroup.getChildren().remove(matchHistoryButton1);
            matchHistoryButtonGroup.getChildren().addAll(matchHistoryButton2);
        });
        matchHistoryButton2.setOnMouseClicked(event -> {
            hideMatchHistory();
            matchHistoryButtonGroup.getChildren().remove(matchHistoryButton2);
            matchHistoryButtonGroup.getChildren().addAll(matchHistoryButton1);
        });

        vBox.getChildren().add(matchHistoryButtonGroup);

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

        ArrayList<MatchInfo> matchHistory = account.getMatchHistory();
        int index = 1;
        for (MatchInfo matchInfo : matchHistory) {

            addNodeToGridPane(gridPane, index, 0, matchInfo.winner, false);
            addNodeToGridPane(gridPane, index, 1, matchInfo.loser, false);
            addNodeToGridPane(gridPane, index, 2, matchInfo.date.toString(), false);
            index++;
        }

        matchHistoryGroup.getChildren().add(gridPane);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.relocate(300, 180);

        matchHistoryGroup.relocate(350, 60);
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
