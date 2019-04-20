package model.Item;

import model.account.Account;

public class CollectableId {
    private String usableId;

    private Collectable collectable;
    private Account account;

    public CollectableId(Account account, Collectable collectable) {
        this.account = account;
        this.collectable = collectable;
        collectable.setCollectableId(this);
    }

    public void setUsableId(int number) {//{number of that model.card will add to cardId}
        usableId = account.getUserName() + "_" + collectable.getName() + " " + number;
    }

    public String getUsableIdAsString() {
        return usableId;

    }


}
