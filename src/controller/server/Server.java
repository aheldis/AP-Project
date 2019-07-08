package controller.server;

import controller.DBClass;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.account.Account;
import model.account.AllAccount;
import model.account.Shop;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static view.Graphic.GeneralGraphicMethods.*;


class ServerThread extends Thread {
    private SocketClass socketClass;
    static ArrayList<SocketClass> socketClasses = new ArrayList<>();
    private static int PORT = 8080;

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
    private static int PORT = 8080;
    private static final String baseAddress = "http://127.0.0.1:8080/";


    public static ArrayList<SocketClass> getSockets() {
        return ServerThread.socketClasses;
    }

    static SocketClass getSocketClassByName(String name) {
        for(SocketClass socketClass : ServerThread.socketClasses){
            if(socketClass.getAccount().getUserName().equals(name))
                return socketClass;
        }
        return null;
    }

    public static void main(String[] args) {

        DBClass.makeDB();
//        DBClass.putSocketClass();
//        System.out.println(DBClass.getSocketClass());
       // DBClass.putObject(new Collectible(),"hero");
       // System.out.println(DBClass.getObject("hero"));

        try {
                FileWriter fileWriter = new FileWriter("src/controller/configServer");
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

        private static void addClient (VBox vBox, Account account){
            Group group = new Group();
            vBox.getChildren().addAll(group);
            if (account != null) {
                addRectangle(group, 50, 0, 500, 50, 10, 10, Color.grayRgb(50, 0.5));
                addText(group, 50, 10, account.getUserName()
                        + "   Online:" + AllAccount.getInstance().isOnline(account), Color.BLACK, 30);
            }
        }

        private static Group addButton ( int x, int y, String path, String input){
            Group group = new Group();
            group.relocate(x, y);
            addImage(group, path, 0, 0, 200, 100);
            addTextWithShadow(group, 50, 50, input, "Arial", 30);
            return group;
        }


        private static VBox makeScene (Scene firstScene, Stage primaryStage){
            Group root = new Group();
            ScrollPane scrollPane = new ScrollPane(root);
            setBackground(root, "pics/other/chapter10_preview@2x.jpg", false, 0, 0);
            Scene scene = new Scene(scrollPane, 600, 800);
            primaryStage.setScene(scene);
            VBox vBox = new VBox();
            root.getChildren().addAll(vBox);
            ImageView back = addImage(vBox, "pics/menu/button_back_corner@2x.png", 0, 0, 50, 50);


            back.setOnMouseClicked(event -> Platform.runLater(() -> primaryStage.setScene(firstScene)));

            return vBox;
        }


        private static void makeClientsScene (Scene firstScene, Stage primaryStage){
            VBox vBox = makeScene(firstScene, primaryStage);
            for (int i = 0; i < AllAccount.getInstance().getAccounts().size(); i++) {
                addClient(vBox, AllAccount.getInstance().getAccounts().get(i));
            }


        }

        private static void addShopCard (VBox vBox, String name, String number){
            Group group = new Group();
            vBox.getChildren().addAll(group);
            addRectangle(group, 50, 0, 500, 50, 10, 10, Color.grayRgb(50, 0.5));
            addText(group, 50, 10, name + ":" + number, Color.BLACK, 30);

        }

        private static void makeShopScene (Scene firstScene, Stage primaryStage){
            VBox vBox = makeScene(firstScene, primaryStage);
            ScrollBar scrollBar = new ScrollBar();
            scrollBar.relocate(300, 135);
            HashMap<String, Integer> hm = Shop.getInstance().getRemainingCards();
            Object[] keys = hm.keySet().toArray();
            for (int i = 0; i < hm.size(); i++) {
                addShopCard(vBox, keys[i].toString(), hm.get(keys[i].toString()).toString());
            }
        }

        @Override
        public void start (Stage primaryStage){
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
