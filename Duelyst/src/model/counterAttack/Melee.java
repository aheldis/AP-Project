package model.counterAttack;

import model.requirment.Coordinate;

public class Melee extends CounterAttack{
    public Melee(String name){
        this.className="Melee";
    }

    public Boolean checkIfSquareIsWithinRange(Coordinate coordinate){
        return (Math.abs(this.getCoordinate().getX() - coordinate.getX()) <= 1 &&
                Math.abs(this.getCoordinate().getY() - coordinate.getY()) <= 1);
    }

    public void callFunctionForDefend(Coordinate coordinate){
        if(checkIfSquareIsWithinRange(coordinate)) {

        }
    }

    public void callFunctionForAttack(Coordinate coordinate){
        if(checkIfSquareIsWithinRange(coordinate)) {

        }
    }
}
