package controller.server;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.RequestEnum;
import controller.Transmitter;
import javafx.scene.Group;
import model.account.Account;
import model.account.AllAccount;
import model.account.Shop;
import model.battle.Deck;
import model.battle.Game;
import model.card.Card;
import model.card.CardId;
import model.item.Usable;
import model.requirment.GeneralLogicMethods;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestEnumController {
    static ArrayList<Group> groupTexts = new ArrayList<>();
    private static ArrayList<SocketClass> chatPerson = new ArrayList<>();

    public static void main(RequestEnum requestEnum, SocketClass socketClass, Transmitter clientTransmitter) {
        Transmitter transmitter = socketClass.getTransmitter();
        transmitter.transmitterId = clientTransmitter.transmitterId;
        AllAccount allAccount = AllAccount.getInstance();
        Account account = socketClass.getAccount();
        switch (requestEnum) {

            case END_OF_CLIENT:
                socketClass.getClientHandlerServer().stop();
                break;
            case SIGN_UP:
                boolean canSignUp = allAccount.signUp(clientTransmitter.name, clientTransmitter.password);
                if (!canSignUp)
                    transmitter.errorType = ErrorType.USER_NAME_ALREADY_EXIST;
                else {
                    socketClass.setAccount(allAccount.getAccountByName(clientTransmitter.name));
                    socketClass.getAccount().setAuthToken(AllAccount.getInstance().getAuthToken(socketClass.getAccount()));
                }
                transfer(socketClass);
                break;
            case LOGIN:
                if (!allAccount.userNameHaveBeenExist(clientTransmitter.name))
                    transmitter.errorType = ErrorType.USER_NAME_NOT_FOUND;
                else if (!allAccount.passwordMatcher(clientTransmitter.name, clientTransmitter.password))
                    transmitter.errorType = ErrorType.PASSWORD_DOES_NOT_MATCH;
                else {
                    socketClass.setAccount(allAccount.getAccountByName(clientTransmitter.name));
                    socketClass.getAccount().setAuthToken(AllAccount.getInstance().getAuthToken(socketClass.getAccount()));
                }
                transfer(socketClass);
                break;
            case LOGOUT:
                AllAccount.getInstance().saveAccount(socketClass.getAccount());
                socketClass.getAccount().setAuthToken(null);
                socketClass.setAccount(null);
                break;
            case PROFILE:
                transmitter.path = socketClass.getAccount().getAccountImagePath();
                transmitter.name = socketClass.getAccount().getUserName();
                transmitter.matchInfos = socketClass.getAccount().getMatchHistory();
                transfer(socketClass);
                break;
            case SHOP_BUY_AND_SELL:
                break;
            case SHOP_BUY:
                Shop.getInstance().buy(socketClass.getAccount(), clientTransmitter.name);
                transmitter.daric = socketClass.getAccount().getDaric();
                transfer(socketClass);
                break;
            case SHOP_SELL:
                transmitter.errorType = Shop.getInstance().sell(socketClass.getAccount(), clientTransmitter.name);
                transmitter.daric = socketClass.getAccount().getDaric();
                transfer(socketClass);
                break;
            case SHOP_CARDS:
                transmitter.cards = Shop.getInstance().getCards();
                transfer(socketClass);
                break;
            case SHOP_HELP:
                transmitter.string = Shop.getInstance().help();
                transfer(socketClass);
                break;
            case SHOP_SEARCH:
                transmitter.object = Shop.getInstance().search(socketClass.getAccount(), clientTransmitter.name);
                transmitter.name = socketClass.getAccount().getUserName();
                transmitter.collection = socketClass.getAccount().getCollection();
                transfer(socketClass);
                break;
            case SHOP_ITEMS:
                ArrayList<Usable> items = Shop.getInstance().getItems();
                transmitter.items = new ArrayList<>();
                transmitter.items.addAll(items);
                transfer(socketClass);
                break;
            case SHOP_DARIC:
                transmitter.daric = socketClass.getAccount().getDaric();
                transfer(socketClass);
                break;


            case COLLECTION_SHOW:
                transmitter.cards = socketClass.getAccount().getCollection().getAllCards();
                transmitter.items = new ArrayList<>(Arrays.asList(socketClass.getAccount().getCollection().getItems()));
                transfer(socketClass);
                break;
            case COLLECTION_DECKS:
                transmitter.decks = socketClass.getAccount().getDecks();
                transmitter.collection = socketClass.getAccount().getCollection();
                for (Card card : transmitter.collection.getAllCards())
                    System.out.println(card.getName() + " " + card.getCardId());
                transfer(socketClass);
                break;
            case COLLECTION_NEW_DECK:
                socketClass.getAccount().getCollection().createDeck(clientTransmitter.deck.getName());
                break;
            case COLLECTION_EXPORT:
                Deck deck = clientTransmitter.deck;
                String path = "exportedDeck/" + socketClass.getAccount().getUserName()
                        + "." + deck.getName() + ".json";
                GeneralLogicMethods.saveInFile(path, deck);
                break;
            case COLLECTION_IMPORT:
                InputStream input;
                try {
                    input = new FileInputStream("exportedDeck/"
                            + socketClass.getAccount().getUserName() + "." + clientTransmitter.deck.getName() + ".json");
                    Reader reader = new InputStreamReader(input);
                    YaGson mapper = new YaGson();
                    Deck deckImported = mapper.fromJson(reader, Deck.class);//load the deck
                    transmitter.deck = deckImported;
                    if (!socketClass.getAccount().getCollection().checkTheDeckForImport(deckImported)) {
                        transmitter.errorType =
                                ErrorType.HAVE_NOT_CARDS_IN_COLLECTION_FOR_IMPORT;

                    }
                } catch (FileNotFoundException e) {
                    transmitter.errorType = ErrorType.INVALID_NAME_FOR_IMPORTED_DECK;
                } finally {
                    transfer(socketClass);
                }

                break;
            case COLLECTION_ADD_CARD_TO_DECK:
                //todo
                break;
            case COLLECTION_SELECT_MAIN_DECK:
                socketClass.getAccount().getCollection().selectADeckAsMainDeck(transmitter.deck.getName());
                break;
            case COLLECTION_CARDS:
                transmitter.cards = account.getCollection().getAllCards();
                transfer(socketClass);
                break;
            case COLLECTION_ITEMS:
                transmitter.items = new ArrayList<>(Arrays.asList(account.getCollection().getItems()));
                transfer(socketClass);
                break;
            case COLLECTION_HELP:
                transmitter.string = account.getCollection().helpOfCollection();
                transfer(socketClass);
                break;
            case COLLECTION_SEARCH_ITEM:
                transmitter.ids = account.getCollection().searchItemName(clientTransmitter.name);
                transfer(socketClass);
                break;
            case COLLECTION_SEARCH_CARD:
                transmitter.ids = account.getCollection().searchCardName(clientTransmitter.name);
                transfer(socketClass);
                break;
            case ENTER_CHAT:
                chatPerson.add(socketClass);
                account = socketClass.getAccount();
                transmitter.name = account.getUserName();
                transmitter.path = account.getAccountImagePath();
                transfer(socketClass);
                break;
            case NEW_CARD_ID:
                account = socketClass.getAccount();
                Card card = transmitter.card;
                new CardId(account, card, account.getCollection().getNumberOfCardId(card));
                account.getCollection().addToCards(card);
                break;
            case CHAT:
//                groupTexts.add(group);
                for (SocketClass person : chatPerson) {
                    person.getTransmitter().profile = clientTransmitter.profile;
                    person.getTransmitter().name = clientTransmitter.name;
                    person.getTransmitter().message = clientTransmitter.message;
                    person.getTransmitter().path = clientTransmitter.path;
                    transfer(person);
                }
                break;
            case DECKS:
                transmitter.decks = account.getDecks();
                transfer(socketClass);
                break;
            case START_STORY_GAME: {
                Game game = new Game();
                socketClass.setGame(game);
                ErrorType errorType = game.checkPlayerDeck(account, clientTransmitter.playerNumber);
                if (errorType == null) {
                    transmitter.match = socketClass.setMatch(game.makeNewStoryGame(clientTransmitter.level));
                    transmitter.game = game;
                }
                transfer(socketClass);
                break;
            }
            case EXIT_FROM_CHAT:
                chatPerson.remove(socketClass);
                break;
            case START_CUSTOM_GAME: { /*Custom Game*/
                Game game = new Game();
                socketClass.setGame(game);
                ErrorType errorType = game.checkPlayerDeck(account, clientTransmitter.playerNumber);
                if (errorType == null) {
                    transmitter.match = socketClass.setMatch(
                            game.makeNewCustomGame(account, clientTransmitter.name,
                                    clientTransmitter.mode, clientTransmitter.numberOfFlag));
                    transmitter.game = game;
                }
                transfer(socketClass);
                break;
            }
        }
        socketClass.changeTransmitter();

    }

    private static void transfer(SocketClass socketClass) {
        try {
            YaGson altMapper = new YaGsonBuilder().create();
            String json = altMapper.toJson(socketClass.getTransmitter());
            socketClass.getOut().println(json);
            socketClass.getOut().flush();
            /*
            YaGson altMapper = new YaGsonBuilder().create();
            String json = altMapper.toJson(socketClass.getTransmitter());
            socketClass.getOut().print(json);
            socketClass.getOut().flush();
            */
            System.out.println("RequestEnumController.transfer");
            //System.out.println("to client " + json);

            /*
            socketClass.getOutputStream().writeObject(socketClass.getTransmitter());
            socketClass.getOutputStream().flush();
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
