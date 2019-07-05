package view.Graphic;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;

public class SpriteMaker {
    private static SpriteMaker ourInstance = new SpriteMaker();

    public static SpriteMaker getInstance() {
        return ourInstance;
    }

    private SpriteMaker() {
    }

    ImageView makeSpritePic(String path, double x, double y, Parent root, int count,
                            int numberOfPicInEachColumn, long millis,
                            double widthOfEachFrame, double heightOfEachFrame) {
        System.out.println("path = " + path);
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("count = " + count);
        System.out.println("numberOfPicInEachColumn = " + numberOfPicInEachColumn);
        System.out.println("millis = " + millis);
        System.out.println("widthOfEachFrame = " + widthOfEachFrame);
        System.out.println("heightOfEachFrame = " + heightOfEachFrame);
        ImageView imageView = null;
        try {
            Image image = new Image(new FileInputStream(path));
            imageView = new ImageView(image);
            imageView.relocate(x, y);
            imageView.setScaleX(2);
            imageView.setScaleY(2);

            imageView.setViewport(new Rectangle2D(0, 0, widthOfEachFrame, heightOfEachFrame));

            //root.getChildrenUnmodifiable().add(imageView);
            final Animation animation = new SpriteAnimation(
                    imageView,
                    Duration.millis(millis),
                    count, numberOfPicInEachColumn,
                    1, 0,
                    widthOfEachFrame, heightOfEachFrame
            );
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();

            if (root instanceof Group) {
                ((Group) root).getChildren().add(imageView);
            }
            if (root instanceof HBox) {
                ((HBox) root).getChildren().add(imageView);
            }
            if (root instanceof StackPane) {
                ((StackPane) root).getChildren().add(imageView);
            }

        } catch (Exception ignored) {

        }
        return imageView;
    }
}
