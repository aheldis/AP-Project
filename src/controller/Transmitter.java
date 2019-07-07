package controller;

import model.account.Account;
import model.account.Collection;
import model.battle.Deck;
import model.battle.Game;
import model.battle.Match;
import model.battle.MatchInfo;
import model.card.Card;
import model.item.Item;
import view.enums.ErrorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * do Not new anything in this class
 * when client receive this class we should check errorType
 * in SHOP we should receive daric
 */

public class Transmitter implements Serializable { //todo check
    public ArrayList<HashMap<String, String>> hashMapsWithStrings = new ArrayList<>(); //Be tartib: 0: khod card - 1: change - 2: target - 3 be bad buffHa
    public ArrayList<String> changeFieldNames = new ArrayList<>();
    public ArrayList<String> targetFieldNames = new ArrayList<>();
    public ArrayList<String> fieldNames = new ArrayList<>();
    public ArrayList<String> buffFieldNames = new ArrayList<>();
    public int transmitterId = 0;
    public RequestEnum requestEnum;
    public Collection collection;
    public ArrayList<Card> cards;
    public ArrayList<Item> items;
    public ArrayList<String> ids;
    public String name;
    public String password;
    public String cardId;
    public Deck deck;
    public ArrayList<Deck> decks;
    public ArrayList<MatchInfo> matchInfos;
    public ArrayList<Account> accounts;
    public String authToken;
    public ErrorType errorType = null;
    public long daric;
    public byte[] profile;
    public String message;
    public String path;
    public String string; //help & otherThings
    public Object object;
    public Card card;
    public boolean aBoolean;
    public Game game;
    public Match match;
    public int level;
    public int playerNumber;
    public int mode;
    public int numberOfFlag;
    public int reward;
    public int spriteNumber;
    public int spriteNumberCount;
    public String type;
    public  BattleMessage battleMessage;
}
