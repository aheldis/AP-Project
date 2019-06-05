package view;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
    private static MakeAndShowClass singleInstance = new MakeAndShowClass();

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
        Parent root = scene.getRoot();
        Platform.runLater(() -> {
            try {
                BoxBlur boxblur = new BoxBlur();
                StackPane stackPane = new StackPane(root);
                scene.setRoot(stackPane);

                //Setting the width of the box filter
                boxblur.setWidth(10.0f);

                //Setting the height of the box filter
                boxblur.setHeight(10.0f);

                //Setting the no of iterations
                boxblur.setIterations(1);

                root.setEffect(boxblur);

                javafx.scene.image.Image image = new Image(new FileInputStream("pics/error_box.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(350);
                imageView.setFitHeight(200);
                imageView.relocate(600,300);

                Text text = new Text(errorMessage);
                text.relocate(600, 300);
                text.setFont(Font.font(20));
                text.setFill(Color.rgb(173,225,218,0.5));
                stackPane.getChildren().add(imageView);
                stackPane.getChildren().add(text);
                imageView.setOnMouseClicked(event -> {
                    root.setEffect(null);
                    stackPane.getChildren().clear();
                    scene.setRoot(root);
                });
            }catch (Exception ignored){ }

        });

    }

}
