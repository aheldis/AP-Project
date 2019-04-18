package account;

public class Account {
    private String userName;
    private String password;
    private int daric;
    private Arraylist<Match> matchHistory;
    private Collection collection;
    private Player player;
    private Deck mainDeck;
    private ArrayList<Deck> decks;


    public Deck getMainDeck() {
        return mainDeck;
    }

    private void setDeckFromCollection() {
        decks = (ArrayList<Deck>) collection.getDecks();
    }

    public void setMainDeck(Deck deck) {

    }

    public void setUserName(String userName) {

    }

    public void setPassword(String password) {

    }

    public void setPlayer(Player player) {

    }

    public String getUserName() {

    }

    public String getPassword() {
    }

    public int getDaric() {

    }

    public Collection getCollection() {

    }

    public Player getPlayer() {

    }

    public void changeValueOfDaric(int number) {

    }

    public void logOut() {

    }

    public void addMatch(Match match) {

    }

    public Match chooseMatch() {

    }
}
