package view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.sample.StageLauncher;



public class MakeAndShowClass {
    public static MakeAndShowClass singleInstance = new MakeAndShowClass();

    private MakeAndShowClass() {

    }

    public static MakeAndShowClass getInstance() {
        if (singleInstance == null)
            singleInstance = new MakeAndShowClass();
        return singleInstance;
    }

    public void makeError(String errorMessage) {
        Stage stage = StageLauncher.primaryStage;
        Scene scene = stage.getScene();
        Group root = (Group) scene.getRoot();
        Platform.runLater(() -> {
            Rectangle rectangle = new Rectangle(300, 400, 900, 100);
            rectangle.setFill(scene.getFill());
            rectangle.setArcHeight(20);
            rectangle.setArcWidth(30);
            Text text = new Text(errorMessage);
            text.relocate(350,450);
            text.setFont(Font.font(50));
            DropShadow e = new DropShadow();
            e.setWidth(20);
            e.setColor(Color.PINK);
            e.setHeight(20);
            e.setOffsetX(5);
            e.setOffsetY(5);
            e.setRadius(10);
            rectangle.setEffect(e);
            root.getChildren().add(rectangle);
            root.getChildren().add(text);
        });

    }

}
