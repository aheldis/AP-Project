package account;

public class Shop {
    public static Shop singleInstance = null;
    private ArrayList<Card> cards;
    private ArrayList<Item> items;

    private boolean cardOrItemExist(String name){

    }

    private CardId getExistingCardId(String name){

    }

    private CardId getNewCardId(Card card){

    }

    public void addCard(Card card){

    }

    public void addItem(Item item){

    }

    public int search(String name){

    }

    public Card buy(Account account, String name){

    }

    public void sell(Account account, CardId cardId){

    }

    public static void help(){

    }

    public static Shop getInstance(){
        if(singleInstance == null)
            singleInstance = new Shop;
        return singleInstance;
    }
}
