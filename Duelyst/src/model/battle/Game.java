package model.battle;

import model.account.Account;
import model.card.Minion;
import model.card.Spell;
import view.BattleView;
import view.Request;
import view.enums.ErrorType;
import view.enums.StateType;

import javax.xml.stream.events.EntityReference;
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

    public static void gameChecker(Account account){
        Request request=new Request(StateType.BATTLE);
        Deck deck;
        do {
            battleView.print("Enter your mainDeck");
            request.getNewLine();
            deck=account.getCollection().passTheDeckIfHaveBeenExist(request.getCommand());
            if(deck==null){
                ErrorType error= ErrorType.SELECTED_INVALID_DECK;
                error.printMessage();
            }
        }while (deck==null);
        account.setMainDeck(deck);

    }

    public static void makeStoryGame(int level){

    }

    public static void makeCustomGame(String deckName,int mode,int numberOfFlags) {
        //age mode akhar nabashe numberesho 0 midam

        //init game
        //set reward
    }


    public void makeANewGame() {


        Game game = new Game();
        //todo to controller bere bebine single e ya multi
        //age single bud story e ya custom

        //age story bood che marhalie -> level
        int level;
        for (Game game1 : gamesType) {
            if (game1.levelNumber == level) {
                game = game1;
            }
        }
        //baad bere ye deck besaze ba array lista ke toshoon shomare carta o inast...

        //age custom bood list deckha (????) namayesh bede entekhab kone bazi kone

        //age multi bood bere account entekhab kone va mode
    }

    public void makeGames() {
        //bere az roo file bekhone game besaze berize to gamesType
    }
