package controller;

import model.card.Card;
import model.land.Square;

public class BattleMessage {
       public int srcColumn;
       public int srcRow;
       public BattleEnum battleEnum;
       public int desColumn;
       public int desRow;
       public Square desPosition;
       public Square srcPosition;
       public boolean imPlayer0;
       public Card card;
       public Square[][] squares;
}
