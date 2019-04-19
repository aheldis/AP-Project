package land;

import Item.Flag;
import card.Card;
import card.Hero;
import card.Minion;
import card.Spell;
import requirment.Coordinate;

public class Square {
    private int scaleForEachSquare;
    private Coordinate coordinate;
    private Object object;

    Square(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setScale(int scale) {
        scaleForEachSquare = scale;
    }

    public Minion squareHasMinionAndPassIt() {
        if (object instanceof Minion)
            return (Minion) object;
        return null;
    }

    public Hero squareHasHeroAndPassIt() {
        if (object instanceof Hero)
            return (Hero) object;
        return null;
    }

    public void putMinion(Minion minion) {
        object = minion;
    }

    public void putHero(Hero hero) {
        object = hero;
    }

    public void putSpell(Spell spell) {
        object = spell;
    }

    public void putFlag(Flag flag) {
        object = flag;
    }

    public void removeCardFromSquare() {
        if (object instanceof Card)
            object = null;
    }

    public Coordinate passTheCenterOfSquare() {
        return coordinate;
    }

    public Object getObject() {
        return object;
    }
}