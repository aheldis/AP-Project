package view;

import model.ScannerCommand;
import view.enums.RequestType;

import java.util.Scanner;

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private String state;//menu or collection or shop or model.battle
    private RequestType type;

    private String deckName;
    private String Id;

    public Request(String state) {
        this.state = state;
    }

    public void getNewLine() {
        this.command = scanner.nextLine().trim();
    }

    public void getNewCommand() {
        this.command = scanner.nextLine().trim();
        type = getType();
    }

    public String getCommand() {
        return command;
    }

    public boolean isValid() { //todo in be che dard mikhore? :))
        if (type == null)
            return false;
        return false;
    }

    public RequestType getRequestType() {
        return type;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getId() {
        return Id;
    }

    public String getDeckName() {
        return deckName;
    }

    private RequestType getType() {
        ScannerCommand scannerCommand = new ScannerCommand();
        if (state.equals("accountMenu")) {
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
        } else if (state.equals("collection")) {

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
            if (command.toLowerCase().matches("search \\w+")) {
                setId(command.substring(6));
                return RequestType.COLLECTION_SEARCH_CARD;
            } else if (command.toLowerCase().matches("create deck \\w+")) {
                setDeckName(command.substring(11));
                return RequestType.COLLECTION_CREATE_DECK;
            } else if (command.toLowerCase().matches("delete deck \\w+")) {
                setDeckName(command.substring(11));
                return RequestType.COLLECTION_DELETE_DECK;
            } else if (command.toLowerCase().matches("add \\w+ to deck \\w+")) {
                setDeckName(command.split(" ")[4]);
                setId(command.substring(4));
                return RequestType.COLLECTION_ADD_CARD_TO_DECK;
            } else if (command.toLowerCase().matches("remove \\w+ from deck \\w+")) {
                setDeckName(command.split(" ")[4]);
                setId(command.substring(4));
                return RequestType.COLLECTION_REMOVE_CARD_FROM_DECK;
            } else if (command.toLowerCase().matches("validate deck \\w+")) {
                setDeckName(command.substring(14));
                return RequestType.COLLECTION_VALIDATE_DECK;
            } else if (command.toLowerCase().matches("select deck \\w+")) {
                setDeckName(command.substring(12));
                return RequestType.COLLECTION_SELECT_DECK;
            } else if (command.toLowerCase().matches("show deck \\w+")) {
                setDeckName(command.substring(10));
                return RequestType.COLLECTION_SHOW_DECK;
            }

        } else if (state.equals("shop")) {
            if (command.toLowerCase().matches("exit")) {
                return RequestType.SHOP_EXIT;
            }
            else if (command.toLowerCase().matches("show collection")) {
                return RequestType.SHOP_SHOW_COLLECTION;
            }
            else if (command.toLowerCase().matches("search collection \\w+")) {
                setId(command.substring(18));
                return RequestType.SHOP_SEARCH_COLLECTION_CARD;
            }
            else if (command.toLowerCase().matches("search \\w+")) {
                setId(command.substring(7));
                return RequestType.SHOP_SERACH_CARD;
            }
            else if (command.toLowerCase().matches("buy \\w+")) {
                setId(command.substring(4));
                return RequestType.SHOP_BUY;
            }
            else if (command.toLowerCase().matches("sell \\w+")) {
                setId(command.substring(5));
                return RequestType.SHOP_SELL;
            }
            else if (command.toLowerCase().matches("show")) {
                return RequestType.SHOP_SHOW;
            }
            else if (command.toLowerCase().matches("help")) {
                return RequestType.SHOP_HELP;
            }
        } else if (state.equals("mainMenu")) {
            switch (command.toLowerCase()) {
                case "Login":
                    return RequestType.MAIN_MENU_LOGIN;
                case "Create account":
                    return RequestType.MAIN_MENU_SIGN_UP;
                case "Show leaderBoard" :
                    return RequestType.MAIN_MENU_LEADER_BOARD;
                case "Help":
                    return RequestType.MAIN_MENU_HELP;
                case "Save":
                    return getRequestType().MAIN_MENU_SAVE;

            }

        } else if (state.equals("battle")) {
            switch (command) {

            }
        }
        return null;
    }
}
