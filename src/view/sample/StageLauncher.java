package view.sample;

import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StageLauncher extends Application {

    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        StageLauncher.primaryStage = primaryStage;
        primaryStage.setMaximized(true);
        Group root = new Group();
        Scene scene =new Scene(root);



        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
