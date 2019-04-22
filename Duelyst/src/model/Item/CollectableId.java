package model.Item;

import model.account.Account;

public class CollectableId {
    private String collectableId;
    private int number;
    private Collectable collectable;
    private Account account;

    public CollectableId(Account account, Collectable collectable, int number) {
        this.account = account;
        this.collectable = collectable;
        this.number = number;
        collectable.setCollectableId(this);
        setCollectableId();
    }

    public void setCollectableId() {//{number of that model.card will add to cardId}
        collectableId = account.getUserName() + "_" + collectable.getName() + " " + number;
    }

    public String getCollectableIdAsString() {
        return collectableId;

    }


}
