package controller;


import java.io.IOException;

public class ClientHandlerServer extends Thread{
    private Transferor transferor = new Transferor();
    private SocketClass socketClass;

    public ClientHandlerServer(SocketClass socketClass){
        this.socketClass = socketClass;
    }
    public  void run() {
        boolean endOfClient = false;

            while (true){
                //todo if login or signUp add autToken to socketClass
                //todo if quit make auth token null
                try {
                    transferor =(Transferor) socketClass.getInputStream().readObject();
                    if (transferor != null) {
                        RequsetEnumController.main(transferor.requestEnum);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(endOfClient) {
                    try {
                        socketClass.getSocket().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

    }
}
