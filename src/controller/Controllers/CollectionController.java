package controller.Controllers;

import controller.Client;
import controller.RequestEnum;
import controller.Transferor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CollectionController {
   static Transferor transferor = new Transferor();
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

    public static Transferor main(CollectionOrder order){
        switch (order){
            case ENTER_DECK:
                transferor.requestEnum= RequestEnum.COLLECTION_DECKS;
                transfer();
                return transferor;
        }
        return transferor;

    }
}
