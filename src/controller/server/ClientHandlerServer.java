package controller.server;


import controller.Transmitter;

public class ClientHandlerServer extends Thread {
    private Transmitter transmitter;
    private SocketClass socketClass;

    public ClientHandlerServer(SocketClass socketClass) {
        this.socketClass = socketClass;
        transmitter = socketClass.getTransmitter();
    }

    public void run() {

        while (true) {
            try {
                if (socketClass == null || socketClass.getInputStream() == null)
                    break;
                transmitter = (Transmitter) socketClass.getInputStream().readObject();
                if (transmitter != null) {
                    RequestEnumController.main(transmitter.requestEnum, socketClass, transmitter);
                }
                Thread.sleep(10);
            } catch (Exception e) {
                break;
            }
        }
    }
}
