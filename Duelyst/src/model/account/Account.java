package model.account;

import com.gilecode.yagson.com.google.gson.Gson;
import com.gilecode.yagson.com.google.gson.GsonBuilder;
import model.battle.Deck;
import model.battle.MatchInfo;
import model.battle.OrdinaryPlayer;
import model.battle.Player;
import view.AccountView;

import javax.swing.*;
import java.io.FileWriter;
import java.util.ArrayList;


public class Account implements Comparable<Account> {
    public String userName;
    public String password;
    public int daric = 15000;
    public int wins;
    public ArrayList<MatchInfo> matchHistory;
    public Collection collection = new Collection(this);
    public Deck mainDeck = new Deck();
    public Player player = new OrdinaryPlayer();
    public ArrayList<Deck> decks = new ArrayList<>();

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Collection getCollection() {
        return collection;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck deck) {
        this.mainDeck = deck;
    }

    public String getUserName() {
        return userName;
    }

    public int getDaric() {
        return daric;
    }

    public ArrayList<MatchInfo> getMatchHistory() {
        return matchHistory;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    public void addToWins() {
        wins++;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public int compareTo(Account account) {
        return account.getWins() - this.getWins();
    }

    public int getWins() {
        return wins;
    }

    public void changeValueOfDaric(int number) {
        daric += number;
    }

    public void addMatchInfo(MatchInfo matchInfo) {
        matchHistory.add(matchInfo);
    }

    public void showMatchHistory() {
        AccountView accountView = AccountView.getInstance();
        for (MatchInfo match : matchHistory) {
            accountView.viewAMatch(match, this);
        }
    }

    private void setDeckFromCollection() {
        decks = collection.getDecks();
    }

    public void accountSave() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter file = new FileWriter("D:\\project-Duelyst\\Duelyst\\AccountSaver\\" + userName + ".txt");
            file.write(gson.toJson(this));
            file.close();

        } catch (Exception e) {
            System.out.println("account save");
            //todo ye errori bede
        }

    }
}