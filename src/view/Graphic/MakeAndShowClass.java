package view.Graphic;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public void makeError(String errorMessage) {
        Stage stage = StageLauncher.getPrimaryStage();
        Scene scene = stage.getScene();
        Group root = (Group) scene.getRoot();
        Platform.runLater(() -> {
            try {
                GeneralGraphicMethods.playMusic("resource/music/error.m4a", false, scene);
                Rectangle rectangle = new Rectangle(
                        stage.getWidth(),
                        stage.getHeight(),
                        Color.rgb(225, 225, 225, 0.3));


                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);

                javafx.scene.image.Image image = new Image(new FileInputStream("pics/other/error_box.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(350);
                imageView.setFitHeight(200);
                //double x = (StageLauncher.getWidth() - 350) / 2;
                //double y = (StageLauncher.getHeight() - 200) / 2;
                //imageView.relocate(x, y);

                Text text = new Text(errorMessage);
                //text.relocate(620, 380);
                text.setFont(Font.font(20));
                text.setFill(Color.rgb(173, 225, 218, 0.5));

                stackPane.getChildren().addAll(rectangle, imageView, text);

                root.getChildren().addAll(stackPane);
                imageView.setOnMouseClicked(event -> {
                    root.getChildren().remove(stackPane);
                });
            } catch (Exception ignored) {
            }

        });

    }

}
