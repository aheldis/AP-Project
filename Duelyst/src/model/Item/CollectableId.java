package model.Item;

import model.account.Account;

public class CollectableId {
    private String collectableId;

    private Collectable collectable;
    private Account account;

    public CollectableId(Account account, Collectable collectable) {
        this.account = account;
        this.collectable = collectable;
        collectable.setCollectableId(this);
    }

    public void setCollectableId(int number) {//{number of that model.card will add to cardId}
        collectableId = account.getUserName() + "_" + collectable.getName() + " " + number;
    }

    public String getCollectableIdAsString() {
        return collectableId;

    }


}
