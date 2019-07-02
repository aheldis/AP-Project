package controller.server;

import com.gilecode.yagson.YaGson;
import controller.RequestEnum;
import controller.SocketClass;
import controller.Transmitter;
import com.google.gson.Gson;
import javafx.scene.Group;
import model.account.AllAccount;
import model.account.Collection;
import model.account.Shop;
import model.battle.Deck;
import model.item.Item;
import model.item.Usable;
import model.requirment.GeneralLogicMethods;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RequestEnumController {
    static ArrayList<Group> groupTexts = new ArrayList<>();
    static ArrayList<SocketClass> chatPerson = new ArrayList<>();

    public static void main(RequestEnum requestEnum, SocketClass socketClass, Transmitter clientTransmitter) {
        Transmitter transmitter;
        transmitter = socketClass.getTransmitter();
        AllAccount allAccount = AllAccount.getInstance();
        switch (requestEnum) {

            case SIGN_UP:
                boolean canSignUp = allAccount.signUp(clientTransmitter.name, clientTransmitter.password);
                if (!canSignUp)
                    transmitter.errorType = ErrorType.USER_NAME_ALREADY_EXIST;
                break;
            case LOGIN:
                if (!allAccount.userNameHaveBeenExist(clientTransmitter.name))
                    transmitter.errorType = ErrorType.USER_NAME_NOT_FOUND;
                else if (!allAccount.passwordMatcher(clientTransmitter.name, clientTransmitter.password))
                    transmitter.errorType = ErrorType.PASSWORD_DOES_NOT_MATCH;
                break;
            case SHOP_BUY_AND_SELL:
                break;
            case SHOP_BUY: {
                Shop.getInstance().buy(socketClass.getAccount(), clientTransmitter.name);
                transmitter.daric = socketClass.getAccount().getDaric();
                transfer(socketClass);
                break;
            }
            case SHOP_SELL: {
                transmitter.errorType = Shop.getInstance().sell(socketClass.getAccount(), clientTransmitter.name);
                transmitter.daric = socketClass.getAccount().getDaric();
                transfer(socketClass);
                break;
            }
            case SHOP_CARDS: {
                transmitter.cards = Shop.getInstance().getCards();
                transfer(socketClass);
                break;
            }
            case SHOP_HELP: {
                transmitter.string = Shop.getInstance().help();
                transfer(socketClass);
                break;
            }
            case SHOP_SEARCH: {
                transmitter.object = Shop.getInstance().search(socketClass.getAccount(), clientTransmitter.name);
                transfer(socketClass);
                break;
            }
            case SHOP_ITEMS:{
                ArrayList<Usable> items = Shop.getInstance().getItems();
                transmitter.items = new ArrayList<>();
                items.forEach(item -> transmitter.items.add(item));
                transfer(socketClass);
                break;
            }


            case COLLECTION_SHOW:
                transmitter.cards = socketClass.getAccount().getCollection().getAllCards();
                transmitter.items = new ArrayList<>(Arrays.asList(socketClass.getAccount().getCollection().getItems()));
                transfer(socketClass);
                break;
            case COLLECTION_DECKS:
                transmitter.decks = socketClass.getAccount().getDecks();
                transmitter.collection = socketClass.getAccount().getCollection();
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
                InputStream input = null;
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
                break;
            case COLLECTION_SELECT_MAIN_DECK:
                socketClass.getAccount().getCollection().selectADeckAsMainDeck(transmitter.deck.getName());
                break;
            case ENTER_CHAT:
                chatPerson.add(socketClass);
                break;
            case CHAT:
//                groupTexts.add(group);
                for(SocketClass person:chatPerson){
                    person.getTransmitter().profile = clientTransmitter.profile;
                    person.getTransmitter().name = clientTransmitter.name;
                    person.getTransmitter().message = clientTransmitter.message;
                    person.getTransmitter().path = clientTransmitter.path;
                    transfer(person);
                }
                break;
        }
        socketClass.changeTransmitter();

    }

    private static void transfer(SocketClass socketClass) {
        try {
            socketClass.getOutputStream().writeObject(socketClass.getTransmitter());
            socketClass.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
