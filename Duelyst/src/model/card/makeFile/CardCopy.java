package model.card.makeFile;


public class CardCopy {
    public String name;
    public int CardNumber;
    public int cost;
    public int mp;
    public int hp;
    public int ap;
//    public int turnOfCanNotMove = 0;
//    public int turnOfCanNotAttack = 0;
//    public int turnOfCanNotCounterAttack = 0;

//    public CardId cardId;
//    public ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    public String counterAttack;
    public int attackRange;

    //public ArrayList<Buff> buffsOnThisCard;
//    public HashMap<Buff, ArrayList<Integer>> buffsOnThisCard = new HashMap<>(); //todo to init perturn as addada kam kone har ki sefr shod disaffect seda kone
//    public Square position;
//    public LandOfGame landOfGame;

//    public Player player;
//    public boolean canMove = false;
//    public boolean canAttack = false;
//    public boolean canCounterAttack = true;
//    public int hpChangeAfterAttack = 0; //todo mogheE ke be yeki hamle mishe va az hpsh kam mishe bayad ba in jam konin hpSh ro
    public String description;
    public ChangeCopy change = new ChangeCopy();//HAS-A
    public TargetCopy target = new TargetCopy();

    public void setChange(ChangeCopy change) {
        this.change = change;
    }

    public void setTarget(TargetCopy target) {
        this.target = target;
    }
}
