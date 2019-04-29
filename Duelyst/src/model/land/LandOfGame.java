package model.land;

import model.card.Card;
import model.requirment.Coordinate;

public class LandOfGame {
    private static final LandOfGame LAND_OF_GAME = new LandOfGame();
    private static final int NUMBER_OF_ROWS = 5;
    private static final int NUMBER_OF_COLUMNS = 9;
    private Square[][] squares = new Square[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

    private LandOfGame() {}

// we may not use this function

    //todo fekr konam nabayd singletone bashe chon har matchi ke dorost mikonim yebar bayad besazimesh

    public static LandOfGame getInstance() {
        return LAND_OF_GAME;
    }

    public Square passSquareInThisCoordinate(Coordinate coordinate){
       return squares[coordinate.getX()][coordinate.getY()];
    }
//    public void removeCardFromAnSquare(Coordinate coordinate){
//        squares[coordinate.getX()][coordinate.getY()].removeCardFromSquare();
//    }
//    public void addCardToAnSquare(Coordinate coordinate, Card card){
//        squares[coordinate.getX()][coordinate.getY()].putCard(card);
//    }

    public Square[][] getSquares() {
        return squares;
    }

    public int getNumberOfColumns() {
        return NUMBER_OF_COLUMNS;
    }

    public int getNumberOfRows() {
        return NUMBER_OF_ROWS;
    }
}
