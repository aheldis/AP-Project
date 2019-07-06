package model.card;

import model.account.Account;

import java.io.Serializable;

public class CardId implements Serializable {
    private String cardId;
    private Account account = null;
    private Card card;
    private int number;

    public CardId(Account account, Card card, int number) {//{search for cardName in player cards and make Id}
        this.account = account;
        this.card = card;
        this.number = number;
        card.setCardId(this);
        setCardId();
    }

    public CardId(Card card, int number) {//{search for cardName in player cards and make Id}
        this.card = card;
        this.number = number;
        card.setCardId(this);
        setCardId();
    }

    private void setCardId() {//{number will add to  cardId}
        if (account == null)
            this.cardId = "computer_" + card.getName() + "_" + number;
        else
            this.cardId = account.getUserName() + "_" + card.getName() + "_" + number;
    }

    public String getCardIdAsString() {
        return cardId;
    }

    public Account getAccount() {
        return account;
    }
}
