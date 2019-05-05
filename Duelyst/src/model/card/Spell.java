package model.card;

import model.land.Square;

import java.util.ArrayList;

public class Spell extends Card {
    private int playerNameByNumber;//{player1 or 2}
    private int numberOfAttack;
    private int turn;
    private ArrayList<Buff> buffs;
    private ArrayList<Integer> turnOfBuffs;

    //todo in chertaro dorsot kon :|||
    private int apChanges;
    private int turnForApChanges;
    private int hpChanges;
    private int turnForHpChanges;

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

    public void printdesc() {
        System.out.println(getDescription());//todo sout
    }

    /*
    public void affect() {
//az tabeye too card estefade kone
    }
    */

    public void setTarget(Square square) {

    }

    public int getApChanges() {
        return apChanges;
    }

    public void setApChanges(int apChanges) {
        this.apChanges = apChanges;
    }

    public int getTurnForApChanges() {
        return turnForApChanges;
    }

    public void setTurnForApChanges(int turnForHpChanges) {
        this.turnForApChanges = turnForHpChanges;
    }

    public int getHpChanges() {
        return hpChanges;
    }

    public void setHpChanges(int hpChanges) {
        this.hpChanges = hpChanges;
    }

    public int getTurnForHpChanges() {
        return turnForHpChanges;
    }

    public void setTurnForHpChanges(int turnForHpChanges) {
        this.turnForHpChanges = turnForHpChanges;
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


}
