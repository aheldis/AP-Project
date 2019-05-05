package model.battle;

import model.account.Account;

public class OrdinaryPlayer extends Player {

    public OrdinaryPlayer(){
    }

    public OrdinaryPlayer(Account account, Deck deck, int mana) {
        this.setAccount(account);
        this.setMainDeck(deck);
        this.setType("OrdinaryPlayer");
        getMainDeck().setRandomOrderForDeck();
        setMana(mana);
        setHand();
    }

    public void addToAccountWins() {
        getAccount().addToWins();
    }

    public void addMatchInfo(MatchInfo matchInfo) {
        getAccount().addMatchInfo(matchInfo);
    }


//    public static OrdinaryPlayer makeNewPlayer(Account account, Deck mainDeck) {
//        if (mainDeck == null) {
//            ErrorType error = ErrorType.DONT_HAVE_MAIN_DECK;
//            BattleView.getInstance().printError(error);
//            return null;
//        }
//        if (!mainDeck.validate()) {
//            ErrorType error = ErrorType.SELECTED_INVALID_DECK;
//            BattleView.getInstance().printError(error);
//            return null;
//        }
//
//        OrdinaryPlayer player = new OrdinaryPlayer(account, mainDeck);
//        return player;
//    }
}
