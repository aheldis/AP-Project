package view.Graphic;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.enums.Cursor;
import view.enums.StateType;

import java.io.*;

public class GeneralGraphicMethods {
    private static double HEIGHT = StageLauncher.getHeight();
    private static double WIDTH = StageLauncher.getWidth();
    private static double sabasXRatio = 1;//0.7563451776649747;
    private static double sabasYRatio = 1;//0.8592592592592593;
    private static double zahrasXRatio = 1;//0.7868020304568528;
    private static double zahrasYRatio = 1;//0.9351851851851852;
    static boolean stopper = false;

    static double getSabasXRatio() {
        return sabasXRatio;
    }

    static double getSabasYRatio() {
        return sabasYRatio;
    }

    public static ImageView setBackground(Parent root, String path, Boolean blur, double width, double height) {
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

    public static Text addText(Parent root, String input, double x, double y, Paint color, double fontSize) {
        Text text = new Text(input);
        text.relocate(x, y);
        text.setFill(color);
        text.setFont(Font.font("Lato Regular", fontSize));
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

    public static ImageView createImage(String path, double width, double height) {
        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream(path)));
            imageView.setFitWidth(width * getRatioX());
            imageView.setFitHeight(height * getRatioY());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageView;

    }

    public static ImageView addImage(Parent root, String path, double x, double y, double width, double height) {
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

    static void log(Group root, String helps, StateType backState, int height) {
        ImageView log = addImage(root, "pics/other/log.png", -40, 200, 70, 150);
        ImageView expand = addImage(root, "pics/other/glow_next.png", 0, 300 - 15, 20, 30);
        ImageView back = addImage(root, "pics/other/glow_back.png", 40, 300 - 15, 20, 30);
        root.getChildren().remove(back);
        expand.setOnMouseEntered(event -> {
            root.getChildren().remove(expand);
            root.getChildren().addAll(back);
            log.setX(40);
            ImageView backButton = addImage(root, "pics/other/left-arrow.png", 5, 230, 20, 20);
            ImageView help = addImage(root, "pics/other/question.png", 7, 280, 18, 20);

            backButton.setOnMouseClicked(event12 -> {
                root.getChildren().clear();
                StageLauncher.decorateScene(backState);
            });

            help.setOnMouseClicked(event13 -> {
                Rectangle rectangle = addRectangle(root, 450, 200,
                        400, height, 50, 50, Color.rgb(0, 0, 0, 0.7));
                ImageView close = addImage(root,
                        "pics/collection/button_close@2x.png", 800, 200, 50, 50);
                Text text = addText(root, helps, 470, 250,
                        Color.rgb(225, 225, 225, 0.8), 20);
                close.setOnMouseClicked(event131 -> root.getChildren().removeAll(text, close, rectangle));
            });

            back.setOnMouseEntered(event1 -> {
                root.getChildren().removeAll(back, backButton, help);
                root.getChildren().addAll(expand);
                log.setX(0);
            });
        });

    }

   public static void playMusic(String path, boolean constant, Scene firstScene) {
        Media media = new Media(new File(path).toURI().toString());
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

    static Text addTextWithShadow(String textString, double x, double y, Parent group, String fontFamily, int size) {
        Text text = new Text(textString);
        text.relocate(x, y);
        text.setFont(Font.font(fontFamily, FontWeight.BOLD, size));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        text.setEffect(dropShadow);
        nodeAdder(text, group);
        return text;
    }

    static void setOnMouseEntered(Node node, Scene scene, boolean glowBoolean) {
        playMusic("resource\\music\\sfx_ui_tab_in.m4a",false,scene);
        Glow glow = new Glow(0);
        node.setEffect(glow);
        node.setOnMouseEntered(event -> {
            setCursor(scene, Cursor.LIGHTEN);
            if (glowBoolean)
                glow.setLevel(1);
            if(!glowBoolean)
                glow.setLevel(0);
        });
        node.setOnMouseExited(event -> {
            setCursor(scene, Cursor.AUTO);
            if (glowBoolean)
                glow.setLevel(0);
        });
    }

    static ImageView addBack(Group root) {
        addImage(root, "pics/other/circle.png", 100, 750, 70, 70);
        return addImage(root, "pics/other/back.png", 115, 765, 40, 40);
    }

    static ImageView addNext(Group root) {
        addImage(root, "pics/other/circle.png", 1200, 750, 70, 70);
        return addImage(root, "pics/other/next.png", 1215, 765, 40, 40);
    }

    static Button imageButton(Scene scene, Group root, String path, String name,
                              double x, double y, double width, double height) {
        Button button = new javafx.scene.control.Button(name);
        button.setPrefSize(width, height);
        button.relocate(x, y);
        BackgroundImage backgroundImage = null;
        try {
            backgroundImage = new BackgroundImage(
                    new Image(new FileInputStream(path), width, height, false, false),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Background background = new Background(backgroundImage);
        button.setBackground(background);
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 19");
        root.getChildren().add(button);
        setOnMouseEntered(button, scene, true);
        return button;
    }


    static Lighting getLighting(Color color) {
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(45, 45,
                color));
        return lighting;
    }

    public static void saveInFile(String path, Object object) {
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
            YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(file);
            altMapper.toJson(object, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
