package view;

public class NewCardMessages {

    private static NewCardMessages singleInstance=null;
    public static NewCardMessages getInstance(){
        if(singleInstance==null)
            singleInstance=new NewCardMessages();
        return singleInstance;
    }

    public void showEnterName(){
        System.out.println("Enter the name of card");
    }


    public void showFormatForSpells(String order){
        if(order.equals("cost"))
            System.out.println("Enter cost: #cost");
        else if(order.equals("Mp"))
            System.out.println("Enter Mp: #MP");
        else if(order.equals("ApChange"))
            System.out.println("Enter ApChanges-TurnOfEffect: #ApChanges - #TurnOfEffects");
        else if(order.equals("HpChange"))
            System.out.println("HpChanges-TurnOfEffect: #HpChange - #turnOfEffects");
        else if(order.equals("buff"))
            System.out.println("Buffs-TurnOfEffect: #buffs - #turnOfEffects");
        else if(order.equals("target"))
            System.out.println("target: (Enemy/Friend - All/One/Range(m-m',n-n') - Hero/Card) or square(m*n)");
        else if(order.equals("desc"))
            System.out.println("desc: #desc");
    }
}
