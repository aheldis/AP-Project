package model.battle;

import model.card.Card;
import model.land.LandOfGame;
import model.land.Square;

import java.util.ArrayList;

public class Target {
    private Player player;
    private LandOfGame land;
    private ArrayList<Square> targets = new ArrayList<>();
    private String counterAttackName; //ranged hybrid melee
    private String cardType; //minion hero spell
//    private String number; // 0 <=
    private boolean one;
    private boolean row;
    private boolean column;
    private boolean all;
    private boolean random;

    public Target(Player player, LandOfGame land) {
        this.player = player;
        this.land = land;
    }

    public ArrayList<Square> setTarget(Square square /*squari e ke seda mikone */) {

    }


}
