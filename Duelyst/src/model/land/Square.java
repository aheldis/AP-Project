package model.land;

import model.Item.Flag;
import model.battle.Hand;
import model.card.*;
import model.requirment.Coordinate;

import java.util.ArrayList;

public class Square {
    private int scaleForEachSquare;
    private Coordinate coordinate;
    private Object object;
    private ArrayList<Buff> buffs=new ArrayList<>();

    public void putBuffOnSquare(Buff buff){
        buffs.add(buff);
    }
    public ArrayList<Buff> getBuffs(){
        return buffs;
    }

   public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getXCoordinate(){
       return coordinate.getX();
    }
    public int getYCoordinate(){
       return coordinate.getY();
    }
    public Coordinate getCoordinate(){
       return coordinate;
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

    public static Square findSquare(Coordinate coordinate) {
       Square[][] squares = LandOfGame.getInstance().getSquares();
       for (int i = 0; i < LandOfGame.getInstance().getNumberOfRows(); i++) {
           for (int j = 0; j < LandOfGame.getInstance().getNumberOfColumns(); j++) {
               if (squares[i][j].coordinate.getX() == coordinate.getX() && squares[i][j].coordinate.getY() == coordinate.getY())
                   return squares[i][j];
           }
       }
       return null;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Coordinate passTheCenterOfSquare() {
        return coordinate;
    }

    public Object getObject() {
        return object;
    }

    //todo hamsayeha
}
