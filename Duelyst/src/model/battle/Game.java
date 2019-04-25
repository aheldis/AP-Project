package model.battle;

import model.card.Minion;
import model.card.Spell;

import java.util.ArrayList;

public class Game {
    private boolean singlePlayer;
    private boolean story;
    private boolean customGame:
    private String mode;
    private int heroNumber;
    private ArrayList<int> spellIds;
    private ArrayList<int> minionIds;
    private int itemNumber;
    private int reward;
    private int levelNumber = -1;
    private ArrayList<Game> gamesType;

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
