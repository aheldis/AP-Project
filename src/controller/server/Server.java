package controller.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static view.Graphic.GeneralGraphicMethods.*;


class ServerThread extends Thread {
    private SocketClass socketClass;
    static ArrayList<SocketClass> socketClasses = new ArrayList<>();
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

    private static Group addButton(int x, int y, String path, String input) {
        Group group = new Group();
        group.relocate(x, y);
        addImage(group, path, 0, 0, 200, 100);
        addTextWithShadow(group, 50, 50, input, "Arial", 30);
        return group;
    }


    private static VBox makeScene(Scene firstScene, Stage primaryStage) {
        Group root = new Group();
        setBackground(root, "pics/other/chapter10_preview@2x.jpg", false, 0, 0);
        Scene scene = new Scene(root, 600, 800);
        primaryStage.setScene(scene);
        VBox vBox = new VBox();
        root.getChildren().addAll(vBox);
        ImageView back = addImage(vBox, "pics/menu/button_back_corner@2x.png", 0, 0, 50, 50);


        back.setOnMouseClicked(event -> Platform.runLater(() -> primaryStage.setScene(firstScene)));

        return vBox;
    }


    private static void makeClientsScene(Scene firstScene, Stage primaryStage) {
        VBox vBox = makeScene(firstScene, primaryStage);
        for (int i = 0; i < ServerThread.socketClasses.size(); i++) {
            addClient(vBox, ServerThread.socketClasses.get(i));
        }


    }

    private static void makeShopScene(Scene firstScene, Stage primaryStage) {
        VBox vBox = makeScene(firstScene, primaryStage);

    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 800);

        setBackground(root, "pics/other/chapter10_preview@2x.jpg", false, 0, 0);

        Group clients = addButton(70, 100, "pics/other/button_secondary_glow@2x.png", "Clients");
        Group shop = addButton(70, 200, "pics/other/button_secondary_glow@2x.png", "Shop");
        root.getChildren().addAll(clients, shop);

        clients.setOnMouseClicked(event -> makeClientsScene(scene, primaryStage));
        shop.setOnMouseClicked(event -> makeShopScene(scene, primaryStage));


        primaryStage.setOnCloseRequest(event -> serverThread.stop());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
