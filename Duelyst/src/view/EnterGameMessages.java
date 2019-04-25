package view;

public class EnterGameMessages {
    private static EnterGameMessages singleInstance=null;
    public static EnterGameMessages getInstance(){
        if(singleInstance==null)
            singleInstance=new EnterGameMessages();
        return singleInstance;
    }

    public void showLoginGetName(){
        System.out.println("Enter you UserName");
    }
    public void showLoginHaveNotName(){
        System.out.println("This name have not  been exist");
    }
    public void showLoginGetPassword(){
        System.out.println("Enter your password");
    }
    public void showSignUpGetUserName(){
        System.out.println("Enter a name as UserName");
    }
    public void showSignUpHaveUserName(){
        System.out.println("UserName have been exist");
    }
    public void showSignUpGetPassword(){
        System.out.println("Enter your password");
    }
}
