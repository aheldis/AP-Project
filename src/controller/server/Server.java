package controller.server;

import controller.client.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.Graphic.GeneralGraphicMethods;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;


class ServerThread extends Thread {
    private SocketClass socketClass;
    public static ArrayList<SocketClass> socketClasses = new ArrayList<>();
    private static int PORT = 8000;

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket;
            ClientHandlerServer clientHandlerServer;
            while (true) {
                socket = serverSocket.accept();
                socketClass = new SocketClass(socket);
                socketClasses.add(socketClass);
                clientHandlerServer = new ClientHandlerServer(socketClass);
                socketClass.setClientHandlerServer(clientHandlerServer);
                clientHandlerServer.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Server extends Application {
    static ServerThread serverThread;
    private static int PORT = 8000;

    public static void main(String[] args) {
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("name","myDB");
//        HttpResponse<String> response =DBClass.main(hashMap,"init_DB");
//        System.out.println(response);


        try {
            FileWriter fileWriter = new FileWriter("src/controller/config");
            fileWriter.write("ip:" + "127.0.0.1" + "\n" + "port:" + PORT);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverThread = new ServerThread();
        serverThread.start();
        launch(args);
        serverThread.stop();

    }

    private static void addClient(VBox vBox, SocketClass socketClass) {
        Group group = new Group();
        vBox.getChildren().addAll(group);
        addRectangle(group, 50, 0, 500, 50, 10, 10, Color.GRAY);
        if (socketClass.getAccount() != null)
            addText(group, 50, 10, socketClass.getAccount().getUserName(), Color.BLACK, 30);

    }

    public static Group addButton(int x, int y, String path, String input) {
        Group group = new Group();
        group.relocate(x, y);
        addImage(group, path, 0, 0, 200, 100);
        addTextWithShadow(group, 50, 50, input, "Arial", 30);
        return group;
    }

    private static Scene makeClientsScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 800);
        VBox vBox = new VBox();
        root.getChildren().addAll(vBox);
        for (int i = 0; i < ServerThread.socketClasses.size(); i++) {
            addClient(vBox, ServerThread.socketClasses.get(i));
        }
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 800);

        setBackground(root, "pics/other/chapter10_preview@2x.jpg", false, 0, 0);

        Group clients = addButton(70, 100, "pics/other/button_secondary_glow@2x.png", "Clients");
        Group shop = addButton(70, 200, "pics/other/button_secondary_glow@2x.png", "Shop");
        root.getChildren().addAll(clients, shop);

        clients.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setScene(makeClientsScene());
            }
        });
        shop.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                serverThread.stop();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
