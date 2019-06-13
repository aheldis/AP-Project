package view.Graphic;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import model.item.Usable;

class DragAndDropClass {
    private static double orgSceneX, orgSceneY;
    private static double firstX, firstY;
    private static double dx, dy;
    private static Group sourceRoot;
    private static Node source = null;

    static void dragAndDropForCollection(Node source, Node target, Deck deck, Object card, VBox sourceRoot, Group sceneRoot,
                                         double dx, double dy) {
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            sourceRoot.getChildren().remove(source);
            source.relocate(orgSceneX - dx, orgSceneY - dy);
            sceneRoot.getChildren().add(source);
        });

        setOnMouseDragged(source);

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
                            System.out.println("an item");
                            deck.addItemToDeck((Usable) card);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(DragAndDropClass.source);
                    e.printStackTrace();
                }
            } else {
                sourceRoot.getChildren().add(source);
                if (deck.cardHaveBeenExistInThisDeck(((Card) card).getCardId().getCardIdAsString()) != null)
                    deck.removeFromCardsOfDeck((Card) card);
            }
        });
    }

    static void dragAndDropForGame(Node source, Object card, Group sourceRoot, Group sceneRoot,
                                   double dx, double dy, double firstX, double firstY) {
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            DragAndDropClass.dx = dx;
            DragAndDropClass.dy = dy;
            if (DragAndDropClass.sourceRoot == null)
                DragAndDropClass.sourceRoot = sourceRoot;
            sourceRoot.getChildren().remove(source);
            DragAndDropClass.firstX = source.getLayoutX();
            DragAndDropClass.firstY = source.getLayoutY();
            if (DragAndDropClass.firstX != firstX && DragAndDropClass.firstY != firstY) {
                DragAndDropClass.dx = orgSceneX - DragAndDropClass.firstX;
                DragAndDropClass.dy = orgSceneY - DragAndDropClass.firstY;
            }
            source.relocate(orgSceneX - DragAndDropClass.dx, orgSceneY - DragAndDropClass.dy);
            sceneRoot.getChildren().add(source);
        });

        setOnMouseDragged(source);


        source.setOnMouseReleased(event -> {
            sceneRoot.getChildren().remove(source);
            boolean breaker = false;
            BattleScene battleScene = BattleScene.getSingleInstance();
            Group group = battleScene.addCardToBoard(event.getSceneX(), event.getSceneY(),
                    (Card) card, (ImageView) source);
            if (group != null) {
                breaker = true;
                DragAndDropClass.sourceRoot = group;
            }
            if (!breaker) {
                source.relocate(DragAndDropClass.firstX, DragAndDropClass.firstY);
                DragAndDropClass.sourceRoot.getChildren().add(source);
            }
        });
    }

    private static void setOnMouseDragged(Node source) {
        source.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            source.relocate(source.getLayoutX() + offsetX, source.getLayoutY() + offsetY);
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            event.consume();
        });
    }
}
