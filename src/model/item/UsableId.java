package model.item;

import model.account.Account;

import java.io.Serializable;

public class UsableId implements Serializable {
    private String usableId;
    private int number;
    private Usable usable;
    private Account account = null;

    public UsableId(Account account, Usable usable, int number) {
        this.account = account;
        this.usable = usable;
        this.number = number;
        usable.setUsableId(this);
        setUsableId();
    }

    public UsableId(Usable usable, int number) {
        this.usable = usable;
        this.number = number;
        usable.setUsableId(this);
        setUsableId();
    }

    public void setUsableId() {//{number of that model.card will add to cardId}
        if(account == null)
            usableId = "computer_" + usable.getName() + "_" + number;
        else

        usableId = account.getUserName() + "_" + usable.getName() + "_" + number;
    }

    public String getUsableIdAsString() {
        return usableId;

    }


}
