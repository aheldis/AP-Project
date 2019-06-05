package view;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GeneralGraphicMethods {

    private static int HEIGHT = StageLauncher.getHEIGHT();
    private static int WIDTH = StageLauncher.getWIDTH();

    public static void setBackground(Group root, String path, Boolean blur, double width, double height) {
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

    public static Text addText(Group root, String input, int x, int y, Paint color, double fontSize){
        Text text = new Text(input);
        text.setX(x);
        text.setY(y);
        text.setFill(color);
        text.setFont(Font.font(fontSize));
        root.getChildren().add(text);
        return text;
    }

    public static ImageView addImage(Group root, String path, int x, int y,int width,int height) {
        try {
            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);
            root.getChildren().add(imageView);
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
