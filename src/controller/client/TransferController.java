package controller.client;

import controller.OrderEnum;
import controller.RequestEnum;
import controller.Transmitter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TransferController {
    private static Transmitter transmitter;
    private static ObjectOutputStream objectOutputStream = Client.getObjectOutputStream();
    private static ObjectInputStream objectInputStream = Client.getObjectInputStream();

    public static Transmitter main(OrderEnum order, Transmitter transmitter) {
        TransferController.transmitter = transmitter;
        switch (order) {
            case ENTER_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_DECKS;
                transfer(true);
                return transmitter;
            case ENTER_COLLECTION:
                transmitter.requestEnum = RequestEnum.COLLECTION_SHOW;
                transfer(true);
                return transmitter;
            case EXPORT_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_EXPORT;
                transfer(false);
                return transmitter;
            case IMPORT_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_IMPORT;
                transfer(true);
                return transmitter;
            case NEW_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_NEW_DECK;
                transfer(false);
                return transmitter;
            case MAIN_DECK:
                transmitter.requestEnum = RequestEnum.COLLECTION_SELECT_MAIN_DECK;
                transfer(false);
                return transmitter;
            case ENTER_CHAT:
                transmitter.requestEnum = RequestEnum.ENTER_CHAT;
                transfer(false);
                return transmitter;
            case CHAT:
                transmitter.requestEnum = RequestEnum.CHAT;
                transfer(false);
                return transmitter;
            case CHECK_NEW_MASSAGE:
                try {
                    Object object =  objectInputStream.readObject();
                    if(object!=null){
                        transmitter.group=((Transmitter )object).group;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return transmitter;
        }
        return transmitter;

    }

    private static void transfer(boolean waitForAnswer) {
        try {
            objectOutputStream.writeObject(transmitter);
            objectOutputStream.flush();
            if (waitForAnswer) {
                do {
                    transmitter = (Transmitter) objectInputStream.readObject();
                } while (transmitter == null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
