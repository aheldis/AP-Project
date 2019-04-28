package view;

import model.requirment.Coordinate;
import view.enums.RequestType;
import view.enums.StateType;

import java.util.Scanner;

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private StateType state;//menu or collection or shop or model.battle
    private RequestType type;
    private Coordinate coordinate = new Coordinate();

    private String deckName;
    private String Id;

    public Request(StateType state) {
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isValid() { //todo in be che dard mikhore? :))  //todo نمیدونم (zahra)
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
        if (state == StateType.ACCOUNT_MENU) {
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
        else if (state == StateType.COLLECTION) {
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
            }
            if (command.toLowerCase().matches("create deck \\w+")) {
                setDeckName(command.substring(11));
                return RequestType.COLLECTION_CREATE_DECK;
            }
            if (command.toLowerCase().matches("delete deck \\w+")) {
                setDeckName(command.substring(11));
                return RequestType.COLLECTION_DELETE_DECK;
            }
            if (command.toLowerCase().matches("add \\w+ to deck \\w+")) {
                setDeckName(command.split(" ")[4]);
                setId(command.substring(4));
                return RequestType.COLLECTION_ADD_CARD_TO_DECK;
            }
            if (command.toLowerCase().matches("remove \\w+ from deck \\w+")) {
                setDeckName(command.split(" ")[4]);
                setId(command.substring(4));
                return RequestType.COLLECTION_REMOVE_CARD_FROM_DECK;
            }
            if (command.toLowerCase().matches("validate deck \\w+")) {
                setDeckName(command.substring(14));
                return RequestType.COLLECTION_VALIDATE_DECK;
            }
            if (command.toLowerCase().matches("select deck \\w+")) {
                setDeckName(command.substring(12));
                return RequestType.COLLECTION_SELECT_DECK;
            }
            if (command.toLowerCase().matches("show deck \\w+")) {
                setDeckName(command.substring(10));
                return RequestType.COLLECTION_SHOW_DECK;
            }

        }
        else if (state == StateType.SHOP) {
            if (command.toLowerCase().matches("exit")) {
                return RequestType.SHOP_EXIT;
            }
            if (command.toLowerCase().matches("show collection")) {
                return RequestType.SHOP_SHOW_COLLECTION;
            }
            if (command.toLowerCase().matches("search collection \\w+")) {
                setId(command.substring(18));
                return RequestType.SHOP_SEARCH_COLLECTION_CARD;
            }
            if (command.toLowerCase().matches("search \\w+")) {
                setId(command.substring(7));
                return RequestType.SHOP_SEARCH_CARD;
            }
            if (command.toLowerCase().matches("buy \\w+")) {
                setId(command.substring(4));
                return RequestType.SHOP_BUY;
            }
            if (command.toLowerCase().matches("sell \\w+")) {
                setId(command.substring(5));
                return RequestType.SHOP_SELL;
            }
            if (command.toLowerCase().matches("show")) {
                return RequestType.SHOP_SHOW;
            }
            if (command.toLowerCase().matches("help")) {
                return RequestType.SHOP_HELP;
            }
        }
        else if (state == StateType.MAIN_MENU) {
            switch (command.toLowerCase()) {
                case "login":
                    return RequestType.MAIN_MENU_LOGIN;
                case "create account":
                    return RequestType.MAIN_MENU_SIGN_UP;
                case "show leaderBoard":
                    return RequestType.MAIN_MENU_LEADER_BOARD;
                case "help":
                    return RequestType.MAIN_MENU_HELP;
                case "save":
                    return RequestType.MAIN_MENU_SAVE;

            }

        }
        else if (state == StateType.BATTLE) {
            switch (command.toLowerCase()) {
                case "game Info":
                    return RequestType.GAME_GAME_INFO;
                case "Show my minions":
                    return RequestType.GAME_SHOW_MY_MINION;
                case "show opponent minions":
                    return RequestType.GAME_SHOW_OPPONENT_MINION;
                case "show hand":
                    return RequestType.GAME_SHOW_HAND;
                case "end turn":
                    return RequestType.GAME_END_TURN;
                case "show collectables":
                    return RequestType.GAME_SHOW_COLLECTABLES;
                case "show next card":
                    return RequestType.GAME_SHOW_NEXT_CARD;
                case "enter grave yard":
                    state = StateType.GRAVE_YARD;
                    return RequestType.GAME_ENTER_GRAVE_YARD;
                case "help":
                    return RequestType.GAME_HELP;
                case "end game":
                    return RequestType.GAME_END_GAME;
                case "exit":
                    state = StateType.ACCOUNT_MENU;
                    return RequestType.GAME_EXIT;
                case "show menu":
                    return RequestType.GAME_SHOW_MENU;

            }
            if (command.toLowerCase().matches("select card \\w+")) {//todo get input
                setId(command.substring(12));
                state = StateType.SELECT_CARD;
                return RequestType.GAME_SELECT_CARD_ID;
            }
            if (command.toLowerCase().matches("show card info \\W+")) {
                setId(command.substring(15));
                return RequestType.GAME_SHOW_CARD_INFO;
            }
            if (command.toLowerCase().matches("use special power \\(\\d+,\\d+\\)")) {
                coordinate.setX(Integer.parseInt(command.substring(19, 20)));
                coordinate.setY(Integer.parseInt(command.substring(21, 22)));
                return RequestType.GAME_USE_SPECIAL_POWER;
            }
            if (command.toLowerCase().matches("insert \\w+ in \\(\\d+,\\d+\\)")) {
                setId(command.split(" ")[1]);
                coordinate.setX(Integer.parseInt(command.split(" ")[3].substring(1, 2)));
                coordinate.setY(Integer.parseInt(command.split(" ")[3].substring(3, 4)));
                return RequestType.GAME_INSERT;
            }
            if (command.toLowerCase().matches("select item \\d+")) {
                setId(command.substring(12));
                state = StateType.SELECT_ITEM;
                return RequestType.GAME_SELECT_COLLECTABLE;
            }


        }
        if (state == StateType.GRAVE_YARD) {
            if (command.toLowerCase().matches("show info \\w+")) {
                setId(command.substring(10));
                return RequestType.GAME_GRAVE_YARD_SHOW_INFO;
            }
            if (command.toLowerCase().matches("show cards"))
                return RequestType.GAME_GRAVE_YARD_SHOW_CARDS;
            if (command.toLowerCase().matches("exit"))
                state = StateType.BATTLE;
        }
        if (state == StateType.SELECT_CARD) {
            if (command.toLowerCase().matches("move to \\(\\d+,\\d+\\)")) {
                coordinate.setX(Integer.parseInt(command.substring(9, 10)));
                coordinate.setY(Integer.parseInt(command.substring(11, 12)));
                return RequestType.GAME_MOVE;
            }
            if (command.toLowerCase().matches("attack \\w+ ")) {
                setId(command.substring(7));
                return RequestType.GAME_ATTACK;
            }
            if (command.toLowerCase().matches("attack combo \\w+ (\\w+)+")) {
                //TODO: chejori vorodi bgiram ino???? :))))
                return RequestType.GAME_ATTACK_COMBO;
            }

        }
        if (state == StateType.SELECT_ITEM) {
            if (command.toLowerCase().matches("show info"))
                return RequestType.GAME_ITEM_SHOW_INFO;
            if (command.toLowerCase().matches("use \\(\\d+,\\d+\\)")) {
                coordinate.setX(Integer.parseInt(command.substring(5, 6)));
                coordinate.setY(Integer.parseInt(command.substring(7, 8)));
                return RequestType.GAME_ITEM_USE;
            }

        }
        if (state == StateType.SELECT_MODE) {
            if (command.toLowerCase().matches("single player"))
                return RequestType.MODE_SINGLE_PLAYER;
            if (command.toLowerCase().matches("multi player"))
                return RequestType.MODE_MULTI_PLAYER;
        }
        if (state == StateType.SINGLE_GAME) {
            if (command.toLowerCase().matches("custom game"))
                return RequestType.SINGLE_CUSTOM;
            if (command.toLowerCase().matches("story"))
                return RequestType.SINGLE_STORY;
        }
        return null;
    }
}
