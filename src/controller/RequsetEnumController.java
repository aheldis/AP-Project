package controller;

import model.battle.Deck;
import view.Graphic.GeneralGraphicMethods;

import java.io.IOException;
import java.net.Socket;

public class RequsetEnumController {

    public static void transfer(SocketClass socketClass){
        try {
            socketClass.getOutputStream().writeObject(socketClass.getTransferor());
            socketClass.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(RequestEnum requestEnum, SocketClass socketClass,Transferor clientTransferor){
        Transferor transferor;
        switch (requestEnum){

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
                transferor = socketClass.getTransferor();
                transferor.cards = socketClass.getAccount().getCollection().getAllCards();
                transferor.items = socketClass.getAccount().getCollection().getItems();
                transfer(socketClass);
                break;
            case COLLECTION_DECKS:
                transferor= socketClass.getTransferor();
                transferor.decks=socketClass.getAccount().getDecks();
                transferor.collection = socketClass.getAccount().getCollection();
                transfer(socketClass);
                break;
            case COLLECTION_NEW_DECK:
                break;
            case COLLECTION_EXPORT:
                Deck deck=clientTransferor.deck;
                String path = "exportedDeck/" + socketClass.getAccount().getUserName()
                        + "." + deck.getName() + ".json";
                GeneralGraphicMethods.saveInFile(path, deck);
                break;
            case COLLECTION_IMPORT:
                break;
            case COLLECTION_SELECT_DECK:
                break;
            case COLLECTION_ADD_CARD_TO_DECK:
                break;
            case COLLECTION_SELECT_MAIN_DECK:
                break;
        }
        socketClass.changeTransferor();

    }
}
