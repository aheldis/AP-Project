package model.account;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import view.AccountView;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class AllAccount {
    private static AllAccount singleInstance = new AllAccount();
    private ArrayList<Account> accounts = new ArrayList<>();


    private AllAccount() {
        try {
            FileReader fileReader = new FileReader("AccountSaver/AccountUser.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {

                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                String userName = line.split("/")[0];
                try {
                    InputStream input = new FileInputStream("AccountSaver/" + userName + ".json");
                    Reader reader = new InputStreamReader(input);
                    YaGson mapper = new YaGson();
                    Account account = mapper.fromJson(reader, Account.class);//load the deck
                    addToAccounts(account);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AllAccount getInstance() {
        if (singleInstance == null) {
            singleInstance = new AllAccount();

//            try{
//                Account account;
//                FileReader fr = new FileReader("D:\\project-Duelyst\\Duelyst\\AccountSaver\\AccountUser.txt");
//                BufferedReader br = new BufferedReader(fr);
//                String username = br.readLine();
//                username = br.readLine();
//                while(username != null) {
//                    fr = new FileReader("D:\\project-Duelyst\\Duelyst\\AccountSaver\\" + username.trim() + ".txt");
//                    Gson gson = new GsonBuilder().create();
//                    account= gson.fromJson(fr, Account.class);
//                    accounts.add(account);
//                    username = br.readLine();
//                }
//                fr.close();
//                br.close();
//            }
//            catch (Exception e){
//                System.out.println(e);
/** save don't delete*/
//            }
        }
        return singleInstance;
    }

    public ArrayList<Account> getAccountsArrayList() {
        return accounts;
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
            File file = new File("AccountSaver/AccountUser.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(userName + "/" + password + '\n');
            fileWriter.close();
        } catch (IOException ignored) {
        }
        Account account = new Account(userName, password);
        addToAccounts(account);
    }

    public void addToAccounts(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void saveAccount(Account account) {
        try {
            String path = "AccountSaver/" + account.getUserName() + ".json";
            File file = new File(path);
            if (file.exists())
                file.delete();
            YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(file);
            altMapper.toJson(account, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
