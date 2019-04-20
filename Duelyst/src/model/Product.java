package model;

/**
 * chon mitone on id harchi bashe aval miyayn id ro migirim baad
 * check mikonim k card boode item
 */

public class Product {
    private String cardId = "";
    private String usableItemId = "";
    private String deckName = "";
    private String id="";


    public void setId(String id) {
        this.id=id;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setUsableItemId(String usableItemId) {
        this.usableItemId = usableItemId;
    }

    public String getUsableItemId(){
        return usableItemId;
    }
    public String getDeckName(){
        return deckName;
    }
    public String getCardId(){
        return cardId;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
