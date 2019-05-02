package model.item;

import model.account.Account;

public class CollectibleId {
    private String CollectibleId;
    private int number;
    private Collectible collectible;
    private Account account;

    public CollectibleId(Account account, Collectible collectible, int number) {
        this.account = account;
        this.collectible = collectible;
        this.number = number;
        collectible.setCollectibleId(this);
        setCollectibleId();
    }

    public void setCollectibleId() {//{number of that model.card will add to cardId}
        CollectibleId = account.getUserName() + "_" + collectible.getName() + " " + number;
    }

    public String getCollectibleIdAsString() {
        return CollectibleId;

    }


}
