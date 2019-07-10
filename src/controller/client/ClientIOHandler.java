package controller.client;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.Transmitter;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ClientIOHandler extends Thread {
    final private Object lock = new Object();
    private Scanner in;
    private PrintWriter out;
    private HashMap<Integer, Transmitter> transmitters = new HashMap<>();

    private int countOfId = 1;

    ClientIOHandler() {
    }

    public void run() {
        while (Client.alive && in.hasNextLine()) {
            try {

                String line = in.nextLine();
                YaGson mapper = new YaGson();
                Transmitter transmitter = mapper.fromJson(line, Transmitter.class);
//                System.out.println(transmitter.errorType);
//                System.out.println(transmitter.requestEnum);
//                System.out.println(transmitter.transmitterId);
                if(transmitter.errorType != null)
                    transmitter.errorType.printMessage();
                new Thread(() -> {
                    if (transmitter.transmitterId != 0) {
                        transmitters.put(transmitter.transmitterId, transmitter);
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    } else if(transmitter.requestEnum != null)
                        TransferController.fromServerTransmitter(transmitter);
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    synchronized Transmitter transfer(boolean waitForAnswer, Transmitter clientTransmitter) {
        clientTransmitter.transmitterId = countOfId++;
        Transmitter fromServerTransmitter = null;
        try {
            try {
                YaGson altMapper = new YaGsonBuilder().create();
                String json = altMapper.toJson(clientTransmitter);
                out.println(json);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (waitForAnswer) {
                fromServerTransmitter = getTransmitterFromServer(clientTransmitter.transmitterId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fromServerTransmitter;
    }

    private Transmitter getTransmitterFromServer(int transmitterId) {
        while (!transmitters.containsKey(transmitterId)) {
            synchronized (lock) {
                try {

                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Transmitter transmitter = transmitters.get(transmitterId);
        transmitters.remove(transmitterId);
        return transmitter;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

}

