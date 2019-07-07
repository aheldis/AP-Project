package controller;

import controller.server.SocketClass;
import model.card.Card;

public class BattleMessage {
       public int srcColumn;
       public int srcRow;
       public BattleEnum battleEnum;
       public int desColumn;
       public int desRow;
       public Card card;
       public SocketClass[] socketClasses;
}
