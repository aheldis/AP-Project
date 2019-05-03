package model.battle;

import model.card.Card;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;

import java.util.Random;

public class ComputerPlayer extends Player {

    private int RANDOM_NUMBER_FOR_PUT_CARD = 3;
    private int RANDOM_NUMBER_FOR_MOVE = 10;
    private int RANDOM_NUMBER_FOR_ATTACK = 13;

    public ComputerPlayer(Deck deck) {
        this.setMainDeck(deck);
        this.setType("ComputerPlayer");
        mainDeck.setRandomOrderForDeck();
        setMana(2);
        setHand();
    }

    public static ComputerPlayer makeNewPlayer(Deck mainDeck) {
        ComputerPlayer player = new ComputerPlayer(mainDeck);
        return player;
    }

    private boolean checkerForSquare(int x, int y) {
        Square[][] square = match.getLand().getSquares();
        if (x > 4 || x < 0)
            return false;
        if (y < 0 || y > 8)
            return false;
        if (square[x][y].getObject() != null)
            return false;
        return true;

    }

    private int yMovement(int x, int y) {
        y++;
        if (!checkerForSquare(x, y)) {
            y++;
            if (!checkerForSquare(x, y)) {
                y -= 3;
                if (!checkerForSquare(x, y)) {
                    y--;
                    if (!checkerForSquare(x, y)) {
                        return -1;
                    }
                }
            }
        }
        return y;

    }

    private int xMovement(int x, int y) {
        x++;
        if (!checkerForSquare(x, y)) {
            x++;
            if (!checkerForSquare(x, y)) {
                x -= 3;
                if (!checkerForSquare(x, y)) {
                    x--;
                    if (!checkerForSquare(x, y)) {
                        return -1;
                    }
                }
            }
        }
        return x;

    }

    public void playTurnForComputer() {
        Coordinate coordinate = new Coordinate();
        Random random = new Random();
        int x, y;
        //put card
        if (random.nextInt() % RANDOM_NUMBER_FOR_PUT_CARD == 0) {
            int randomNumberForCards = random.nextInt(2);
            for (int i = 0; i < randomNumberForCards; i++) {
                x = mainDeck.getHero().getPosition().getXCoordinate();
                y = mainDeck.getHero().getPosition().getYCoordinate();
                if (random.nextInt() % 2 == 0) {//x =x hero
                    y = yMovement(x, y);
                    if (y == -1)
                        continue;

                } else {//y= y hero
                    x = xMovement(x, y);
                    if (x == -1)
                        continue;
                }
                coordinate = new Coordinate();
                coordinate.setY(y);
                coordinate.setX(x);
                Card card = hand.chooseARandomCard();
                if (manaOfThisTurn < card.getMp())
                    putCardOnLand(card, coordinate, match.getLand());
            }
        }

        if (random.nextInt() % RANDOM_NUMBER_FOR_MOVE == 0) {
            x = mainDeck.getHero().getPosition().getXCoordinate();
            y = mainDeck.getHero().getPosition().getYCoordinate();
            int cardMoven = random.nextInt(cardsOnLand.size() - 1);
            Card card = cardsOnLand.get(cardMoven);
            if (random.nextInt() % 2 == 0) {
                y = yMovement(x, y);
                if (y != -1) {
                    coordinate.setX(x);
                    coordinate.setY(y);
                    card.move(coordinate);
                }
            } else {
                x = xMovement(x, y);
                if (x != -1) {
                    coordinate.setX(x);
                    coordinate.setY(y);
                    card.move(coordinate);
                }
            }
        }

        if (random.nextInt() % RANDOM_NUMBER_FOR_ATTACK == 0) {
            //todo
        }
    }

    public void addToAccountWins() {
        //nothing
    }

    public void addMatchInfo(MatchInfo matchInfo) {
        //nothing
    }


}
