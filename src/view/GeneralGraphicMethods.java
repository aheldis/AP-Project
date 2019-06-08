package view;

import javafx.scene.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.enums.Cursor;
import view.sample.StageLauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GeneralGraphicMethods {
    private static double HEIGHT = StageLauncher.getHeight();
    private static double WIDTH = StageLauncher.getWidth();
    private static double sabasXRatio = 0.7563451776649747;
    private static double sabasYRatio = 0.8592592592592593;
    private static double zahrasXRatio = 0.7563451776649747;
    private static double zahrasYRatio = 0.8592592592592593;

    public static double getSabasXRatio() {
        return sabasXRatio;
    }

    public static double getSabasYRatio() {
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

    public static void setCursor(Scene scene, Cursor cursor) {
        String path = cursor.getPath();
        try {
            scene.setCursor(new ImageCursor(new Image(new FileInputStream(path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static javafx.scene.shape.Rectangle addRectangle(Parent root, int x, int y, int width, int height, int arcW, int arcH, Paint color) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setArcWidth(arcW);
        rectangle.setArcHeight(arcH);
        nodeAdder(rectangle, root);
        return rectangle;
    }

    static double getRatioX() {
        //return 1;
        return StageLauncher.getWidth() / 1970;
    }

    static double getRatioY() {
        //return 1;
        return StageLauncher.getHeight() / 1080;
    }

    {
        File file = new File("resource/fonts");
        File[] files = file.listFiles();
        if (files != null) {
            for(File file1: files){
                if(file1.getName().contains("ttf"))
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

}
