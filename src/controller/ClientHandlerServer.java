package controller;


import java.io.IOException;

public class ClientHandlerServer extends Thread {
    private Transmitter transmitter;
    private SocketClass socketClass;

    public ClientHandlerServer(SocketClass socketClass) {
        this.socketClass = socketClass;
        transmitter = socketClass.getTransmitter();
    }

    public void run() {
        boolean endOfClient = false;

        while (true) {
            //todo if login or signUp add autToken to socketClass
            //todo if quit make auth token null
            try {
                if(socketClass ==null || socketClass.getInputStream() == null)
                    break;
                transmitter = (Transmitter) socketClass.getInputStream().readObject();
                if (transmitter != null) {
                    RequsetEnumController.main(transmitter.requestEnum, socketClass, transmitter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (endOfClient) {
                try {
                    socketClass.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
