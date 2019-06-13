package view.Graphic;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import model.item.Usable;

class DragAndDrop {
    private static double orgSceneX, orgSceneY;
    private static double firstX, firstY;
    private static double dx, dy;
    private static Group sourceRoot;
    private static Node source = null;

    void dragAndDropForCollection(Node source, Node target, Deck deck, Object card, VBox sourceRoot, Group sceneRoot,
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
                    System.out.println(DragAndDrop.source);
                    e.printStackTrace();
                }
            } else {
                sourceRoot.getChildren().add(source);
                if (deck.cardHaveBeenExistInThisDeck(((Card) card).getCardId().getCardIdAsString()) != null)
                    deck.removeFromCardsOfDeck((Card) card);
            }
        });
    }

    void dragAndDropForGame(Node source, Object card, Group sourceRoot, Group sceneRoot,
                            double dx, double dy, double firstX, double firstY) {
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            DragAndDrop.dx = dx;
            DragAndDrop.dy = dy;
            if (DragAndDrop.sourceRoot == null)
                DragAndDrop.sourceRoot = sourceRoot;
            sourceRoot.getChildren().remove(source);
            DragAndDrop.firstX = source.getLayoutX();
            DragAndDrop.firstY = source.getLayoutY();
            if (DragAndDrop.firstX != firstX && DragAndDrop.firstY != firstY) {
                DragAndDrop.dx = orgSceneX - DragAndDrop.firstX;
                DragAndDrop.dy = orgSceneY - DragAndDrop.firstY;
            }
            source.relocate(orgSceneX - DragAndDrop.dx, orgSceneY - DragAndDrop.dy);
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
                DragAndDrop.sourceRoot = group;
            }
            if (!breaker) {
                source.relocate(DragAndDrop.firstX, DragAndDrop.firstY);
                DragAndDrop.sourceRoot.getChildren().add(source);
            }
        });
    }

    private void setOnMouseDragged(Node source) {
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
