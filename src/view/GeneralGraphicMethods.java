package view;

import javafx.scene.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GeneralGraphicMethods {
    private static double HEIGHT = StageLauncher.getHeight();
    private static double WIDTH = StageLauncher.getWidth();

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

    public static Text addText(Parent root, String input, double x, double y, Paint color, double fontSize) {
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
        if(root instanceof StackPane)
            ((StackPane) root).getChildren().add(node);
    }

    public static ImageView addImage(Parent root, String path, double x, double y, double width, double height) {
        try {
            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);
            imageView.relocate(x * getRatioX(), y * getRatioY());
            imageView.setX(x * getRatioX());
            imageView.setY(y * getRatioY());
            imageView.setFitHeight(height * getRatioY());
            imageView.setFitWidth(width * getRatioX());
            nodeAdder(imageView, root);
            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setCursor(Scene scene, String path) {
        try {
            scene.setCursor(new ImageCursor(new Image(new FileInputStream(path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static double getRatioX() {
        return StageLauncher.getWidth() / 1970;
    }

    static double getRatioY() {
        return StageLauncher.getHeight() / 1080;
    }
}
