package controller;

import model.account.Account;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClass {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private Transferor transferor =new Transferor();
    private Account account ;
    private String authToken;

    public SocketClass(Socket socket){
        this.socket= socket;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return objectOutputStream;
    }

    public Transferor getTransferor() {
        return transferor;
    }
    public void changeTransferor(){
        transferor = new Transferor();
    }

    public Account getAccount() {
        return account;
    }
}
