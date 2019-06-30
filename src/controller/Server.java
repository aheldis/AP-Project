package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<SocketClass> socketClasses = new ArrayList<>();
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public static void main(String[] args){
        SocketClass socketClass;
        try {

            ServerSocket serverSocket = new ServerSocket(8888);
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
