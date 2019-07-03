package controller.server;

import controller.Transmitter;
import model.account.Account;
import model.battle.Game;
import model.battle.Match;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClass {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private Transmitter transmitter = new Transmitter();
    private Account account;
    private Game game;
    private Match match;

    public SocketClass(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
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
}
