package model.battle;

import model.card.Card;
import model.land.Square;
import model.requirment.Coordinate;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer(Deck deck) {
        this.setMainDeck(deck);
        this.setType("ComputerPlayer");
        mainDeck.setRandomOrderForDeck();
        setMana(2);
        setHand();
    }

    public static ComputerPlayer makeNewPlayer(Deck mainDeck) {
        return new ComputerPlayer(mainDeck);
    }

    private int yMovement(int x, int y) {
        y++;
        if (!Square.checkerForSquare(x, y, match.getLand())) {
            y++;
            if (!Square.checkerForSquare(x, y, match.getLand())) {
                y -= 3;
                if (!Square.checkerForSquare(x, y, match.getLand())) {
                    y--;
                    if (!Square.checkerForSquare(x, y, match.getLand())) {
                        return -1;
                    }
                }
            }
        }
        return y;

    }

    private int xMovement(int x, int y) {
        x++;
        if (!Square.checkerForSquare(x, y, match.getLand())) {
            x++;
            if (!Square.checkerForSquare(x, y, match.getLand())) {
                x -= 3;
                if (Square.checkerForSquare(x, y, match.getLand())) {
                    x--;
                    if (!Square.checkerForSquare(x, y, match.getLand())) {
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
        int RANDOM_NUMBER_FOR_PUT_CARD = 3;
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

        int RANDOM_NUMBER_FOR_MOVE = 10;
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

        int RANDOM_NUMBER_FOR_ATTACK = 13;
        if (random.nextInt() % RANDOM_NUMBER_FOR_ATTACK == 0) {
            ArrayList<Card> cards = getOpponent().getCardsOnLand();
            int randomIndex = random.nextInt(cards.size() - 1);
            cardsOnLand.get(random.nextInt(cardsOnLand.size() - 1)).attack(cards.get(randomIndex));
        }
    }

    public void addToAccountWins() {
        //nothing
    }

    public void addMatchInfo(MatchInfo matchInfo) {
        //nothing
    }


}
