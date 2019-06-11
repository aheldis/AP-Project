package view.Graphic;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import model.item.Usable;

public class DragAndDropClass {
    static double orgSceneX, orgSceneY;
    static double orgTranslateX, orgTranslateY;

    public static void dragAndDrop(Node source, Node target, Deck deck, Object card) {

        source.setOnDragDetected(event -> {
            //circleOnMousePressedEventHandler.handle(event);
            Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("hi");
            dragboard.setContent(content);
            event.consume();
        });

//        source.setOnDragOver(event -> {
//                    System.out.println("here");
//
//                   // circleOnMouseDraggedEventHandler.handle(event);
//                }
//        );

        target.setOnDragOver((DragEvent event) -> {
            if (event.getGestureSource() != target) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target) {
                    System.out.println("hello girls, drop here");
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("chera rafti?");
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("line 78");
                System.out.println(source);
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);
                try {
                    if (target instanceof VBox) {
                        ((VBox) target).getChildren().add(source);
                        if(card instanceof Card)
                            deck.addToCardsOfDeck((Card)card);
                        if(card instanceof Item)
                            deck.addItemToDeck((Usable) card);
                    }
                }catch (Exception e){
                    System.out.println(source);
                    e.printStackTrace();
                }
                event.consume();
            }
        });

        target.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("tamome");

                }

                event.consume();
            }
        });
    }

    static EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                   // orgTranslateX = ((ImageView)((t.getSource())).getTranslateX();
                  //  orgTranslateY = ( (t.getSource())).getTranslateY();
                }
            };

    static EventHandler<DragEvent> circleOnMouseDraggedEventHandler =
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