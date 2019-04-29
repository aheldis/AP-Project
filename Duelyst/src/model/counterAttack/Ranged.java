package model.counterAttack;

import model.requirment.Coordinate;

public class Ranged extends CounterAttack {
    public Ranged() {
        this.className = "Ranged";
    }

    public Boolean checkIfSquareIsWithinRange(Coordinate coordinate) {
        return !(Math.abs(this.getCoordinate().getX() - coordinate.getX()) <= 1 &&
                Math.abs(this.getCoordinate().getY() - coordinate.getY()) <= 1);
    }

    public void callFunctionForDefend(Coordinate coordinate) {
        if (checkIfSquareIsWithinRange(coordinate)) {

        }
    }

    public void callFunctionForAttack(Coordinate coordinate) {
        if (checkIfSquareIsWithinRangeOfCard(this.getCard().getRange(), coordinate) && checkIfSquareIsWithinRange(coordinate)) {

        }
    }

    private boolean checkIfSquareIsWithinRangeOfCard(int range, Coordinate coordinate) {
        return (Math.abs(this.getCoordinate().getX() - coordinate.getX()) +
                Math.abs(this.getCoordinate().getY() - coordinate.getY()) <= range);
    }

}
