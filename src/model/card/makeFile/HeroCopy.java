package model.card.makeFile;

import model.card.Buff;
import model.card.Card;
import model.card.Spell;

import java.util.ArrayList;

public class HeroCopy extends Card {
    public String name;
    public String type;
    public int cost;
    public int mp;
    public int hp;
    public int ap;
    public String counterAttack;
    public int attackRange;
    public int coolDown;
    public String description;
    protected ChangeCopy change = new ChangeCopy();//HAS-A
    protected TargetCopy target = new TargetCopy();
}
