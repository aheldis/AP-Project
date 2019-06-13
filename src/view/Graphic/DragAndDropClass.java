package view.Graphic;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import model.item.Usable;
import view.enums.ErrorType;

class DragAndDropClass {
    private static double orgSceneX, orgSceneY;
    private static Node source = null;

    static void dragAndDrop(Node source, Node target, Deck deck, Object card, VBox sourceRoot, Group sceneRoot,
                            double dx, double dy) {
        source.setOnMousePressed(event -> {
            if(deck.getItem() != null && card instanceof Usable) {
                ErrorType.HAVE_ONE_ITEM_IN_DECK.printMessage();
                return;
            }
            if(card instanceof Card && !deck.canAddCard((Card) card)){
                return;
            }
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            sourceRoot.getChildren().remove(source);
            source.relocate(orgSceneX - dx, orgSceneY - dy);
            sceneRoot.getChildren().add(source);
        });

        source.setOnMouseDragged(event -> {
            if(deck.getItem() != null && card instanceof Usable) {
                ErrorType.HAVE_ONE_ITEM_IN_DECK.printMessage();
                return;
            }
            if(card instanceof Card && !deck.canAddCard((Card) card)){
                return;
            }
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            source.relocate(source.getLayoutX() + offsetX, source.getLayoutY() + offsetY);
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            event.consume();
        });

        source.setOnMouseReleased(event -> {
            if(deck.getItem() != null && card instanceof Usable) {
                ErrorType.HAVE_ONE_ITEM_IN_DECK.printMessage();
                return;
            }
            if(card instanceof Card && !deck.canAddCard((Card) card)){
                return;
            }
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
            }
        });
    }
}
