package controller;

import model.card.Card;
import model.land.Square;

public class BattleMessage {
       public BattleEnum battleEnum;
       public Square desPosition;
       public Square srcPosition;
       public boolean imPlayer0;
       public String cardId;
       public Card card;
       public Square[][] squares;
}
