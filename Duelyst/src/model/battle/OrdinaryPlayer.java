package model.battle;

import view.BattleView;
import view.enums.ErrorType;

public class OrdinaryPlayer extends Player {
    public void setType() {
        type = "ordinaryPlayer";
    }

    public void addToAccountWins() {
        getAccount().addToWins();
    }

    public void setDeck() {
        mainDeck = getAccount().getMainDeck();
        if (mainDeck == null) {
            ErrorType error = ErrorType.DONT_HAVE_MAIN_DECK;
            BattleView.getInstance().printError(error);
        }

        mainDeck.setRandomOrderForDeck();
    }

    public void setHand() {

    }
}
