package controller.client;

import controller.RequestEnum;
import controller.Transmitter;

public class TransferController {
    private static Transmitter fromServerTransmitter;
    private static Transmitter object;
    private static ClientIOhandler clientIOhandler = Client.getClientIOhandler();

    public static Transmitter main(RequestEnum requestEnum, Transmitter transmitter) {
        fromServerTransmitter = transmitter;
        transmitter.requestEnum = requestEnum;
        switch (requestEnum) {
            case SIGN_UP:
            case LOGIN:
            case COLLECTION_DECKS:
            case COLLECTION_CARDS:
            case COLLECTION_ITEMS:
            case COLLECTION_HELP:
            case COLLECTION_SEARCH_CARD:
            case COLLECTION_SEARCH_ITEM:
            case IMPORT_DECK:
            case SHOP_BUY:
            case SHOP_HELP:
            case SHOP_SEARCH:
            case SHOP_CARDS:
            case SHOP_ITEMS:
            case SHOP_DARIC:
            case PROFILE:
            case DECKS:
            case START_STORY_GAME:
            case ENTER_CHAT:
            case START_CUSTOM_GAME:
            case SHOP_SELL:
               fromServerTransmitter = clientIOhandler.transfer(true, transmitter);
                return fromServerTransmitter;
            case LOGOUT:
            case EXPORT_DECK:
            case NEW_DECK:
            case MAIN_DECK:
            case CHAT:
            case NEW_CARD_ID:
            case ENTER_COLLECTION:
            case EXIT_FROM_CHAT:
                fromServerTransmitter = clientIOhandler.transfer(false, transmitter);
                return fromServerTransmitter;
            case CHECK_NEW_MESSAGE:
                /*
                // try {
                Thread one = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            object = (Transmitter) objectInputStream.readObject();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                one.start();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (object != null) {
                    fromServerTransmitter = (Transmitter) object;
                }
                one.stop();
//                } catch (IOException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                return fromServerTransmitter;
                */
        }
        return fromServerTransmitter;
    }

    static void fromServerTransmitter(Transmitter transmitter) {
        System.out.println("transmitter.requestEnum = " + transmitter.requestEnum);
        switch (transmitter.requestEnum){
            case CHECK_NEW_MESSAGE:
        }
    }

}
