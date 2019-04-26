package model.battle;

public class ComputerPlayer extends Player {

    public ComputerPlayer(Deck deck) {
        this.setMainDeck(deck);
        this.setType("ComputerPlayer");
        mainDeck.setRandomOrderForDeck();
        setMana(2);
        setHand();
    }

    public static ComputerPlayer makeNewPlayer(Deck mainDeck) {
        ComputerPlayer player = new ComputerPlayer(mainDeck);
        return player;
    }

    public void addToAccountWins() {
    }

    public void addMatchInfo(MatchInfo matchInfo) {
    }

    public void setDeck() {

    }

    public void playTurn() {

        //yeseri kar random anjam bede
    }

}
