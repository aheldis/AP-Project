package controller.client;

import controller.OrderEnum;
import controller.RequestEnum;
import controller.Transmitter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TransferController {
    private static Transmitter fromServerTransmitter;
    private static ObjectOutputStream objectOutputStream = Client.getObjectOutputStream();
    private static ObjectInputStream objectInputStream = Client.getObjectInputStream();

    public static Transmitter main(OrderEnum order, Transmitter transmitter) {
        fromServerTransmitter = transmitter;
        switch (order) {
            case SIGN_UP:
                transmitter.requestEnum = RequestEnum.SIGN_UP;
                transfer(true);
                return fromServerTransmitter;
            case LOGIN:
                transmitter.requestEnum = RequestEnum.LOGIN;
                transfer(true);
                return fromServerTransmitter;
            case ENTER_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_DECKS;
                transfer(true);
                return fromServerTransmitter;
            case ENTER_COLLECTION:
                transmitter.requestEnum = RequestEnum.COLLECTION_SHOW;
                transfer(true);
                return fromServerTransmitter;
            case EXPORT_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_EXPORT;
                transfer(false);
                return fromServerTransmitter;
            case IMPORT_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_IMPORT;
                transfer(true);
                return fromServerTransmitter;
            case NEW_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_NEW_DECK;
                transfer(false);
                return fromServerTransmitter;
            case MAIN_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_SELECT_MAIN_DECK;
                transfer(false);
                return fromServerTransmitter;
            case ENTER_CHAT:
                transmitter.requestEnum = RequestEnum.ENTER_CHAT;
                transfer(false);
                return fromServerTransmitter;
            case CHAT:
                transmitter.requestEnum = RequestEnum.CHAT;
                transfer(false);
                return fromServerTransmitter;
            case CHECK_NEW_MESSAGE:
                try {
                    Object object = objectInputStream.readObject();
                    if (object != null) {
                        transmitter.group = ((Transmitter) object).group;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return fromServerTransmitter;
            case SHOP_SELL:{
                //todo gand zadi
                transmitter.requestEnum = RequestEnum.SHOP_SELL;
                transfer(true);
                return fromServerTransmitter;
            }
            case SHOP_BUY:{
                transmitter.requestEnum = RequestEnum.SHOP_BUY;
                transfer(true);
                break;
            }
            case SHOP_HELP:{
                transmitter.requestEnum = RequestEnum.SHOP_HELP;
                transfer(true);
                break;
            }
            case SHOP_SEARCH:{
                transmitter.requestEnum = RequestEnum.SHOP_SEARCH;
                transfer(true);
                break;
            }
            case SHOP_CARDS:{
                transmitter.requestEnum= RequestEnum.SHOP_CARDS;
                transfer(true);
                break;
            }
            case SHOP_ITEMS:{
                transmitter.requestEnum = RequestEnum.SHOP_ITEMS;
                transfer(true);
                break;
            }
        }
        return fromServerTransmitter;

    }

    private static void transfer(boolean waitForAnswer) {
        try {
            objectOutputStream.writeObject(fromServerTransmitter);
            objectOutputStream.flush();
            if (waitForAnswer) {
                do {
                    fromServerTransmitter = (Transmitter) objectInputStream.readObject();
                } while (fromServerTransmitter == null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
