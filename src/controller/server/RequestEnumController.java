package controller;

import com.gilecode.yagson.YaGson;
import javafx.scene.Group;
import model.battle.Deck;
import model.requirment.GeneralLogicMethods;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestEnumController {
    static ArrayList<Group> groupTexts = new ArrayList<>();
    static ArrayList<SocketClass> chatPerson = new ArrayList<>();

    public static void main(RequestEnum requestEnum, SocketClass socketClass, Transmitter clientTransmitter) {
        Transmitter transmitter;
        transmitter = socketClass.getTransmitter();
        switch (requestEnum) {

            case SIGN_UP:
                break;
            case LOGIN:
                break;
            case SHOP_BUY_AND_SELL:
                break;
            case SHOP_BUY:
                break;
            case SHOP_SELL:
                break;
            case SHOP_SEARCH:
                break;


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
                groupTexts.add(clientTransmitter.group);
                for(SocketClass person:chatPerson){
                    person.getTransmitter().group = clientTransmitter.group;
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
