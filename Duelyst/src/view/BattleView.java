package view;


public class BattleView {
    private static BattleView singleInstance=null;
    private BattleView(){ }
    public static BattleView getInstance() {
        if(singleInstance==null)
            singleInstance=new BattleView();
        return singleInstance;
    }

    public void showNextCardId(String cardId){
        System.out.println(cardId);
    }
}
