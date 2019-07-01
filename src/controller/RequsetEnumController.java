package controller;

import com.gilecode.yagson.YaGson;
import model.account.Collection;
import model.battle.Deck;
import view.Graphic.GeneralGraphicMethods;
import view.enums.ErrorType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RequsetEnumController {

    public static void transfer(SocketClass socketClass) {
        try {
            socketClass.getOutputStream().writeObject(socketClass.getTransferor());
            socketClass.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(RequestEnum requestEnum, SocketClass socketClass, Transferor clientTransferor) {
        Transferor transferor;
        transferor = socketClass.getTransferor();
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
                transferor.cards = socketClass.getAccount().getCollection().getAllCards();
                transferor.items =new ArrayList<>(Arrays.asList(socketClass.getAccount().getCollection().getItems()));
                transfer(socketClass);
                break;
            case COLLECTION_DECKS:
                transferor.decks = socketClass.getAccount().getDecks();
                transferor.collection = socketClass.getAccount().getCollection();
                transfer(socketClass);
                break;
            case COLLECTION_NEW_DECK:
                socketClass.getAccount().getCollection().createDeck(clientTransferor.deck.getName());
                break;
            case COLLECTION_EXPORT:
                Deck deck = clientTransferor.deck;
                String path = "exportedDeck/" + socketClass.getAccount().getUserName()
                        + "." + deck.getName() + ".json";
                GeneralGraphicMethods.saveInFile(path, deck);
                break;
            case COLLECTION_IMPORT:
                InputStream input = null;
                try {
                    input = new FileInputStream("exportedDeck/"
                            + socketClass.getAccount().getUserName() + "." + clientTransferor.deck.getName() + ".json");
                    Reader reader = new InputStreamReader(input);
                    YaGson mapper = new YaGson();
                    Deck deckImported = mapper.fromJson(reader, Deck.class);//load the deck
                    transferor.deck = deckImported;
                    if (!socketClass.getAccount().getCollection().checkTheDeckForImport(deckImported)) {
                        transferor.errorType =
                                ErrorType.HAVE_NOT_CARDS_IN_COLLECTION_FOR_IMPORT;

                    }
                } catch (FileNotFoundException e) {
                    transferor.errorType=ErrorType.INVALID_NAME_FOR_IMPORTED_DECK;
                }finally {
                    transfer(socketClass);
                }

                break;
            case COLLECTION_ADD_CARD_TO_DECK:
                break;
            case COLLECTION_SELECT_MAIN_DECK:
                socketClass.getAccount().getCollection().selectADeckAsMainDeck(transferor.deck.getName());
                break;
        }
        socketClass.changeTransferor();

    }
}
