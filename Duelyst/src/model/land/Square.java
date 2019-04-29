package model.land;

import model.Item.Flag;
import model.battle.Hand;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import model.requirment.Coordinate;

public class Square {
    private int scaleForEachSquare;
    private Coordinate coordinate;
    private Object object;
    private Card card;

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
    public void putCard(Card card){
       if(card instanceof Spell)
           putSpell((Spell) card);
       if(card instanceof Minion)
           putMinion((Minion) card);
       if(card instanceof Hero)
           putHero((Hero)card);
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

    public void setCard(Card card) {
        this.card = card;
    }

    public Coordinate passTheCenterOfSquare() {
        return coordinate;
    }

    public Object getObject() {
        return object;
    }

    //todo hamsayeha
}
