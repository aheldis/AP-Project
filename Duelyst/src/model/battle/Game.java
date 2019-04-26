package model.battle;

import model.card.Minion;
import model.card.Spell;
import model.account.Account;

import java.util.ArrayList;

public class Game {
    private boolean singlePlayer; //false -> multiplayer
    private boolean story; //false -> custom
    private String mode;
    private int heroNumber;
    private ArrayList<int> spellIds;
    private ArrayList<int> minionIds;
    private int itemNumber;
    private int reward;
    private int levelNumber = -1;
    private ArrayList<Game> gamesType;

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
