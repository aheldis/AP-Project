package view.Graphic;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.Graphic.StageLauncher;

import java.util.Random;

public class Raining {
    static Random random = new Random();

    public static void rain(Scene scene) {

        Group root = new Group();
        scene.setFill(Color.BLACK);

        Circle c[] = new Circle[2000];
        try {

            for (int i = 0; i < 2000; i++) {
                c[i] = new Circle(1, 1, 1);
                c[i].setRadius(random.nextDouble() * 3);
                Color color = Color.rgb(255, 255, 255, random.nextDouble());
                c[i].setFill(color);
                root.getChildren().add(c[i]);
                Raining(c[i], root);
            }

        } catch (Exception e) {

        }
    }



    private static void Raining(Circle c,Group root) {

        KeyValue XValue = new KeyValue(c.centerXProperty(),random.nextDouble() * c.getCenterX());
        KeyValue YValue = new KeyValue(c.centerYProperty(),534+200);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(5000),XValue,YValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        Path path = new Path(new MoveTo(100,100),new LineTo(500,500));
        path.setVisible(true);
        root.getChildren().add(path);
        PathTransition pathTransition = new PathTransition(Duration.INDEFINITE,path,c);
        pathTransition.setCycleCount(5);
        pathTransition.play();



        c.setCenterX(random.nextInt((int) StageLauncher.getWidth())); //Window width = 950

/*
        int time = 10 + random.nextInt(50);
        Animation fall = TranslateTransitionBuilder.create()
                .node(c)
                .fromY(-200)
                .toY(534+200) //Window height = 534
                .toX(random.nextDouble() * c.getCenterX())
                .duration(Duration.seconds(time))
                .onFinished(t -> Raining(c,root)).build();
        fall.play();
        */
    }
}
