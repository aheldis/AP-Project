package account;

import java.util.ArrayList;
import java.util.Collections;

public class AllAccount{

    public static ArrayList<Account> accounts;

    public static void addToAccounts(Account account) {
        accounts.add(account);
    }

    public static ArrayList<Account> getAccountsArrayList() {
        return accounts;
    }

    public static Account getAccountByName(String userName) {
        for(Account account: accounts) {
            if (account.getUserName().equals(userName))
                return account;
        }
        return null;
    }

    public static boolean userNameHaveBeenExist(String userName) {
        for(Account account: accounts) {
            if (account.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public static void login(String userName, String password) {
        Account account = getAccountByName(userName);
        if(account == null){
            System.out.println("Error: User name not found!"); //sout
            //todo
            return;
        }
        if(account.matchPassword(password)){
            //TODO login
        }
        else{
            System.out.println("Error: Password doesn't match."); //sout
        }
    }

    public static void showLeaderBoard() {
        Collections.sort(accounts);
        for(int i = 0; i < accounts.size(); i++){
            System.out.println(i + 1 + "- UserName: " + accounts.get(i).getUserName() + " - Wins: " + accounts.get(i).getWins()); //sout
        }
    }

    public static void helpOfAccount() {
        //todo
    }

    public static void createAccount(String userName, String password){
        if(userNameHaveBeenExist(userName)){
            System.out.println("Error: User name already exist."); //sout
            return;
        }

        Account account = new Account(userName, password);
        addToAccounts(account);
        login(userName, password);
    }

}
