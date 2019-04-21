package Controller;


import model.account.Account;
import model.account.AllAccount;
import view.*;
import view.enums.SignUpMessages;
import view.enums.RequestType;

public class MenuController {
    private String state = "login";
    private boolean endProgram = false;

    public void main() {
        Request request= new Request(state);// mige signUp ya logIn hast
        request.getNewCommand();
        while (!endProgram) {

            if(state.equals("login")){
                if(request.getRequestType()==RequestType.SIGN_UP){
                    SignUpMessages signUpMessages = SignUpMessages.getInstance();
                    signUpMessages.showSignUpGetUserName();
                    request.getNewLine();
                    while (AllAccount.userNameHaveBeenExist(request.getCommand())){
                        signUpMessages.showSignUpHaveUserName();
                        signUpMessages.showSignUpGetUserName();
                        request.getNewLine();
                    }
                    String username=request.getCommand();
                    signUpMessages.showSignUpGetPassword();
                    request.getNewLine();
                    AllAccount.createAccount(username,request.getCommand());
                }
                if(request.getRequestType()== RequestType.LOGIN){


                }
            }

            if (state.equals("menu")) {
                while (request.getRequestType() != RequestType.MENU_ENTER_EXIT) {
                    switch (request.getRequestType()) {
                        case MENU_ENTER_COLLECTION:
                            state = "collection";
                            break;
                        case MENU_ENTER_BATTLE:
                            break;
                        case MENU_ENTER_HELP:
                            MenuView menu = MenuView.getInstance();
                            menu.helpForMainMenu();
                            break;
                        case MENU_ENTER_SHOP:
                            state = "shop";
                            break;
                    }


                    request = new Request(state);
                    request.getNewCommand();
                }
            }

            if (state.equals("collection")) {
                while (request.getRequestType() != RequestType.COLLECTION_EXIT) {
                    switch (request.getRequestType()) {
                        case COLLECTION_HELP:


                    }


                    request = new Request(state);
                    request.getNewCommand();

                }
            }


        }

    }

}
