package controller;

import model.card.Card;
import model.land.Square;

import java.io.Serializable;

public class BattleMessage implements Serializable {
       public BattleEnum battleEnum;
       public Square desPosition;
       public Square srcPosition;
       public boolean imPlayer0;
       public Card card;
       public Square[][] squares;
}
