package view.Graphic;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
//                BoxBlur boxblur = new BoxBlur();
//                StackPane stackPane = new StackPane(root);
//                scene.setRoot(stackPane);
//                stackPane.setPadding(Insets.EMPTY);

                Rectangle rectangle = new Rectangle(
                        stage.getWidth(),
                        stage.getHeight(),
                        Color.rgb(225, 225, 225, 0.3));

                rectangle.relocate(0, 0);
//                //Setting the width of the box filter
//                boxblur.setWidth(10.0f);
//
//                //Setting the height of the box filter
//                boxblur.setHeight(10.0f);
//
//                //Setting the no of iterations
//                boxblur.setIterations(1);
//
//                root.setEffect(boxblur);

                double ratioX = GeneralGraphicMethods.getRatioX();
                double ratioY = GeneralGraphicMethods.getRatioY();
                double sbasRatioX = GeneralGraphicMethods.getSabasXRatio();
                double sbasRatioY = GeneralGraphicMethods.getSabasYRatio();
                javafx.scene.image.Image image = new Image(new FileInputStream("pics/other/error_box.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(350 / sbasRatioX * ratioX);
                imageView.setFitHeight(200 / sbasRatioY * ratioY);
                imageView.relocate((600 / sbasRatioX - 40) * ratioX, 300 / sbasRatioY * ratioY);

                Text text = new Text(errorMessage);
                text.relocate(620 / sbasRatioX * ratioX-45, 380 / sbasRatioY * ratioY);
                text.setFont(Font.font(20));
                text.setFill(Color.rgb(173, 225, 218, 0.5));
                root.getChildren().addAll(rectangle, imageView, text);
//                stackPane.getChildren().add(imageView);
//                stackPane.getChildren().add(text);
                imageView.setOnMouseClicked(event -> {
                    //root.setEffect(null);
                    //stackPane.getChildren().clear();
                    //scene.setRoot(root);
                    root.getChildren().removeAll(text, rectangle, imageView);


                });
            } catch (Exception ignored) {
            }

        });

    }

}
