package Controller;

import model.Item.Collectable;
import model.Item.Item;
import model.Item.Usable;
import model.account.Account;
import model.account.AllAccount;
import model.account.Collection;
import model.account.Shop;
import model.battle.Game;
import model.battle.GraveYard;
import model.battle.Match;
import model.battle.Player;
import model.card.*;
import model.land.Square;
import model.requirment.Coordinate;
import view.EnterGameMessages;
import view.MenuView;
import view.Request;
import view.enums.ErrorType;
import view.enums.RequestType;
import view.enums.StateType;

import javax.xml.parsers.SAXParser;


public class MenuController {
    private static StateType state = StateType.MAIN_MENU;
    private static Account account;
    private static AllAccount allAccount = AllAccount.getInstance();
    private static MenuView menuView = MenuView.getInstance();

    private static Game game;
    private static Match match;

    public static void main() {
        String id;
        Request request = new Request(state);// mige signUp ya logIn hast
        request.getNewCommand();
        while (state != StateType.END_PROGRAM) {
            Card selectedCard = null;
            Item selectedItem = null;

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
                        menuView.helpForMainMenu();
                        break;
                    case MAIN_MENU_LEADER_BOARD:
                        allAccount.showLeaderBoard();
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
                            game = new Game();
                            if (!game.checkPlayerDeck(account, 1))
                                state = StateType.ACCOUNT_MENU;
                            state = StateType.SELECT_MODE;
                            break;
                        case MENU_ENTER_HELP:
                            MenuView accountMenu = MenuView.getInstance();
                            accountMenu.helpForAccountMenu();
                            break;
                        case MENU_ENTER_SHOP:
                            state = StateType.SHOP;
                            break;
                        case MENU_ENTER_EXIT:
                            state = StateType.END_PROGRAM;
                            break;
                    }
                    request = new Request(state);
                    request.getNewCommand();
                }
            }

            if (state == StateType.COLLECTION) {//todo id ro check konam ke chiye :)
                Collection collection = account.getCollection();
                String deckName;
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
            if (state == StateType.SELECT_MODE) {
                switch (request.getRequestType()) {
                    case MODE_MULTI_PLAYER:
                        int mode = 0;
                        int numberOfFlags = 0;
                        String command;
                        String userName;
                        menuView.showAllAccount();
                        do {
                            menuView.printer("Select user [user name]");
                            request.getNewLine();
                            userName = request.getCommand();
                            if (!allAccount.userNameHaveBeenExist(userName)) {
                                ErrorType error = ErrorType.USER_NAME_NOT_FOUND;
                                menuView.printError(error);
                            }
                        } while (!allAccount.userNameHaveBeenExist(userName));
                        menuView.showModes();
                        do {
                            menuView.printer("Start multiPlayer game [mode] [number of flags] ");
                            request.getNewLine();
                            command = request.getCommand();
                            if (!command.matches("Start multiPlayer game \\d (\\d+)*"))
                                continue;
                            mode = Integer.parseInt(command.split(" ")[3]);
                            if (command.split(" ").length > 4) {
                                numberOfFlags = Integer.parseInt(command.split(" ")[4]);
                            }
                            //todo in chand khat ham duplicate e
                        } while (mode != 0);
                        Account secondPlayerAccount = allAccount.getAccountByName(userName);
                        menuView.printer("Enter your passWord");
                        request.getNewLine();
                        if (secondPlayerAccount.checkPassword(request.getCommand())) {
                            if (!game.checkPlayerDeck(secondPlayerAccount, 2)) {
                                state = StateType.ACCOUNT_MENU;
                                break;
                            }

                            //todo baad gofte be andaze pool e taeen shode vali nagofte pool taeen konim :-?
                            match = game.makeNewMultiGame(mode, numberOfFlags);
                            state = StateType.BATTLE;
                        } else {
                            menuView.printer("Select your mode");
                        }
                        break;
                    case MODE_SINGLE_PLAYER:
                        state = StateType.SINGLE_GAME;
                        break;
                    case MODE_HELP:

                        break;
                }
            }
            if (state == StateType.SINGLE_GAME) {
                switch (request.getRequestType()) {
                    case SINGLE_CUSTOM: {
                        int mode = 0;
                        int numberOfFLags = 0;
                        boolean valid = false;
                        String deckName = null;
                        String command;
                        menuView.showDecksAndModes(account);
                        do {
                            menuView.printer("Enter Start game [deck name] [mode] [number of flags]");
                            request.getNewLine();
                            command = request.getCommand();
                            if (!command.matches("Start game \\w+ \\d+ (\\d)*"))
                                continue;
                            deckName = command.split(" ")[2];
                            mode = Integer.parseInt(command.split(" ")[3]);
                            if (command.split(" ").length > 4)
                                numberOfFLags = Integer.parseInt(command.split(" ")[4]);
                            if (mode != 0 && deckName != null)
                                valid = true;

                        } while (!valid);
                        match = game.makeNewCustomGame(account, deckName, mode, numberOfFLags);
                        if (match == null) {
                            state = StateType.ACCOUNT_MENU;
                        }
                        state = StateType.BATTLE;
                        break;
                    }
                    case SINGLE_STORY: {
                        menuView.showLevelsForStory();
                        int level = 0;
                        do {
                            menuView.printer("Enter the level");
                            request.getNewLine();
                            if (!request.getCommand().matches("\\d"))
                                continue;
                            level = Integer.parseInt(request.getCommand());

                        } while (level > 0 && level < 4);
                        match = game.makeNewStoryGame(level);
                        state = StateType.BATTLE;
                        break;
                    }
                }
            }

            if (state == StateType.BATTLE) {
                ErrorType error;
                Player player = match.passPlayerWithTurn();
                switch (request.getRequestType()) {
                    case GAME_GAME_INFO:
                        menuView.printGameInfo(match);
                        break;
                    case GAME_SHOW_MY_MINION:
                        menuView.showMyMinions(player);
                        break;
                    case GAME_SHOW_OPPONENT_MINION:
                        menuView.showMyMinions(player.getOpponent());
                        break;
                    case GAME_SHOW_HAND:
                        menuView.showHand();
                        break;
                    case GAME_END_TURN:
                        match.changeTurn();
                        break;
                    case GAME_SHOW_COLLECTABLES:
                        menuView.showCollectableItems(player);
                        break;
                    case GAME_SHOW_NEXT_CARD:
                        menuView.showNextCard(player.getHand());
                        break;
                    case GAME_ENTER_GRAVE_YARD:
                        state = StateType.GRAVE_YARD;
                        break;
                    case GAME_HELP:
                        //todo
                        break;
                    case GAME_END_GAME:
                        state = StateType.ACCOUNT_MENU;
                        break;
                    case GAME_SHOW_MENU:
                        menuView.showBattleMenu();
                        break;
                    case GAME_SELECT_CARD_ID:
                        id = request.getId();
                        selectedCard = player.passCardInGame(id);
                        if (selectedCard != null)
                            state = StateType.SELECT_CARD;
                        break;
                    case GAME_ATTACK_COMBO:
                        Player player2 = match.passAnotherPlayerWithOutTurn();
                        id = request.getCommand().split(" ")[1];
                        Card opponentCard = player2.passCardInGame(id);
                        if (opponentCard == null) {
                            error = ErrorType.INVALID_CARD_ID;
                            error.printMessage();
                            return;
                        }
                        String[] cardIds = request.getCommand().split(" ")[2].split(" ");
                        for (String cardId : cardIds) {
                            selectedCard = player.passCardInGame(cardId);
                            if (selectedCard == null) {
                                error = ErrorType.INVALID_CARD_ID;
                                error.printMessage();
                            }else if(selectedCard instanceof Minion ){
                                if(((Minion) selectedCard).getActivationTimeOfSpecialPower()==
                                        ActivationTimeOfSpecialPower.COMBOO)
                                    selectedCard.attack(opponentCard);
                            }
                            else{
                                error= ErrorType.CAN_NOT_COMBO_ATTACK;
                                error.printMessage();
                            }

                        }
                        break;
                    case GAME_SHOW_CARD_INFO:
                        id = request.getId();
                        menuView.showCardInfo(player.passCardInGame(id));
                        break;
                    case GAME_INSERT:
                        id = request.getId();
                        Card card = player.getHand().passCardInHand(id);
                        Coordinate coordinate = request.getCoordinate();
                        if (card == null) {
                            error = ErrorType.INVALID_CARD_ID;
                            error.printMessage();
                        }
                        if (player.getMana() < card.getMp()) {
                            error = ErrorType.HAVE_NOT_ENOUGH_MANA;
                            error.printMessage();
                            break;
                        }
                        player.putCardOnLand(card, coordinate, match.getLand());
                        break;
                    case GAME_SELECT_COLLECTABLE:
                        id = request.getId();
                        selectedItem = player.getHand().passCollectableInHand(id);
                        if (selectedItem == null) {
                            error = ErrorType.INVALID_ITEM;
                            error.printMessage();
                        }
                        state = StateType.SELECT_ITEM;
                        break;
                }

            }
            if (state == StateType.GRAVE_YARD) {
                GraveYard graveYard;
                graveYard = match.passPlayerWithTurn().getGraveYard();
                switch (request.getRequestType()) {

                    case GAME_GRAVE_YARD_SHOW_INFO:

                        request.getNewLine();
                        String command = request.getCommand();

                        Card card = graveYard.cardHaveBeenExistInGraveYard(command);
                        if (card != null) {
                            graveYard.showInfo(card);
                        } else {
                            ErrorType error;
                            error = ErrorType.INVALID_CARD_ID;
                            error.printMessage();
                        }
                        break;
                    case GAME_GRAVE_YARD_SHOW_CARDS:
                        graveYard.showGraveYard();
                        break;
                }

            }

            if (state == StateType.SELECT_ITEM) {
                Player player = match.passPlayerWithTurn();
                id = ((Collectable) selectedItem).getCollectableId().getCollectableIdAsString();
                switch (request.getRequestType()) {
                    case GAME_ITEM_SHOW_INFO:
                        menuView.showItemInfo(player.getHand(), id);
                        break;
                    case GAME_ITEM_USE:
                        Coordinate coordinate = request.getCoordinate();
                        player.putCollectableItemOnLand(coordinate, id);
                        break;
                }

            }
            if (state == StateType.SELECT_CARD) {
                Square square;
                Player player = match.passPlayerWithTurn();
                switch (request.getRequestType()) {

                    case GAME_USE_SPECIAL_POWER:
                        ErrorType error;
                        if (player.getMana() < player.getHero().getMpRequiredForSpell()) {
                            error = ErrorType.NOT_ENOUGH_MANA;
                            error.printMessage();
                            break;
                        }
                        selectedCard.useSpecialPower(request.getCoordinate());
                        break;
                    case GAME_MOVE:
                        if (selectedCard == null) {
                            ErrorType errorType = ErrorType.INVALID_CARD_ID;
                            errorType.printMessage();
                            break;

                        }
                        selectedCard.move(request.getCoordinate());
                        break;
                    case GAME_ATTACK:
                        Card card;
                        square = match.getLand().passSquareInThisCoordinate(request.getCoordinate());
                        id = request.getId();
                        card = player.passCardInGame(id);
                        if (card == null) {
                            ErrorType errorType = ErrorType.INVALID_CARD_ID;
                            errorType.printMessage();
                            break;
                        }
                        selectedCard.attack(card);
                        break;
                }
            }
        }
    }
}
