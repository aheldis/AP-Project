package view;

import model.battle.Game;
import model.battle.Match;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.ErrorType;

public class BattleView {
    private static BattleView singleInstance = null;
    private BattleView(){

    }
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

    public void endGameView(Match match){
        System.out.println("GAME ENDED");
        System.out.println(match.getWinner() + " is winner");
        System.out.println(match.getLoser() + " is loser");
    }




}
