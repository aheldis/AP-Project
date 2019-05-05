package model.land;

import model.card.Buff;
import model.card.Hero;
import model.card.Minion;
import model.requirment.Coordinate;

import java.util.ArrayList;

public class Square {
    private int scaleForEachSquare;
    private Coordinate coordinate;
    private Object object;
    private ArrayList<Buff> buffs = new ArrayList<>();

    public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void addBuffToSquare(Buff buff) {
        buffs.add(buff);
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

    public void setScale(int scale) {
        scaleForEachSquare = scale;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public int getXCoordinate() {
        return coordinate.getX();
    }

    public int getYCoordinate() {
        return coordinate.getY();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static boolean checkerForSquare(int x, int y, LandOfGame landOfGame) {
        if (x > 4 || x < 0)
            return false;
        if (y < 0 || y > 8)
            return false;
        return landOfGame.getSquares()[x][y].getObject() == null;
    }

    //todo collectible

    //todo hamsayeha
}
