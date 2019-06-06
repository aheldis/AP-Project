import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DragAndDropClass {
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 800);
        Circle circle2 = new Circle(30, Color.PINK);
        circle2.setCenterX(400);
        circle2.setCenterY(500);

        Circle circle = new Circle(30, Color.BLACK);
        circle.setCenterX(40);
        circle.setCenterY(40);
        root.getChildren().addAll(circle, circle2);
        circle.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circleOnMousePressedEventHandler.handle(event);
                System.out.println("bye");
                circle.setFill(Color.RED);
                Dragboard dragboard = circle.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("hi");
                dragboard.setContent(content);
                event.consume();
            }
        });

        circle.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                circleOnMouseDraggedEventHandler.handle(event);
            }
        });

        circle2.setOnDragOver((DragEvent event) -> {
            if (event.getGestureSource() != circle2) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        circle2.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != circle2) {
                    circle2.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        circle2.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                circle2.setFill(Color.PINK);
                event.consume();
            }
        });

        circle2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
            }
        });

        circle.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    root.getChildren().remove(circle);
                }

                event.consume();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle) (t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle) (t.getSource())).getTranslateY();
                }
            };

    EventHandler<DragEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Circle) (t.getSource())).setTranslateX(newTranslateX);
                    ((Circle) (t.getSource())).setTranslateY(newTranslateY);
                }
            };
}
