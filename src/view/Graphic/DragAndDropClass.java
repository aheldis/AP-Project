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
    private static Node source = null;

    static void dragAndDrop(Node source, Node target, Deck deck, Object card, Parent sourceRoot, Group sceneRoot,
                            double dx, double dy) {
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            firstX = source.getLayoutX();
            firstY = source.getLayoutY();
            if (sourceRoot instanceof VBox)
                ((VBox) sourceRoot).getChildren().remove(source);
            else
                ((Group) sourceRoot).getChildren().remove(source);
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
            boolean breaker = false;
            if (target == null) {
                BattleScene battleScene = BattleScene.getSingleInstance();
                ImageView imageView = battleScene.addCardToBoard(event.getSceneX(), event.getSceneY(),
                        (Card) card, (ImageView) source);
                if (imageView != null)
                    breaker = true;
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
                if (sourceRoot instanceof VBox)
                    ((VBox) sourceRoot).getChildren().add(source);
                else {
                    source.relocate(firstX, firstY);
                    ((Group) sourceRoot).getChildren().add(source);
                }
            }
        });
    }
}
