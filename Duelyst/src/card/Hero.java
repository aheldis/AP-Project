package card;

import java.util.ArrayList;

public class Hero extends Card {
    private ArrayList<Buff> buffs = new ArrayList<>();;
    private CounterAttack counterAttack;
    private coordinate Square;
    private int hp;
    private int ap;
    private Spell spell;
    private int mpRequiredForSpell;
    private int coolDown;
    private int lastTimeSpellUsed;
    private int price;
    private int attackRange;

    public void addToBuffsOfHero(Buff buff) {
        buffs.add(buff);
    }

    public void increaseHp(int number) {
        hp += number;
    }

    public void decreaseHp(int number) {
        hp -= number;
    }

    public int getHp() {
        return hp;
    }

    public void increaseAp(int number) {
        ap += number;
    }

    public void decreaseAp(int number) {
        ap -= number;
    }

    public int getAp() {
        return ap;
    }

    public void useSpell() {
        //TODO
    }
}
