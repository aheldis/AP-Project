package account;
import view.*;

public class Collection {

    private ArrayList<Spell> spells;
    private ArrayList<Hero> heroes;
    private ArrayList<Minion> minions;
    private Item[] items = new Item[3];


    public void showCardsAndItems() {
        cardsAndItemsView(spells,minions,minions,heroes,items);
    }

    public void searchCardName(String cardName) {

    }

    public void save() {

    }

    private boolean deckHaveBeenExist(String deckName) {

    }

    public void createDeck(String deckName) {

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
