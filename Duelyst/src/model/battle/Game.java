package model.battle;

import model.account.Account;
import view.BattleView;
import view.enums.ErrorType;


public class Game {
//    private static Game singleInstance = null;
  /*  private boolean singlePlayer;
    private boolean story;
    private boolean customGame;
    private int mode; //1 -> dead 2 -> saveFlag 3 -> collectFlag
    private int numberOfFlags;
    private int heroNumber;
    private ArrayList<Integer> spellIds;//todo id ha string hastan
    private ArrayList<Integer> minionIds;
    private int itemNumber;
    private int reward;
    private int levelNumber = -1;
  */  //private ArrayList<Game> gamesType;
    private  Player[] players = new Player[2];
    private int mode;
    private int numberOfFlags = 0;
    private int reward = 0;
    private static BattleView battleView = BattleView.getInstance();


    public  boolean checkPlayerDeck(Account account, int playerNumber /* 1 or 2 */) {

        Deck deck = account.getMainDeck();
        if (deck == null || !deck.validate()) {
            ErrorType error;
            if (playerNumber == 1)
                error = ErrorType.SELECTED_INVALID_DECK;
            else
                error = ErrorType.SELECTED_INVALID_DECK_FOR_PLAYER2;
            error.printMessage();
            return false;
        }

        players[playerNumber - 1] = new OrdinaryPlayer(account, account.getMainDeck(), playerNumber);
        return true;
    }

    public Match makeNewMultiGame(int mode, int numberOfFlags) {
        return new Match(players, getModeAsString(mode), numberOfFlags, reward);
    }


    public Match makeNewStoryGame(int level) {
        //todo bere az file level bekhoone oon deckharo ye deck besaze -> secondPlayerDeck mode -> mode reward -> reward
        Deck secondPlayerDeck = null;
        players[1] = new ComputerPlayer(secondPlayerDeck);
        return new Match(players, getModeAsString(mode), numberOfFlags, reward);
    }

    public Match makeNewCustomGame(Account account, String deckName, int mode, int numberOfFlags) {
        Deck secondPlayerDeck = account.getCollection().getDeckByName(deckName);
        if(secondPlayerDeck == null || !secondPlayerDeck.validate()){
            ErrorType error = ErrorType.SELECTED_INVALID_DECK_FOR_PLAYER2;
            error.printMessage();
            return null;
        }
        players[1] = new ComputerPlayer(secondPlayerDeck);
        reward = 1000;
        return new Match(players, getModeAsString(mode), numberOfFlags, reward);

    }

    public static String getModeAsString(int mode) {
        switch (mode) {
            case 1:
                return "DeathMode";
            case 2:
                return "SaveFlagMode";
            case 3:
                return "CollectFlagMode";
        }
        return "invalid";
    }

}
