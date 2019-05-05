package model.card;

import model.battle.Player;
import model.land.LandOfGame;
import model.land.Square;

import java.util.ArrayList;

public class Target {
    private Player player;
    private LandOfGame land;
    private ArrayList<Square> targets = new ArrayList<>();
    private String counterAttackType; //ranged hybrid melee
    private String cardType; //minion hero spell
    //    private String number; // 0 <=
    private boolean one;
    private boolean row;
    private boolean column;
    private boolean all;
    private boolean random;
    private static final int DEFAULT = -1;
    private int distance = DEFAULT;
    //default
    private boolean self = false;
    private boolean enemy = false;
    private boolean ally = false;


    public Target() {

    }

    public Target(Player player, LandOfGame land) {
        this.player = player;
        this.land = land;
    }

    public boolean checkIfAttackedCardIsValid(Object attacked) {
        //check beshe ba sharayet target mikhoone ya na todo kamel nistaa
        if (!(attacked instanceof Card))
            return false;
        if (attacked instanceof Spell)
            return true;
        String counterAttackName = null;
        if (attacked instanceof Minion) {
            if (!cardType.equals("minion")) {
                return false;
            }
            counterAttackName = ((Card)attacked).getCounterAttackName();
        }
        if (attacked instanceof Hero) {
            if (!cardType.equals("hero")) {
                return false;
            }
            counterAttackName = ((Card)attacked).getCounterAttackName();
        }
        if (counterAttackName != null) {
            return counterAttackName.equals(counterAttackType);

        }
        return false;
    }

    public boolean checkIsEnemy(Player me, Square check) {
        if (!enemy)
            return false;
        return check.getObject() != null && check.getObject() instanceof Card &&
                ((Card)check.getObject()).getPlayer() == me.getOpponent();
    }

    public boolean checkIsAlly(Player me, Square check) {
        if (!ally)
            return false;
        return check.getObject() != null && check.getObject() instanceof Card &&
                ((Card)check.getObject()).getPlayer() == me;
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
            return !(y == position.getYCoordinate() && x == position.getXCoordinate());
        return true;
    }
}
