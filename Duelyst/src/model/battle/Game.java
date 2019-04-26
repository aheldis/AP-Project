package model.battle;

import model.account.Account;
import view.BattleView;
import view.enums.ErrorType;

import java.util.ArrayList;

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
    private static Match match;
    private static BattleView battleView = BattleView.getInstance();

/*
    public static Game singleInstance() {
        return singleInstance;
    }
*/

/*    public void makeGames() {
        //bere az roo file bekhone game besaze berize to gamesType
    }
*/

    public static boolean checkPlayerDeck(Account account, int playerNumber) {
        Deck deck = account.getMainDeck();
        if (deck == null || !deck.validate()) {
            ErrorType error;
            if (playerNumber == 1)
                error = ErrorType.SELECTED_INVALID_DECK;
            else
                error = ErrorType.SELECTED_INVALID_DECK_FOR_PLAYER2
            error.printMessage();
            return false;
        }
        return true;
    }

    public static Match makeNewMultiGame(Account firstPlayerAccount, Account secondPlayerAccount, int mode, int numberOfFlags) {

        Game game = new Game();

        Player player1 = new OrdinaryPlayer(firstPlayerAccount, firstPlayerAccount.getMainDeck());
        Player player2 = new OrdinaryPlayer(secondPlayerAccount, secondPlayerAccount.getMainDeck());

//        game.singlePlayer = false;
//        game.mode = mode;
//        game.numberOfFlags = numberOfFlags;

        Match match = new Match(); //todo
        return match;
    }


    public static Match makeNewStoryGame(Account account, int level) {
        //todo bere az file level bekhoone oon deckharo ye deck besaze -> secondPlayerDeck
        Deck secondPlayerDeck = null;
        Player player1 = new OrdinaryPlayer(account, account.getMainDeck());
        Player player2 = new ComputerPlayer(secondPlayerDeck);

        Match match = new Match(); //todo
        return match;
    }

    public static Match makeNewCustomGame(Account account, String deckName, int mode, int numberOfFlags) {
        //todo deck inam bayad besaziim (how?)
        Deck secondPlayerDeck = null;
        Player player1 = new OrdinaryPlayer(account, account.getMainDeck());
        Player player2 = new ComputerPlayer(secondPlayerDeck);

        Match match = new Match();
        return match;
        //age mode akhar nabashe numberesho 0 midam
        //svw: yani chi?

        //init game
        //set reward 1000
    }

}
