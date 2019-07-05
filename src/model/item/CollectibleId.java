package model.item;

import java.io.Serializable;

public class CollectibleId implements Serializable {
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
        CollectibleId = collectible.getName() + "_" + number;
    }

    public String getCollectibleIdAsString() {
        return CollectibleId;

    }


}
