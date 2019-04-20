package model.land;

public class LandOfGame {
    private static final LandOfGame LAND_OF_GAME = new LandOfGame();
    private Square[][] squares = new Square[5][9];

    private LandOfGame() {

    }

// we may not use this function
    public LandOfGame getInstance() {
        return LAND_OF_GAME;
    }

    public Square[][] getSquares() {
        return LAND_OF_GAME.squares;
    }
}
