package model.account;

import model.battle.Deck;
import model.battle.Match;
import model.battle.NormalDeck;
import view.AccountView;

import java.util.ArrayList;


public class Account implements Comparable<Account> {
    private String userName;
    private String password;
    private int daric;
    private int wins;
    private ArrayList<Match> matchHistory;
    private Collection collection;
    private Player player;
    private NormalDeck mainDeck;
    private ArrayList<Deck> decks;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public String getUserName() {
        return userName;
    }

    public int getDaric() {
        return daric;
    }

    public ArrayList<Match> getMatchHistory() {
        return matchHistory;
    }

    public Collection getCollection() {
        return collection;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public NormalDeck getMainDeck() {
        return mainDeck;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void setDeckFromCollection() {
        decks = (ArrayList<Deck>) collection.getDecks();
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public int compareTo(Account account) {
        return account.getWins() - this.getWins();
    }

    public void changeValueOfDaric(int number) {
        daric += number;
    }

    public void addMatch(Match match) {
        matchHistory.add(match);
    }

    public void showMatchHistory() {
        AccountView accountView = AccountView.getInstance();
        for (Match match : matchHistory) {
            ArrayList<Player> players = match.getPlayers();
            Player opponent;
            if (players.get(0).getAccount().getUserName().equals(this.getUserName())) {
                opponent = players.get(1);
            } else {
                opponent = players.get(0);
            }

            accountView.viewAMatch(match, opponent);
        }
    }

}
