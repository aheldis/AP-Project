package view.Graphic;

import com.gilecode.yagson.YaGson;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.account.FilesType;
import model.account.Shop;
import model.battle.Match;
import model.card.Card;
import model.card.Minion;
import model.land.LandOfGame;
import view.enums.StateType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BattleScene {
    private static BattleScene singleInstance = null;
    private Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
    private Group root = (Group) battleScene.getRoot();
    private Group board = null; //!!!! Har chi roo board gharare bashe be in add she
    private double width = StageLauncher.getWidth();
    private double height = StageLauncher.getHeight();
    private int numberOfMap;
    private Rectangle[][] gameGrid;
    private MapProperties mapProperties;
    private Match match;
    private BattleHeaderGraphic battleHeader;
    private BattleFooterGraphic battleFooter;

    private BattleScene() {
    }

    public static BattleScene getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new BattleScene();
        return singleInstance;
    }

    public void setBattleScene(int numberOfMap) {
        root.getChildren().clear();
        this.numberOfMap = numberOfMap;
        setMapProperties();
        setMapBackground();
        GeneralGraphicMethods.playMusic("resource/music/battle_music/" + numberOfMap + ".m4a", true, battleScene);
        addGrid();
        battleHeader = new BattleHeaderGraphic(root);
        StageLauncher.testzahraFooter(root);
        // battleFooter = new BattleFooterGraphic(root,match.getPlayers()[0]);
        battleHeader.test();
    }

    private void setMapProperties() {
        String path = "pics/maps_categorized/map" + numberOfMap + "/property.json";
        YaGson yaGson = new YaGson();
        try {
            mapProperties = yaGson.fromJson(new FileReader(path), MapProperties.class);
            mapProperties.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setMapBackground() {
        //System.out.println("numberOfMap = " + numberOfMap);
        String pathOfFile = "pics/maps_categorized/map" + numberOfMap + "/background";
        File file = new File(pathOfFile);
        File[] files = file.listFiles();
        if (files != null) {
            Arrays.sort(files);
            for (File file1 : files) {
                //System.out.println("file1.getName() = " + file1.getName());
                ImageView imageView = GeneralGraphicMethods.setBackground(root, file1.getPath(), false, 0, 0);
                imageView.setOnMouseClicked(event -> System.out.println(event.getX() + " " + event.getY()));
                if (file1.getName().contains("middleground") || file1.getName().contains("midground")) {
                    //todo duration ya ye chiz dige
                    moveBackgrounds(imageView, false, false);
                }
                if (file1.getName().contains("foreground")) {
                    moveBackgrounds(imageView, false, true);
                }
            }
        }
    }

    private void moveBackgrounds(ImageView imageView, boolean horizontal, boolean vertical) {
        int randomNumber = (new Random().nextInt(3)) - 1;
        if (randomNumber == 0) randomNumber = 1;
        if (vertical)
            randomNumber *= 15;
        else
            randomNumber *= 10;
        if (vertical && (imageView.getX() - randomNumber > width || imageView.getX() - randomNumber < 0))
            randomNumber *= -1;
        if (horizontal && (imageView.getY() - randomNumber > height || imageView.getY() - randomNumber < 0))
            randomNumber *= -1;

        final int moveDistance = randomNumber;

        imageView.setOnMouseEntered(event -> {
            double primaryX = imageView.getX();
            double primaryY = imageView.getY();
            if (vertical)
                imageView.setX(primaryX + moveDistance);
            if (horizontal) {
                imageView.setY(primaryY + moveDistance);
            }
        });
        imageView.setOnMouseExited(event -> {
            double primaryX = imageView.getX();
            double primaryY = imageView.getY();
            if (vertical)
                imageView.setX(primaryX - moveDistance);
            if (horizontal)
                imageView.setY(primaryY - moveDistance);
        });
    }

    public Rectangle getCell(int row, int column) {
        return gameGrid[row][column];
    }

    public Pair<Double, Double> getCellPosition(int row, int column) {
        return new Pair<>(gameGrid[row][column].getLayoutX(), gameGrid[row][column].getLayoutY());
    }

    private void addGrid() {
        board = new Group();
        int numberOfColumns = LandOfGame.getNumberOfColumns();
        int numberOfRows = LandOfGame.getNumberOfRows();
        double primaryX = (mapProperties.ulx + mapProperties.llx) / 2, primaryY = mapProperties.uly;
        double currentX = primaryX, currentY = primaryY;

        System.out.println("mapProperties.cellHeight = " + mapProperties.cellHeight);
        System.out.println("mapProperties = " + mapProperties.cellHeight);
        gameGrid = new Rectangle[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++)
            for (int j = 0; j < numberOfColumns; j++) {
                if (j == 0) {
                    currentY += mapProperties.cellHeight + mapProperties.gap;
                    currentX = primaryX;
                } else
                    currentX += mapProperties.cellWidth + mapProperties.gap;
                Rectangle rectangle = new Rectangle(mapProperties.cellWidth, mapProperties.cellHeight);
                rectangle.setFill(Color.rgb(0, 0, 0, 0.2));
                rectangle.relocate(currentX, currentY);
                System.out.println("currentX = " + currentX);
                System.out.println("currentY = " + currentY);
                gameGrid[i][j] = rectangle;
                board.getChildren().add(rectangle);
            }

        root.getChildren().add(board);

        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();

        perspectiveTransform.setUlx(mapProperties.ulx);
        perspectiveTransform.setUly(mapProperties.uly);
        perspectiveTransform.setUrx(mapProperties.urx);
        perspectiveTransform.setUry(mapProperties.ury);
        perspectiveTransform.setLlx(mapProperties.llx);
        perspectiveTransform.setLly(mapProperties.lly);
        perspectiveTransform.setLrx(mapProperties.lrx);
        perspectiveTransform.setLry(mapProperties.lry);

        board.setEffect(perspectiveTransform);
    }

    public void addNodeToBoard(int x, int y, Node node) {
        Pair<Double, Double> position = getCellPosition(x, y);
        node.relocate(x + mapProperties.cellWidth / 2, y + mapProperties.cellHeight / 2);
        board.getChildren().add(node);
    }

    public void removeNodeFromBoard(Node node) {
        board.getChildren().remove(node);
    }


    public ImageView addCardToBoard(int row, int column, Card card, FilesType filesType, String mode) {
        ImageView imageView = null;
        Pair<Double, Double> position = getCellPosition(row, column);

        if (mode.equals("ATTACK")) {
            SpriteAnimationProperties spriteProperties = new SpriteAnimationProperties(
                    card.getName(), FilesType.MINION, card.getCountOfAnimation());
            imageView = SpriteMaker.getInstance().makeSpritePic(spriteProperties.spriteSheetPath,
                    0, 0,
                    board, spriteProperties.count,
                    spriteProperties.rows, card.getMillis(),
                    (int) spriteProperties.widthOfEachFrame, (int) spriteProperties.heightOfEachFrame);
            imageView.relocate(position.getKey() - 10, position.getValue() - 48);
            imageView.setFitWidth(mapProperties.cellWidth + 10);
            imageView.setFitHeight(mapProperties.cellHeight + 20);
        } else {
            String path = "pics/" + filesType.getName() + "/" + card.getName() + ".gif";
            try {
                imageView = new ImageView(new Image(new FileInputStream(path)));
                imageView.setScaleX(2);
                imageView.setScaleY(2);
                imageView.relocate(position.getKey(), position.getValue() - 45);
                imageView.setFitWidth(mapProperties.cellWidth + 10);
                imageView.setFitHeight(mapProperties.cellHeight + 20);
                board.getChildren().add(imageView);
                //root.getChildren().add(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("position x = " + position.getKey());
        System.out.println("position y = " + position.getValue());



        return imageView;
    }

    public void test() {

        /*
        Minion minion = (Minion) Shop.getInstance().getNewCardByName("Siavash");
        System.out.println(minion.getName());
        addCardToBoard(2, 3, minion, FilesType.MINION, "ATTACK");
        */

        ArrayList<Card> cards = Shop.getInstance().getCards();
        int number = 0;
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 9; j++) {
                while(number < cards.size() && !(cards.get(number) instanceof Minion))
                    number++;
                if(number == cards.size())
                    break;
                System.out.println("number = " + number);
                System.out.println(cards.get(number).getName());
                addCardToBoard(i, j, cards.get(number), FilesType.MINION, "ATTACK");
                number++;
            }

    }

}
