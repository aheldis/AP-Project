package model.battle;

public class OrdinaryPlayer extends Player {
    public void setType() {
        type = "ordinaryPlayer";
    }

    public void addToAccountWins() {
        getAccount().addToWins();
    }

    public void setDeck() {

    }

    public void setHand() {

    }
}
