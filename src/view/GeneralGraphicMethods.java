package view;

import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.sample.StageLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GeneralGraphicMethods {

    private static int height = StageLauncher.getHEIGHT();
    private static int width = StageLauncher.getWIDTH();

    public static void setBackground(Group root, String path, Boolean blur) {
            ImageView imageView = addImage(root, path, 0, 0);
            if(imageView == null)
                return;
            if (blur) {
                BoxBlur boxBlur = new BoxBlur();
                boxBlur.setWidth(20.0f);
                boxBlur.setHeight(20.0f);
                boxBlur.setIterations(1);
                imageView.setEffect(boxBlur);
            }
        imageView.fitWidthProperty();
        imageView.fitHeightProperty();
    }

    public static ImageView addImage(Group root, String path, int x, int y) {
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
}
