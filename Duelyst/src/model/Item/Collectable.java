package model.Item;

public class Collectable extends Item {

    private CollectableId collectableId;

    public CollectableId getCollectableId() {
        return collectableId;
    }

    public void setCollectableId(CollectableId collectableId) {
        this.collectableId = collectableId;
    }
}
