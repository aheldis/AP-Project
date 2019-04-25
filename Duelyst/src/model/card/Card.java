package model.card;


import model.battle.Target;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;

import java.util.ArrayList;

public abstract class Card {
    private Change change=new Change();//HAS-A
    private Target target;
    private String name;
    private CardId cardId;
    private ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    private int cost;
    private ArrayList <Buff> buffsOnThisCard;
    private Square position;
    private LandOfGame landOfGame;

    public void setLandOfGame(LandOfGame landOfGame){
        this.landOfGame=landOfGame;
    }

    public void move(Coordinate coordinate ){
        if(withinRange(coordinate)){
            landOfGame.removeCardFromAnSquare(position.getCoordinate());
            landOfGame.addCardToAnSquare(coordinate,cardId);//todo
        }

    }

    public boolean withinRange(Coordinate coordinate){


    }
    public void attack(){

    }

    public void changeTurnOfCanNotAttack(int number){
        change.turnOfCanNotAttack +=number;
    }
    public void changeTurnOfCanNotCounterAttack(int number){
        change.turnOfCanNotCounterAttack +=number;
    }
    public void changeTurnOfCanNotMove(int number){
        change.turnOfCanNotMove+= number;
    }

    public void setTurnOfCanNotAttack(int number){
        change.turnOfCanNotAttack =number;
    }
    public void setTurnofCanNotCounterAttack(int number){
        change.turnOfCanNotCounterAttack =number;
    }
    public void setTurnOfCanNotMove(int number){
        change.turnOfCanNotMove = number;
    }

    public int getTurnOfCanNotAttack(){
        return change.turnOfCanNotAttack ;
    }
    public int  getTurnOfCanNotCounterAttack(){
       return change.turnOfCanNotCounterAttack ;
    }
    public int getTurnOfCanNotMove(){
       return change.turnOfCanNotMove ;
    }

    public boolean isCanAttack() {
        return change.canAttack;
    }

    public boolean isCanMove() {
        return change.canMove;
    }

    public boolean isCanCounterAttack(){
        return change.canCounterAttack;
    }
    public  void setCanMove(boolean bool){
        change.canMove=bool;
    }
    public void setCanCounterAttack(boolean bool){
        change.canCounterAttack=bool;
    }
    public void setCanAttack(boolean bool){
        change.canAttack=bool;
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

    public void setCost(int cost){
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

    //ye method ke ye square ba card begire khoonehaee ke mikhaim roshoon kari konim ro bede  arraylist
}
