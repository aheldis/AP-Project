package counterAttack;

import requirment.Coordinate;

public abstract class CounterAttack {
    private Coordinate coordinate;
    private Card card;
    private String name;

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

    public void setName(String name) {
        this.name = name;
    }

    public void deleteCard(){
        card.removeCounterAttack();
        card = null;
    }


    public abstract void callFunctionForDefend(Coordinate coordinate);
    public abstract void callFunctionForAttack(Coordinate coordinate);
    public abstract Boolean checkIfSquereIsWithinRange(Coordinate coordinate);
}
