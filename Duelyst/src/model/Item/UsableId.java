package model.Item;

import model.account.Account;

public class UsableId {
    private String usableId;

    private Usable usable;
    private Account account;

    public UsableId(Account account, Usable usable) {
        this.account = account;
        this.usable = usable;
        usable.setUsableId(this);
    }

    public void setUsableId(int number) {//{number of that model.card will add to cardId}
        usableId = account.getUserName() + "_" + usable.getName() + " " + number;
    }

    public String getUsableIdAsString() {
        return usableId;

    }


}
