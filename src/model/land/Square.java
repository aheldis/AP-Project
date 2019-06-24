package model.land;

import javafx.scene.image.ImageView;
import model.card.Buff;
import model.card.Hero;
import model.card.Minion;
import model.item.Collectible;
import model.item.Flag;
import model.requirment.Coordinate;
import view.Graphic.BattleScene;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private int scaleForEachSquare;
    private Coordinate coordinate;
    private Object object;
    private ArrayList<Flag> flags = new ArrayList<>();
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ImageView cellEffectImageView = null;

    public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void clearBuffs(){
        buffs = null;
        BattleScene.getSingleInstance().removeNodeFromBoard(cellEffectImageView);
    }
    public void addBuffToSquare(Buff buff) {
        buffs.add(buff);
        if(cellEffectImageView == null)
            cellEffectImageView = BattleScene.getSingleInstance().addCellEffect(getXCoordinate(), getYCoordinate());
    }

    public Minion squareHasMinionAndPassIt() {
        if (object instanceof Minion)
            return (Minion) object;
        if (object instanceof Collectible && ((Collectible) object).getTheOneWhoCollects() instanceof Minion)
            return (Minion) ((Collectible) object).getTheOneWhoCollects();
        return null;
    }

    public Hero squareHasHeroAndPassIt() {
        if (object instanceof Hero)
            return (Hero) object;
        if (object instanceof Collectible && ((Collectible) object).getTheOneWhoCollects() instanceof Hero)
            return (Hero) ((Collectible) object).getTheOneWhoCollects();
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
        if (x >= LandOfGame.getNumberOfRows() || x < 0)
            return false;
        if (y < 0 || y >= LandOfGame.getNumberOfColumns())
            return false;
        return landOfGame.getSquares()[x][y].getObject() == null && landOfGame.getSquares()[x][y].flags.size()==0;
        //todo in vase chie? flag ro check nemikone lazeme begin
    }

    public boolean squareHasMinionOrHero() {
        if(object == null)
            return false;
        if (squareHasHeroAndPassIt() != null)
            return true;
        return squareHasMinionAndPassIt() != null;
    }

    public void addToFlags(Flag flag){
        flags.add(flag);
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public void clearFlags(){
        flags = new ArrayList<>();
    }
}
