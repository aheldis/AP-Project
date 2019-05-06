package model.card;

import model.battle.OrdinaryPlayer;
import model.battle.Player;
import model.item.ActivationTimeOfItem;
import model.item.Collectible;
import model.item.Flag;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.enums.ErrorType;
import view.enums.RequestSuccessionType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Card {
    protected Change change = new Change();//HAS-A
    protected Target target = new Target();
    protected int mp;
    protected int hp;
    protected int ap;
    private int turnOfCanNotMove = 0;
    private int turnOfCanNotAttack = 0;
    private int turnOfCanNotCounterAttack = 0;
    private String name;
    private CardId cardId;
    private ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    private String counterAttack;
    protected int attackRange;
    private int cost;
    //    private HashMap<Buff, ArrayList<Integer>> buffsOnThisCard = new HashMap<>(); //todo to init perturn as addada kam kone har ki sefr shod disaffect seda kone
    private ArrayList<Buff> buffsOnThisCard = new ArrayList<>();
    private Square position;
    private LandOfGame landOfGame;
    private int CardNumber;
    private Player player;
    private boolean canMove = false;
    private boolean canAttack = false;
    private boolean canCounterAttack = true;
    private int hpChangeAfterAttack = 0;
    /**
     * mogheE ke be yeki hamle mishe va az hpsh kam mishe bayad ba in jam konin hpSh ro
     */
    private String description;
    private static final int DEFAULT = -1;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Change getChange() {
        return change;
    }

    public void addBuff(Buff buff) {
        buffsOnThisCard.add(buff);
    }

    public void removeBuffs(boolean goodBuff) {
        ArrayList<Buff> buffsWhichAreGoingToDeleted = new ArrayList<>();
        for (Buff buff : buffsOnThisCard) {
            if (buff.isGoodBuff() == goodBuff && !buff.isContinuous()) {
                if (buff.isHaveUnAffect())
                    buff.unAffect(this);
                buffsWhichAreGoingToDeleted.add(buff);
            }
        }
        for (Buff buff : buffsWhichAreGoingToDeleted) {
            buffsOnThisCard.remove(buff);
        }
    }

    public void removeBuff(String buffName) {
        for (Buff buff : buffsOnThisCard) {
            if (buff.getName().equals(buffName) && !buff.isContinuous()) {
                if (buff.isHaveUnAffect())
                    buff.unAffect(this);
                buffsOnThisCard.remove(buff);
                return;
            }
        }
    }


    public void move(Coordinate newCoordination) {
        Square newPosition = landOfGame.passSquareInThisCoordinate(newCoordination);

        if (player instanceof OrdinaryPlayer) {
            if (newPosition == null) {
                ErrorType.CAN_NOT_MOVE_IN_SQUARE.printMessage();
                return;
            }

            if (!canMove) {
                ErrorType.CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION.printMessage();
                return;
            }

            if (!(canMoveToCoordination(this, newCoordination) && withinRange(newCoordination, 2))) {
                ErrorType.INVALID_TARGET.printMessage();
                return;
            }
        }

        ArrayList<Buff> buffsOfSquare = newPosition.getBuffs();
        for (Buff buff : buffsOfSquare) {
            buff.affect(this);
        }

        if (this instanceof Minion) {
            Square square = landOfGame.passSquareInThisCoordinate(newCoordination);
            for (Buff buff : square.getBuffs()) {
                this.addBuff(buff);
            }
        }

        if (newPosition.getObject() instanceof Flag) {
            ((Flag) newPosition.getObject()).setOwnerCard(this);
            player.addToOwnFlags((Flag) newPosition.getObject());
            //player.setFlagSaver(this);
            player.addToTurnForSavingFlag();
        }

        if (newPosition.getObject() instanceof Collectible &&
                ((Collectible) newPosition.getObject()).getTarget().checkTheOneWhoCollects(this)) {
            player.getHand().addToCollectibleItem((Collectible) newPosition.getObject());
            ((Collectible) newPosition.getObject()).setTheOneWhoCollects(this);
        }

        setPosition(newPosition);
        newPosition.setObject(this);
        position.setObject(null);
        if (player instanceof OrdinaryPlayer) {
            RequestSuccessionType.MOVE_TO.setMessage(getCardId().getCardIdAsString() +
                    " moved to x: " + newCoordination.getX() + ", y: " + newCoordination.getY());
            RequestSuccessionType.MOVE_TO.printMessage();
        }
        canMove = false;
        for (Flag flag : player.getOwnFlags())
            if (flag.getOwnerCard().equalCard(cardId.getCardIdAsString()))
                flag.setSquare(newPosition);
    }

    public boolean canMoveToCoordination(Card card, Coordinate destination) {
        if (card.getManhatanDistance(destination) == 2) {
            int x = card.position.getXCoordinate();
            int y = card.position.getYCoordinate();

            if (x < 0 || x > 4 || y < 0 || y > 8)
                return false;

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
        if (counterAttack.equals("Ranged") && getNormalDistance(coordinate) == 1)
            return false;
        return getManhatanDistance(coordinate) <= range;
    }

    private boolean checkTarget(Square check) {
        return target.checkIfAttackedCardIsValid(check.getObject()) &&
                target.checkNotItSelf(check.getYCoordinate(), check.getXCoordinate(), position) &&
                target.checkDistance(this, check) && target.checkIsEnemy(player, check) &&
                target.checkIsAlly(player, check);
    }

    public void setTarget(Square cardSquare) {
        boolean isSquare = change.getTargetType().equals("square");
        boolean isCard = change.getTargetType().equals("card");
        ArrayList<Square> targets = new ArrayList<>();
        if (isSquare)
            targets.add(cardSquare);
        else if (isCard) {

            if (target.isOne() && checkTarget(cardSquare)) {
                targets.add(cardSquare);

            } else if (target.isAll()) {

                for (int i = 0; i < landOfGame.getNumberOfRows(); i++)
                    for (int j = 0; j < landOfGame.getNumberOfColumns(); j++) {
                        Square check = landOfGame.getSquares()[i][j];
                        if (checkTarget(check))
                            targets.add(check);
                    }

            } else if (target.isRow()) {

                for (int j = 0; j < landOfGame.getNumberOfColumns(); j++) {
                    Square check = landOfGame.getSquares()[cardSquare.getYCoordinate()][j];
                    if (checkTarget(check))
                        targets.add(check);
                }

            } else if (target.isColumn()) {

                for (int i = 0; i < landOfGame.getNumberOfRows(); i++) {
                    Square check = landOfGame.getSquares()[i][cardSquare.getXCoordinate()];
                    if (checkTarget(check))
                        targets.add(check);
                }

            } else if (target.isRandom()) {

                int i, j;
                Random random = new Random();
                if (target.getDistance() != DEFAULT) {
                    i = position.getYCoordinate() + random.nextInt(2 * target.getDistance() + 1) - 1;
                    j = position.getXCoordinate() + random.nextInt(2 * target.getDistance() + 1) - 1;
                    if (i == -1) i += 2;
                    if (j == -1) j += 2;
                    if (i == landOfGame.getNumberOfRows()) i -= 2;
                    if (j == landOfGame.getNumberOfColumns()) j -= 2;
                } else {
                    i = position.getYCoordinate() + random.nextInt(landOfGame.getNumberOfRows());
                    j = position.getXCoordinate() + random.nextInt(landOfGame.getNumberOfColumns());
                }
                targets.add(landOfGame.getSquares()[i][j]);

            }
        }
        target.setTargets(targets);

    }

    public int getManhatanDistance(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - position.getXCoordinate()) +
                Math.abs(coordinate.getY() - position.getYCoordinate());
    }

    public int getNormalDistance(Coordinate coordinate) {
        if (Math.abs(coordinate.getX() - position.getXCoordinate()) >= Math.abs(coordinate.getY() - position.getYCoordinate()))
            return Math.abs(coordinate.getX() - position.getXCoordinate());
        return Math.abs(coordinate.getY() - position.getYCoordinate());
    }

    public void attack(Card attackedCard) {
        if (this instanceof Spell) {
            return;
        }

        if (player instanceof OrdinaryPlayer) {
            if (attackedCard == null) {
                ErrorType.INVALID_CARD_ID.printMessage();
                return;
            }

            if (!canAttack) {
                ErrorType.CAN_NOT_ATTACK.printMessage();
                return;
            }
        }
        if (this instanceof Minion) {
            if (((Minion) this).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_ATTACK) {
                useSpecialPower(position);
                getChange().affect(player, this.getTargetClass().getTargets());
            }
        }

        if (!withinRange(attackedCard.position.getCoordinate(), attackRange) && player instanceof OrdinaryPlayer) {
            ErrorType.UNAVAILABLE_OPPONENT.printMessage();
            return;
        }
        attackedCard.changeHp(-ap + hpChangeAfterAttack);
        attackedCard.counterAttack(this);
        setCanAttack(false, 1);

        if (player.getMainDeck().getItem().getActivationTimeOfItem() == ActivationTimeOfItem.ON_ATTACK &&
                player.getMainDeck().getItem().getTarget().checkTheOneWhoDoesTheThing(this)) {
            player.getMainDeck().getItem().setTarget(player);
            player.getMainDeck().getItem().getChange().affect(player, player.getMainDeck().getItem().getTarget().getTargets());
        }
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void changeHp(int number) {
        hp += number;
        if (hp <= 0) {
            player.getGraveYard().addCardToGraveYard(this, position);
            position = null;
        }
    }

    public void counterAttack(Card theOneWhoAttacked) {
        boolean canCounterAttack = counterAttack.equals("Melee") &&
                getNormalDistance(theOneWhoAttacked.getPosition().getCoordinate()) == 1;
        if (!canCounterAttack)
            canCounterAttack = counterAttack.equals("Ranged") &&
                    getNormalDistance(theOneWhoAttacked.getPosition().getCoordinate()) != 1;
        if (!canCounterAttack)
            canCounterAttack = counterAttack.equals("Hybrid");
        if (this.canCounterAttack && canCounterAttack)
            theOneWhoAttacked.changeHp(-ap);
        if (theOneWhoAttacked instanceof Minion) {
            if (((Minion) theOneWhoAttacked).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_ATTACK ||
                    ((Minion) theOneWhoAttacked).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_SPAWN ||
                    ((Minion) theOneWhoAttacked).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_DEFEND) {
                useSpecialPower(theOneWhoAttacked.getPosition());
            }
        }
    }

    public Card findNearestOne(Target target) {
        int x = position.getXCoordinate();
        int y = position.getYCoordinate();
        int distance = 1;
        boolean check[] = {false, false, false, false, false, false, false, false};
        while (true) {

            boolean allChecked = true;
            for (int i = 0; i < 8; i++)
                if (!check[i]) {
                    allChecked = false;
                    break;
                }

            if (allChecked) {
                distance++;
                if (x + distance >= landOfGame.getNumberOfColumns() && x - distance < 0 &&
                        y + distance >= landOfGame.getNumberOfRows() && y - distance < 0)
                    return null;
                for (int i = 0; i < 8; i++)
                    check[i] = false;
            }

            int dx[] = {1, 0, -1, 0, 1, 1, -1, -1};
            int dy[] = {0, 1, 0, -1, -1, 1, 1, -1};
            Random random = new Random();
            int randomNumber = random.nextInt();

            if (check[randomNumber])
                continue;

            check[randomNumber] = true;

            if (x + dx[randomNumber] * distance >= landOfGame.getNumberOfColumns() || x + dx[randomNumber] * distance < 0)
                continue;

            if (y + dy[randomNumber] * distance >= landOfGame.getNumberOfRows() || y + dy[randomNumber] * distance < 0)
                continue;

            if (landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasHeroAndPassIt() == null) {
                if (landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasMinionAndPassIt() == null)
                    continue;
                if (!target.checkIfAttackedCardIsValid(landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]]))
                    continue;
                return landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasMinionAndPassIt();
            }
            if (!target.checkIfAttackedCardIsValid(landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]]))
                continue;
            return landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasHeroAndPassIt();
        }
    }

    public void setCanAttack(boolean bool, int forHowManyTurn) {
        canAttack = bool;
        if (!bool) {
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
        if (!canMove) {
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
        if (!bool) {
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


    public void changeAp(int number) {
        ap += number;
    }

    public void useSpecialPower(Square cardSquare) {

        ErrorType error;
        if (this instanceof Spell) {
            error = ErrorType.CAN_NOT_USE_SPECIAL_POWER;
            error.printMessage();
            return;
        }

        if (this instanceof Minion) {
            if (((Minion) this).getHaveSpecialPower()) {
                setTarget(cardSquare);
                change.affect(player, target.getTargets());
                return;
            }

        }

        if (this instanceof Hero) {
            if (((Hero) this).getHaveSpecialPower()) {
                if (((Hero) this).getTurnNotUsedSpecialPower() <= ((Hero) this).getCoolDown()) {
                    setTarget(cardSquare);
                    change.affect(player, target.getTargets());
                    return;
                }
                ((Hero) this).setTurnNotUsedSpecialPower(0);
                return;
            }
        }
        error = ErrorType.DO_NOT_HAVE_SPECIAL_POWER;
        error.printMessage();
    }

    public static ArrayList<Hero> getHeroes(ArrayList<Card> cards) {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Card card : cards)
            if (card instanceof Hero)
                heroes.add((Hero) card);
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

    public ArrayList<Buff> getBuffsOnThisCard() {
        return buffsOnThisCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRange() {
        return attackRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCounterAttackName() {
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

    public Target getTarget() {
        return target;
    }
}
