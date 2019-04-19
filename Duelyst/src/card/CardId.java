package card;

public class CardId {
    String cardId;
    String cardName;
    String playerName;

    public void CardId(String playerName, String cardName) {//{search for cardName in player cards and make Id}
        this.playerName=playerName;
        this.cardName=cardName;
    }


    public String getCardIdAsString() {
        return cardId;
    }

    public void setCardId(int number) {//{number will add to  cardId}
       this. cardId=this.playerName+this.cardName+number;

    }

}
