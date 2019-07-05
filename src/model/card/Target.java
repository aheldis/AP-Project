package model.card;

import model.battle.Player;
import model.item.Collectible;
import model.land.LandOfGame;
import model.land.Square;

import java.io.Serializable;
import java.util.ArrayList;

public class Target implements Serializable {
    private Player player;
    private LandOfGame land;
    private ArrayList<Square> targets = new ArrayList<>();
    private String counterAttackType = "-"; //ranged hybrid melee
    private String cardType = "-"; //minion hero Spell  -> force ro ezafe kardam baraye hero ya minion
    //    private String number; // 0 <=
    private boolean one = false;
    private boolean row = false;
    private boolean column = false;
    private boolean all = false;
    private boolean random = false;
    private static final int DEFAULT = -1;
    private int distance = DEFAULT;
    //default
    private boolean self = false;
    private boolean enemy = false;
    private boolean ally = false;
    private boolean haveRange = false; //baraye item
    private boolean notHaveRange = false; //baraye item
    private String theOneWhoCollects = "-"; //baraye item
    private String theOneWhoDoesTheThing = "-"; //baraye item hero va folan

    public Target() {

    }

    public Target(Player player, LandOfGame land) {
        this.player = player;
        this.land = land;
    }

    public boolean checkIfAttackedCardIsValid(Object attacked) {
        //check beshe ba sharayet target mikhoone ya na todo kamel nistaa
        if (attacked == null)
            return false;

        if (attacked instanceof Spell)
            return true;

        if (attacked instanceof Collectible)
            attacked = ((Collectible) attacked).getTheOneWhoCollects();

        if (!(attacked instanceof Card))
            return false;

        if (theOneWhoCollects.equals("-") && haveRange && ((Card) attacked).getRange() == 0)
            return false;

        return !notHaveRange || ((Card) attacked).getRange() == 0;
    }

    public boolean checkTheOneWhoDoesTheThing(Object object) {
        if (theOneWhoDoesTheThing.equals("-"))
            return true;
        if (object instanceof Collectible)
            object = ((Collectible) object).getTheOneWhoCollects();
        if (object instanceof Hero && (theOneWhoDoesTheThing.equals("hero") || theOneWhoDoesTheThing.equals("force")))
            return true;
        return object instanceof Minion && (theOneWhoDoesTheThing.equals("minion") || theOneWhoDoesTheThing.equals("force"));
    }

    public boolean checkIsEnemy(Player me, Square check) {
        Object object = check.getObject();
        if (object == null)
            return false;
        if (object instanceof Collectible)
            object = ((Collectible) object).getTheOneWhoCollects();
        return check.getObject() != null && object instanceof Card &&
                (((Card) object).getPlayer() == me.getOpponent() || !enemy);
    }

    public boolean checkIsAlly(Player me, Square check) {
        Object object = check.getObject();
        if (object == null)
            return false;
        if (object instanceof Collectible)
            object = ((Collectible) object).getTheOneWhoCollects();
        return check.getObject() != null && object instanceof Card && (((Card) object).getPlayer() == me || !ally);
    }

    public boolean checkDistance(Card forWitchCard, Square squareOfTarget) {
        if (distance == DEFAULT)
            return true;
        return forWitchCard.withinRange(squareOfTarget.getCoordinate(), distance);
    }

    public ArrayList<Square> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Square> targets) {
        this.targets = targets;
    }

    public boolean isColumn() {
        return column;
    }

    public boolean isAll() {
        return all;
    }

    public boolean isOne() {
        return one;
    }

    public boolean isRow() {
        return row;
    }

    public boolean isRandom() {
        return random;
    }

    public boolean isSelf() {
        return self;
    }

    public boolean isAlly() {
        return ally;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public String getCardType() {
        return cardType;
    }

    public int getDistance() {
        return distance;
    }

    public boolean checkNotItSelf(int y, int x, Square position) {
        if (self)
            return (y == position.getYCoordinate() && x == position.getXCoordinate());
        return true;
    }

    public boolean checkTheOneWhoCollects(Card card) {
        if (!(card instanceof Hero) && theOneWhoCollects.equals("hero"))
            return false;
        return card.getRange() != 0 || !haveRange;
    }

    public String getTheOneWhoDoesTheThing() {
        return theOneWhoDoesTheThing;
    }
}
