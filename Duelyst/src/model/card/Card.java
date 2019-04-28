package model.card;


import model.battle.Target;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;

import java.util.ArrayList;

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
    private  String playerName;

    public int getMp(){
        return mp;
    }
    public int getHp() {
        return hp;
    }
    public int getAp() {
        return ap;
    }
    private String description;



    public  String getPlayerName(){
        return playerName;
    }
    public void setTarget() {

    }

    public void setLandOfGame(LandOfGame landOfGame) {
        this.landOfGame = landOfGame;
    }

    public void move(Coordinate coordinate) {
        if (change.canMove && withinRange(coordinate)) {
            landOfGame.removeCardFromAnSquare(position.getCoordinate());
            landOfGame.addCardToAnSquare(coordinate, cardId);//todo
        }
        //check asare khane
        //can move = false
        //ویژگی هایی که موقع حرکت اعمال میشود

    }

    public boolean withinRange(Coordinate coordinate) {


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

    public boolean isCanMove() {
        return change.canMove;
    }

    public boolean isCanCounterAttack() {
        return change.canCounterAttack;
    }

    public void setCanMove(boolean bool) {
        change.canMove = bool;
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

    //ye method ke ye square ba card begire khoonehaee ke mikhaim roshoon kari konim ro bede  arraylist
}
