package controller.Controllers;

import controller.Client;
import controller.RequestEnum;
import controller.Transferor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TransferController {
   static Transferor transferor ;
   static ObjectOutputStream objectOutputStream =Client.getObjectOutputStream();
   static ObjectInputStream objectInputStream = Client.getObjectInputStream();

   public static void transfer(boolean waitForAnswer){
       try {
           objectOutputStream.writeObject(transferor);
           objectOutputStream.flush();
           if(waitForAnswer) {
               while (true) {
                   transferor = (Transferor) objectInputStream.readObject();
                   if (transferor != null) {
                       break;
                   }
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    public static Transferor main(OrderEnum order, Transferor transferor){
        TransferController.transferor = transferor;
        switch (order){
            case ENTER_DECK:
                transferor.requestEnum= RequestEnum.COLLECTION_DECKS;
                transfer(true);
                return transferor;
            case ENTER_COLLECTION:
                transferor.requestEnum = RequestEnum.COLLECTION_SHOW;
                transfer(true);
                return transferor;
            case EXPORT_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_EXPORT;
                transfer(false);
                return transferor;
            case IMPORT_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_IMPORT;
                transfer(true);
                return transferor;
            case NEW_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_NEW_DECK;
                transfer(false);
                return transferor;
            case MAIN_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_SELECT_MAIN_DECK;
                transfer(false);
                return transferor;
            case CHAT:
                transferor.requestEnum = RequestEnum.CHAT;
                transfer(false);
                return transferor;
        }
        return transferor;

    }
}
