package controller.client;

import view.Graphic.StageLauncher;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static Socket socket;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream objectOutputStream;

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return inputStream;
    }

    public static void main(String[] args) {
        String line;
        try {
            try {
                Scanner fileScanner = new Scanner(new File("src/controller/config"));
                socket = new Socket(fileScanner.nextLine().split(":")[1], Integer.parseInt(fileScanner.nextLine().split((":"))[1]));
                fileScanner.close();
            } catch (Exception e) {
                socket = new Socket(HOST, PORT);
                e.printStackTrace();
            }
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StageLauncher.main(args);
        while (true) {
//
//            if(inputStream.hasNextLine()) {
//                line = inputStream.nextLine() ;
//                RequestEnumController.main(line);
//            }

        }
      //  }

        //todo at the end close everything
    }
}
