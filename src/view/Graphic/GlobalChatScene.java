package view.Graphic;

import controller.Transmitter;
import controller.client.TransferController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import view.enums.StateType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static controller.server.RequestEnum.*;
import static view.Graphic.GeneralGraphicMethods.*;

public class GlobalChatScene {
    private static Scene chatScene = StageLauncher.getScene(StateType.GLOBAL_CHAT);
    private static Group root = (Group) chatScene.getRoot();
    protected static Insets defaultInset = new Insets(7, 20, 7, 20);


    public static ImageView sendEmoji(String name, Group root, int x, int y, String pathOfPorofile) {
        int size = 180;
        if (!name.matches("\\((\\w+)\\)"))
            return null;

        Circle circle = new Circle(30);
        circle.relocate(20, 30);
        try {
            circle.setFill(new ImagePattern(new Image(new FileInputStream(pathOfPorofile))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        root.getChildren().addAll(circle);

        name = name.substring(1, name.length() - 1);
        ImageView imageView = null;
        switch (name) {
            case "bow":
                imageView = addImage(root, "pics/emoji/bow.png", x, y, size, size);
                break;
            case "happy":
                imageView = addImage(root, "pics/emoji/happy.png", x, y, size, size);
                break;
            case "sad":
                imageView = addImage(root, "pics/emoji/sad.png", x, y, size, size);
                break;
            case "dead":
                imageView = addImage(root, "pics/emoji/dead.png", x, y, size, size);
                break;
            case "confused":
                imageView = addImage(root, "pics/emoji/confused.png", x, y, size, size);
                break;
            case "kiss":
                imageView = addImage(root, "pics/emoji/kiss.png", x, y, size, size);
                break;
            case "sleep":
                imageView = addImage(root, "pics/emoji/sleep.png", x, y, size, size);
                break;
            case "frustrated":
                imageView = addImage(root, "pics/emoji/frustrated.png", x, y, size, size);
                break;
            case "sunglasses":
                imageView = addImage(root, "pics/emoji/sunglasses.png", x, y, size, size);
                break;
            case "surprised":
                imageView = addImage(root, "pics/emoji/surprised.png", x, y, size, size);
                break;
            case "taunt":
                imageView = addImage(root, "pics/emoji/taunt.png", x, y, size, size);
                break;


        }
        return imageView;
    }

    public static void main() {
        Transmitter answer = TransferController.main(ENTER_CHAT, new Transmitter());


        setBackground(root, "pics/menu/world_map@2x.jpg", false, 0, 0);
//        addRectangle(root,0,0,(int)StageLauncher.getWidth(),
//                (int)StageLauncher.getHeight(),0,0,Color.rgb(106,50,200,0.3));

        Group sideGroup = new Group();
        sideGroup.relocate(0, 0);
        root.getChildren().addAll(sideGroup);

        addRectangle(sideGroup, 0, 0, 400, (int) StageLauncher.getHeight(),
                0, 0, Color.rgb(0, 0, 0, 0.5));

        addImage(sideGroup, answer.path, 70, 50, 300, 300);
        addTextWithShadow(sideGroup, 180, 400, answer.name, "Arial", 40);

        addRectangle(sideGroup, 10, 760, 330, 50, 20,
                20, Color.rgb(0, 0, 0, 0.65));
        TextField textField = new TextField();
        textField.setPrefHeight(50);
        textField.relocate(20, 760);
        textField.positionCaret(1);
        textField.setStyle("-fx-text-fill: #a3b2cc; -fx-font-size: 15px; -fx-font-weight: bold;");
        textField.setBackground(new Background(
                new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                        CornerRadii.EMPTY, Insets.EMPTY)));
        sideGroup.getChildren().addAll(textField);
        ImageView send = addImage(sideGroup, "pics/menu/send.png", 345, 760, 50, 50);

        VBox chatGroup = new VBox();
        chatGroup.setSpacing(15);


        chatGroup.setBackground(new Background(new BackgroundFill(
                Color.rgb(5, 5, 5, 0.8),
                CornerRadii.EMPTY, Insets.EMPTY)));
        chatGroup.relocate(500, 10);
        chatGroup.setPrefSize(800, 800);
        root.getChildren().addAll(chatGroup);

        ScrollBar sc = new ScrollBar();
        sc.relocate(1250, 20);
        sc.setBackground(new Background(
                new BackgroundFill(Color.rgb(225, 225, 225, 0.0001),
                        CornerRadii.EMPTY, Insets.EMPTY)));
        sc.setPrefHeight(StageLauncher.getHeight() - 170);
        sc.setVisibleAmount(2);
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        root.getChildren().addAll(sc);
        sc.valueProperty().addListener((ov, old_val, new_val) ->
                chatGroup.setLayoutY(-new_val.doubleValue() * 11));



//       new Thread(new Runnable() {
//           @Override
//           public void run() {
//
//                Transmitter transmitter = TransferController.main(OrderEnum.CHECK_NEW_MEESSAGE, new Transmitter());
//                Group groupText = new Group();
//                groupText.relocate(50, 0);
//                String message = transmitter.message;
//                try {
//                    ByteArrayInputStream bis = new ByteArrayInputStream(transmitter.profile);
//                    BufferedImage bImage = ImageIO.read(bis);
//                    ImageIO.write(bImage, "jpg", new File("output.jpg"));
//
//                    System.out.println("transmitter = " + transmitter.path);
//                groupText=makeMessage(message,groupText, transmitter.name,"output.jpg");
//                chatGroup.getChildren().addAll(groupText);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//    });


        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            String message;
            Group groupText;

            @Override
            public void handle(MouseEvent event) {
                groupText = new Group();
                groupText.relocate(50, 0);
                message = textField.getText();
                textField.clear();

                //groupText = makeMessage(message,groupText,account);

                // chatGroup.getChildren().addAll(groupText);

                sendMessageToServer(message, answer.path, answer.name);
            }
        });

       chatScene.setOnMouseClicked(event -> {
           Transmitter transmitter = TransferController.main(CHECK_NEW_MESSAGE, new Transmitter());
           Group groupText = new Group();
           groupText.relocate(50, 0);
            String message = transmitter.message;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(transmitter.profile);
                BufferedImage bImage = ImageIO.read(bis);
                ImageIO.write(bImage, "jpg", new File("output.jpg"));

                System.out.println("transmitter = " + transmitter.path);
                groupText=makeMessage(message,groupText, transmitter.name,"output.jpg");
                chatGroup.getChildren().addAll(groupText);
            }catch (Exception e){
                e.printStackTrace();
            }
       });

//        AnimationTimer animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                Transmitter transmitter = TransferController.main(OrderEnum.CHECK_NEW_MEESSAGE, new Transmitter());
//                Group groupText = new Group();
//                groupText.relocate(50, 0);
//                String message = transmitter.message;
//                try {
//                    ByteArrayInputStream bis = new ByteArrayInputStream(transmitter.profile);
//                    BufferedImage bImage = ImageIO.read(bis);
//                    ImageIO.write(bImage, "jpg", new File("output.jpg"));
//
//                    System.out.println("transmitter = " + transmitter.path);
//                    groupText=makeMessage(message,groupText, transmitter.name,"output.jpg");
//                    chatGroup.getChildren().addAll(groupText);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        animationTimer.start();

