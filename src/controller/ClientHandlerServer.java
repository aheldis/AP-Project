package controller;


import java.io.IOException;

public class ClientHandlerServer extends Thread{
    private SocketClass socketClass;
    public ClientHandlerServer(SocketClass socketClass){
        this.socketClass = socketClass;
    }
    public  void run() {
        boolean endOfClient = false;

        new Thread(() -> {
            while (true){
                //todo if login or signUp add autToken to socketClass
                //todo if quit make auth token null


                if(endOfClient) {
                    try {
                        socketClass.getSocket().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }).start();
    }
}
