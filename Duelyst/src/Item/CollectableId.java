package Item;

public class CollectableId {
    private String collectableId;
    private String itemName;
    private String playerName;

    public  CollectableId(String playerName, String itemName) {
        this.playerName=playerName;
        this.itemName=itemName;

    }

    public void setCollectableId(int number) {//{number of that card will add to cardId}
        collectableId=playerName+itemName+number;
    }

    public String getCollectableIdAsString() {
        return collectableId;

    }

}
