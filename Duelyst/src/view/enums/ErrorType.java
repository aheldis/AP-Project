package view.enums;

import view.ErrorViewer;

public enum ErrorType {
    USER_NAME_NOT_FOUND("User name not found"),
    PASSWORD_DOES_NOT_MATCH("Password doesn't match"),
    USER_NAME_ALREADY_EXIST("User name already exist"),
    HAVE_NOT_CARD_IN_COLLECTION("You don't have this Card in collection"),
    HAVE_NOT_ITEM_IN_COLLECTION("You don't have this model.Item in collection"),
    HAVE_NOT_ITEM_IN_DECK("You don't have this model.Item in deck"),
    HAVE_NOT_CARD_IN_DECK("You don't have this model.Item in deck"),
    DECK_HAVE_BEEN_EXIST("deck Have been exist"),
    HAVE_NOT_DECK("you don't have this deck"),
    CAN_NOT_ADD_CARD("you have 20 cards, you can't add model.card"),
    HAVE_CARD_IN_DECK("model.card have been exist in this deck"),
    HAVE_NOT_HERO_IN_COLLECTION("you don't have this Hero in your collection"),
    HAVE_NOT_HERO_IN_DECK("you don't have this Hero in your DECK"),
    HAVE_HERO_IN_DECK("Hero have been exist in this deck"),
    HAVE_ONE_ITEM_IN_DECK("you have one item in this deck"),
    NO_SUCH_CARD_OR_ITEM_IN_SHOP("There is no such model.card or item in shop"),
    NOT_ENOUGH_MONEY("You don't have enough money to buy this model.card/item"),
    NO_SUCH_CARD_OR_ITEM_IN_Collection("You don't have this model.card or item in your collection");

    private String message;

    public String getMessage() {
        return message;
    }

    public void printMessage(){
        ErrorViewer errorViewer=ErrorViewer.getInstance();
        errorViewer.collectionError(message);
    }

    ErrorType(String message) {
        this.message = message;
    }
}
