package model.battle;

import model.card.Card;
import model.card.CardId;
import view.AccountView;

import java.util.ArrayList;

public class GraveYard {

    private ArrayList<Card> cards;
    private Player player;

    public Card cardHaveBeenExistInGraveYard(String cardId){
        for(Card card : cards){
            if(card.getCardId().getCardIdAsString().equals(cardId))
                return card;
        }
        return null;
    }

    public void addCardToGraveYard(Card card) {
        cards.add(card);
    }

    public void showGraveYard() {
        AccountView.getInstance().cardsAndItemsView(Card.getSpells(cards), Card.getMinions(cards), Card.getHeroes(cards), new ArrayList<> ());
    }

    public void showInfo(Card card) {

    }

}
