package controller;

import model.battle.Deck;
import model.card.Card;
import model.item.Item;
import view.enums.ErrorType;
/**
 * do Not new anything in this class
 * when client receive this class we should check errorType
 * in SHOP we should receive daric
 */

import java.util.ArrayList;

public class Transferor {
    private RequestEnum requestEnum;
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private String name;
    private String cardId;
    private Deck deck;
    private ArrayList<Deck> decks;
    private String authToken;
    private ErrorType errorType=null;
    private long daric;


}
