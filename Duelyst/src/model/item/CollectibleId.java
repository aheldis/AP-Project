package model.item;

public class CollectibleId {
    private String CollectibleId;
    private int number;
    private Collectible collectible;

    public CollectibleId(Collectible collectible, int number) {
        this.collectible = collectible;
        this.number = number;
        collectible.setCollectibleId(this);
        setCollectibleId();
    }

    public void setCollectibleId() {//{number of that model.card will add to cardId}
        CollectibleId = collectible.getName() + " " + number;
    }

    public String getCollectibleIdAsString() {
        return CollectibleId;

    }


}
