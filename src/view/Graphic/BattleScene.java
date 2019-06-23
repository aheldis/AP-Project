package view.Graphic;

import com.gilecode.yagson.YaGson;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.print.PageLayout;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import model.account.FilesType;
import model.battle.Game;
import model.battle.Match;
import model.card.Card;
import model.card.Hero;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.Cursor;
import view.enums.StateType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static view.Graphic.GeneralGraphicMethods.*;

public class BattleScene {
    private static BattleScene singleInstance = null;
    private Scene battleScene = StageLauncher.getScene(StateType.BATTLE);
    private Group root = (Group) Objects.requireNonNull(battleScene).getRoot();
    private Group board = null; //!!!! Har chi roo board gharare bashe be in add she
    private double width = StageLauncher.getWidth();
    private double height = StageLauncher.getHeight();
    private int numberOfMap;
    private Rectangle[][] gameGrid;
    private HashMap<Rectangle, Square> positionHashMap = new HashMap<>();
    private ArrayList<Rectangle> coloredRectangles = new ArrayList<>();
    private MapProperties mapProperties;
    private Match match;
    private Game game;
    private BattleHeaderGraphic battleHeader;
    private BattleFooterGraphic battleFooter;
    private Square onMousePressedPosition;
    private Card selectedCard;
    private ImageView imageOfSelectedCard;
    private Glow glow = new Glow();
    private HashMap<Card, ImageView> cardsHashMap = new HashMap<>();
    private boolean heroSpecialPowerClicked = false;

    private BattleScene() {
    }

    public static void changeSingleInstance(BattleScene battleScene) {
        singleInstance = battleScene;
    }

    public static BattleScene getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new BattleScene();
        singleInstance.heroSpecialPowerClicked = false;
        return singleInstance;
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

    public Pair<Double, Double> getCellPosition(int row, int column) {
        return new Pair<>(gameGrid[row][column].getLayoutX(), gameGrid[row][column].getLayoutY());
    }

    public void removeNodeFromBoard(Node node) {
        board.getChildren().remove(node);
    }

    //*
    Group addCardToBoard(double x, double y, Card card, ImageView imageView, boolean putOrMove) {
//        PUT = true;
//        MOVE = false;
        int numberOfColumns = LandOfGame.getNumberOfColumns();
        int numberOfRows = LandOfGame.getNumberOfRows();
        for (int i = 0; i < numberOfRows; i++)
            for (int j = 0; j < numberOfColumns; j++) {
                Rectangle grid = gameGrid[i][j];
                double minX = grid.getLayoutX();
                double maxX = grid.getLayoutX() + grid.getWidth();
                double minY = grid.getLayoutY();
                double maxY = grid.getLayoutY() + grid.getHeight();
                Square position = positionHashMap.get(gameGrid[i][j]);
                if (x <= maxX && x >= minX && y <= maxY && y >= minY) {
                    if (position.equals(onMousePressedPosition)) {
                        removeColorFromRectangles();
                        selectCard(card, imageView, gameGrid[i][j]);
                        return null;
                    }
                    if (putOrMove) {
                        boolean canPut = match.getPlayers()[0].putCardOnLand(card,
                                position.getCoordinate(), match.getLand(), true);
                        if (!canPut) {
                            removeColorFromRectangles();
                            return null;
                        }
                    } else {
                        boolean canMove = card.move(position.getCoordinate());
                        if (!canMove) {
                            removeColorFromRectangles();
                            return null;
                        }
                    }
                    if (coloredRectangles.contains(grid)) {
                        removeColorFromRectangles();
                        selectedCard = null;
                        return addCardToBoard(i, j, card, "normal", imageView, false, false);
                    }
                }
            }
        return null;
    }

    private Pair<Integer, Integer> withinRange(Point2D point2D) {
        for (int ii = 0; ii < 5; ii++)
            for (int jj = 0; jj < 9; jj++) {
                if (gameGrid[ii][jj].getBoundsInParent().contains(point2D))
                    return new Pair<>(ii, jj);
            }
        return null;
    }

    private void selectCard(Card card, ImageView gifOfCard, Rectangle grid) {
        selectedCard = card;
        imageOfSelectedCard = gifOfCard;
        grid.setFill(Color.GOLD);
        coloredRectangles.add(grid);
        glow = new Glow(1);
        gifOfCard.setEffect(glow);
    }

