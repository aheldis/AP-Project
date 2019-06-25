package view.Graphic;

import com.gilecode.yagson.YaGson;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import model.account.FilesType;
import model.battle.Game;
import model.battle.Match;
import model.card.Card;
import model.card.Hero;
import model.card.Spell;
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
    private int lastWait;

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

    public void addNodeToBoard(int x, int y, Node node, Boolean samePlace) {
        Pair<Double, Double> position = getCellPosition(x, y);
        if (samePlace)
            node.relocate(position.getKey(), position.getValue());
        else
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
                        if (card instanceof Spell)
                            return board;
                        return addCardToBoard(i, j, card, "normal", imageView, false, false, false);
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
                                ImageView image, boolean drag, boolean flip, boolean beingAttacked) {
        FilesType filesType = FilesType.MINION;
        if (card instanceof Hero)
            filesType = FilesType.HERO;

        ImageView imageView;
        Pair<Double, Double> position = getCellPosition(row, column);

        if (mode.equals("ATTACK")) {
            if (!beingAttacked) selectedCard = card;
            imageView = attack(row, column, card, image, filesType, flip, beingAttacked);
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
        }

        imageView.relocate(position.getKey() - 8, position.getValue() - 48);
        imageView.setFitWidth(mapProperties.cellWidth + 10);
        imageView.setFitHeight(mapProperties.cellHeight + 20);
        workWithMouse(imageView, card, flip);
        cardsHashMap.put(card, imageView);

        if (drag) {
            DragAndDrop dragAndDrop = new DragAndDrop();
            dragAndDrop.dragAndDropForGame(imageView, card, null, board, root,
                    imageView.getFitWidth() / 2, imageView.getFitHeight() / 2,
                    imageView.getLayoutX(), imageView.getLayoutY());
        }
        return board;
    }

    private ImageView attack(int row, int column, Card card, ImageView image,
                             FilesType filesType, boolean flip, boolean beingAttacked) {
        SpriteAnimationProperties spriteProperties = new SpriteAnimationProperties(
                card.getName(), filesType, card.getCountOfAnimation());
        ImageView imageView = SpriteMaker.getInstance().makeSpritePic(spriteProperties.spriteSheetPath,
                0, 0, board, spriteProperties.count,
                spriteProperties.rows, spriteProperties.millis,
                (int) spriteProperties.widthOfEachFrame, (int) spriteProperties.heightOfEachFrame);
        imageView.setOpacity(0);
        playMusic("resource/music/attack/attack-2.m4a", false, battleScene);
        int wait = 0;
        if (flip) {
            imageView.setRotationAxis(Rotate.Y_AXIS);
            imageView.setRotate(180);
            getCell(row, column).setFill(Color.RED);
        }
        if (beingAttacked) {
            wait = selectedCard.getMillis();
            lastWait = card.getMillis();
            selectedCard = null;
        }
        int finalWait = wait;
        new AnimationTimer() {
            boolean once = true;
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (card.getHp() <= 0 && !once && finalWait == 0 && !beingAttacked &&
                        now > lastTime + lastWait * Math.pow(10, 6)) {
                    lastTime = now;
                    image.setOpacity(0);
                    removeCard(card);
                    selectedCard = null;
                    getCell(row, column).setFill(Color.BLACK);
                }
                if (once && now > lastTime + (spriteProperties.millis + finalWait) * Math.pow(10, 6)) {
                    lastTime = now;
                    board.getChildren().remove(imageView);
                    image.setOpacity(1);
                    if (finalWait != 0 && card.getHp() <= 0) {
                        getCell(row, column).setFill(Color.BLACK);
                        image.setOpacity(0);
                        removeCard(card);
                    }
                    once = false;
                } else if (once && now > lastTime + finalWait * Math.pow(10, 6)) {
                    image.setOpacity(0);
                    imageView.setOpacity(1);
                }

            }
        }.start();
        return imageView;
    }

    public Rectangle getCell(int row, int column) {
        return gameGrid[row][column];
    }

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
*/

    private void workWithMouse(ImageView imageOfCard, Card card, boolean enemy) {
        Group group = new Group();
        group.relocate(imageOfCard.getLayoutX(), imageOfCard.getLayoutY() + 80);
        board.getChildren().addAll(group);
        imageOfCard.setOnMouseEntered(event -> {
            if (selectedCard != null && selectedCard.canAttack(card)) {
                setCursor(battleScene, Cursor.ATTACK);
            } else {
                setCursor(battleScene, Cursor.LIGHTEN);
            }
            if (enemy) {
                imageOfCard.setEffect(getLighting(Color.RED));
            } else {
                imageOfCard.setEffect(getLighting(Color.WHITE));
            }

            addImage(group, "pics/battle_categorized/icon_atk@2x.png", 0, 0, 40, 40);
            addImage(group, "pics/battle_categorized/icon_hp@2x.png", mapProperties.cellWidth - 30, 0, 40, 40);
            addText(group, 13, 10, card.getAp() + "", Color.WHITE, 14);
            addText(group, mapProperties.cellWidth - 11, 10, card.getHp() + "", Color.WHITE, 14);
            group.setOpacity(1);
        });

        imageOfCard.setOnMouseExited(event -> {
            setCursor(battleScene, Cursor.AUTO);
            imageOfCard.setEffect(null);
            group.setOpacity(0);
        });

        imageOfCard.setOnMouseClicked(event -> {

            if (selectedCard != null && selectedCard.attack(card, true)) {
                addCardToBoard(selectedCard.getPosition().getXCoordinate(),
                        selectedCard.getPosition().getYCoordinate(), selectedCard,
                        "ATTACK", imageOfSelectedCard, false, false, false);
                if (card.counterAttack(selectedCard))
                    addCardToBoard(card.getPosition().getXCoordinate(),
                            card.getPosition().getYCoordinate(), card,
                            "ATTACK", imageOfCard, false, true, true);
                setCursor(battleScene, Cursor.AUTO);
                imageOfCard.setEffect(null);
                backToDefault();
            } else if (isHeroSpecialPowerClicked()) {
                //todo add for minion and hero :-?
                /*todo yani inke negah kone age oon boolean e true bood select nakone o ina
                todo for more information contact Sba
                */
                match.passPlayerWithTurn().getHero().useSpecialPower(card.getPosition());
                setHeroSpecialPowerClicked(false);
                backToDefault();
            }
            group.setOpacity(0);
        });
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
        if (card instanceof Spell) {

        } else {
            ArrayList<Square> squares = card.getCanPutInSquares();
            for (Square square : squares) {
                Coordinate coordinate = square.getCoordinate();
                Rectangle grid = gameGrid[coordinate.getX()][coordinate.getY()];
                grid.setFill(Color.BLUEVIOLET);
                coloredRectangles.add(grid);
            }
        }
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
        double primaryX = (mapProperties.ulx + mapProperties.llx) / 2;
        double currentX = primaryX, currentY = mapProperties.uly;

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

//                setOnMouseClickedForSpecialPower(rectangle, coordinate);

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

    public void showSpecialPowerUsed(String type) {
        Group group = new Group();
        addRectangle(group, 0, 0, 435, 100, 20, 20, Color.rgb(100, 100, 200, 0.5));
        addTextWithShadow(group, 10, 40, type + " Special Power Activated", "Luminari", 30);
        root.getChildren().add(group);
        group.relocate(490, 50);
        GeneralGraphicMethods.setOnMouseEntered(group, battleScene, true);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> root.getChildren().remove(group));
        }).start();
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

    public ImageView addCellEffect(int x, int y) {
        //"pics/battle/stunned@2x.png"
        ImageView imageView = GeneralGraphicMethods.createImage(
                "pics/battle/1.png", 20, 20);
        BattleScene.getSingleInstance().addNodeToBoard(x, y, imageView, true);
        return imageView;

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

    private void removeColorFromRectangles() {
        for (Rectangle rectangle : coloredRectangles)
            rectangle.setFill(Color.BLACK);
        coloredRectangles = new ArrayList<>();
    }

    public HashMap<Card, ImageView> getCardsHashMap() {
        return cardsHashMap;
    }

    public void removeCard(Card card) {
        ImageView imageView = cardsHashMap.get(card);
        removeNodeFromBoard(imageView);
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

    Scene getBattleScene() {
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

    }

    public void showTarget(Square target, String targetType) {
        Rectangle rectangle = getCell(target.getXCoordinate(), target.getYCoordinate());
        Paint preColor = rectangle.getFill();
        if (targetType.equals("square"))
            rectangle.setFill(Color.GREEN);
        ImageView imageView = null;
        if (targetType.equals("force")) {
            if (target.squareHasHeroAndPassIt() != null) {
                imageView = cardsHashMap.get(target.squareHasHeroAndPassIt());
                imageView.setEffect(getLighting(Color.GREEN));
            }
            if (target.squareHasMinionAndPassIt() != null) {
                imageView = cardsHashMap.get(target.squareHasMinionAndPassIt());
                imageView.setEffect(getLighting(Color.GREEN));
            }
        }
        ImageView finalImageView = imageView;
        new AnimationTimer() {
            double lastTime = 0;
            double second = Math.pow(10, 9);

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now > lastTime + second) {
                    lastTime = now;
                    if (target.squareHasMinionOrHero())
                        rectangle.setFill(preColor);
                    else
                        rectangle.setFill(Color.BLACK);
                    if (finalImageView != null) {
                        finalImageView.setEffect(null);
                    }
                }
            }
        }.start();
    }

    boolean isHeroSpecialPowerClicked() {
        return heroSpecialPowerClicked;
    }

    void setHeroSpecialPowerClicked(boolean heroSpecialPowerClicked) {
        this.heroSpecialPowerClicked = heroSpecialPowerClicked;
    }

    public Group getBoard() {
        return board;
    }

    public Group getRoot() {
        return root;
    }
}
