package view.Graphic;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import model.item.Usable;

class DragAndDropClass {
    private static double orgSceneX, orgSceneY;
    private static Node source = null;

    static void dragAndDrop(Node source, Node target, Deck deck, Object card, VBox sourceRoot, Group sceneRoot,
                            double dx, double dy) {
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            sourceRoot.getChildren().remove(source);
            source.relocate(orgSceneX - dx, orgSceneY - dy);
            sceneRoot.getChildren().add(source);
        });

        source.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            source.relocate(source.getLayoutX() + offsetX, source.getLayoutY() + offsetY);
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            event.consume();
        });

        source.setOnMouseReleased(event -> {
            sceneRoot.getChildren().remove(source);
            if (target.contains(event.getSceneX(), event.getSceneY())) {
                try {
                    if (target instanceof VBox) {
                        ((VBox) target).getChildren().add(source);
                        if (card instanceof Card) {
                            deck.addToCardsOfDeck((Card) card);
                        }
                        if (card instanceof Item) {
                            deck.addItemToDeck((Usable) card);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(DragAndDropClass.source);
                    e.printStackTrace();
                }
            } else {
                sourceRoot.getChildren().add(source);
            }
        });
    }
}
