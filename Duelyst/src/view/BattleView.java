package view;

import view.enums.ErrorType;

public class BattleView {
    private static BattleView singleInstance = null;

    public static BattleView getInstance() {
        if (singleInstance == null) {
            singleInstance = new BattleView();
        }
        return singleInstance;
    }

    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void print(String string) {
        System.out.println(string);
    }

    public void showNextCardId(String cardId){
        System.out.println(cardId);
    }
}
