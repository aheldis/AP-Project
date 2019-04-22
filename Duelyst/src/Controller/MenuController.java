package Controller;


import model.Item.Usable;
import model.account.Account;
import model.account.AllAccount;
import model.account.Collection;
import model.account.Shop;
import model.card.Card;
import view.MenuView;
import view.Request;
import view.enums.EnterGameMessages;
import view.enums.ErrorType;
import view.enums.RequestType;



public class MenuController {
    private String state = "mainMenu"; //todo esme in ye chiz dige bashe behtar nist? (svw)
    private boolean endProgram = false;
    private Account account;

    public void main() {
        Request request = new Request(state);// mige signUp ya logIn hast
        request.getNewCommand();
        while (!endProgram) {

            if (state.equals("mainMenu")) {
                EnterGameMessages enterGameMessages = EnterGameMessages.getInstance();
                if (request.getRequestType() == RequestType.SIGN_UP) {

                    enterGameMessages.showSignUpGetUserName();
                    request.getNewLine();
                    while (AllAccount.userNameHaveBeenExist(request.getCommand())) {
                        enterGameMessages.showSignUpHaveUserName();
                        enterGameMessages.showSignUpGetUserName();
                        request.getNewLine();
                    }
                    String username = request.getCommand();
                    enterGameMessages.showSignUpGetPassword();
                    request.getNewLine();
                    AllAccount.createAccount(username, request.getCommand());
                    account = AllAccount.getAccountByName(username);
                    state = "accountMenu";
                }
                if (request.getRequestType() == RequestType.LOGIN) {
                    enterGameMessages.showLoginGetName();
                    request.getNewLine();
                    String userName = request.getCommand();
                    while (!AllAccount.userNameHaveBeenExist(userName)) {
                        enterGameMessages.showLoginHaveNotName();
                        enterGameMessages.showLoginGetName();
                        request.getNewLine();
                        userName = request.getCommand();
                    }
                    enterGameMessages.showLoginGetPassword();
                    request.getNewLine();
                    while (!AllAccount.passwordMatcher(userName, request.getCommand())) {
                        enterGameMessages.showLoginGetPassword();
                        request.getNewLine();
                    }
                    AllAccount.login(userName, request.getCommand());
                    account = AllAccount.getAccountByName(userName);
                    state = "accountMenu";


                }
            }

            if (state.equals("accountMenu")) {
                while (request.getRequestType() != RequestType.MENU_ENTER_EXIT) {
                    switch (request.getRequestType()) {
                        case MENU_ENTER_COLLECTION:
                            state = "collection";
                            break;
                        case MENU_ENTER_BATTLE:
                            state = "battle";
                            // TODO: bayad bebarim dakhele ye bazi
                            break;
                        case MENU_ENTER_HELP:
                            MenuView accountMenu = MenuView.getInstance();
                            accountMenu.helpForMainMenu();
                            break;
                        case MENU_ENTER_SHOP:
                            state = "shop";
                            break;
                        case MENU_ENTER_EXIT:
                            endProgram = true;
                            break;
                    }
                    request = new Request(state);
                    request.getNewCommand();
                }
            }

            if (state.equals("collection")) {//todo id ro check konam ke chiye :)
                Collection collection = account.getCollection();
                String deckName, id;
                Card card;
                Usable item;
                ErrorType error;
                while (request.getRequestType() != RequestType.COLLECTION_EXIT) {
                    switch (request.getRequestType()) {
                        case COLLECTION_HELP:
                            collection.helpOfCollection();
                            break;
                        case COLLECTION_SHOW:
                            collection.showCardsAndItems();
                            break;
                        case COLLECTION_SHOW_ALL_DECKS:
                            collection.showAlldecks();
                            break;
                        case COLLECTION_SHOW_DECK:
                            deckName = request.getDeckName();
                            collection.showThisDeck(deckName);
                            break;
                        case COLLECTION_ADD_CARD_TO_DECK:
                            deckName = request.getDeckName();
                            id = request.getId();
                            card = collection.passCardByCardId(id);
                            if (card != null) {
                                collection.addCardToThisDeck(card, deckName);
                                break;
                            }
                            item = collection.passUsableItemByUsableItemId(id);
                            if (item != null) {
                                collection.addItemToThisDeck(item, deckName);
                                break;
                            } else {
                                error = ErrorType.HAVE_NOT_CARD_IN_COLLECTION;
                                error.printMessage();
                            }
                            break;
                        case COLLECTION_CREATE_DECK:
                            deckName = request.getDeckName();
                            collection.createDeck(deckName);
                            break;
                        case COLLECTION_DELETE_DECK:
                            deckName = request.getDeckName();
                            collection.deleteDeck(deckName);
                            break;
                        case COLLECTION_REMOVE_CARD_FROM_DECK:
                            deckName = request.getDeckName();
                            id = request.getId();
                            card = collection.passCardByCardId(id);
                            if (card != null) {
                                collection.removeCardFromDeck(card, deckName);
                                break;
                            }
                            item = collection.passUsableItemByUsableItemId(id);
                            if (item != null) {
                                collection.removeItemFromDeck(item, deckName);
                                break;
                            } else {
                                error = ErrorType.HAVE_NOT_CARD_IN_COLLECTION;
                                error.printMessage();
                            }
                            break;
                        case COLLECTION_SEARCH_CARD:
                            id = request.getId();
                            if (collection.searchCardName(id))
                                break;
                            if (collection.searchItemName(id))
                                break;
                            error = ErrorType.HAVE_NOT_CARD_IN_COLLECTION;
                            error.printMessage();
                            break;
                        case COLLECTION_SELECT_DECK:
                            deckName = request.getDeckName();
                            collection.selectADeckAsMainDeck(deckName);
                            break;
                        case COLLECTION_VALIDATE_DECK:
                            deckName = request.getDeckName();
                            collection.validateDeck(deckName);
                            break;
                        case COLLECTION_SAVE:
                            break;
                        case COLLECTION_EXIT:
                            state = "accountMenu";
                            break;
                    }


                    request = new Request(state);
                    request.getNewCommand();

                }
            }

            if (state.equals("shop")) {
                Shop shop = Shop.getInstance();
                while (request.getRequestType() != RequestType.SHOP_EXIT) {
                    switch (request.getRequestType()) {
                        case SHOP_SHOW_COLLECTION:
                            account.getCollection().showCardsAndItems();
                            break;
                        case SHOP_SEARCH_COLLECTION_CARD:
                            shop.searchCollection(account, request.getId());
                            break;
                        case SHOP_SERACH_CARD:
                            shop.search(account, request.getId());
                            break;
                        case SHOP_BUY:
                            shop.buy(account, request.getId());
                            break;
                        case SHOP_SELL:
                            shop.sell(account, request.getId());
                            break;
                        case SHOP_SHOW:
                            shop.show();
                            break;
                        case SHOP_HELP:
                            shop.help();

                    }
                    request = new Request(state);
                    request.getNewCommand();
                }
            }

            if (state.equals("battle")) {

            }


        }

    }

}
