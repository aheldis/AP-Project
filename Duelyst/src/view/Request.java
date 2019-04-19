package view;

import IDK.RequestType;

import java.util.Scanner;


/**
 * mikhay esmesho bezarim Scanner (to javaDoc neveshtam ke
 * sabz she :o )
 */

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private String state = "mainMenu";//menu or collection or shop or battle

    public void getNewCommand() {
        this.command = scanner.nextLine();
    }

    public String getCommand() {
        return command;
    }

    public boolean isValid() {
        RequestType type = getType();

    }

    public RequestType getType() {
        if (state.equals("mainMenu"))
            switch (command.toLowerCase()) {
                case "enter collection":
                    return RequestType.MENU_ENTER_COLLECTION;
                case "enter shop":
                    return RequestType.MENU_ENTER_SHOP;
                case "enter battle":
                    return RequestType.MENU_ENTER_BATTLE;
                case "enter help":
                    return RequestType.MENU_ENTER_HELP;
                case "enter exit":
                    return RequestType.MENU_ENTER_EXIT;
            }
        else if (state.equals("collection")) {
            switch (command) {
                case "exit":
                    return RequestType.COLLECTION_EXIT;
                case "show":
                    return RequestType.COLLECTION_SHOW;
                case "save":
                    return RequestType.COLLECTION_SAVE;
                case "help:":
                    return RequestType.COLLECTION_HELP;
            }

            //todo check syntax
        } else if (state.equals("shop"))
            switch (command) {

            }
        else if (state.equals("battle"))
            switch (command) {

            }
    }

}
