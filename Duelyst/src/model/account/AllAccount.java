package model.account;

import view.enums.ErrorType;
import view.AccountView;

import java.util.ArrayList;
import java.util.Collections;

public class AllAccount {
    public static AllAccount singleInstance = null;
    public AccountView accountView = AccountView.getInstance();
    public ArrayList<Account> accounts;

    public static AllAccount getInstance() {
        if (singleInstance == null)
            singleInstance = new AllAccount();
        return singleInstance;
    }

    public void addToAccounts(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccountsArrayList() {
        return accounts;
    }

    public Account getAccountByName(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName))
                return account;
        }
        return null;
    }

    public boolean userNameHaveBeenExist(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean passwordMatcher(String userName, String password) {
        Account account = getAccountByName(userName);
        if (account == null)
            return false;
        if (account.matchPassword(password))
            return true;
        return false;
    }

    public void login(String userName, String password) { //todo ina bayad bere to conroller o ina :-?
        //(kheyr)todo controller miyad inja donbalet :))
            //TODO login

    }

    public void showLeaderBoard() {
        Collections.sort(accounts);
        for (int i = 0; i < accounts.size(); i++) {
            accountView.viewAccount(i + 1, accounts.get(i).getUserName(), accounts.get(i).getWins());
        }
    }

    public void helpOfAccount() {
        accountView.viewHelpOfAccount();
    }

    public void createAccount(String userName, String password) {
//        if (userNameHaveBeenExist(userName)) {
//            ErrorType error = ErrorType.USER_NAME_ALREADY_EXIST;
//            accountView.printError(error);
//            return;
//        }

        Account account = new Account(userName, password);
        addToAccounts(account);
        login(userName, password);
    }

}