    public Group addCardToBoard(int row, int column, Card card, String mode,
                                ImageView image, boolean drag, boolean flip) {
        FilesType filesType = FilesType.MINION;
        if (card instanceof Hero)
            filesType = FilesType.HERO;

        ImageView imageView;
        Pair<Double, Double> position = getCellPosition(row, column);

        if (mode.equals("ATTACK")) {
            SpriteAnimationProperties spriteProperties = new SpriteAnimationProperties(
                    card.getName(), filesType, card.getCountOfAnimation());
            imageView = SpriteMaker.getInstance().makeSpritePic(spriteProperties.spriteSheetPath,
                    0, 0, board, spriteProperties.count,
                    spriteProperties.rows, card.getMillis(),
                    (int) spriteProperties.widthOfEachFrame, (int) spriteProperties.heightOfEachFrame);
            playMusic("resource/music/attack/attack-2.m4a", false, battleScene);
        } else {
            if (image == null) {
                String path = "pics/" + filesType.getName() + "/" + card.getName() + ".gif";
                imageView = addImage(board, path, 0, 0, 110, 150);
                imageView.setScaleX(2);
                imageView.setScaleY(2);
                if (flip) {
                    imageView.setRotationAxis(Rotate.Y_AXIS);
                    imageView.setRotate(180);
                    getCell(row, column).setFill(Color.RED);
                }
            } else {
                imageView = image;
                imageView.relocate(0, 0);
                imageView.setScaleX(1.8);
                imageView.setScaleY(1.8);
                board.getChildren().add(image);
            }
            //root.getChildren().add(imageView);
        }

        assert imageView != null;
        imageView.relocate(position.getKey() - 8, position.getValue() - 48);
        imageView.setFitWidth(mapProperties.cellWidth + 10);
        imageView.setFitHeight(mapProperties.cellHeight + 20);
        setOnMouseEntered(imageView, card, flip);
        cardsHashMap.put(card, imageView);

        if (drag) {
            DragAndDrop dragAndDrop = new DragAndDrop();
            dragAndDrop.dragAndDropForGame(imageView, card, null, board, root,
                    imageView.getFitWidth() / 2, imageView.getFitHeight() / 2,
                    imageView.getLayoutX(), imageView.getLayoutY());
        }
        return board;
    }

    public Rectangle getCell(int row, int column) {
        return gameGrid[row][column];
    }
    //*/
/*
    Group addCardToBoard(double x, double y, Card card, ImageView imageView, boolean putOrMove) {
//        PUT = true;
//        MOVE = false;

        Pair <Integer, Integer> coordinate = withinRange(new Point2D(x, y));
        if (coordinate == null)
            return null;
        int i = coordinate.getKey(), j = coordinate.getValue();
        Rectangle grid = gameGrid[i][j];
        Square position = positionHashMap.get(gameGrid[i][j]);




        if (position.equals(onMousePressedPosition)) {
            removeColorFromRectangles();
            selectCard(card, imageView, gameGrid[i][j]);
            return null;
        }
        if (putOrMove) {
            boolean canPut = match.getPlayers()[0].putCardOnLand(card,
                    position.getCoordinate(), match.getLand(), true);
            if (!canPut) {
                removeColorFromRectangles();
                return null;
            }
        } else {
            boolean canMove = card.move(position.getCoordinate());
            if (!canMove) {
                removeColorFromRectangles();
                return null;
            }
        }
        if (coloredRectangles.contains(grid)) {
            removeColorFromRectangles();
            selectedCard = null;
            return addCardToBoard(i, j, card, "normal", imageView, false, false);
        }

        return null;
    }
//*/

    private void setOnMouseEntered(ImageView imageOfCard, Card card, boolean enemy) {
        imageOfCard.setOnMouseEntered(event -> {
            if (selectedCard != null && selectedCard.canAttack(card))
                setCursor(battleScene, Cursor.ATTACK);
            else
                setCursor(battleScene, Cursor.LIGHTEN);
            if (enemy)
                imageOfCard.setEffect(getLighting(Color.RED));
            else
                imageOfCard.setEffect(getLighting(Color.WHITE));
        });

        imageOfCard.setOnMouseExited(event -> {
            setCursor(battleScene, Cursor.AUTO);
            imageOfCard.setEffect(null);
        });

        if (enemy) {
            imageOfCard.setOnMouseClicked(event -> {

                if (selectedCard != null && selectedCard.attack(card)) {
                    imageOfSelectedCard.setOpacity(0);
                    addCardToBoard(selectedCard.getPosition().getXCoordinate(),
                            selectedCard.getPosition().getYCoordinate(), selectedCard,
                            "ATTACK", null, false, false);
                    backToDefault();
                }

            });
        }
    }

    void showCanMoveToCoordinations(Card card) {
        ArrayList<Square> squares = card.getCanMoveToSquares();
        for (Square square : squares) {
            Coordinate coordinate = square.getCoordinate();
            Rectangle grid = gameGrid[coordinate.getX()][coordinate.getY()];
            grid.setFill(Color.ALICEBLUE);
            coloredRectangles.add(grid);
        }
    }

