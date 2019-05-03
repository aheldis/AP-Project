package model.account;

import com.gilecode.yagson.com.google.gson.Gson;
import com.gilecode.yagson.com.google.gson.GsonBuilder;
import view.AccountView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AllAccount {
    private static AllAccount singleInstance = null;
    public static   ArrayList<Account> accounts = new ArrayList<>();


    private AllAccount() {
    }

    public static AllAccount getInstance() {
        if (singleInstance == null){
            singleInstance = new AllAccount();
            try{
                Account account;
                FileReader fr = new FileReader("D:\\project-Duelyst\\Duelyst\\AccountSaver\\AccountUser.txt");
                BufferedReader br = new BufferedReader(fr);
                String username = br.readLine();
                username = br.readLine();
                while(username != null) {
                    fr = new FileReader("D:\\project-Duelyst\\Duelyst\\AccountSaver\\" + username + ".txt");
                    Gson gson = new GsonBuilder().create();
                    account= gson.fromJson(fr, Account.class);
                    accounts.add(account);
                    username = br.readLine();
                }
            }
            catch (Exception e){
                System.out.println("can't find");
                //todo mitonim errore khas bedim :D
            }
        }
        return singleInstance;
    }

    public ArrayList<Account> getAccountsArrayList() {
        return accounts;
    }

    public Account userNameHaveBeenExist(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                return account;
            }
        }
        return null;
    }

    public boolean passwordMatcher(String userName, String password) {
        Account account = getAccountByName(userName);
        if (account == null)
            return false;
        return account.matchPassword(password);
    }

    public Account getAccountByName(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName))
                return account;
        }
        return null;
    }

    public void showLeaderBoard() {
        AccountView accountView = AccountView.getInstance();
        Collections.sort(accounts);
        for (int i = 0; i < accounts.size(); i++) {
            accountView.viewAccount(i + 1, accounts.get(i).getUserName(), accounts.get(i).getWins());
        }
    }

    public void createAccount(String userName, String password) {
        try {
            FileWriter file = new FileWriter("D:\\project-Duelyst\\Duelyst\\AccountSaver\\AccountUser.txt");
            file.append("\n"+userName);
            Account account = new Account(userName, password);
            addToAccounts(account);
        }
        catch (Exception e){

        }
    }

    public void addToAccounts(Account account) {
        accounts.add(account);
    }

//    public void allAccountSaver(){
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try {
//            FileWriter file = new FileWriter("D:\\project-Duelyst\\Duelyst\\AccountSaver\\AccountUser.txt");
//            file.write(gson.toJson(this));
//            file.close();
//
//        } catch (Exception e) {
//            //todo ye errori bede
//        }
//    }

}
