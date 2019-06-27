package controller;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args){
        try {

            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket ;
            while (true){
               socket= serverSocket.accept();
               ClientHandlerServer.main(socket);




            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
