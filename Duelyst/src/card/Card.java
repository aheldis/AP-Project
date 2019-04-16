package card;

public abstract class Card {
    String name;
    CardId cardId;

    public void setCardIdFromClassCardId(){
    }

    public void addNewNameOfCardToCard(String cardName){
    }

    public void decreaseNumberOfSameCard(String cardName){

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setMP(int mp){

    }

    public int getMP(){

    }

    public void addToTurnOfPickingUp(int turn){

    }
}
