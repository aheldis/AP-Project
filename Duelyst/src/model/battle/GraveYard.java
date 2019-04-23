package model.battle;

import model.card.Card;
import model.card.CardId;

import java.util.ArrayList;

public class GraveYard {

    private ArrayList<Card> cards;
    private Player player;

    public void addCardToGraveYard(Card card) {
        cards.add(card);
    }

    public void showGraveYard() {

    }

    public void showInfo(CardId cardId) {

    }

}
