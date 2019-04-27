package model.card;

public class Buff {
    private String type;
    private int apChange = 0;
    private int hpChange = 0;
    private boolean canMove = true;
    private boolean hpChangeAfterAttack = false;

    public void affect(Card card) {
        if (card instanceof Hero) {
            Hero hero = (Hero) card;
            hero.changeAp(apChange);
            hero.changeHp(hpChange);
            hero.setCanMove()//Oh gand khord //todo dorost beshe
        }
    }
}
