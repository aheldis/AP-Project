package controller.server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<SocketClass> socketClasses = new ArrayList<>();
    private static int PORT = 8000;

    public static void main(String[] args) {
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("name","myDB");
//        HttpResponse<String> response =DBClass.main(hashMap,"init_DB");
//        System.out.println(response);

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
            while (true) {
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                socketClass = new SocketClass(socket);
                socketClasses.add(socketClass);
                new ClientHandlerServer(socketClass).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
