package model;

/**
 * chon mitone on id harchi bashe aval miyayn id ro migirim baad
 * check mikonim k card boode item
 */

public class ScannerCommand {
    private String cardId = "";
    private String usableItemId = "";
    private String deckName = "";
    private String id="";
    String userName="";
    String password="";


    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getPassword(){
        return password;
    }
    public String getUserName(){
        return userName;
    }
    public void setPassword(String password){
        this.password=password;
    }


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
