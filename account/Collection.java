package account;
import view.*;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

public class Collection {

    private ArrayList<Spell> spells;
    private ArrayList<Hero> heroes;
    private ArrayList<Minion> minions;
    private Item[] items = new Item[3];


    public void showCardsAndItems() {
        cardsAndItemsView(spells,minions,minions,heroes,items);
    }

    public void searchCardName(String cardName)
    {
        boolean have=false;
        ArrayList<Card> cards=new ArrayList<Card>();
        cards.addAll(spells);
        cards.addAll(minions);
        cards.addAll(heroes);
        for(Card card:cards){
            if(card.getCardId.getCardName.equals(cardName)){
                System.out.println(card.getCardId().getCardId());
                have=true;
            }
        }
        if (!have)
            System.out.println("You don't have this Card");

    }
    public void searchItemName(String itemName){
        boolean have=false;
        for(Item item:items){
            if(item instanceof Usable){
                if(item.getUsableId().getItemName().equals(itemName)){
                    System.out.println(item.getUsableId().getUsableId());
                    have=true;
                }
            }
            if(item instanceof Collectable){
                if(item.getCollectableId().getItemName().equals(itemName)){
                    System.out.println(item.getCollectableId().getCollectableId());
                    have=true;
                }
            }
            if(!have){
                System.out.println("You don't have this Item");
            }
        }

    }

    public void save() {

    }

    private boolean deckHaveBeenExist(String deckName) {

    }

    public void createDeck(String deckName) {
        if(deckHaveBeenExist(deckName)){
            System.out.println("deck Have been exist");
            return;
        }
        
    }

    public int deleteDeck(String deckName) {

    }

    private int numberOfCardsForThisDeck(String deckName) {

    }

    private int numberOfHerosForThisDeck(String deckName) {

    }

    private boolean heroHaveBeenExistInThisDeck(String deckName, int heroId) {

    }

    private boolean cardHaveBeenExistInThisDeck(String deckName, int cardId) {

    }

    public void addCard(int cardId, String deckName) {

    }

    public void addHero(int heroId, String deckName) {

    }

    public void removeCard(int cardId, String deckName) {

    }

    public void removeHero(int heroId, String deckName) {

    }

    public void validateDeck(String deckName) {

    }

    public void showAlldecks() {

    }

    public void showThisDeck(String deckName) {

    }

    public void helpOfCollection() {

    }

}
