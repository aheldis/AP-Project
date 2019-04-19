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
        this.command = scanner.nextLine().trim();
    }

    public boolean isValid() {
        RequestType type = getType();

    }

    public RequestType getType() {
        if (state.equals("mainMenu")) {
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
        }
        else if (state.equals("collection")) {
            switch (command) {
                case "exit \\d+":
                    return RequestType.COLLECTION_EXIT;
                case "show":
                    return RequestType.COLLECTION_SHOW;
                case "save":
                    return RequestType.COLLECTION_SAVE;
                case "help:":
                    return RequestType.COLLECTION_HELP;
            }
        }
        else if (state.equals("shop")) {
            if (command.equals("exit"))
                return RequestType.SHOP_EXIT;
            else if (command.equals("show collection"))
                return RequestType.SHOP_SHOW_COLLECTION;
            else if (command.substring(0, 17).equals("search collection"))
                return RequestType.SHOP_SEARCH_COLLECTION_CARD;
            else if (command.substring(0, 6).equals("search"))
                return RequestType.SHOP_SERACH_CARD;
            else if(command.substring(0, 3).equals("buy"))
                return RequestType.SHOP_BUY;
            else if(command.substring(0, 4).equals("sell"))
                return RequestType.SHOP_SELL;
            else if(command.equals("show"))
                return RequestType.SHOP_SHOW;
            else if(command.equals("help"))
                return  RequestType.SHOP_HELP;
        }
        else if (state.equals("battle")) {
            switch (command) {

            }
        }
    }
}
