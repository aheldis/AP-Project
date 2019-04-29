package model.card;


import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;
import view.enums.RequestSuccessionType;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Card {
    protected Change change = new Change();//HAS-A
    protected Target target = new Target();
    private String name;
    private CardId cardId;
    private ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    private int cost;
    private ArrayList<Buff> buffsOnThisCard;
    private Square position;
    private LandOfGame landOfGame;
    private int CardNumber;//todo card number dashte bashan oon shomareE ke to doc e vase sakhtan mode ha, albate mitoonan nadashte bashan ba esm besazim game card ha ro :-? item ha ham hamin tor
    protected int mp;
    protected int hp;
    protected int ap;
    private String playerName;

    private String description;

    public Target getTargetClass() {
        return target;
    }

    public boolean isCanMove(){//maybe it have stun buff and can not move
       return this.change.canMove;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setTargetClass() {

    }

    public void setLandOfGame(LandOfGame landOfGame) {
        this.landOfGame = landOfGame;
    }

    public void move(Coordinate coordinate) {
        if (canMoveToCoordination(coordinate) && withinRange(coordinate)) {
            landOfGame.removeCardFromAnSquare(position.getCoordinate());
            landOfGame.addCardToAnSquare(coordinate, this);//todo
            RequestSuccessionType.MOVE_TO.setMessage(getCardId().getCardIdAsString() + "moved to" + coordinate.getX() + coordinate.getY());
            RequestSuccessionType.MOVE_TO.printMessage();
            //todo check if RequestSuccessionType works correctly
        }
        else
            ErrorType.INVALID_TARGET.printMessage();
        //check asare khane
        //can move = false
        //ویژگی هایی که موقع حرکت اعمال میشود

    }

    public boolean withinRange(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - position.getXCoordinate()) + Math.abs(coordinate.getY() - position.getYCoordinate()) <= 2;
    }

    public void attack() {
        // if can attack && within range
        //counter attack
        //ویژگی هایی که موقع حمله اعمال میشود

    }

    public void changeTurnOfCanNotAttack(int number) {
        change.turnOfCanNotAttack += number;
    }

    public void changeTurnOfCanNotCounterAttack(int number) {
        change.turnOfCanNotCounterAttack += number;
    }

    public void changeTurnOfCanNotMove(int number) {
        change.turnOfCanNotMove += number;
    }

    public void setTurnOfCanNotAttack(int number) {
        change.turnOfCanNotAttack = number;
    }

    public void setTurnofCanNotCounterAttack(int number) {
        change.turnOfCanNotCounterAttack = number;
    }

    public void setTurnOfCanNotMove(int number) {
        change.turnOfCanNotMove = number;
    }

    public int getTurnOfCanNotAttack() {
        return change.turnOfCanNotAttack;
    }

    public int getTurnOfCanNotCounterAttack() {
        return change.turnOfCanNotCounterAttack;
    }

    public int getTurnOfCanNotMove() {
        return change.turnOfCanNotMove;
    }

    public boolean isCanAttack() {
        return change.canAttack;
    }

    public boolean isCanCounterAttack() {
        return change.canCounterAttack;
    }

    public void setCanCounterAttack(boolean bool) {
        change.canCounterAttack = bool;
    }

    public void setCanAttack(boolean bool) {
        change.canAttack = bool;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Buff> getBuffsOnThisCard() {
        return buffsOnThisCard;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCardIdFromClassCardId() {
    }

    public void addNewNameOfCardToCard(String cardName) {
    }

    public void decreaseNumberOfSameCard(String cardName) {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCardId(CardId cardId) {
        this.cardId = cardId;
    }

    public CardId getCardId() {
        return cardId;
    }

    public void addToTurnsOfPickingUp(int turn) {
        turnsOfPickingUp.add(turn);
    }

    public void addToTurnOfpickingUp(int number) {
        turnsOfPickingUp.add(number);
    }

    public boolean equalCard(String cardId) {
        if (this.cardId.getCardIdAsString().equals(cardId))
            return true;
        return false;
    }

    public void removeCounterAttack() {//TODO

    }

    public int getRange() {
        //todo
        return 0;
    }


    public static ArrayList<Hero> getHeroes(ArrayList<Card> cards) {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Hero)
                heroes.add((Hero) card);
        }
        return heroes;
    }

    public static ArrayList<Minion> getMinions(ArrayList<Card> cards) {
        ArrayList<Minion> minions = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Minion)
                minions.add((Minion) card);
        }
        return minions;
    }

    public static ArrayList<Spell> getSpells(ArrayList<Card> cards) {
        ArrayList<Spell> spells = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Spell)
                spells.add((Spell) card);
        }
        return spells;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCanMove(boolean canMove) {
        change.canMove = canMove;
    }

    public Boolean getCanMove() {
        return change.canMove;
    }

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }


    public void changeHp(int number) {
        hp += number;
    }

    public void changeAp(int number) {
        ap += number;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMp() {
        return mp;
    }

    public static Card getCardById(String cardId, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardId().equals(cardId))
                return card;
        }
        return null;
    }


    public boolean canMoveToCoordination(Coordinate coordinate) {
        return Objects.requireNonNull(Square.findSquare(coordinate)).getObject() == null;
    }

    public void setTarget(Card card ,Square CardSquare){
        //todo check kone ke to classe targete card (one/all/column/row) hast
        //todo age square hast ya distance dare check kone
        //todo bere range ro nega kone har kodoom ke bashe aval bege to range hast ya na
        //todo khone haye to range ro be onvane arrayList bede be ma
        //todo ArrayList e target ro to classe taget bere bezare
    }

    //ye method ke ye square ba card begire khoonehaee ke mikhaim roshoon kari konim ro bede  arraylist
}
