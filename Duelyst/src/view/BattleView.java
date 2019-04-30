package view;

import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.ErrorType;

public class BattleView {
    private static BattleView singleInstance = null;

    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void print(String string) {
        System.out.println(string);
    }

    public void showCardId(String cardId) {
        System.out.println(cardId);
    }

    public void showCardInfo(Card card) {
        System.out.println("Name: " + card.getName());
        System.out.println("Cost: " + card.getCost());
        System.out.println("Desc: " + card.getDescription());
        if (!(card instanceof Hero)) {
            System.out.println("MP: " + card.getMp());
        }
        if (!(card instanceof Spell)) {
            System.out.println("AP: " + card.getAp());
            System.out.println("HP: " + card.getHp());
            System.out.println("Range: " + card.getRange());
        }

        if (card instanceof Minion) {
            System.out.println("Combo-ability: " + ((Minion) card).getActivationTimeOfSpecialPower());
        }
    }

    public static BattleView getInstance() {
        if (singleInstance == null) {
            singleInstance = new BattleView();
        }
        return singleInstance;
    }


}
