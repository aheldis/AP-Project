package view;

import com.sun.javafx.scene.traversal.ParentTraversalEngine;
import javafx.scene.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GeneralGraphicMethods {

    /*static void setBackground(Parent root, String path, Boolean blur, double width, double height) {
        ImageView imageView = addImage(root, path, 0, 0,WIDTH,HEIGHT);
        if (imageView == null)
            return;
        if (blur) {
            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(width);
            boxBlur.setHeight(height);
            boxBlur.setIterations(1);
            imageView.setEffect(boxBlur);
        }
        imageView.fitWidthProperty();
        imageView.fitHeightProperty();
    }
*/

    static void setBackground(StackPane root, String path, Boolean blur, double width, double height) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (image == null)
            return;
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true,
                true, true,true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        StackPane background = new StackPane();
        background.setBackground(new Background(backgroundImage));

        if (blur) {
            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(width);
            boxBlur.setHeight(height);
            boxBlur.setIterations(1);
            background.setEffect(boxBlur);
        }
        root.getChildren().add(background);
    }

    static Text addText(Parent root, String input, double x, double y, Paint color, double fontSize) {
        Text text = new Text(input);
        text.setX(x);
        text.setY(y);
        text.setFill(color);
        text.setFont(Font.font(fontSize));
        nodeAdder(text, root);
        return text;
    }

    private static void nodeAdder(Node node, Parent root) {
        if (root instanceof StackPane)
            ((StackPane) root).getChildren().add(node);
        if (root instanceof HBox)
            ((HBox) root).getChildren().add(node);
        if (root instanceof VBox)
            ((VBox) root).getChildren().add(node);
    }

    static ImageView addImage(Parent root, String path, double x, double y, double width, double height) {
        try {
            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);

            nodeAdder(imageView, root);

            imageView.relocate(x, y);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
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
}
