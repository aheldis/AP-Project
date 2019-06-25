package view.Graphic;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.battle.Deck;
import model.battle.Hand;
import model.card.Card;
import model.card.Spell;
import model.item.Item;
import model.item.Usable;
import view.enums.Cursor;

import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.setCursor;

public class DragAndDrop {
    private double orgSceneX, orgSceneY;
    private double firstX, firstY;
    private double dx, dy;
    private Parent sourceRoot;
    private Parent target;
    private Node source = null;
    private final int NUMBER_OF_ROWS = 5;
    private static boolean wait = false;
    private boolean start = false;

    void dragAndDropForCollection(Node source, Parent target, Deck deck, Object card, VBox sourceRoot, Group sceneRoot,
                                  double dx, double dy, ArrayList<VBox> vBoxes) {
        this.target = target;
        this.sourceRoot = sourceRoot;
        if (this.target == null)
            for (VBox vBox : vBoxes)
                if (vBox.getChildren().size() < NUMBER_OF_ROWS) {
                    this.target = vBox;
                    break;
                }
        source.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            sourceRoot.getChildren().remove(source);
            source.relocate(orgSceneX - dx, orgSceneY - dy);
            sceneRoot.getChildren().add(source);
            start = true;
        });

        setOnMouseDragged(source, null, null, false);

        source.setOnMouseReleased(event -> {
            sceneRoot.getChildren().remove(source);
            boolean returnToFirstPosition = true;
            if (this.target != null && this.target.contains(event.getSceneX(), event.getSceneY()) && this.target instanceof VBox) {
                try {
                    if (!vBoxes.contains(this.target)) {
                        if (card instanceof Card && deck.addToCardsOfDeck((Card) card)) {
                            returnToFirstPosition = false;
                        }
                        if (card instanceof Item) {
                            System.out.println("an item");
                            deck.addItemToDeck((Usable) card);
                            returnToFirstPosition = false;
                        }
                    }
                    if (!returnToFirstPosition) {
                        ((VBox) this.target).getChildren().add(source);
                        Parent replace = this.target;
                        this.target = this.sourceRoot;
                        this.sourceRoot = replace;
                    }
                } catch (Exception e) {
                    System.out.println(this.source);
                    e.printStackTrace();
                }
            }
            if (returnToFirstPosition) {
                if ((this.target instanceof VBox && vBoxes.contains(this.target) &&
                        ((VBox) this.target).getChildren().size() >= NUMBER_OF_ROWS) ||
                        (this.sourceRoot instanceof VBox && vBoxes.contains(this.sourceRoot) &&
                                ((VBox) this.sourceRoot).getChildren().size() > NUMBER_OF_ROWS)) {
                    for (VBox vBox : vBoxes) {
                        System.out.println(vBox.getChildren().size());
                        if (vBox.getChildren().size() < NUMBER_OF_ROWS) {
                            vBox.getChildren().add(source);
                            Parent replace = this.target;
                            this.target = this.sourceRoot;
                            this.sourceRoot = replace;
                            break;
                        }
                    }
                } else if (this.target instanceof VBox && vBoxes.contains(this.target)) {
                    ((VBox) this.target).getChildren().add(source);
                    Parent replace = this.target;
                    this.target = this.sourceRoot;
                    this.sourceRoot = replace;
                } else if (this.sourceRoot instanceof VBox) {
                    this.target = target;
                    ((VBox) this.sourceRoot).getChildren().add(source);
                }
                if (this.target != null) {
                    if (card instanceof Item &&
                            deck.getItem().getUsableId().getUsableIdAsString().
                                    equals(((Usable) card).getUsableId().getUsableIdAsString()))
                        deck.removeItemOfDeck((Usable) (card));
                    else if (card instanceof Card &&
                            deck.cardHaveBeenExistInThisDeck(((Card) card).getCardId().getCardIdAsString()) != null)
                        deck.removeFromCardsOfDeck((Card) card);
                    else if (deck.getHero() == card) {
                        deck.setHero(null);
                    }
                }
            }
            start = false;
        });
    }

    void dragAndDropForGame(Node source, Card card, Hand hand, Group sourceRoot, Group sceneRoot,
                            double dx, double dy, double firstX, double firstY) {
        source.setOnMousePressed(event -> {
            if (!wait) {
                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();
                this.dx = dx;
                this.dy = dy;
                if (this.sourceRoot == null)
                    this.sourceRoot = sourceRoot;
                sourceRoot.getChildren().remove(source);
                this.firstX = source.getLayoutX();
                this.firstY = source.getLayoutY();
                if (this.firstX != firstX && this.firstY != firstY) {
                    this.dx = orgSceneX - this.firstX;
                    this.dy = orgSceneY - this.firstY;
                }
                BattleScene battleScene = BattleScene.getSingleInstance();
                battleScene.setOnMousePressedPosition(card);
                if (hand != null && (hand.getGameCards().contains(card))) {
                    if (!(card instanceof Spell))
                        battleScene.showCanPutInCoordinations(card);
                } else
                    battleScene.showCanMoveToCoordinations(card);
                source.relocate(orgSceneX - this.dx, orgSceneY - this.dy);
                sceneRoot.getChildren().add(source);
                start = true;
                if (card instanceof Spell)
                    battleScene.showAlert(card.getName() + ": " + card.getDescription());
            }
        });

        setOnMouseDragged(source, hand, card, true);


        source.setOnMouseReleased(event -> {
            if (!wait && start) {
                sceneRoot.getChildren().remove(source);
                BattleScene battleScene = BattleScene.getSingleInstance();
                setCursor(battleScene.getBattleScene(), Cursor.AUTO);
                Group group = battleScene.addCardToBoard(event.getSceneX(), event.getSceneY(),
                        card, (ImageView) source, hand != null && (hand.getGameCards().contains(card)));
                if (group != null) {
                    this.sourceRoot = group;
                } else {
                    source.relocate(this.firstX, this.firstY);
                    ((Group) this.sourceRoot).getChildren().add(source);
                }
                start = false;
            }
        });
    }

    private void setOnMouseDragged(Node source, Hand hand, Card card, boolean moveCursor) {
        source.setOnMouseDragged(event -> {
            if (start) {
                double offsetX = event.getSceneX() - orgSceneX;
                double offsetY = event.getSceneY() - orgSceneY;
                source.relocate(source.getLayoutX() + offsetX, source.getLayoutY() + offsetY);
                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();
                if (moveCursor) {
                    BattleScene battleScene = BattleScene.getSingleInstance();
                    if (hand != null && (hand.getGameCards().contains(card)))
                        setCursor(battleScene.getBattleScene(), Cursor.CARD);
                    else
                        setCursor(battleScene.getBattleScene(), Cursor.MOVE);
                }
            }
        });
    }

    public static void setWait(boolean wait) {
        DragAndDrop.wait = wait;
    }

    public static boolean getWait() {
        return wait;
    }
}
