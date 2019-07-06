package controller.client;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.Transmitter;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ClientIOhandler extends Thread {
    final private Object lock = new Object();
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Scanner in;
    private PrintWriter out;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private HashMap<Integer, Transmitter> transmitters = new HashMap<>();

    private int countOfId = 1;

    public void run() {
        while (Client.alive && in.hasNextLine()) {
            try {

                String line = in.nextLine();
                System.out.println("ClientIOhandler.run");
                System.out.println("from server: " + line);
                YaGson mapper = new YaGson();
                Transmitter transmitter = mapper.fromJson(line, Transmitter.class);
                if(transmitter.errorType != null)
                    transmitter.errorType.printMessage();
                //Transmitter transmitter = (Transmitter) objectInputStream.readObject();

                new Thread(() -> {
                    if (transmitter.transmitterId != 0) {
                        transmitters.put(transmitter.transmitterId, transmitter);
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    } else
                        TransferController.fromServerTransmitter(transmitter);
                }).start();

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Transmitter transfer(boolean waitForAnswer, Transmitter clientTransmitter) {
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

            /*
            objectOutputStream.writeObject(clientTransmitter);
            objectOutputStream.flush();
            */
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

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
        this.inputStreamReader = new InputStreamReader(objectInputStream);
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
        this.outputStreamWriter = new OutputStreamWriter(objectOutputStream);
    }
}

