package account;
import view.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

public class Collection {

    private ArrayList<Spell> spells;
    private ArrayList<Hero> heroes;
    private ArrayList<Minion> minions;
    private Item[] items = new Item[3];
    //    private Account account;
    private ArrayList<Deck> decks;

//    public Collection (Account account){
//        this.account=account;
//    }

    public ArrayList getDecks(){
        return decks;
    }

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
                System.out.println(card.getCardId().getCardIdAsString());
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
        for(Deck deck:decks){
            if(decks.getName().equals(deckName))
                return true;
        }
        return false;
    }

    public void createDeck(String deckName) {
        if(deckHaveBeenExist(deckName)){
            System.out.println("deck Have been exist");
            return;
        }
        Deck deck =new Deck();
        deck.setName(deckName);
        decks.add(deck);

    }

    public void deleteDeck(String deckName) {
        for(int i=0;i<decks.size();i++){
            if(deck.get(i).getName.equals(deckName)){
                decks.remove(i);
                return;
            }
        }
    }

    private int numberOfCardsExceptHeroesForThisDeck(String deckName) {
        for(Deck item:decks){
            if(item.getName.equals(deckName)){
                return item.getCardsOfDeck().size();
            }
        }
    }

    private int numberOfItemsForThisDeck(String deckName) {
        for(Deck item:decks){
            if(item.getName.equals(deckName)){
                return item.getItemOfDeck().size();
            }
        }
    }

    public int numberOfCards(){//for make cardID
        return minions.size()+spells.size()+heroes.size();

    }

    private boolean heroHaveBeenExistInThisDeck(String deckName, String heroId) {
        for(Deck deck:decks){
            if(deck.getName.equals(deckName)){
                if(deck.hero==null)
                    return false;
                else {
                    if(deck.hero.getCardId().getCardIdAsString().equals(heroId)){
                        return true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean cardHaveBeenExistInThisDeck(String deckName, String cardId) {
        for(Deck deck:decks){
            if(deck.getName.equals(deckName)){
                ArrayList<Card> cards=deck.getCardOfDeck();
                for(Card card: cards){
                    if(cards.getCardId().getCardIdASString().equals(cardId))
                        return true;
                }
                break;
            }
        }
        return false;
    }

    private Card passCardByCardId(String cardId){
        for(Hero hero:heroes){
            if(hero.getCardId().getCardIdAsString().equals(cardId))
                return hero;
        }
        for(Minion minion:minions){
            if(minion.getCardId().getCardIdAsString().equals(cardId))
                return minion;
        }
        for(Spell spell:spells){
            if(spell.getCardId().getCardIdAsString().equals(cardId))
                return spell;
        }
        return null;
    }

    private Item passUsableItemByUsableItemId(String usableItemId){
        for(Item item:items){
            if(item.getUsableId().getUsableIdAsString().equals(usableItemId)){
                return item;
            }
        }
        return null;
    }

    public void addCardToThisDeck(String cardId, String deckName) {
        Deck deck=null;
        for(Deck item :decks){
            if(item.getName.equals(deckName))
                deck=item;
        }
        if(deck==null)
            return;

        if(deck.getCardsOfDeck().size()==20)
        {
            System.out.println("you have 20 cards you can't add card");
            return;
        }

        Card card= passCardByCardId(cardId);
        if(card==null){
            System.out.println("you don't have this card in your collection");
            return;
        }
        else {
            if(cardHaveBeenExistInThisDeck(deckName,cardId))
            {
                System.out.println("card have been exist in this deck");
                return;
            }
            deck.addToCardsOfDeck(card);
        }

    }

    public void addHeroToThisDeck(String heroId, String deckName) {
        if(heroHaveBeenExistInThisDeck(deckName,heroId))
            return ;
        Deck deck=null;
        for(Deck item :decks){
            if(item.getName.equals(deckName))
                deck=item;
        }
        if(deck==null)
            return;
        Card card= passCardByCardId(heroId);
        if(card==null){
            System.out.println("you don't have this Hero in your collection");
            return;
        }
        else {
            if(heroHaveBeenExistInThisDeck(deckName,heroId))
            {
                System.out.println("Hero have been exist in this deck");
                return;
            }
            deck.setHero(card);
        }

    }

    public void addItemToThisDeck(String usableItemId, String deckName){
        Deck deck=null;
        for(Deck item :decks){
            if(item.getName.equals(deckName))
                deck=item;
        }
        if(deck==null)
            return;

        if(deck.item!=null){
            System.out.println("you have one item in this deck");
            return;
        }
        Item item = passUsableItemByUsableItemId(usableItemId);
        if(item==null)
        {
            System.out.println("you don't have this item in collection");
            return;
        }
//        if(item.getUsableCardId().getUsableCardIdAsString().equals(usableItemId)){
//            System.out.println("this item have been exist in this deck");
//            return;
//        }
        deck.addItemToDeck(item);


    }


    public void removeCardFromDeck(String cardId, String deckName) {
        if(!cardHaveBeenExistInThisDeck(deckName,cardId)) {
            System.out.println("you don't have this card in this deck");
            return;
        }
        Deck deck=null;
        for(Deck item :decks){
            if(item.getName.equals(deckName))
                deck=item;
        }
        if(deck==null)
            return;

        Card card= passCardByCardId(cardId);

        if(card==null){
            System.out.println("you don't have this card");
        }
        else
            deck.removeFromCardsOFDeck(card);
    }

    public void removeHeroFromDeck(String heroId, String deckName) {
        Deck deck=null;
        for(Deck item :decks){
            if(item.getName.equals(deckName))
                deck=item;
        }
        if(deck==null)
            return;
        Card card= passCardByCardId(heroId);

        if(card.getCardId().getCardIdAsString().equals(heroId)){
            deck.removeFromCardsOFDeck(card);

        }
        else
            System.out.println("you don't have this Hero");

    }

    public void removeItemFromDeck(String usableItemId, String deckName){
        Deck deck=null;
        for(Deck item :decks){
            if(item.getName.equals(deckName))
                deck=item;
        }
        if(deck==null)
            return;

//        if(deck.item!=null){
//            System.out.println("you have one item in this deck");
//            return;
//        }
        Item item = passUsableItemByUsableItemId(usableItemId);
        if(item==null)
        {
            System.out.println("you don't have this item in collection");
            return;
        }
        if(item.getUsableCardId().getUsableCardIdAsString().equals(usableItemId)){
            deck.addItemToDeck(item);
            return;
        }
        else
            System.out.println("you don't have this item in this deck");


    }

    public boolean validateDeck(String deckName) {
        for(Deck deck:decks){
            if(deck.getName().equals(deckName)){
                if(deck.validate())
                    return true;

            }
        }
        return false;
    }

    public void showAlldecks() {

    }

    public void showThisDeck(String deckName) {

    }

    public void helpOfCollection() {

    }

}
