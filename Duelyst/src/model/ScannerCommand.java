package model;

/**
 * chon mitone on id harchi bashe aval miyayn id ro migirim baad
 * check mikonim k card boode item
 */

public class ScannerCommand {
    String userName = "";
    String password = "";
    private String cardId = "";
    private String usableItemId = "";
    private String deckName = "";
    private String id = "";

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsableItemId() {
        return usableItemId;
    }

    public void setUsableItemId(String usableItemId) {
        this.usableItemId = usableItemId;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
