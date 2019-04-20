package model.card;

import model.account.Account;

public class CardId {
    String cardId;
    Account account;
    Card card;

    public CardId(Account account, Card card) {//{search for cardName in player cards and make Id}
        this.account = account;
        this.card = card;
        card.setCardId(this);
    }

    public String getCardIdAsString() {
        return cardId;
    }

    public void setCardId(int number) {//{number will add to  cardId}
        this.cardId = account.getUserName() + "_" + card.getName() + "_" + number;
    }

}
