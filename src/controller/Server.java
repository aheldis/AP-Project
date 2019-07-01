package controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<SocketClass> socketClasses = new ArrayList<>();
    private static int PORT =8000;

    public static void main(String[] args){
        SocketClass socketClass;
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket ;
            while (true){
               socket= serverSocket.accept();
               socketClass = new SocketClass(socket);
               new ClientHandlerServer(socketClass).start();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
