package Item;

public class UsableId {
    private String usableId;
    private String itemName;
    private String playerName;

    public  UsableId(String playerName, String itemName) {
        this.playerName=playerName;
        this.itemName=itemName;

    }

    public void setUsableId(int number) {//{number of that card will add to cardId}
        usableId=playerName+itemName+number;
    }

    public String getUsableIdAsString() {
        return usableId;

    }



}
