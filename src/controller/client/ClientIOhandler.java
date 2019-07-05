package controller.client;

import controller.Transmitter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ClientIOhandler extends Thread {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private HashMap<Integer, Transmitter> transmitters = new HashMap<>();
    //private ArrayList<Transmitter> transmitters = new ArrayList<>();
    private int countOfId = 1;
    final private Object lock = new Object();

    public void run() {
        while (Client.alive) {
            try {
                Transmitter transmitter = (Transmitter) objectInputStream.readObject();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (transmitter.transmitterId != 0) {
                            transmitters.put(transmitter.transmitterId, transmitter);
                            synchronized (lock) {
                                lock.notifyAll();
                            }
                        } else
                            TransferController.fromServerTransmitter(transmitter);
                    }
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
            objectOutputStream.writeObject(clientTransmitter);
            objectOutputStream.flush();
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

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
}