        //todo back send exit from chat delete from server + stop animationimer
    }

    public static void sendMessageToServer(String message, String pathOfProfile, String name) {
        Transmitter transmitter = new Transmitter();
        transmitter.message = message;
        transmitter.path = pathOfProfile;
        try {
            BufferedImage bImage = ImageIO.read(new File(pathOfProfile));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos);
            transmitter.profile = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        transmitter.name = name;
        TransferController.main(CHAT, transmitter);

    }

    public static Group makeMessage(String message, Group groupText, String name,
                                    String pathOfProfile) {

        if (sendEmoji(message, groupText, 50, 50, pathOfProfile) == null) {

            addRectangle(groupText, 100, 10, Math.max(message.length(), name.length()) * 20,
                    90, 20, 20, Color.rgb(0, 0, 0, 0.5));

            Circle circle = new Circle(30);
            circle.relocate(20, 30);
            try {
                circle.setFill(new ImagePattern(new Image(new FileInputStream(pathOfProfile))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            groupText.getChildren().addAll(circle);
            Text text = addText(groupText, 30 + 85, 30, name, Color.ORANGE, 20);
            text.setStrokeWidth(1);
            text.setStroke(Color.rgb(200, 100, 100));
            addText(groupText, 30 + 85, 60, message, Color.WHITE, 20);
        }

        return groupText;

    }
}
