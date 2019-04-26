package model.counterAttack;

import model.requirment.Coordinate;

public class Hybrid extends CounterAttack {

    public Hybrid(String name) {
        this.className = "Hybrid";
    }

    public Boolean checkIfSquareIsWithinRange(Coordinate coordinate) {
        return true;
    }

    public void callFunctionForDefend(Coordinate coordinate) {

    }

    public void callFunctionForAttack(Coordinate coordinate) {

    }
}
