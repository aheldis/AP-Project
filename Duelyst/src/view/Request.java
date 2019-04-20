package view;

import view.enums.RequestType;
import model.Product;

import java.util.Scanner;


/**
 * mikhay esmesho bezarim Scanner (to javaDoc neveshtam ke
 * sabz she :o )
 */

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private String state = "mainMenu";//menu or collection or shop or model.battle
    private RequestType type;

    public void getNewCommand() {
        this.command = scanner.nextLine().trim();
        type = getType();
    }

    public String getCommand(){
        return command;
    }
    public boolean isValid() {
        if(type==null)
            return false;
        return false;
    }

    public RequestType getType() {
        Product product=new Product();
        if (state.equals("mainMenu")) {
            switch (command.toLowerCase()) {
                case "enter collection":
                    return RequestType.MENU_ENTER_COLLECTION;
                case "enter shop":
                    return RequestType.MENU_ENTER_SHOP;
                case "enter model.battle":
                    return RequestType.MENU_ENTER_BATTLE;
                case "enter help":
                    return RequestType.MENU_ENTER_HELP;
                case "enter exit":
                    return RequestType.MENU_ENTER_EXIT;
            }
        }
        else if (state.equals("collection")) {

            switch (command.toLowerCase()) {
                case "exit":
                    return RequestType.COLLECTION_EXIT;
                case "show":
                    return RequestType.COLLECTION_SHOW;
                case "save":
                    return RequestType.COLLECTION_SAVE;
                case "help:":
                    return RequestType.COLLECTION_HELP;
                case "show all decks":
                    return RequestType.COLLECTION_SHOW_ALL_DECKS;
            }
            if(command.toLowerCase().matches("search \\w+")) {
                product.setCardId(command.substring(6));
                return RequestType.COLLECTION_SEARCH_CARD;
            }
            else if(command.toLowerCase().matches("create deck \\w+")) {
                product.setDeckName(command.substring(11));
                return RequestType.COLLECTION_CREATE_DECK;
            }
            else if(command.toLowerCase().matches("delete deck \\w+") ) {
                product.setDeckName(command.substring(11));
                return RequestType.COLLECTION_DELETE_DECK;
            }
            else if(command.toLowerCase().matches("add \\w+ to deck \\w+")) {
                product.setDeckName(command.split(" ")[4]);
                product.setCardId(command.substring(4));
                return RequestType.COLLECTION_ADD_CARD_TO_DECK;
            }
            else if(command.toLowerCase().matches("remove \\w+ from deck \\w+")){
                product.setDeckName(command.split(" ")[4]);
                product.setCardId(command.substring(4));
                return RequestType.COLLECTION_REMOVE_CARD_FROM_DECK;
            }

            else if(command.toLowerCase().matches("validate deck \\w+")){
                product.setDeckName(command.substring(14));
                return RequestType.COLLECTION_VALIDATE_DECK;
            }

            else if(command.toLowerCase().matches("select deck \\w+")) {
                product.setDeckName(command.substring(12));
                return RequestType.COLLECTION_SELECT_DECK;
            }
            else if(command.toLowerCase().matches("show deck \\w+")) {
                product.setDeckName(command.substring(10));
                return RequestType.COLLECTION_SHOW_DECK;
            }

        }
        else if (state.equals("shop")) {
            if (command.toLowerCase().matches("exit"))
                return RequestType.SHOP_EXIT;
            else if (command.toLowerCase().matches("show collection"))
                return RequestType.SHOP_SHOW_COLLECTION;
            else if (command.toLowerCase().matches("search collection \\w+"))
                return RequestType.SHOP_SEARCH_COLLECTION_CARD;
            else if (command.toLowerCase().matches("search \\w+"))
                return RequestType.SHOP_SERACH_CARD;
            else if(command.toLowerCase().matches("buy \\w+"))
                return RequestType.SHOP_BUY;
            else if(command.toLowerCase().matches("sell \\w+"))
                return RequestType.SHOP_SELL;
            else if(command.toLowerCase().matches("show"))
                return RequestType.SHOP_SHOW;
            else if(command.toLowerCase().matches("help"))
                return  RequestType.SHOP_HELP;
        }
        else if (state.equals("model/battle")) {
            switch (command) {

            }
        }
        return null;
    }
}
