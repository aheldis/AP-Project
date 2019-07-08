package controller.client;

import view.Graphic.StageLauncher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static Socket socket;
    private static ClientIOHandler clientIOhandler;
    static boolean alive = true;
    public static void main(String[] args) {
        try {
            String host;
            int port;
            try {
                Scanner fileScanner = new Scanner(new File("src/controller/configServer"));
                host = fileScanner.nextLine().split(":")[1];
                port = Integer.parseInt(fileScanner.nextLine().split((":"))[1]);
                fileScanner.close();
            } catch (Exception e) {
                host = HOST;
                port = PORT;
                e.printStackTrace();
            }
            socket = new Socket(host, port);
            System.out.println("Connected to server");

            clientIOhandler = new ClientIOHandler();
            clientIOhandler.setIn(new Scanner(socket.getInputStream()));
            clientIOhandler.setOut(new PrintWriter(socket.getOutputStream(), true));

        } catch (IOException e) {
            e.printStackTrace();
        }

        clientIOhandler.start();

        StageLauncher.main(args);

        //todo at the end close everything

        alive = false;

        try {
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    static ClientIOHandler getClientIOHandler() {
        return clientIOhandler;
    }
}

