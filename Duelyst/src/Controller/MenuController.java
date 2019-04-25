package Controller;


import model.Item.Usable;
import model.account.Account;
import model.account.AllAccount;
import model.account.Collection;
import model.account.Shop;
import model.card.Card;
import view.EnterGameMessages;
import view.MenuView;
import view.Request;
import view.enums.ErrorType;
import view.enums.RequestType;
import view.enums.StateType;


public class MenuController {
    private StateType state = StateType.MAIN_MENU; //todo esme in ye chiz dige bashe behtar nist? (svw)
    // todo masalan chi??(zahra)

    private boolean endProgram = false;
    private Account account;
    private AllAccount allAccount = AllAccount.getInstance();

    public void main() {
        Request request = new Request(state);// mige signUp ya logIn hast
        request.getNewCommand();
        while (!endProgram) {

            if (state == StateType.MAIN_MENU) {
                EnterGameMessages enterGameMessages = EnterGameMessages.getInstance();
                switch (request.getRequestType()) {
                    case MAIN_MENU_SIGN_UP:
                        enterGameMessages.showSignUpGetUserName();
                        request.getNewLine();
                        while (allAccount.userNameHaveBeenExist(request.getCommand())) {
                            enterGameMessages.showSignUpHaveUserName();
                            enterGameMessages.showSignUpGetUserName();
                            request.getNewLine();
                        }
                        String username = request.getCommand();
                        enterGameMessages.showSignUpGetPassword();
                        request.getNewLine();
                        allAccount.createAccount(username, request.getCommand());
                        account = allAccount.getAccountByName(username);
                        state = StateType.ACCOUNT_MENU;
                        break;
                    case MAIN_MENU_LOGIN:
                        enterGameMessages.showLoginGetName();
                        request.getNewLine();
                        String userName = request.getCommand();
                        while (!allAccount.userNameHaveBeenExist(userName)) {
                            enterGameMessages.showLoginHaveNotName();
                            enterGameMessages.showLoginGetName();
                            request.getNewLine();
                            userName = request.getCommand();
                        }
                        enterGameMessages.showLoginGetPassword();
                        request.getNewLine();
                        while (!allAccount.passwordMatcher(userName, request.getCommand())) {
                            enterGameMessages.showLoginGetPassword();
                            request.getNewLine();
                        }
                        allAccount.login(userName, request.getCommand());
                        account = allAccount.getAccountByName(userName);
                        state = StateType.ACCOUNT_MENU;
                        break;
                    case MAIN_MENU_HELP:
                        MenuView menuView = MenuView.getInstance();
                        menuView.helpForMainMenu();
                        break;
                    case MAIN_MENU_LEADER_BOARD:
                        AllAccount.getInstance().showLeaderBoard();
                        break;
                    case MAIN_MENU_SAVE:
                        //todo
                        break;
                }
            }

            if (state == StateType.ACCOUNT_MENU) {
                while (request.getRequestType() != RequestType.MENU_ENTER_EXIT) {
                    switch (request.getRequestType()) {
                        case MENU_ENTER_COLLECTION:
                            state = StateType.COLLECTION;
                            break;
                        case MENU_ENTER_BATTLE:
                            state = StateType.BATTLE;
                            // TODO: bayad bebarim dakhele ye bazi
                            break;
                        case MENU_ENTER_HELP:
                            MenuView accountMenu = MenuView.getInstance();
                            accountMenu.helpForAccountMenu();
                            break;
                        case MENU_ENTER_SHOP:
                            state = StateType.SHOP;
                            break;
                        case MENU_ENTER_EXIT:
                            endProgram = true;
                            break;
                    }
                    request = new Request(state);
                    request.getNewCommand();
                }
            }

            if (state == StateType.COLLECTION) {//todo id ro check konam ke chiye :)
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
                            state = StateType.ACCOUNT_MENU;
                            break;
                    }


                    request = new Request(state);
                    request.getNewCommand();

                }
            }

            if (state == StateType.SHOP) {
                Shop shop = Shop.getInstance();
                while (request.getRequestType() != RequestType.SHOP_EXIT) {
                    switch (request.getRequestType()) {
                        case SHOP_SHOW_COLLECTION:
                            account.getCollection().showCardsAndItems();
                            break;
                        case SHOP_SEARCH_COLLECTION_CARD:
                            shop.searchCollection(account, request.getId());
                            break;
                        case SHOP_SEARCH_CARD:
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

            if (state == StateType.BATTLE) {

            }
            if (state == StateType.GRAVE_YARD) {

            }
            if (state == StateType.SELECT_ITEM) {

            }
            if (state == StateType.SELECT_CARD) {

            }


        }

    }

}
