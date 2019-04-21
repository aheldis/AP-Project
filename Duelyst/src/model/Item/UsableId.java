package model.Item;

import model.account.Account;

public class UsableId {
    private String usableId;
    private int number;
    private Usable usable;
    private Account account;

    public UsableId(Account account, Usable usable, int number) {
        this.account = account;
        this.usable = usable;
        this.number = number;
        usable.setUsableId(this);
        setUsableId();
    }

    public void setUsableId() {//{number of that model.card will add to cardId}
        usableId = account.getUserName() + "_" + usable.getName() + " " + number;
    }

    public String getUsableIdAsString() {
        return usableId;

    }


}
