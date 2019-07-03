package controller.client;

import controller.server.RequestEnum;
import controller.Transmitter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TransferController {
    private static Transmitter fromServerTransmitter;
    private static ObjectOutputStream objectOutputStream = Client.getObjectOutputStream();
    private static ObjectInputStream objectInputStream = Client.getObjectInputStream();

    public static Transmitter main(RequestEnum requestEnum, Transmitter transmitter) {
        fromServerTransmitter = transmitter;
        transmitter.requestEnum = requestEnum;
        switch (requestEnum) {
            case SIGN_UP:
            case LOGIN:
            case COLLECTION_DECKS:
            case ENTER_COLLECTION:
            case IMPORT_DECK:
            case SHOP_BUY:
            case SHOP_HELP:
            case SHOP_SEARCH:
            case SHOP_CARDS:
            case SHOP_ITEMS:
            case SHOP_DARIC:
            case PROFILE:
                transfer(true);
                return fromServerTransmitter;
            case LOGOUT:
            case EXPORT_DECK:
            case NEW_DECK:
            case MAIN_DECK:
            case ENTER_CHAT:
            case CHAT:
            case NEW_CARDID:
                transfer(false);
                return fromServerTransmitter;
            case CHECK_NEW_MESSAGE:
                try {
                    Object object = objectInputStream.readObject();
                    if (object != null) {
                        transmitter = (Transmitter) object;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return fromServerTransmitter;
            case EXIT_FROM_CHAT:
                transmitter.requestEnum = RequestEnum.EXIT_FROM_CHAT;
                transfer(false);
                return fromServerTransmitter;
            case SHOP_SELL: {
                transmitter.requestEnum = RequestEnum.SHOP_SELL;
                transfer(true);
                return fromServerTransmitter;
            }
        }

        return fromServerTransmitter;

    }

        private static void transfer ( boolean waitForAnswer){
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
