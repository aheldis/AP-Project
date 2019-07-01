package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<SocketClass> socketClasses = new ArrayList<>();
    private static int PORT = 8000;

    public static void main(String[] args) {
        SocketClass socketClass;
        try {
            FileWriter fileWriter = new FileWriter("src/controller/config");
            fileWriter.write("ip:" + "127.0.0.1" + "\n" + "port:" + PORT);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                socketClass = new SocketClass(socket);
                socketClasses.add(socketClass);
                new ClientHandlerServer(socketClass).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
