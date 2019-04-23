package view;

public class MenuView {
    private static MenuView singleInstance = null;
    private MenuView(){}
    public static MenuView getInstance() {
        if (singleInstance == null)
            singleInstance = new MenuView();
        return singleInstance;
    }

    public void helpForAccountMenu(){
        System.out.println("Enter Collection - goto your collection");
        System.out.println("Enter Shop - goto shop");
        System.out.println("Enter Battle - start a game");
        System.out.println("Enter Help - show help of main menu");
        System.out.println("Enter Exit - logout from game");
    }
    public void helpForMainMenu(){
        System.out.println("Enter Create Account - create a new account");
        System.out.println("Enter Save - to save ");
        System.out.println("Enter Show LeaderBoard");
        System.out.println("Enter Login - to login to your account");
        System.out.println("Enter Help - to show the orders");
    }
}
