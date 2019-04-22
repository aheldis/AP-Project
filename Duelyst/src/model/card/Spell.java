package model.card;

import model.land.Square;

import java.util.ArrayList;

public class Spell extends Card {
    private int playerNameByNumber;//{player1 or 2}
    private int numberOfAttack;
    private int turn;
    private String description;
    private ArrayList<Square> target;
    private ArrayList<Buff> buffs;
    private ArrayList<Integer> turnOfBuffs;
    private int mp;
    private int apChanges;
    private int turnForApChanges;
    private int hpChanges;
    private int turnForHpChanges;


    public void setDescription(String description){
        this.description=description;
    }

    public void setMp(int mp){
        this.mp=mp;
    }
    public void setApChanges(int apChanges){
        this.apChanges=apChanges;
    }
    public int getApChanges(){
        return apChanges;
    }
    public int getTurnForApChanges(){
        return turnForApChanges;
    }
    public int getHpChanges(){
        return hpChanges;
    }
    public int getTurnForHpChanges(){
        return turnForHpChanges;
    }
    public void setTurnForApChanges(int turnForHpChanges){
        this.turnForApChanges=turnForHpChanges;
    }
    public void setTurnForHpChanges(int turnForHpChanges){
        this.turnForHpChanges=turnForHpChanges;
    }
    public void setHpChanges(int hpChanges ){
        this.hpChanges=hpChanges;
    }
    public void Spell(int cost, int mp, int apChanges, int turnForApChanges, int hpChanges,
                      int turnForHpChanges, String description, ArrayList<Buff> buffs) {
        this.apChanges = apChanges;
        setCost(cost);
        this.mp = mp;
        this.turnForApChanges = turnForApChanges;
        this.hpChanges = hpChanges;
        this.description = description;
        this.turnForApChanges = turnForHpChanges;
        this.buffs = buffs;
    }

    public int getPlayerNameByNumber() {
        return playerNameByNumber;
    }

    public int getNumberOfAttack() {
        return numberOfAttack;
    }

    public int getTurn() {
        return turn;
    }

    public ArrayList<Square> getTarget() {
        return target;
    }

    public int getMp() {
        return mp;
    }


    public String getDescription() {
        return description;
    }

    public void printdesc() {
        System.out.println(description);//todo sout
    }

    public ArrayList<Square> getTargets() {
        return target;
    }

    public void setTarget(Square square) {

    }

    public void affect(/*square card*/)
    {
//az tabeye too card estefade kone
    }

}
