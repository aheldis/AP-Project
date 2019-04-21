package model.account;

import view.enums.ErrorType;
import view.AccountView;

import java.util.ArrayList;
import java.util.Collections;

public class AllAccount {

    //inam singleton bashe? :-?

    public static AccountView accountView = AccountView.getInstance();
    public static ArrayList<Account> accounts;

    public static void addToAccounts(Account account) {
        accounts.add(account);
    }

    public static ArrayList<Account> getAccountsArrayList() {
        return accounts;
    }

    public static Account getAccountByName(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName))
                return account;
        }
        return null;
    }

    public static boolean userNameHaveBeenExist(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean passwordMatcher(String userName,String password){
        Account account = getAccountByName(userName);
        if(account==null)
            return false;
        if(account.matchPassword(password))
            return true;
        return false;
    }

    public static void login(String userName, String password) { //todo ina bayad bere to conroller o ina :-?
        //(kheyr)todo controller miyad inja donbalet :))
        Account account = getAccountByName(userName);
        if (account == null) {//todo hamishe vojod dare chon to controller check mikonim
//            ErrorType error = ErrorType.USER_NAME_NOT_FOUND;
//            accountView.printError(error);
            return;
        }
        if (account.matchPassword(password)) {
            //TODO login
            return ;
        }
            ErrorType error = ErrorType.PASSWORD_DOES_NOT_MATCH;
            accountView.printError(error);
            return ;

    }

    public static void showLeaderBoard() {
        Collections.sort(accounts);
        for (int i = 0; i < accounts.size(); i++) {
            accountView.viewAccount(i + 1, accounts.get(i).getUserName(), accounts.get(i).getWins());
        }
    }

    public static void helpOfAccount() {
        accountView.viewHelpOfAccount();
    }

    public static void createAccount(String userName, String password) {
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
