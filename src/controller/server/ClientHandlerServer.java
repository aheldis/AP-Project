package controller.server;


import com.gilecode.yagson.YaGson;
import controller.Transmitter;

import java.io.IOException;

public class ClientHandlerServer extends Thread {
    public boolean endOfClient = false;
    //  private Transmitter transmitter;
    private SocketClass socketClass;

    public ClientHandlerServer(SocketClass socketClass) {
        this.socketClass = socketClass;
        //  transmitter = socketClass.getTransmitter();
    }

    public void run() {

        while (true) {
            try {
                if (socketClass == null || socketClass.getIn() == null)
                    break;

               /* if (!socketClass.getIn().hasNextLine()) {
                    System.out.println("no next line");
                    continue;
                }*/
                System.out.println("waiting for line");
                String line = socketClass.getIn().readLine();
                System.out.println("get line");
                YaGson mapper = new YaGson();
                Transmitter transmitter = mapper.fromJson(line, Transmitter.class);
                System.out.println("ClientHandlerServer.run");
                //transmitter = (Transmitter) socketClass.getInputStream().readObject();

                if (transmitter != null) {
                    RequestEnumController.main(transmitter.requestEnum, socketClass, transmitter);
                }
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }


            if (endOfClient) {
                try {
                    socketClass.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("???????®");

    }


}
