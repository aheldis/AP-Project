package view.Graphic;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.enums.Cursor;
import view.enums.StateType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GeneralGraphicMethods {
    private static double HEIGHT = StageLauncher.getHeight();
    private static double WIDTH = StageLauncher.getWidth();
    private static double sabasXRatio = 1;//0.7563451776649747;
    private static double sabasYRatio = 1;//0.8592592592592593;
    private static double zahrasXRatio = 1;//0.7868020304568528;
    private static double zahrasYRatio = 1;//0.9351851851851852;

    static double getSabasXRatio() {
        return sabasXRatio;
    }

    static double getSabasYRatio() {
        return sabasYRatio;
    }

    static ImageView setBackground(Parent root, String path, Boolean blur, double width, double height) {
        ImageView imageView = addImage(root, path, 0, 0, WIDTH / getRatioX(), HEIGHT / getRatioY());
        if (imageView == null)
            return null;
        if (blur) {
            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(width);
            boxBlur.setHeight(height);
            boxBlur.setIterations(1);
            imageView.setEffect(boxBlur);
        }
        imageView.fitWidthProperty();
        imageView.fitHeightProperty();
        return imageView;
    }

    static Text addText(Parent root, String input, double x, double y, Paint color, double fontSize) {
        Text text = new Text(input);
        text.relocate(x, y);
        text.setFill(color);
        text.setFont(Font.font(fontSize));
        nodeAdder(text, root);
        return text;
    }

    static Rectangle addRectangleForCollection(Parent root, int x, int y, int width, int height, int arcW, int arcH, Paint color) {
        Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setArcWidth(arcW);
        rectangle.setArcHeight(arcH);
        nodeAdder(rectangle, root);
        return rectangle;
    }

    private static void nodeAdder(Node node, Parent root) {
        if (root instanceof Group)
            ((Group) root).getChildren().add(node);
        if (root instanceof HBox)
            ((HBox) root).getChildren().add(node);
        if (root instanceof VBox)
            ((VBox) root).getChildren().add(node);
        if (root instanceof StackPane)
            ((StackPane) root).getChildren().add(node);
    }

    static ImageView addImage(Parent root, String path, double x, double y, double width, double height) {
        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream(path)));
            /*
            imageView.relocate(x,y);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
*/

            imageView.setFitWidth(width * getRatioX());
            imageView.setFitHeight(height * getRatioY());
            imageView.setLayoutX(x * getRatioX());
            imageView.setLayoutY(y * getRatioY());
            imageView.relocate(x * getRatioX(), y * getRatioY());
/*
            imageView.relocate(x, y);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
*/
            nodeAdder(imageView, root);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageView;
    }

    static void setCursor(Scene scene, Cursor cursor) {
        String path = cursor.getPath();
        try {
            scene.setCursor(new ImageCursor(new Image(new FileInputStream(path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static javafx.scene.shape.Rectangle addRectangle(Parent root, int x, int y, int width, int height, int arcW, int arcH, Paint color) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        rectangle.relocate(x, y);
        rectangle.setFill(color);
        rectangle.setArcWidth(arcW);
        rectangle.setArcHeight(arcH);
        nodeAdder(rectangle, root);
        return rectangle;
    }

    static double getRatioX() {
        return 1;
//        return 1380 / 1970;
    }

    static double getRatioY() {
        return 1;
//        return 850 / 1080;
    }

    static {
        File file = new File("resource/fonts");
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                if (file1.getName().contains("ttf"))
                    Font.loadFont(file1.getPath(), 50);
            }
        }
    }

    static double changeXWithSabasRatio(double x) {
        return x / sabasXRatio * getRatioX();
    }

    static double changeYWithSabasRatio(double y) {
        return y / sabasYRatio * getRatioY();
    }

    static double changeXWithZahrasRatio(double x) {
        return x / zahrasXRatio * getRatioX();
    }

    static double changeYWithZahrasRatio(double y) {
        return y / zahrasYRatio * getRatioY();
    }

    static void makeCircleRotation(ImageView imageView, int centerX, int centerY) {
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {

                for (int i = 0; i < 20; i++) {
                    if (now - lastUpdate >= 120_000_000) {
                        imageView.getTransforms().add(new Rotate(30, centerX, centerY));
                        lastUpdate = now;
                    }
                }

            }
        };
        animationTimer.start();
    }

    static void log(Group root, String helps, Scene backScene, int height) {
        ImageView log = addImage(root, "pics/log.png", -40, 200, 70, 150);
        ImageView expand = addImage(root, "pics/glow_next.png", 0, 300 - 15, 20, 30);
        ImageView back = addImage(root, "pics/glow_back.png", 40, 300 - 15, 20, 30);
        root.getChildren().remove(back);
        expand.setOnMouseEntered(event -> {
            root.getChildren().remove(expand);
            root.getChildren().addAll(back);
            log.setX(40);
            ImageView backButton = addImage(root, "pics/left-arrow.png", 5, 230, 20, 20);
            ImageView help = addImage(root, "pics/question.png", 7, 280, 18, 20);

            backButton.setOnMouseClicked(event12 -> Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    StageLauncher.getPrimaryStage().setScene(backScene);
                }
            }));

            help.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Rectangle rectangle = addRectangle(root, 450, 200,
                            400, height, 50, 50, Color.rgb(0, 0, 0, 0.7));
                    ImageView close = addImage(root,
                            "pics/collection/button_close@2x.png", 800, 200, 50, 50);
                    Text text = addText(root, helps, 470, 250,
                            Color.rgb(225, 225, 225, 0.8), 20);
                    close.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            root.getChildren().removeAll(text, close, rectangle);
                        }
                    });
                }
            });

            back.setOnMouseEntered(event1 -> {
                root.getChildren().removeAll(back, backButton, help);
                root.getChildren().addAll(expand);
                log.setX(0);
            });
        });

    }

    static void playMusic(String path, boolean constant, Scene firstScene) {
        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        if (constant) {
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
            mediaPlayer.play();
        }
        mediaPlayer.setAutoPlay(true);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (StageLauncher.getPrimaryStage().getScene() != firstScene) {
                    mediaPlayer.stop();
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    static void setScene(StateType state) {
        Scene scene = StageLauncher.getScene(state);
        Stage primaryStage = StageLauncher.getPrimaryStage();
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
