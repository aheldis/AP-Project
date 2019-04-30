package model.card;

import model.account.Account;

public class CardId {
    private String cardId;
    private Account account;
    private Card card;
    private int number;

    public CardId(Account account, Card card, int number) {//{search for cardName in player cards and make Id}
        this.account = account;
        this.card = card;
        this.number = number;
        card.setCardId(this);
        setCardId();
    }

    public void setCardId() {//{number will add to  cardId}
        this.cardId = account.getUserName() + "_" + card.getName() + "_" + number;
    }

    public String getCardIdAsString() {
        return cardId;
    }

}
