package counterAttack;

import card.Card;
import requirment.Coordinate;

public abstract class CounterAttack {
    protected String className;
    private Coordinate coordinate;
    private Card card;
    boolean canCounterAttack=true;//if it has disarm buff it is false


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Card getCard() {
        return card;
    }

    public String getClassName(){
        return className;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void deleteCard(){
        card.removeCounterAttack();
        card = null;
    }


    public abstract void callFunctionForDefend(Coordinate coordinate);
    public abstract void callFunctionForAttack(Coordinate coordinate);
    public abstract Boolean checkIfSquareIsWithinRange(Coordinate coordinate);
}
