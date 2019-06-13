package view.Graphic;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    private static Parent sourceRoot;
    private static Node source = null;

    static void dragAndDrop(Node source, Node target, Deck deck, Object card, Parent sourceRoot, Group sceneRoot,
                            double dx, double dy) {
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            DragAndDropClass.dx = dx;
            DragAndDropClass.dy = dy;
            if (sourceRoot instanceof VBox)
                ((VBox) sourceRoot).getChildren().remove(source);
            else {
                if (DragAndDropClass.sourceRoot == null)
                    DragAndDropClass.sourceRoot = sourceRoot;
                ((Group) sourceRoot).getChildren().remove(source);
                firstX = source.getLayoutX();
                firstY = source.getLayoutY();
                if (firstX != 15 && firstY != -21) {
                    DragAndDropClass.dx = orgSceneX - firstX;
                    DragAndDropClass.dy = orgSceneY - firstY;
                }
            }
            source.relocate(orgSceneX - DragAndDropClass.dx, orgSceneY - DragAndDropClass.dy);
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
            boolean breaker = false;
            if (target == null) {
                BattleScene battleScene = BattleScene.getSingleInstance();
                Group group = battleScene.addCardToBoard(event.getSceneX(), event.getSceneY(),
                        (Card) card, (ImageView) source);
                if (group != null) {
                    breaker = true;
                    DragAndDropClass.sourceRoot = group;
                }
            }
            if (target != null && target.contains(event.getSceneX(), event.getSceneY())) {
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
            } else if (!breaker) {
                if (sourceRoot instanceof VBox) {
                    ((VBox) sourceRoot).getChildren().add(source);
                    if (deck.cardHaveBeenExistInThisDeck(((Card) card).getCardId().getCardIdAsString()) != null)
                        deck.removeFromCardsOfDeck((Card) card);
                } else {
                    source.relocate(firstX, firstY);
                    ((Group) DragAndDropClass.sourceRoot).getChildren().add(source);
                }
            }
        });
    }
}
