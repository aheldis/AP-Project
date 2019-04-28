package view;

import model.account.Account;
import model.account.AllAccount;
import model.battle.Game;
import model.battle.Hand;
import model.battle.Player;
import view.enums.ErrorType;

public class MenuView {
    private static MenuView singleInstance = null;
    private MenuView(){}
    public static MenuView getInstance() {
        if (singleInstance == null)
            singleInstance = new MenuView();
        return singleInstance;
    }

    public void helpForAccountMenu(){
        System.out.println("Enter Collection - Go to your collection");
        System.out.println("Enter Shop - Go to shop");
        System.out.println("Enter Battle - Start a game");
        System.out.println("Enter Help - Show help of main menu");
        System.out.println("Enter Exit - Log out from game");
    }
    public void helpForMainMenu(){
        System.out.println("Enter Create Account - Create a new account");
        System.out.println("Enter Save - To save ");
        System.out.println("Enter Show LeaderBoard");
        System.out.println("Enter Login - To log in to your account");
        System.out.println("Enter Help - To show the orders");
    }
    public void printer(String string){
        System.out.println(string);
    }
    public void showLevelsForStory(){

    }
    public void showDecksAndModes(Account account){
        showModes();
        account.getCollection().showAllDecksName();
    }
    public void showAllAccount(){
        AllAccount allAccount=AllAccount.getInstance();
        for(Account account: allAccount.accounts){
            System.out.println(account.getUserName());
        }
    }
    public void showModes(){
        System.out.println("1. Death mode");
        System.out.println("2. SaveFlag for 6 turn");
        System.out.println("3. Collect half if flags");

    }
    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void printGameInfo(Game game){

    }
    public void showMyMinions(Player player){

    }
    public void showHand(){
        //to hand function showNextCard baraye card badi darim :D

    }
    public void showNextCard(Hand hand){

    }
}
