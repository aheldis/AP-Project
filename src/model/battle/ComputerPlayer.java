package model.battle;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.util.Pair;
import model.account.Account;
import model.card.Card;
import model.item.ActivationTimeOfItem;
import model.land.Square;
import model.requirment.Coordinate;
import view.Graphic.BattleScene;
import view.Graphic.StageLauncher;
import view.enums.StateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ComputerPlayer extends Player {


    ComputerPlayer(Deck deck) {
        this.setAccount(new Account("computer", "12"));
        this.setMainDeck(deck);
        this.setType("ComputerPlayer");
        getMainDeck().setRandomOrderForDeck();
        setMana(2);
        setHand();
    }

    public static ComputerPlayer makeNewPlayer(Deck mainDeck) {
        return new ComputerPlayer(mainDeck);
    }

    private int yMovement(int x, int y) {
        y++;
        if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
            y++;
            if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                y -= 3;
                if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                    y--;
                    if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                        return -1;
                    }
                }
            }
        }
        return y;

    }

    private int xMovement(int x, int y) {
        x++;
        if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
            x++;
            if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                x -= 3;
                if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                    x--;
                    if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                        return -1;
                    }
                }
            }
        }
        return x;

    }

    private void moveAnimation(int x1, int y1, int x2, int y2, Card card) {
        Pair<Double, Double> position = BattleScene.getSingleInstance().getCellPosition(x1,y1);
        Pair<Double, Double> destination = BattleScene.getSingleInstance().getCellPosition(x2,y2);

        HashMap<Card, ImageView> cardsHashMap = BattleScene.getSingleInstance().getCardsHashMap();
        ImageView imageView = cardsHashMap.get(card);

        Path path = new Path();
        ((Group)( StageLauncher.getScene(StateType.BATTLE).getRoot())).getChildren().addAll(path);
        path.setVisible(true);
        MoveTo moveTo = new MoveTo(position.getKey()+10, position.getValue()+10);
        LineTo lineTo = new LineTo(destination.getKey()+10, destination.getValue()+10);
        path.getElements().addAll(moveTo,lineTo);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }

    public void playTurnForComputer() {
        Coordinate coordinate = new Coordinate();
        Random random = new Random();
        int x, y;

        //put card
        int RANDOM_NUMBER_FOR_PUT_CARD = 2;
        if (random.nextInt() % RANDOM_NUMBER_FOR_PUT_CARD == 0) {
            int randomNumberForCards = random.nextInt(4);
            for (int i = 0; i < randomNumberForCards; i++) {
                x = getMainDeck().getHero().getPosition().getXCoordinate();
                y = getMainDeck().getHero().getPosition().getYCoordinate();
                if (random.nextInt(100) % 2 == 0) {//x =x hero
                    y = yMovement(x, y);
                    if (y == -1) {
                        continue;
                    }

                } else {//y = y hero
                    x = xMovement(x, y);
                    if (x == -1)
                        continue;
                }
                coordinate = new Coordinate();
                coordinate.setY(y);
                coordinate.setX(x);
                Card card = getHand().chooseARandomCard();
                if (getMana() >= card.getMp()) {
                    if (putCardOnLand(card, coordinate, getMatch().getLand(), false))
                        BattleScene.getSingleInstance().addCardToBoard(x, y, card,
                                "Breathing", null, false, true);
                }
            }
        }

        int RANDOM_NUMBER_FOR_MOVE = 5;
        int firstPosition;
       // if (random.nextInt() % RANDOM_NUMBER_FOR_MOVE == 0) {
            x = getMainDeck().getHero().getPosition().getXCoordinate();
            y = getMainDeck().getHero().getPosition().getYCoordinate();
            if (getCardsOnLand().size() > 1) {
                int cardMoven = random.nextInt(getCardsOnLand().size() - 1);
                Card card = getCardsOnLand().get(cardMoven);
                if (random.nextInt() % 2 == 0) {
                    firstPosition = y;
                    y = yMovement(x, y);
                    if (y != -1) {
                        coordinate.setX(x);
                        coordinate.setY(y);
                        if (card.move(coordinate)) {
                            moveAnimation(x,firstPosition,x,y,card);
                        }
                    }
                } else {
                    firstPosition = x;
                    x = xMovement(x, y);
                    if (x != -1) {
                        coordinate.setX(x);
                        coordinate.setY(y);
                        if (card.move(coordinate)) {
                            moveAnimation(firstPosition,y,x,y,card);
                        }
                    }
                }

            }
       // }

        int RANDOM_NUMBER_FOR_ATTACK = 13;
        if (random.nextInt() % RANDOM_NUMBER_FOR_ATTACK == 0) {
            ArrayList<Card> cards = getOpponent().getCardsOnLand();
            if (cards.size() > 1) {
                int randomIndex = random.nextInt(cards.size() - 1);
                if (getCardsOnLand().size() > 1)
                    getCardsOnLand().get(random.nextInt(getCardsOnLand().size() - 1)).attack(cards.get(randomIndex));
            }


            if (getMainDeck().getItem() != null && getMainDeck().getItem().getActivationTimeOfItem() == ActivationTimeOfItem.ON_ATTACK &&
                    getMainDeck().getItem().getTarget().checkTheOneWhoDoesTheThing(this)) {
                getMainDeck().getItem().setTarget(this);
                getMainDeck().getItem().getChange().affect(this, getMainDeck().getItem().getTarget().getTargets());
            }
        }
    }

    public void addToAccountWins() {
        //nothing
    }

    public void addMatchInfo(MatchInfo matchInfo) {
        //nothing
    }

}
