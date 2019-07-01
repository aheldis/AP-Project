package controller.Controllers;

import controller.Client;
import controller.RequestEnum;
import controller.Transferor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CollectionController {
   static Transferor transferor ;
   static ObjectOutputStream objectOutputStream =Client.getObjectOutputStream();
   static ObjectInputStream objectInputStream = Client.getObjectInputStream();

   public static void transfer(){
       try {
           objectOutputStream.writeObject(transferor);
           objectOutputStream.flush();
           while (true){
               transferor =(Transferor) objectInputStream.readObject();
               if(transferor!=null){
                   break;
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    public static Transferor main(CollectionOrder order,Transferor transferor){
        CollectionController.transferor = transferor;
        switch (order){
            case ENTER_DECK:
                transferor.requestEnum= RequestEnum.COLLECTION_DECKS;
                transfer();
                return transferor;
            case ENTER_COLLECTION:
                transferor.requestEnum = RequestEnum.COLLECTION_SHOW;
                transfer();
                return transferor;
            case EXPORT_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_EXPORT;
                transfer();
                return transferor;
            case IMPORT_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_IMPORT;
                transfer();
                return transferor;
            case NEW_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_NEW_DECK;
                transfer();
                return transferor;
            case MAIN_DECK:
                transferor.requestEnum = RequestEnum.COLLECTION_SELECT_MAIN_DECK;
                transfer();
                return transferor;
        }
        return transferor;

    }
}
