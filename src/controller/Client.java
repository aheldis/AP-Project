package controller;

import view.Graphic.StageLauncher;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static final String HOST="127.0.0.1";
    private static final int PORT = 8000;
    private static ObjectInputStream inputStream ;
    private static ObjectOutputStream objectOutputStream;

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return inputStream;
    }

    public static void main(String[] args){
        String line;
        try {
            socket = new Socket(HOST,PORT);
            inputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StageLauncher.main(args);
        while (true){
//
//            if(inputStream.hasNextLine()) {
//                line = inputStream.nextLine() ;
//                RequsetEnumController.main(line);
//            }


        }

        //todo at the end close everything
    }
}
