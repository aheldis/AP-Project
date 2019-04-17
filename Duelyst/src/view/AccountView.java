package view;


import java.util.ArrayList;

public class AccountView {
    private Hero hero;


    public static void  cardsAndItemsView(ArrayList<Spell> spells, ArrayList<Minion> minions, ArrayList<Hero> heroes, Item[] items){
        int counter=1;
        System.out.print("Heroes : ");
        for(Hero hero:heroes) {
            System.out.print("          "+counter+" : ");
            System.out.println("Name : "+hero.getName+
                    " - AP : "+hero.getAp()
                    + " – HP : " + hero.getHP()
                    +" – Class : "+hero.getCounterAttackclassName()+
                    " – Special power: "+hero.getSpecialPowerInfo()+
                    ". - Sell Cost : "+hero.getSellCost() );
            counter ++;
        }
        counter=1;
        for(Item item:items){
            System.out.print("          "+counter+" : ");
            System.out.println("Name : "+item.getName()+
                    " – Desc: "+item.getDescription()
                    + " – Sell Cost : 300" + item.getSellCost());
            counter ++;
        }
        counter=1;
        for(Spell spell:spells){
            System.out.print("          "+counter+" : ");
            System.out.println("Type : Spell - Name : "+spell.getName()+
                    " – MP : "+spell.getMp()+
                    "– Desc: "+spell.getDescription()+
                    " – Sell Cost : "+spell.getCost());
            counter ++;
        }
        for(Minion minion:minions){
            System.out.print("          "+counter+" : ");
            System.out.println("Type : Minion - Name : "+minion.getName()+
                    "– Class: "+minion.getCounterAttackClasName()+
                    " - AP : +" +minion.getAP()+
                    " - HP : "+minion.getHP()+
                    " - MP : +"+minion.getMp()+
                    " Special power : "+minion.getSpecialPowerInfo+
                    " – Sell Cost : "+minion.getSellCost());
            counter ++;
        }
    }
    public static void deckView(){


    }
    
    public static void shopView(){

    }

}
