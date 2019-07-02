package controller;

import model.account.Collection;
import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import view.enums.ErrorType;
/**
 * do Not new anything in this class
 * when client receive this class we should check errorType
 * in SHOP we should receive daric
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Transmitter implements Serializable {
    public RequestEnum requestEnum;
    public Collection collection;
    public ArrayList<Card> cards;
    public ArrayList<Item> items;
    public String name;
    public String cardId;
    public Deck deck;
    public ArrayList<Deck> decks;
    public String authToken;
    public ErrorType errorType = null;
    public long daric;
    public byte[] profile;
    public String message;
    public String path;
}
