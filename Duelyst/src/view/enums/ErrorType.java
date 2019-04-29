package view.enums;

import view.MessageViewer;

public enum ErrorType {
    HAVE_NOT_THIS_CARD_FILE("You have not this card definition"),
    USER_NAME_NOT_FOUND("User name not found"),
    PASSWORD_DOES_NOT_MATCH("Password doesn't match"),
    USER_NAME_ALREADY_EXIST("User name already exist"),

    HAVE_NOT_CARD_IN_COLLECTION("You don't have this Card in collection"),
    HAVE_NOT_ITEM_IN_COLLECTION("You don't have this model.Item in collection"),
    HAVE_NOT_CARD_OR_ITEM_IN_COLLECTION("You don't have this Card or Item in collection"),

    HAVE_NOT_ITEM_IN_DECK("You don't have this model.Item in deck"),
    HAVE_NOT_CARD_IN_DECK("You don't have this model.Item in deck"),
    DECK_HAVE_BEEN_EXIST("Deck Have been exist"),
    HAVE_NOT_DECK("You don't have this deck"),
    CAN_NOT_ADD_CARD("You have 20 cards, you can't add model.card"),
    HAVE_CARD_IN_DECK("model.card have been exist in this deck"),
    HAVE_NOT_HERO_IN_COLLECTION("You don't have this Hero in your collection"),
    HAVE_NOT_HERO_IN_DECK("You don't have this Hero in your DECK"),
    HAVE_HERO_IN_DECK("Hero have been exist in this deck"),
    HAVE_ONE_ITEM_IN_DECK("You have one item in this deck"),

    NO_SUCH_CARD_OR_ITEM_IN_SHOP("There is no such model.card or item in shop"),
    NOT_ENOUGH_MONEY("You don't have enough money to buy this model.card/item"),
    NO_SUCH_CARD_OR_ITEM_IN_COLLECTION("You don't have this model.card or item in your collection"),
    SELECTED_INVALID_DECK("Selected deck is invalid"),
    SELECTED_INVALID_DECK_FOR_PLAYER2("Selected deck for second player is invalid"),
    DONT_HAVE_MAIN_DECK("You don't have a main deck"),
    INVALID_CARD_ID("Invalid card id"),
    NOT_ENOUGH_MANA("You don't have enough mana"),
    INVALID_TARGET("You can not put it here"),
    HAVE_NOT_ENOUGH_MANA("You don't have enough mana"),
    INVALID_ITEM("You don't have this collectable Item"),
    UNAVAILABLE_OPPONENT("Opponent minion is unavailable for attack"),
    CAN_NOT_USE_SPECIAL_POWER("Can not use special power for this card"),
    DO_NOT_HAVE_SPECIAL_POWER("This card doesn't have special power"),
    CAN_NOT_MOVE_IN_SQUARE("You can not move in this square"),
    CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION("This card is exhausted");


    private String message;

    public String getMessage() {
        return message;
    }

    public void printMessage() {
        MessageViewer errorViewer = MessageViewer.getInstance();
        errorViewer.collectionMessage(message);
    }

    ErrorType(String message) {
        this.message = message;
    }
}