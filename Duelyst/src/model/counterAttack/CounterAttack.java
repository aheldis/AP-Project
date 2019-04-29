package model.counterAttack;

import model.card.Card;
import model.requirment.Coordinate;

public abstract class CounterAttack {
    protected String name;
    //    todo "comment" name hamoon esme class nist?
    private Coordinate coordinate;
    private Card card;
    boolean canCounterAttack = true;//if it has disarm buff it is false


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Card getCard() {
        return card;
    }

    public String getName() {
        return name;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void deleteCard() {
        card.removeCounterAttack();
        card = null;
    }


    public abstract void callFunctionForDefend(Coordinate coordinate);

    public abstract void callFunctionForAttack(Coordinate coordinate);

    public abstract Boolean checkIfSquareIsWithinRange(Coordinate coordinate);

    public String getClassName() {
        if (this instanceof Hybrid)
            return "Hybrid";
        if (this instanceof Melee)
            return "Melee";
        return "Ranged";
    }
}
