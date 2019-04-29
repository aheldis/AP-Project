package model.card;

import model.land.Square;

import java.util.ArrayList;

public class Spell extends Card {
    private int playerNameByNumber;//{player1 or 2}
    private int numberOfAttack;
    private int turn;
    private ArrayList<Square> target;
    private ArrayList<Buff> buffs;
    private ArrayList<Integer> turnOfBuffs;

    //todo in chertaro dorsot kon :|||
    private int apChanges;
    private int turnForApChanges;
    private int hpChanges;
    private int turnForHpChanges;


    public void setApChanges(int apChanges) {
        this.apChanges = apChanges;
    }

    public int getApChanges() {
        return apChanges;
    }

    public int getTurnForApChanges() {
        return turnForApChanges;
    }

    public int getHpChanges() {
        return hpChanges;
    }

    public int getTurnForHpChanges() {
        return turnForHpChanges;
    }

    public void setTurnForApChanges(int turnForHpChanges) {
        this.turnForApChanges = turnForHpChanges;
    }

    public void setTurnForHpChanges(int turnForHpChanges) {
        this.turnForHpChanges = turnForHpChanges;
    }

    public void setHpChanges(int hpChanges) {
        this.hpChanges = hpChanges;
    }

    public void Spell(int cost, int mp, int apChanges, int turnForApChanges, int hpChanges,
                      int turnForHpChanges, String description, ArrayList<Buff> buffs) {
        this.apChanges = apChanges;
        setCost(cost);
        this.mp = mp;
        this.turnForApChanges = turnForApChanges;
        this.hpChanges = hpChanges;
        setDescription(description);
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


//    public void printdesc() {
//        System.out.println(getDescription());//todo sout
//    }

    public void printdesc() {
        System.out.println(getDescription());//todo sout
    }

    public ArrayList<Square> getTargets() {
        return target;
    }

    public void setTarget(Square square) {

    }

    public void affect(/*target card*/) {
//az tabeye too card estefade kone
    }

}
