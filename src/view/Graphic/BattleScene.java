package view.Graphic;

import com.gilecode.yagson.YaGson;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.account.FilesType;
import model.battle.Game;
import model.battle.Match;
import model.card.Card;
import model.card.Hero;
import model.land.LandOfGame;
import view.enums.StateType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static view.Graphic.GeneralGraphicMethods.addImage;

public class BattleScene {
    private static BattleScene singleInstance = null;
    private Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
    private Group root = (Group) Objects.requireNonNull(battleScene).getRoot();
    private Group board = null; //!!!! Har chi roo board gharare bashe be in add she
    private double width = StageLauncher.getWidth();
    private double height = StageLauncher.getHeight();
    private int numberOfMap;
    private Rectangle[][] gameGrid;
    private MapProperties mapProperties;
    private Match match;
    private Game game;
    private BattleHeaderGraphic battleHeader;
    private BattleFooterGraphic battleFooter;

    public void setMatch(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    int getNumberOfMap() {
        return numberOfMap;
    }

    private BattleScene() {
    }

    public static BattleScene getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new BattleScene();
        return singleInstance;
    }

    void setBattleScene(int numberOfMap) {
        root.getChildren().clear();
        this.numberOfMap = numberOfMap;
        setMapProperties();
        setMapBackground();
        GeneralGraphicMethods.playMusic("resource/music/battle_music/" +
                numberOfMap + ".m4a", true, battleScene);
        addGrid();
        battleHeader = new BattleHeaderGraphic(root);
        // StageLauncher.testzahraFooter(root);
        battleFooter = new BattleFooterGraphic(root, game.getPlayers()[0]);
        battleFooter.makeFooter(battleScene);
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
                assert imageView != null;
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

    private Pair<Double, Double> getCellPosition(int row, int column) {
        return new Pair<>(gameGrid[row][column].getLayoutX(), gameGrid[row][column].getLayoutY());
    }

    private void addGrid() {
        board = new Group();
        int numberOfColumns = LandOfGame.getNumberOfColumns();
        int numberOfRows = LandOfGame.getNumberOfRows();
        double primaryX = (mapProperties.ulx + mapProperties.llx) / 2, primaryY = mapProperties.uly;
        double currentX = primaryX, currentY = primaryY;

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

        //    board.setEffect(perspectiveTransform);
/*
        board.setOnMouseClicked(event -> {
            System.out.println("board: " + event.getX() + " " + event.getY());
            Point2D point2D = new Point2D(event.getX(), event.getY());
            Rectangle rectangle = new Rectangle(1,1);
            rectangle.relocate(event.getX(), event.getY());
            rectangle.setEffect(perspectiveTransform);
            for (int ii = 0; ii < 5; ii++)
                for (int jj = 0; jj < 9; jj++) {
                    Bounds bounds = rectangle.getBoundsInParent();
                    if (bounds.intersects(gameGrid[ii][jj].getBoundsInParent()))
                        gameGrid[ii][jj].setFill(Color.RED);
                }


            for (int ii = 0; ii < 5; ii++)
                for (int jj = 0; jj < 9; jj++) {
                    Point2D[] point = new Point2D[7];
                    point[0] = new Point2D(event.getX(), event.getY());
                    point[1] = new Point2D(event.getSceneX(), event.getSceneY());
                    point[2] = new Point2D(event.getScreenX(), event.getScreenY());
                    point[3] = gameGrid[ii][jj].parentToLocal(board.parentToLocal(point[0]));
                    point[4] = gameGrid[ii][jj].parentToLocal(board.parentToLocal(point[1]));
                    point[5] = gameGrid[ii][jj].parentToLocal(board.parentToLocal(point[2]));
                    point[6] = board.localToParent(point[0]);

                    Bounds[] bounds = new Bounds[6];
                    bounds[0] = gameGrid[ii][jj].getBoundsInLocal();
                    bounds[1] = gameGrid[ii][jj].localToParent(bounds[0]);
                    bounds[2] = board.localToParent(bounds[0]);

                    bounds[3] = gameGrid[ii][jj].getBoundsInParent();
                    bounds[4] = gameGrid[ii][jj].localToParent(bounds[1]);
                    bounds[5] = board.localToParent(bounds[1]);

                    for(int k = 0; k < 6; k++)
                        for(int g = 0; g < 7; g++)
                    if (bounds[k].contains(point[g])) {
                        gameGrid[ii][jj].setFill(Color.RED);
                        System.out.println("i = " + ii);
                        System.out.println("j = " + jj);
                        System.out.println("k = " + k);
                        System.out.println("g = " + g);
                    }

                }

        });
        */
    }

    public void addNodeToBoard(int x, int y, Node node) {
        Pair<Double, Double> position = getCellPosition(x, y);
        node.relocate(position.getKey(), position.getValue() - 10);
        if (node instanceof ImageView) {
            ((ImageView) node).setFitWidth(mapProperties.cellWidth);
            ((ImageView) node).setFitHeight(mapProperties.cellHeight);
        }
        board.getChildren().add(node);
    }

    public void removeNodeFromBoard(Node node) {
        board.getChildren().remove(node);
    }

    private boolean withinRange(double x, double y, int row, int column) {
        Pair<Double, Double> cellPosition = getCellPosition(row, column);
        if (!(x > cellPosition.getKey() && x < cellPosition.getKey() + mapProperties.cellWidth + mapProperties.gap))
            return false;
        return y > cellPosition.getValue() && y < cellPosition.getValue() + mapProperties.cellHeight + mapProperties.gap;
    }

    ImageView addCardToBoard(double x, double y, Card card, ImageView imageView) {
        int numberOfColumns = LandOfGame.getNumberOfColumns();
        int numberOfRows = LandOfGame.getNumberOfRows();
        for (int i = 0; i < numberOfRows; i++)
            for (int j = 0; j < numberOfColumns; j++) {
                Rectangle grid = gameGrid[i][j];
                double minX = grid.getLayoutX();
                double maxX = grid.getLayoutX() + grid.getWidth();
                double minY = grid.getLayoutY();
                double maxY = grid.getLayoutY() + grid.getHeight();
//                if (gameGrid[i][j].contains(x, y)) {
                if (x <= maxX && x >= minX && y <= maxY && y >= minY) {
                    //withinRange(x, y, i, j)
                    return addCardToBoard(i, j, card, "normal", imageView);
                }
            }
        return null;
    }

    public ImageView addCardToBoard(int row, int column, Card card, String mode, ImageView image) {
        FilesType filesType = FilesType.MINION;
        if (card instanceof Hero)
            filesType = FilesType.HERO;

        ImageView imageView = null;
        Pair<Double, Double> position = getCellPosition(row, column);

        if (mode.equals("ATTACK")) {
            SpriteAnimationProperties spriteProperties = new SpriteAnimationProperties(
                    card.getName(), filesType, card.getCountOfAnimation());
            imageView = SpriteMaker.getInstance().makeSpritePic(spriteProperties.spriteSheetPath,
                    0, 0,
                    board, spriteProperties.count,
                    spriteProperties.rows, card.getMillis(),
                    (int) spriteProperties.widthOfEachFrame, (int) spriteProperties.heightOfEachFrame);
        } else {
            if (card instanceof Hero) {
                String path = "pics/" + filesType.getName() + "/" + card.getName() + ".gif";
                imageView = addImage(board, path, 0, 0, 110, 150);
                imageView.setScaleX(2);
                imageView.setScaleY(2);
            } else {
                imageView = image;
                imageView.relocate(0, 0);
                imageView.setScaleX(1.5);
                imageView.setScaleY(1.5);
                board.getChildren().add(image);
            }
            //root.getChildren().add(imageView);
        }

        assert imageView != null;
        imageView.relocate(position.getKey() - 8, position.getValue() - 48);
        imageView.setFitWidth(mapProperties.cellWidth + 10);
        imageView.setFitHeight(mapProperties.cellHeight + 20);
        return imageView;
    }

    public void test() {

        /*
        Minion minion = (Minion) Shop.getInstance().getNewCardByName("Siavash");
        System.out.println(minion.getName());
        addCardToBoard(2, 3, minion, "ATTACK");
        */
/*
        ArrayList<Card> cards = Shop.getInstance().getCards();
        int number = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                while (number < cards.size() && !(cards.get(number) instanceof Hero))
                    number++;
                if (number == cards.size())
                    break;
                System.out.println("number = " + number);
                System.out.println(cards.get(number).getName());
                addCardToBoard(i, j, cards.get(number), "ATTACK");
                number++;
            }
*/
    }


}
