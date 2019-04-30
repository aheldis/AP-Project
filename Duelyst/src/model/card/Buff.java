package model.card;

public class Buff {
    private String name;
    private boolean goodBuff;
    private boolean haveUnAffect;
    private int apChange = 0;
    private int hpChange = 0;
    private boolean canMove = true;
    private boolean canAttack = true;
    private boolean canCounterAttack = true;
    private boolean hpChangeAfterAttack = false;


    //todo harbar seda nashe tahash unaffecct beshe
    //to change check she ke doroste
    //ye chizayee mesle holy buff ok she
    //in ke age chandta buff e moshabeh emal shan chi mishe check she

    public void affect(Card card) {
        card.changeAp(apChange);
        card.changeHp(hpChange);
        if (canMove == false)
            card.setCanMove(false, 1);
        if (canAttack == false)
            card.setCanAttack(false, 1);
        if (canCounterAttack == false)
            card.setCanCounterAttack(false, 1);
        if (hpChangeAfterAttack == true)
            card.setHpChangeAfterAttack(1);
    }

    public void unAffect(Card card) {
        card.changeAp(-apChange);
        card.changeHp(-hpChange);
        //in chiza too khod player handle mishe
/*        if (canMove == false)
            card.setCanMove(true, 1);
        if (canAttack == false)
            card.setCanAttack(true, 1);
        if (canCounterAttack == false)
            card.setCanCounterAttack(true, 1);*/
        if (hpChangeAfterAttack == true)
            card.setHpChangeAfterAttack(-1);
    }

    public boolean isGoodBuff() {
        return goodBuff;
    }

    public boolean isHaveUnAffect() {
        return haveUnAffect;
    }
}
