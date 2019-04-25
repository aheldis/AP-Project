package model.land;

import model.card.Card;
import model.requirment.Coordinate;

public class LandOfGame {
    //private static final LandOfGame LAND_OF_GAME = new LandOfGame();
    private Square[][] squares = new Square[5][9];

    //private LandOfGame() {}

// we may not use this function

    //todo fekr konam nabayd singletone bashe chon har matchi ke dorost mikonim yebar bayad besazimesh

//    public static LandOfGame getInstance() {
//        return LAND_OF_GAME;
//    }

    public void removeCardFromAnSquare(Coordinate coordinate){
        squares[coordinate.getX()][coordinate.getY()].removeCardFromSquare();
    }
    public void addCardToAnSquare(Coordinate coordinate, Card card){
        squares[coordinate.getX()][coordinate.getY()].putCard(card);
    }

    public Square[][] getSquares() {
        return squares;
    }
}
