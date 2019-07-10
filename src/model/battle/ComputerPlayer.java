package model.battle;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;
import model.account.Account;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.item.ActivationTimeOfItem;
import model.land.Square;
import model.requirment.Coordinate;
import view.Graphic.BattleScene;

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

    private int movement(int x, int y, boolean xBoolean) {
        if (xBoolean)
            x++;
        else
            y++;
        if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
            if (xBoolean)
                x++;
            else
                y++;
            if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                if (xBoolean)
                    x -= 3;
                else
                    y -= 3;
                if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                    if (xBoolean)
                        x--;
                    else
                        y--;
                    if (!Square.checkerForSquare(x, y, getMatch().getLand())) {
                        return -1;
                    }
                }
            }
        }
        return x;

    }

    private void moveAnimation(int x1, int y1, Card card) {

        BattleScene battleScene = BattleScene.getSingleInstance();
        Pair<Double, Double> destination = battleScene.getCellPosition(card.getPosition().getXCoordinate(),
                card.getPosition().getYCoordinate());
        HashMap<Card, ImageView> cardsHashMap = BattleScene.getSingleInstance().getCardsHashMap();
        ImageView imageView = cardsHashMap.get(card);
        battleScene.getCell(x1, y1).setFill(Color.BLACK);
        Group sceneRoot = battleScene.getRoot();
        battleScene.getBoard().getChildren().remove(imageView);
        sceneRoot.getChildren().removeAll(imageView);
        sceneRoot.getChildren().add(imageView);
        KeyFrame keyFrame;
        boolean haveDistanceX = imageView.layoutXProperty().doubleValue() != destination.getKey() - 8;
        boolean haveDistanceY = imageView.layoutYProperty().doubleValue() != destination.getValue() - 48;
        if (haveDistanceX) {
            KeyValue xValue = new KeyValue(imageView.layoutXProperty(), destination.getKey() - 8);
            keyFrame = new KeyFrame(Duration.millis(1000), xValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
        }

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long second = (long) Math.pow(10, 9);
            boolean once = true;
            boolean twice = false;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (once && (!haveDistanceX || now > lastTime + second)) {
                    lastTime = now;
                    KeyValue yValue = new KeyValue(imageView.layoutYProperty(), destination.getValue() - 48);
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), yValue);
                    Timeline timeline = new Timeline(keyFrame);
                    timeline.play();
                    once = false;
                    twice = true;
                }
                if ((!haveDistanceX || !haveDistanceY || now > lastTime + second) && twice) {
                    sceneRoot.getChildren().remove(imageView);
                    battleScene.addCardToBoard(card
                                    .getPosition()
                                    .getXCoordinate(), card.getPosition().getYCoordinate(),
                            card, "normal", imageView, false, true, false);
                    battleScene.getCell(card.getPosition().getXCoordinate(), card.getPosition().getYCoordinate()).setFill(Color.RED);
                    twice = false;
                }
            }
        };
        animationTimer.start();
    }

    public void playTurnForComputer() {
        Random random = new Random();
//      attack
        ArrayList<Card> exhaustedCards = new ArrayList<>();
        outer:
        for (Card card : getCardsOnLand())
            for (Card opponentCard : getOpponent().getCardsOnLand()) {
                Square opponentPosition = opponentCard.getPosition();
                if (card.attack(opponentCard, false)) {
                    BattleScene battleScene = BattleScene.getSingleInstance();
                    battleScene.addCardToBoard(card.getPosition().getXCoordinate(), card.getPosition().getYCoordinate(),
                            card, "ATTACK", battleScene.getCardsHashMap().get(card), false,
                            true, false);
                    battleScene.addCardToBoard(opponentPosition.getXCoordinate(),
                            opponentPosition.getYCoordinate(), opponentCard, "ATTACK",
                            battleScene.getCardsHashMap().get(opponentCard),
                            false, false, true);
                    exhaustedCards.add(card);
                    if (getMainDeck().getItem() != null && getMainDeck().getItem().getActivationTimeOfItem() == ActivationTimeOfItem.ON_ATTACK &&
                            getMainDeck().getItem().getTarget().checkTheOneWhoDoesTheThing(this)) {
                        getMainDeck().getItem().setTarget(this);
                        getMainDeck().getItem().getChange().affect(this, getMainDeck().getItem().getTarget().getTargets());
                    }
                    break outer;
                }
            }

//      move card
        Square firstPosition;
        int RANDOM_NUMBER_FOR_MOVE;
        if (getCardsOnLand().size() >= 1) {
            int cardMoved = random.nextInt(getCardsOnLand().size());
            Card card = getCardsOnLand().get(cardMoved);
            if (!exhaustedCards.contains(card)) {
                ArrayList<Square> squares = card.getCanMoveToSquares();
                RANDOM_NUMBER_FOR_MOVE = random.nextInt(squares.size());
                Coordinate coordinate = squares.get(RANDOM_NUMBER_FOR_MOVE).getCoordinate();
                firstPosition = card.getPosition();
                if (card.move(coordinate)) {
                    moveAnimation(firstPosition.getXCoordinate(), firstPosition.getYCoordinate(), card);
                }
            }
        }
//        put card
        int RANDOM_NUMBER_FOR_PUT_CARD = 2;
        if (random.nextInt(RANDOM_NUMBER_FOR_PUT_CARD) == 0) {
            int randomNumberForCards = random.nextInt(4);
            for (int i = 0; i < randomNumberForCards; i++) {
                Card card = getHand().chooseARandomCard();
                ArrayList<Square> squares = card.getCanPutInSquares();
                randomNumberForCards = random.nextInt(squares.size());
                if (getMana() >= card.getMp() && (card instanceof Hero || card instanceof Minion)) {
                    Coordinate coordinate = squares.get(randomNumberForCards).getCoordinate();
                    if (putCardOnLand(card, coordinate, getMatch().getLand()) == null) {
                        BattleScene.getSingleInstance().addCardToBoard(coordinate.getX(), coordinate.getY(), card,
                                "Breathing", null, false, true, false);
                    }
                }
            }
        }

    }


    public void endGame(MatchInfo matchInfo, int reward) {
        //nothing
    }

}
