package model.card;


import model.battle.Player;
import model.counterAttack.CounterAttack;
import model.counterAttack.Hybrid;
import model.counterAttack.Melee;
import model.counterAttack.Ranged;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;
import view.enums.RequestSuccessionType;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Card {
    protected Change change = new Change();//HAS-A
    protected Target target = new Target();
    private String name;
    private CardId cardId;
    private ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    CounterAttack counterAttack;
    private int cost;
    private ArrayList<Buff> buffsOnThisCard;
    private Square position;
    private LandOfGame landOfGame;
    private int CardNumber;//todo card number dashte bashan oon shomareE ke to doc e vase sakhtan mode ha, albate mitoonan nadashte bashan ba esm besazim game card ha ro :-? item ha ham hamin tor
    protected int mp;
    protected int hp;
    protected int ap;
    private Player player;
    private boolean canMove = false;
    private boolean canAttack = false;
    private boolean canCounterAttack = true;
    protected int turnOfCanNotMove = 0;
    protected int turnOfCanNotAttack = 0;
    protected int turnOfCanNotCounterAttack = 0;

    //todo
    private String playerName;

    private String description;

    public Target getTargetClass() {
        return target;
    }

    public boolean isCanMove() {//maybe it have stun buff and can not move
        return canMove;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setTargetClass() {

    }

    public void setLandOfGame(LandOfGame landOfGame) {
        this.landOfGame = landOfGame;
    }

    public void move(Coordinate coordinate) {
        if (!change.canMove) {
            ErrorType.CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION.printMessage();
            return;
        }
        if (canMoveToCoordination(this, coordinate) && withinRange(coordinate)) {
            position.setObject(null);
            position = Square.findSquare(coordinate);
            if (position != null) {
                position.setObject(this);//todo
            }
            RequestSuccessionType.MOVE_TO.setMessage(getCardId().getCardIdAsString() + "moved to" + coordinate.getX() + coordinate.getY());
            RequestSuccessionType.MOVE_TO.printMessage();
            change.canMove = false;
            //todo check if RequestSuccessionType works correctly
        } else
            ErrorType.INVALID_TARGET.printMessage();
        //check asare khane
        //can move = false
        //ویژگی هایی که موقع حرکت اعمال میشود

    }

    public boolean withinRange(Coordinate coordinate) {
        return getDistance(coordinate) <= 2;
    }

    public void attack(Player opponent, String cardId) {
        Card attackedCard = getCardById(cardId, opponent.getCardsOnLand());
        if (attackedCard == null) {
            ErrorType.INVALID_CARD_ID.printMessage();
            return;
        }
        if (this instanceof Spell) {
            return;
        }
        if (getDistance(attackedCard.position.getCoordinate()) > attackRange) {
            ErrorType.UNAVAILABLE_OPPONENT.printMessage();
            return;
        }
        if (!isCanAttack()) {
            ErrorType.CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION.printMessage();
        }
        attackedCard.changeHp(-ap);
        attackedCard.counterAttack(this);
        setCanAttack(false);
    }

    public void changeTurnOfCanNotAttack(int number) {
        turnOfCanNotAttack += number;
    }

    public void changeTurnOfCanNotCounterAttack(int number) {
        turnOfCanNotCounterAttack += number;
    }

    public void changeTurnOfCanNotMove(int number) {
        turnOfCanNotMove += number;
    }

    public void setTurnOfCanNotAttack(int number) {
        turnOfCanNotAttack = number;
    }

    public void setTurnOfCanNotCounterAttack(int number) {
        turnOfCanNotCounterAttack = number;
    }

    public void setTurnOfCanNotMove(int number) {
        turnOfCanNotMove = number;
    }

    public int getTurnOfCanNotAttack() {
        return turnOfCanNotAttack;
    }

    public int getTurnOfCanNotCounterAttack() {
        return turnOfCanNotCounterAttack;
    }

    public int getTurnOfCanNotMove() {
        return turnOfCanNotMove;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public void setCanCounterAttack(boolean bool) {
        canCounterAttack = bool;
    }

    public void setCanAttack(boolean bool) {
        canAttack = bool;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Buff> getBuffsOnThisCard() {
        return buffsOnThisCard;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCardIdFromClassCardId() {
    }

    public void addNewNameOfCardToCard(String cardName) {
    }

    public void decreaseNumberOfSameCard(String cardName) {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCardId(CardId cardId) {
        this.cardId = cardId;
    }

    public CardId getCardId() {
        return cardId;
    }

    public void addToTurnsOfPickingUp(int turn) {
        turnsOfPickingUp.add(turn);
    }

    public void addToTurnOfpickingUp(int number) {
        turnsOfPickingUp.add(number);
    }

    public boolean equalCard(String cardId) {
        if (this.cardId.getCardIdAsString().equals(cardId))
            return true;
        return false;
    }

    public void removeCounterAttack() {//TODO

    }

    public int getDistance(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - position.getXCoordinate()) + Math.abs(coordinate.getY() - position.getYCoordinate());
    }

    public int getRange() {
        return 0;
    }


    public static ArrayList<Hero> getHeroes(ArrayList<Card> cards) {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Hero)
                heroes.add((Hero) card);
        }
        return heroes;
    }

    public static ArrayList<Minion> getMinions(ArrayList<Card> cards) {
        ArrayList<Minion> minions = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Minion)
                minions.add((Minion) card);
        }
        return minions;
    }

    public static ArrayList<Spell> getSpells(ArrayList<Card> cards) {
        ArrayList<Spell> spells = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Spell)
                spells.add((Spell) card);
        }
        return spells;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCanMove(boolean canMove) {
        canMove = canMove;
    }

    public Boolean getCanMove() {
        return canMove;
    }

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }


    public void changeHp(int number) {
        hp += number;
        if (hp <= 0) {
            player.getGraveYard().addCardToGraveYard(this);
            position.setObject(null);
            position = null;
        }
    }

    public void changeAp(int number) {
        ap += number;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMp() {
        return mp;
    }

    public static Card getCardById(String cardId, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardId().getCardIdAsString().equals(cardId))
                return card;
        }
        return null;
    }

    public void counterAttack(Card theOneWhoAttacked) {
        boolean canCounterAttack = counterAttack.equals("Melee") && getDistance(theOneWhoAttacked.getPosition().getCoordinate()) == 1;
        if (!canCounterAttack)
            canCounterAttack = counterAttack.equals("Ranged") && getDistance(theOneWhoAttacked.getPosition().getCoordinate()) != 1;
        if (!canCounterAttack)
            canCounterAttack = counterAttack.equals("Hybrid");
        if (this.canCounterAttack && canCounterAttack)
            theOneWhoAttacked.changeHp(-ap);
    }

    public boolean canMoveToCoordination(Card card, Coordinate destination) {
        if (card.getDistance(destination) == 2) {
            int x = card.position.getXCoordinate();
            int y = card.position.getYCoordinate();
            int distanceOfX = destination.getX() - card.position.getXCoordinate();
            int distanceOfY = destination.getY() - card.position.getYCoordinate();
            if (Math.abs(distanceOfX) == 2 || Math.abs(distanceOfY) == 2) {
                x -= distanceOfX / 2;
                y -= distanceOfY / 2;
                Square square = landOfGame.getSquares()[x][y];
                if (square.getObject() != null)
                    return false;
            } else {
                x += distanceOfX;
                Square square = landOfGame.getSquares()[x][y];
                if (square.getObject() != null) {
                    x -= distanceOfX;
                    y += distanceOfY;
                    square = landOfGame.getSquares()[x][y];
                    if (square.getObject() != null)
                        return false;
                }
            }
        }
        return Objects.requireNonNull(landOfGame.passSquareInThisCoordinate(destination)).getObject() == null;
    }

    public void setTarget(Card card, Square CardSquare) {
        //todo checkIfAttackedCardIsValid to class target
        //todo check kone ke to classe targete card (one/all/column/row) hast
        //todo age square hast ya distance dare check kone
        //todo bere range ro nega kone har kodoom ke bashe aval bege to range hast ya na
        //todo khone haye to range ro be onvane arrayList bede be ma
        //todo ArrayList e target ro to classe target bere bezare

    }

    //ye method ke ye square ba card begire khoonehaee ke mikhaim roshoon kari konim ro bede  arraylist
}
