package counterAttack;

import requirment.Coordinate;

public class Melee extends CounterAttack{

    public Boolean checkIfSquereIsWithinRange(Coordinate coordinate){
        return (Math.abs(this.getCoordinate().getX() - coordinate.getX()) <= 1 &&
                Math.abs(this.getCoordinate().getY() - coordinate.getY()) <= 1)
    }

    public void callFunctionForDefend(Coordinate coordinate){
        if(checkIfSquereIsWithinRange(coordinate)) {

        }
    }

    public void callFunctionForAttack(Coordinate coordinate){
        if(checkIfSquereIsWithinRange(coordinate)) {

        }
    }
}
