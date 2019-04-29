package model.land;

import model.requirment.Coordinate;

public class LandOfGame {
    private static final int NUMBER_OF_ROWS = 5;
    private static final int NUMBER_OF_COLUMNS = 9;
    private Square[][] squares = new Square[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

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