    void showCanPutInCoordinations(Card card) {
        ArrayList<Square> squares = card.getCanPutInSquares();
        for (Square square : squares) {
            Coordinate coordinate = square.getCoordinate();
            Rectangle grid = gameGrid[coordinate.getX()][coordinate.getY()];
            grid.setFill(Color.BLUEVIOLET);
            coloredRectangles.add(grid);
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    void setOnMousePressedPosition(Card card) {
        backToDefault();
        this.onMousePressedPosition = card.getPosition();
    }

    void backToDefault() {
        selectedCard = null;
        removeColorFromRectangles();
        glow.setLevel(0);
    }

    void removeColorFromRectangles() {
        for (Rectangle rectangle : coloredRectangles)
            rectangle.setFill(Color.BLACK);
        coloredRectangles.removeAll(coloredRectangles);
    }

    public HashMap<Card, ImageView> getCardsHashMap() {
        return cardsHashMap;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    int getNumberOfMap() {
        return numberOfMap;
    }

    public BattleHeaderGraphic getBattleHeader() {
        return battleHeader;
    }

    public BattleFooterGraphic getBattleFooter() {
        return battleFooter;
    }

    public Scene getBattleScene() {
        return battleScene;
    }

    void setBattleScene(int numberOfMap) {
        root.getChildren().clear();
        this.numberOfMap = numberOfMap;
        setMapProperties();
        setMapBackground();
        playMusic("resource/music/battle_music/" +
                numberOfMap + ".m4a", true, battleScene);
        addGrid();
        battleHeader = new BattleHeaderGraphic(this, root);
        battleFooter = new BattleFooterGraphic(this, root, game.getPlayers()[0], battleScene);
        showSpecialPowerUsed("Hero");
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
                    if (i != 0)
                        currentY += mapProperties.cellHeight + mapProperties.gap;
                    currentX = primaryX;
                } else
                    currentX += mapProperties.cellWidth + mapProperties.gap;
                Rectangle rectangle = new Rectangle(mapProperties.cellWidth, mapProperties.cellHeight);
                rectangle.setFill(Color.BLACK);
                rectangle.setOpacity(0.2);
                rectangle.relocate(currentX, currentY);
                gameGrid[i][j] = rectangle;

                Coordinate coordinate = new Coordinate();
                coordinate.setX(i);
                coordinate.setY(j);

                setOnMouseClickedForSpecialPower(rectangle, coordinate);

                positionHashMap.put(gameGrid[i][j], match.getLand().getSquares()[i][j]);
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

        //board.setEffect(perspectiveTransform);

        /*
        Rectangle rectangle = new Rectangle(currentX - primaryX + mapProperties.cellWidth, currentY - primaryY + mapProperties.cellHeight);
        rectangle.setFill(Color.gray(1, 0.5));
        board.getChildren().add(rectangle);
        rectangle.relocate(primaryX, primaryY);


        Transform localToSceneTransform = board.getLocalToSceneTransform();
        System.out.println("localToSceneTransform = " + localToSceneTransform);
        System.out.println("localToSceneTransform.getMxx() = " + localToSceneTransform.getMxx());
        System.out.println("localToSceneTransform.getMxy() = " + localToSceneTransform.getMxy());
        System.out.println("localToSceneTransform.getMyx() = " + localToSceneTransform.getMyx());
        System.out.println("localToSceneTransform.getMyy() = " + localToSceneTransform.getMyy());
*/
        /*
        board.setOnMouseClicked(event -> {

            Point2D point2D = new Point2D(event.getX(), event.getY());

            for (int ii = 0; ii < 5; ii++)
                for (int jj = 0; jj < 9; jj++) {
                    if (gameGrid[ii][jj].getBoundsInParent().contains(point2D))
                        gameGrid[ii][jj].setFill(Color.RED);
                }

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

    public void test() {

        /*
        numberOfMap = 7;
        setMapProperties();
        setMapBackground();
        addGrid();
*/
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

    private void setOnMouseClickedForSpecialPower(Node node, Coordinate coordinate) {
        //todo add for minion and hero :-?
        /*todo yani inke negah kone age oon boolean e true bood select nakone o ina
          todo for more information contact Sba
         */
        node.setOnMouseClicked(event -> {
            if (isHeroSpecialPowerClicked()) {
                match.getPlayers()[0].getHero().useSpecialPower(match.getLand().passSquareInThisCoordinate(coordinate));
                setHeroSpecialPowerClicked(false);
            }
        });
    }

    public void showSpecialPowerUsed(String type) {
        Group group = new Group();
        addRectangle(group, 0, 0, 420, 100, 20, 20, Color.rgb(100, 100, 200, 0.5));
        addTextWithShadow(group, 10, 40, type + " Special Power Activated", "Luminari", 30);
        root.getChildren().add(group);
        group.relocate(490, 50);
        GeneralGraphicMethods.setOnMouseEntered(group, battleScene, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        root.getChildren().remove(group);
                    }
                });
            }
        }).start();
    }

    public boolean isHeroSpecialPowerClicked() {
        return heroSpecialPowerClicked;
    }

    public void setHeroSpecialPowerClicked(boolean heroSpecialPowerClicked) {
        this.heroSpecialPowerClicked = heroSpecialPowerClicked;
    }

    public Group getBoard() {
        return board;
    }

    public Group getRoot() {
        return root;
    }
}
