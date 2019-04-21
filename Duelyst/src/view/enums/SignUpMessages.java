package view.enums;

public class SignUpMessages {
    private static SignUpMessages singleInstance=null;
    public static SignUpMessages getInstance(){
        if(singleInstance==null)
            singleInstance=new SignUpMessages();
        return singleInstance;
    }

//    public void showLoginMessages(){
//        System.out.println("if you have account:");
//        System.out.println("Enter you UserName and password in two lines");
//    }
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
