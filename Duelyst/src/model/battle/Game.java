package model.battle;

import model.account.Account;
import model.card.Minion;
import model.card.Spell;
import view.BattleView;
import view.Request;
import view.enums.ErrorType;
import view.enums.StateType;
import model.account.Account;

import javax.xml.stream.events.EntityReference;
import java.io.FileWriter;
import java.security.AccessControlContext;
import java.util.ArrayList;

public class Game {
    private boolean singlePlayer;
    private boolean story;
    private boolean customGame;
    private String mode;
    private int heroNumber;
    private ArrayList<Integer> spellIds;//todo id ha string hastan
    private ArrayList<Integer> minionIds;
    private int itemNumber;
    private int reward;
    private int levelNumber = -1;
    private ArrayList<Game> gamesType;
    private static BattleView  battleView= BattleView.getInstance();

    public static boolean gameChecker(Account account){
        //bayad player deck asli hod ra entekhab karde bashad
        Deck deck;
        deck=account.getMainDeck();
        if(deck==null || !deck.validate()){
            ErrorType error= ErrorType.SELECTED_INVALID_DECK;
            error.printMessage();
            return false;
        }
//        Request request=new Request(StateType.BATTLE);
//        Deck deck;
//        do {
//            battleView.print("Enter your mainDeck");
//            request.getNewLine();
//            deck=account.getCollection().passTheDeckIfHaveBeenExist(request.getCommand());
//            if(deck==null){
//                ErrorType error= ErrorType.SELECTED_INVALID_DECK;
//                error.printMessage();
//            }
//        }while (deck==null);
//        account.setMainDeck(deck);

    }

    public static void makeStoryGame(int level){

    }

    public static  void  makeCustomGame(String deckName,int mode,int numberOfFlags) {


        //age mode akhar nabashe numberesho 0 midam

        //init game
        //set reward
    }
    public static boolean makeMultiPlayerGame(String userName,int mode,int numberOfFlags){
    //if main deck of player 2 is invalid print "selected deck for second player is invalid" and return false

        //age mode akhar nabashe numberesho 0 midam
    }




    public void makeANewGame(Account account) {


        Game game = new Game();

        Player player1 = OrdinaryPlayer.makeNewPlayer(account, account.getMainDeck());
        if (player1 == null) {
            return;
        }
        Player player2 = null;
        //todo to controller bere bebine single e ya multi
        //singlePlayer to por kone;
        //age single bud story e ya custom
        //story ro por kone
        if (story) {

            //age story bood che marhalie -> level
            int level; //in to begire
            //bere az too gamefile ha new game e ke sakhtim ro por kone -> arraylist ha va hero va item va reward va mode
        } else {
            //age custom bood list deckha (????) namayesh bede entekhab kone bazi kone
        }


        //baad bere ye deck besaze ba array lista ke toshoon shomare carta o inast...

        //age multi bood bere account entekhab kone va mode
    }

    public void makeGames() {
        //bere az roo file bekhone game besaze berize to gamesType
    }
