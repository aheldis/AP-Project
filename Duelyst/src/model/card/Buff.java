package model.card;

public class Buff {
    private String type;
    private int apChange = 0;
    private int hpChange = 0;
    private boolean canMove = true;
    private boolean canCounterAttack = true;
    private boolean hpChangeAfterAttack = false;


    //todo dont touch please

    //todo harbar seda nashe tahash unaffecct beshe
    //to change check she ke doroste
    //ye chizayee mesle holy buff ok she
    //in ke age chandta buff e moshabeh emal shan chi mishe check she
    public void affect(Card card) {
        card.changeAp(apChange);
        card.changeHp(hpChange);
        if (canMove == false)
            card.setCanMove(false, 1);
        if (canCounterAttack == false)
            card.setCanCounterAttack(false, 1);
    }

    public void unAffect(Card card) {
        //lazeme? :-?
        //todo bayad to khode cardha ke darim baraye chand bar ye buff ro daran bar darim bafe ro mesalan
    }

}
