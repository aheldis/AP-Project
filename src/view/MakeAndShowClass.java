package view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.sample.StageLauncher;

import java.io.FileInputStream;

/**
 * "zahra"
 * always use primaryStage.getScene() for access to scene :)
 * for switching between scene use hashMap
 */

public class MakeAndShowClass {
    public static MakeAndShowClass singleInstance = new MakeAndShowClass();

    private MakeAndShowClass() {

    }

    public static MakeAndShowClass getInstance() {
        if (singleInstance == null)
            singleInstance = new MakeAndShowClass();
        return singleInstance;
    }

    public void makeError(String errorMessage){
        Stage stage = StageLauncher.getPrimaryStage();
        Scene scene = stage.getScene();
        Group root = (Group) scene.getRoot();
        Platform.runLater(() -> {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream("pics/error_box.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(350);
                imageView.setFitHeight(200);
                imageView.relocate(600,300);

                Text text = new Text(errorMessage);
                text.relocate(610, 340);
                text.setFont(Font.font(50));
                text.setFill(Color.rgb(173,225,218,0.5));
                root.getChildren().add(imageView);
                root.getChildren().add(text);
                imageView.setOnMouseClicked(event -> {
                    root.getChildren().removeAll(text,imageView);
                });
            }catch (Exception e){

            }

        });

    }

}
