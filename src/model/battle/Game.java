package model.battle;

import model.account.Account;
import view.enums.ErrorType;


public class Game {
    private Player[] players = new Player[2];
    private int mode;
    private int numberOfFlags = 0;
    private int reward = 0;

    public Player[] getPlayers() {
        return players;
    }

    public boolean checkPlayerDeck(Account account, int playerNumber /* 1 or 2 */) {

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

    public Match makeNewMultiGame(int mode, int numberOfFlags, int reward) {
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

    public Match makeNewStoryGame(int level) {
        Deck secondPlayerDeck = Deck.getDeckForStoryMode(level);
        mode = level;
        switch (level) {
            case 1: {
                reward = 500;
                numberOfFlags = 0;
                break;
            }
            case 2: {
                reward = 1000;
                numberOfFlags = 1;
                break;
            }
            case 3: {
                reward = 1500;
                numberOfFlags = 5;
                break;
            }
        }
        players[1] = new ComputerPlayer(secondPlayerDeck);
        return new Match(players, getModeAsString(mode), numberOfFlags, reward);
    }

    public Match makeNewCustomGame(Account account, String deckName, int mode, int numberOfFlags) {
        Deck secondPlayerDeck = Deck.getDeckForStoryMode(1);
       // Deck secondPlayerDeck = account.getCollection().getDeckByName(deckName);
        if (secondPlayerDeck == null || !secondPlayerDeck.validate()) {
            ErrorType error = ErrorType.SELECTED_INVALID_DECK_FOR_PLAYER2;
            error.printMessage();
            return null;
        }
        players[1] = new ComputerPlayer(secondPlayerDeck);
        reward = 1000;
        return new Match(players, getModeAsString(mode), numberOfFlags, reward);
    }
}
