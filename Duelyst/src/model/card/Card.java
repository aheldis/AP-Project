package model.card;


import model.Item.Collectable;
import model.Item.Flag;
import model.battle.Player;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;
import view.enums.RequestSuccessionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class Card {
    protected Change change = new Change();//HAS-A
    protected Target target = new Target();
    protected int mp;
    protected int hp;
    protected int ap;
    protected int turnOfCanNotMove = 0;
    protected int turnOfCanNotAttack = 0;
    protected int turnOfCanNotCounterAttack = 0;
    private String name;
    private CardId cardId;
    private ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    private String counterAttack;
    private int attackRange;
    private int cost;
    //private ArrayList<Buff> buffsOnThisCard;
    private HashMap<Buff, ArrayList<Integer>> buffsOnThisCard = new HashMap<>(); //todo to init perturn as addada kam kone har ki sefr shod disaffect seda kone
    private Square position;
    private LandOfGame landOfGame;
    private int CardNumber;// card number dashte bashan oon shomareE ke to doc e vase sakhtan mode ha, albate mitoonan nadashte bashan ba esm besazim game card ha ro :-? item ha ham hamin tor
    private Player player;
    private boolean canMove = false;
    private boolean canAttack = false;
    private boolean canCounterAttack = true;
    private int hpChangeAfterAttack = 0; //todo mogheE ke be yeki hamle mishe va az hpsh kam mishe bayad ba in jam konin hpSh ro
    private String description;
    //todo


    public void addBuff(Buff buff, int forHowManyTurn) {
        if (!buffsOnThisCard.containsKey(buff))
            buffsOnThisCard.put(buff, new ArrayList<>());
        buffsOnThisCard.get(buff).add(forHowManyTurn);
    }

    public void removeBuffs(boolean goodBuff) {
        ArrayList<Buff> buffsWhichAreGoingToDeleted = new ArrayList<>();
        for (Buff buff : buffsOnThisCard.keySet()) {
            if (buff.isGoodBuff() == goodBuff)
                buffsWhichAreGoingToDeleted.add(buff);
        }
        for (Buff buff : buffsWhichAreGoingToDeleted) {
            buffsOnThisCard.remove(buff);
        }
    }

    public void removeBuff(Buff buff) {
        //kar dige e ke lazem nist? ye check beshe baad
        buffsOnThisCard.remove(buff);
    }

    public void setTargetClass() {

    }

    public void move(Coordinate newCoordination) {
        Square newPosition = landOfGame.passSquareInThisCoordinate(newCoordination);
        if (!canMove) {
            ErrorType.CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION.printMessage();
            return;
        }
        if (canMoveToCoordination(this, newCoordination) && withinRange(newCoordination, 2)) {
            if (this instanceof Minion) {
                if (((Minion) this).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_RESPAWN) {
                    setTarget(this, newPosition);

                    //todo AffectSpecialPower
                }
            }
            if (newPosition.getObject() instanceof Flag) {
                player.addToFlags((Flag) newPosition.getObject());
                player.setFlagSaver(this);
                player.addToTurnForSavingFlag();//todo dead
            }
            if (newPosition.getObject() instanceof Collectable) {
                player.getHand().addToCollectableItem((Collectable) newPosition.getObject());
            }
            setPosition(newPosition);
            newPosition.setObject(this);
            position.setObject(null);
            RequestSuccessionType.MOVE_TO.setMessage(getCardId().getCardIdAsString() + "moved to" + newCoordination.getX() + newCoordination.getY());
            RequestSuccessionType.MOVE_TO.printMessage();
            canMove = false;
            //todo check if RequestSuccessionType works correctly
        } else
            ErrorType.INVALID_TARGET.printMessage();
        //check asare khane
        //can move = false
        //ویژگی هایی که موقع حرکت اعمال میشود

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

    public boolean withinRange(Coordinate coordinate, int range) {
        return getDistance(coordinate) <= range;
    }

    public void setTarget(Card card, Square CardSquare) {
        //todo checkIfAttackedCardIsValid to class target
        //todo check kone ke to classe targete card (one/all/column/row) hast
        //todo age square hast ya distance dare check kone
        //todo bere range ro nega kone har kodoom ke bashe aval bege to range hast ya na
        //todo khone haye to range ro be onvane arrayList bede be ma
        //todo ArrayList e target ro to classe target bere bezare

    }

    public int getDistance(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - position.getXCoordinate()) + Math.abs(coordinate.getY() - position.getYCoordinate());
    }

    public void attack(Card attackedCard) {
        if (attackedCard == null) {
            ErrorType.INVALID_CARD_ID.printMessage();
            return;
        }
        if (this instanceof Spell) {
            return;
        }
        if (!withinRange(attackedCard.position.getCoordinate(), attackRange)) {
            ErrorType.UNAVAILABLE_OPPONENT.printMessage();
            return;
        }
        if (!isCanAttack()) {
            ErrorType.CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION.printMessage();
        }
        attackedCard.changeHp(-ap);
        attackedCard.counterAttack(this);
        setCanAttack(false, 1);
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void changeHp(int number) {
        hp += number;
        if (hp <= 0) {
            player.getGraveYard().addCardToGraveYard(this);
            position.setObject(null);
            position = null;
        }
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

    public void setCanAttack(boolean bool, int forHowManyTurn) {
        canAttack = bool;
        if (bool == false) {
            setTurnOfCanNotAttack(Math.max(getTurnOfCanNotAttack(), forHowManyTurn));
        }
    }

    public Square getPosition() {
        return position;
    }

    public int getTurnOfCanNotAttack() {
        return turnOfCanNotAttack;
    }

    public void setTurnOfCanNotAttack(int number) {
        turnOfCanNotAttack = number;
    }

    public void setPosition(Square position) {
        this.position = position;
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

    public void setCanMove(boolean canMove, int forHowManyTurn) {
        this.canMove = canMove;
        if (canMove == false) {
            setTurnOfCanNotMove(Math.max(getTurnOfCanNotMove(), forHowManyTurn));
        }
    }

    public int getTurnOfCanNotMove() {
        return turnOfCanNotMove;
    }

    public void setTurnOfCanNotMove(int number) {
        turnOfCanNotMove = number;
    }

    public void setCanCounterAttack(boolean bool, int forHowManyTurn) {
        canCounterAttack = bool;
        if (bool == false) {
            setTurnOfCanNotCounterAttack(Math.max(getTurnOfCanNotAttack(), forHowManyTurn));
        }
    }

    public void setCardIdFromClassCardId() {
    }

    public void addNewNameOfCardToCard(String cardName) {
    }

    public void decreaseNumberOfSameCard(String cardName) {

    }

    public void addToTurnsOfPickingUp(int turn) {
        turnsOfPickingUp.add(turn);
    }

    public void addToTurnOfpickingUp(int number) {
        turnsOfPickingUp.add(number);
    }

    public boolean equalCard(String cardId) {
        return this.cardId.getCardIdAsString().equals(cardId);
    }

    public void removeCounterAttack() {//TODO

    }

    public void changeAp(int number) {
        ap += number;
    }

    public void useSpecialPower(Coordinate coordinate) {
        ErrorType error;
        if (this instanceof Spell) {
            error = ErrorType.CAN_NOT_USE_SPECIAL_POWER;
            error.printMessage();
            return;
        }
        if (this instanceof Minion) {
            if (((Minion) this).getHaveSpecialPower()) {
                //todo AffectSpecialPower
                return;
            }

        }
        if (this instanceof Hero) {
            if (((Hero) this).getHaveSpecialPower()) {
                //todo AffectSpecialPower
                return;
            }
        }
        error = ErrorType.DO_NOT_HAVE_SPECIAL_POWER;
        error.printMessage();
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


    public static Card getCardById(String cardId, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardId().getCardIdAsString().equals(cardId))
                return card;
        }
        return null;
    }

    public CardId getCardId() {
        return cardId;
    }

    public void setCardId(CardId cardId) {
        this.cardId = cardId;
    }

    public static ArrayList<Spell> getSpells(ArrayList<Card> cards) {
        ArrayList<Spell> spells = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Spell)
                spells.add((Spell) card);
        }
        return spells;
    }


    public void setHpChangeAfterAttack(int number) {
        hpChangeAfterAttack += number;
    }

    public void setLandOfGame(LandOfGame landOfGame) {
        this.landOfGame = landOfGame;
    }

    public Player getPlayer() {
        return player;
    }

    public Target getTargetClass() {
        return target;
    }

    public boolean isCanMove() {//maybe it have stun buff and can not move
        return canMove;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTurnOfCanNotCounterAttack() {
        return turnOfCanNotCounterAttack;
    }

    public void setTurnOfCanNotCounterAttack(int number) {
        turnOfCanNotCounterAttack = number;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public HashMap<Buff, ArrayList<Integer>> getBuffsOnThisCard() {
        return buffsOnThisCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRange() {
        return 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCounterAttack() {
        return counterAttack;
    }

    public void setCounterAttack(String counterAttack) {
        this.counterAttack = counterAttack;
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

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }


}
