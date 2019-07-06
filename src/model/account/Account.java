package model.account;

import model.battle.Deck;
import model.battle.MatchInfo;
import model.battle.OrdinaryPlayer;
import model.battle.Player;
import view.AccountView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Account implements Comparable<Account>, Cloneable, Serializable {
    private String userName;
    private String password;
    private int daric = 150000;
    private int wins;
    private ArrayList<MatchInfo> matchHistory = new ArrayList<>();
    private Collection collection = new Collection(this);
    private Deck mainDeck = new Deck();
    private Player player;
    private ArrayList<Deck> decks = new ArrayList<>();
    private String accountImagePath = null;
    private String profileImagePath = null;
    private String authToken;
    private boolean currentlyPlaying = false;

    public String getAuthToken() {
        return authToken;
    }

    public void setCurrentlyPlaying(boolean currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public boolean isCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public String getAccountImagePath() {
        if(accountImagePath == null)
            setAccountImagePath();
        System.out.println(accountImagePath);
        return accountImagePath;
    }
    public String getProfileImagePath(){
        if(profileImagePath == null)
            setProfileImagePath();
        return profileImagePath;
    }

    public void setProfileImagePath() {
        Random random = new Random();
        int number = random.nextInt(20) + 1;
        profileImagePath = "pics/shop/profile-" + number +".jpg";
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.player = new OrdinaryPlayer();
    }

    private void setAccountImagePath(){
        Random random = new Random();
        int number = random.nextInt(20) + 1;
        accountImagePath = "pics/shop/emotes-" + number +".png";
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }


    public void setDaric(int daric) {
        this.daric = daric;
    }

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
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
        return collection.getDecks();
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

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }




//    public void accountSave() {
//        try {
//            FileWriter file = new FileWriter("D:/project-Duelyst/Duelyst/AccountSaver/AccountUser.txt");
//            file.append("\n"+userName);
//            file.close();
//        }
//        catch (Exception e){
//
//        }
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try {
//            FileWriter file = new FileWriter("D:/project-Duelyst/Duelyst/AccountSaver/" + userName + ".txt");
//            file.write(gson.toJson(this));
//            file.close();
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
    /**  for save don't delete **/

}