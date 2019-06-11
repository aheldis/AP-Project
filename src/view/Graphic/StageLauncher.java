package view.Graphic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.account.Account;
import model.account.Collection;
import model.account.Shop;
import model.battle.Deck;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.Cursor;
import view.enums.StateType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static view.Graphic.GeneralGraphicMethods.*;

public class StageLauncher extends Application {

    private static Stage primaryStage;
    private static HashMap<StateType, Scene> sceneHashMap = new HashMap<>();
    private static double HEIGHT;
    private static double WIDTH;

    static double getWidth() {
        return WIDTH;
    }

    static double getHeight() {
        return HEIGHT;
    }

    static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Scene makeScene(StateType stateType, Cursor cursor) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        sceneHashMap.put(stateType, scene);
        Platform.runLater(() -> GeneralGraphicMethods.setCursor(scene, cursor));
        return scene;
    }

    public static Scene getScene(StateType stateType) {
        if (sceneHashMap.containsKey(stateType))
            return sceneHashMap.get(stateType);
        return null;
        //return makeScene(stateType);
    }


    private static void minionMaker(ArrayList<Card> cards, String path) {
        Minion minion = new Minion();
        minion.setPathOfThePicture("pics/minion_background.png");
        minion.setPathOfAnimation(path);
        minion.setHp(10);
        minion.setAp(10);
        cards.add(minion);
        minion.setDescription("i am minion");
    }

    /*
        @Override
        public void start(Stage primaryStage) throws Exception {
            StageLauncher.primaryStage = primaryStage;
            primaryStage.setScene(makeScene(StateType.BATTLE));
            new BattleScene("pics/maps/abyssian/midground.png", WIDTH, HEIGHT);
            primaryStage.show();
        }
    */
    private static void zahraTestDeck() {

        Spell spell = new Spell();
        spell.setPathOfThePicture("pics/minion_background.png");
        spell.setPathOfAnimation("pics/spell/fireBall.png");
        spell.setCountOfAnimation(16);
        spell.setFrameSize(48);

        Spell spell1 = new Spell();
        spell1.setPathOfThePicture("pics/minion_background.png");
        spell1.setPathOfAnimation("pics/spell/fireBall.png");
        spell1.setCountOfAnimation(16);
        spell1.setFrameSize(48);

        ArrayList<Deck> decks = new ArrayList<>();
        Deck deck = new Deck();

        spell.setDescription("atasssh");
        spell.setName("atisih");
        spell1.setDescription("atasssh");
        spell1.setName("atisih");
        deck.getCardsOfDeck().add(spell);
        deck.getCardsOfDeck().add(spell);
        deck.setName("zahra");
        for (int i = 0; i < 10; i++) {
            decks.add(deck);
        }

        Account account =  new Account("zahra", "123");
        Collection collection = new Collection(account);
        for (int i = 0; i < 15; i++) {
            collection.addToCards(spell);
        }
        collection.setDecks(decks);
        account.setCollection(collection);


        SelectGameScene.selectGame(account);
        //CollectionScene.showDeck(decks, collection);
    }

    public static void zahraTestCard() {
        ArrayList<Card> cards = new ArrayList<>();
        Hero hero = new Hero();
        hero.setPathOfThePicture("pics/Hero/hero_card/simorgh.png");
        hero.setHp(10);
        hero.setAp(10);
        hero.setName("ali");
        hero.setDescription("hello girls");

        Spell spell = new Spell();
        spell.setPathOfThePicture("pics/minion_background.png");
        spell.setPathOfAnimation("pics/spell/fireBall.png");
        spell.setCountOfAnimation(16);
        spell.setFrameSize(48);

        Spell spell1 = new Spell();
        spell1.setPathOfThePicture("pics/minion_background.png");
        spell1.setPathOfAnimation("pics/spell/fireBall.png");
        spell1.setCountOfAnimation(16);
        spell1.setFrameSize(48);
        cards.add(spell);

        minionMaker(cards, "pics/gifMinion/giv.gif");
        minionMaker(cards, "pics/gifMinion/gorg.gif");

        Collection collection = new Collection(new Account("zahra", "123"));
        for (int i = 0; i < 6; i++) {
            cards.add(hero);
            collection.addToCards(hero);
        }

        CollectionScene.showInCollection( collection);

    }
    public static void zahraTestShop() {
        Account account = new Account("zahra", "123");
        Spell spell = new Spell();

        Collection collection = new Collection(account);
        spell.setPathOfThePicture("pics/minion_background.png");
        spell.setPathOfAnimation("pics/spell/fireBall.png");
        spell.setCountOfAnimation(16);
        spell.setName("Fireball");
        spell.setFrameSize(48);
        spell.setMp(10);
        spell.setHp(10);
        for (int i = 0; i < 15; i++) {
            collection.addToCards(spell);

            Shop.getInstance().addCard(spell);
        }
        account.setDaric(10000);


        ShopScene.makeShopScene(account);
    }

    static void testzahraFooter(Group root) {
        Group circlesGroup = new Group();
        root.getChildren().addAll(circlesGroup);

        Group nextCardGroup = new Group();
        circlesGroup.relocate(50, 660);
        circlesGroup.getChildren().addAll(nextCardGroup);
        nextCardGroup.relocate(0, 0);

        addImage(nextCardGroup, "pics/battle/next_card.png",
                0, 0, 170, 170);
        addImage(nextCardGroup, "pics/battle/inner_glow.png",
                0, 0, 170, 170);
        addImage(nextCardGroup, "pics/battle/outer_ring.png",
                0, 0, 170, 170);


        Group handCardGroup;
        for (int i = 0; i < 5; i++) {
            handCardGroup = new Group();
            handCardGroup.relocate(30 + 140 * (i + 1), 20);
            circlesGroup.getChildren().addAll(handCardGroup);
            ImageView backgroudCircle = addImage(handCardGroup,
                    "pics/battle/hand_card.png", 0, 0, 140, 140);
            addImage(handCardGroup, "pics/icon_mana@2x.png", 60, 107, 30, 30);
            makeCircleRotation(backgroudCircle,70,70);
        }

        addImage(circlesGroup, "pics/battle/end_turn_yellow.png", 1000, 10, 200, 100);
        addImage(circlesGroup, "pics/battle/graveYard.png", 1000 - 80, 95, 150, 70);
        addImage(circlesGroup, "pics/battle/help.png", 1000 + 90, 95, 150, 70);
        Text text = addText(circlesGroup, "End Turn", 1040, 53, Color.rgb(225, 225, 225, 0.7), 30);
        text.setFont(Font.font("Andele Mono", FontWeight.BOLD, 25));
        Text graveYard = addText(circlesGroup, "Graveyard", 1000 - 60, 122,
                Color.rgb(225, 225, 225, 0.7), 30);
        graveYard.setFont(Font.font("Andele Mono", FontWeight.BOLD, 25));
        Text help = addText(circlesGroup, "Help", 1115, 122, Color.rgb(225, 225, 225, 0.7), 30);
        help.setFont(Font.font("Andele Mono", FontWeight.BOLD, 25));


        text.setStroke(Color.rgb(251,225,60,0.5));
        graveYard.setStroke(Color.rgb(0,225,225,0.5));
        help.setStroke(Color.rgb(0,225,225,0.5));
    }

    public static void graveYardTest(){
        ArrayList<Card> cards = new ArrayList<>();

        minionMaker(cards, "pics/gifMinion/giv.gif");
        minionMaker(cards, "pics/gifMinion/giv.gif");

        Spell spell = new Spell();
        spell.setPathOfThePicture("pics/minion_background.png");
        spell.setPathOfAnimation("pics/spell/fireBall.png");
        spell.setCountOfAnimation(16);
        spell.setName("Fireball");
        spell.setFrameSize(48);
        spell.setMp(10);
        spell.setHp(10);
        for(int i=0;i<13;i++)
        cards.add(spell);
        GraveYard.makeYard(cards );
    }

    @Override
    public void start(Stage primaryStage) {



        StageLauncher.primaryStage = primaryStage;
        WIDTH = 1380;
        HEIGHT = 850;
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Duelyst");
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("pics/duelyst_icon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scene accountScene = makeScene(StateType.ACCOUNT_MENU, Cursor.AUTO);
        Scene mainMenuScene = makeScene(StateType.MAIN_MENU, Cursor.LIGHTEN);
        Scene collectionScene = makeScene(StateType.COLLECTION, Cursor.LIGHTEN);
        Scene selectModeScene = makeScene(StateType.SELECT_MODE, Cursor.GREEN);
        Scene selectGameScene = makeScene(StateType.SELECT_GAME, Cursor.GREEN);
        Scene battleScene = makeScene(StateType.BATTLE, Cursor.RED);
        Scene shopScene = makeScene(StateType.SHOP, Cursor.AUTO);
        Scene graveyardScene = makeScene(StateType.GRAVE_YARD,Cursor.RED);

        //todo add "D:\\project_Duelyst1\\pics\\minion_background.png" to PATH_OF_THE_PICTURE of spell and ... to minion
        //todo add animation to  spell and minions

/*/
        zahraTestDeck();
        getPrimaryStage().setScene(collectionScene);
//*/

//*/

        primaryStage.setScene(selectGameScene);
/*/
        BattleScene battleScene1 = BattleScene.getSingleInstance();
        battleScene1.setBattleScene(7); //from 1 to 12
        primaryStage.setScene(battleScene);
/*/
//        AccountScene.getInstance().makeBackground();
//        primaryStage.setScene(accountScene);


//       graveYardTest();
//        primaryStage.setScene(graveyardScene);


/*/
//        zahraTestDeck();
//        primaryStage.setScene(collectionScene);
//*/


//        zahraTestShop();
//        primaryStage.setScene(shopScene);
//        Raining.rain(shopScene);
//        uncomment init shop

//        collectionScene.setFill(Color.BLACK);
//        testzahraFooter((Group)collectionScene.getRoot());
//        primaryStage.setScene(collectionScene);
zahraTestDeck();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
