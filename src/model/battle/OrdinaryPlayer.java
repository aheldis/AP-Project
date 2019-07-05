package model.battle;

import model.account.Account;

import java.io.Serializable;

public class OrdinaryPlayer extends Player implements Serializable {

    public OrdinaryPlayer(){
    }

    OrdinaryPlayer(Account account, Deck deck, int mana) {
        this.setAccount(account);
        this.setMainDeck(deck);
        this.setType("OrdinaryPlayer");
        getMainDeck().setRandomOrderForDeck();
        setMana(mana);
        setHand();
    }


    public void endGame(MatchInfo matchInfo, int reward) {
        getAccount().changeValueOfDaric(reward);
        getAccount().addMatchInfo(matchInfo);
        getAccount().setCurrentlyPlaying(false);
        getAccount().addToWins();

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
