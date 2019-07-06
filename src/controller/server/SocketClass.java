package controller.server;

import controller.Transmitter;
import model.account.Account;
import model.battle.Game;
import model.battle.Match;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClass {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private Transmitter transmitter = new Transmitter();
    private Account account;
    private Game game;
    private Match match;
    private ClientHandlerServer clientHandlerServer;
    private PrintWriter out;
    private Scanner in;

    public ClientHandlerServer getClientHandlerServer() {
        return clientHandlerServer;
    }

    public void setClientHandlerServer(ClientHandlerServer clientHandlerServer) {
        this.clientHandlerServer = clientHandlerServer;
    }

    public SocketClass(Socket socket) {
        this.socket = socket;
        try {
//            inputStream = new ObjectInputStream(socket.getInputStream());
//            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void changeTransmitter() {
        transmitter = new Transmitter();
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

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Match setMatch(Match match) {
        this.match = match;
        return match;
    }

    public PrintWriter getOut() {
        return out;
    }

    public Scanner getIn() {
        return in;
    }
}
