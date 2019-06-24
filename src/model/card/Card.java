package model.card;

import model.battle.OrdinaryPlayer;
import model.battle.Player;
import model.item.ActivationTimeOfItem;
import model.item.Collectible;
import model.item.Flag;
import model.land.LandOfGame;
import model.land.Square;
import model.requirment.Coordinate;
import view.Graphic.BattleScene;
import view.enums.ErrorType;
import view.enums.RequestSuccessionType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Card {
    private static final int DEFAULT = -1;
    protected Change change = new Change();//HAS-A
    protected Target target = new Target();
    protected int mp;
    protected int hp;
    protected int ap;
    protected int attackRange;
    private int turnOfCanNotMove = 0;
    private int turnOfCanNotAttack = 0;
    private int turnOfCanNotCounterAttack = 0;
    private String name;
    private CardId cardId;
    private ArrayList<Integer> turnsOfPickingUp = new ArrayList<>();
    private String counterAttack;
    private int cost;
    //    private HashMap<Buff, ArrayList<Integer>> buffsOnThisCard = new HashMap<>(); //todo to init perturn as addada kam kone har ki sefr shod disaffect seda kone
    private ArrayList<Buff> buffsOnThisCard = new ArrayList<>();
    private Square position;
    private LandOfGame landOfGame;
    private int CardNumber;
    private Player player;
    private boolean canMove = true;
    private boolean canAttack = true;
    private boolean canCounterAttack = true;
    private int hpChangeAfterAttack = 0;
    //  private Group groupInBattle = null;
/*
    public void setGroupInBattle(Group groupInBattle) {
        this.groupInBattle = groupInBattle;
    }

    public Group getGroupInBattle() {
        return groupInBattle;
    }
*/
    /**
     * mogheE ke be yeki hamle mishe va az hpsh kam mishe bayad ba in jam konin hpSh ro
     */
    private String description = "";
    private String pathOfThePicture;
    private String pathOfAnimation;
    private int countOfAnimation = 16;
    private int animationRow = 5;
    private int frameSize;
    private int heightOfPicture;
    private int millis;

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

    public boolean move(Coordinate newCoordination) {
        Square newPosition = landOfGame.passSquareInThisCoordinate(newCoordination);

        if (player instanceof OrdinaryPlayer) {
            if (newPosition == null) {
                ErrorType.CAN_NOT_MOVE_IN_SQUARE.printMessage();
                return false;
            }

            if (!canMove) {
                ErrorType.CAN_NOT_MOVE_BECAUSE_OF_EXHAUSTION.printMessage();
                return false;
            }

            if (!withinRange(newCoordination, 2) || !(canMoveToCoordination(newCoordination))) {
                ErrorType.INVALID_TARGET.printMessage();
                return false;
            }
        }

        /*
        ArrayList<Buff> buffsOfSquare = newPosition.getBuffs();
        for (Buff buff : buffsOfSquare) {
            buff.affect(this);
        }
        square.clearBuffs();
        */

        if (this instanceof Minion) {
            Square square = landOfGame.passSquareInThisCoordinate(newCoordination);
            for (Buff buff : square.getBuffs()) {
                addBuff(buff);
            }
            square.clearBuffs();
        }

        if (newPosition.getFlags().size() > 0) {
            for (Flag flag : newPosition.getFlags()) {
                flag.setOwnerCard(this);
                player.addToOwnFlags(flag);
                flag.getImageView().setOpacity(0);
            }
            newPosition.clearFlags();
        }

        if (newPosition.getObject() instanceof Collectible &&
                ((Collectible) newPosition.getObject()).getTarget().checkTheOneWhoCollects(this)) {
            player.getHand().addToCollectibleItem((Collectible) newPosition.getObject());
            ((Collectible) newPosition.getObject()).setTheOneWhoCollects(this);
            ((Collectible) newPosition.getObject()).getImageView().setOpacity(0);
        }

        position.setObject(null);
        setPosition(newPosition);
        newPosition.setObject(this);

        if (player instanceof OrdinaryPlayer) {
            RequestSuccessionType.MOVE_TO.setMessage(getCardId().getCardIdAsString() +
                    " moved to x: " + newCoordination.getX() + ", y: " + newCoordination.getY());
            RequestSuccessionType.MOVE_TO.printMessage();
        }
        canMove = false;
        for (Flag flag : player.getOwnFlags())
            if (flag.getOwnerCard().equalCard(cardId.getCardIdAsString()))
                flag.setSquare(newPosition);
        return true;
    }

    boolean withinRange(Coordinate coordinate, int range) {
        if (counterAttack.equals("Ranged") && getNormalDistance(coordinate) == 1)
            return false;
        return getManhatanDistance(coordinate) <= range;
    }

    private boolean canMoveToCoordination(Coordinate destination) {
        if (getManhatanDistance(destination) == 2) {
            int x = position.getXCoordinate();
            int y = position.getYCoordinate();

            if (x < 0 || x >= LandOfGame.getNumberOfRows() || y < 0 || y >= LandOfGame.getNumberOfColumns())
                return false;

            int distanceOfX = destination.getX() - position.getXCoordinate();
            int distanceOfY = destination.getY() - position.getYCoordinate();
            if (Math.abs(distanceOfX) == 2 || Math.abs(distanceOfY) == 2) {
                x += distanceOfX / 2;
                y += distanceOfY / 2;
                Square square = landOfGame.getSquares()[x][y];
                if ((square.squareHasMinionOrHero()))
                    return false;
            } else {
                x += distanceOfX;
                Square square = landOfGame.getSquares()[x][y];
                if ((square.squareHasMinionOrHero())) {
                    x -= distanceOfX;
                    y += distanceOfY;
                    square = landOfGame.getSquares()[x][y];
                    if (square.squareHasMinionOrHero())
                        return false;
                }
            }
        }

        return !Objects.requireNonNull(landOfGame.passSquareInThisCoordinate(destination)).squareHasMinionOrHero();
    }

    public void addBuff(Buff buff) {
        buffsOnThisCard.add(buff);
    }

    public boolean equalCard(String cardId) {
        return this.cardId.getCardIdAsString().equals(cardId);
    }

    public int getNormalDistance(Coordinate coordinate) {
        if (Math.abs(coordinate.getX() - position.getXCoordinate()) >= Math.abs(coordinate.getY() - position.getYCoordinate()))
            return Math.abs(coordinate.getX() - position.getXCoordinate());
        return Math.abs(coordinate.getY() - position.getYCoordinate());
    }

    public int getManhatanDistance(Coordinate coordinate) {
        return Math.abs(coordinate.getX() -
                position.getXCoordinate()) +
                Math.abs(coordinate.getY() - position.getYCoordinate());
    }

    public boolean canInsertToCoordination(Coordinate heroCoordination, Coordinate destination) {
        int x = destination.getX();
        int y = destination.getY();
        if (x < 0 || x >= LandOfGame.getNumberOfRows() || y < 0 || y > LandOfGame.getNumberOfColumns())
            return false;

        if (Math.abs(heroCoordination.getX() - x) + Math.abs(heroCoordination.getY() - y) <= 2) {
            Square square = landOfGame.getSquares()[x][y];
            return (this instanceof Spell || !square.squareHasMinionOrHero());
        }
        return false;

    }

    private boolean checkTarget(Square check, String targetType) {
        return check != null && target.checkIfAttackedCardIsValid(check.getObject(), targetType) &&
                target.checkNotItSelf(check.getYCoordinate(), check.getXCoordinate(), position) &&
                target.checkDistance(this, check) && (target.checkIsEnemy(player, check) ||
                target.checkIsAlly(player, check)) && target.checkTheOneWhoDoesTheThing(check.getObject());
    }

    public boolean attack(Card attackedCard, boolean showError) {
        //false:

        if (this instanceof Spell) {
            return false;
        }

        if (attackedCard == null) {
            if (showError)
                ErrorType.INVALID_CARD_ID.printMessage();
            return false;
        }

        if (!canAttack) {
            if (showError)
                ErrorType.CAN_NOT_ATTACK.printMessage();
            return false;
        }


        if (!withinRange(attackedCard
                .position
                .getCoordinate(), attackRange) && player instanceof OrdinaryPlayer) {
            if (showError)
                ErrorType.UNAVAILABLE_OPPONENT.printMessage();
            return false;
        }


        //true:
        if (this instanceof Minion) {
            if (((Minion) this).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_ATTACK) {
                useSpecialPower(position);
                getChange().affect(player, this.getTargetClass().getTargets());
            }
        }

        attackedCard.changeHp(-ap + hpChangeAfterAttack);
        attackedCard.counterAttack(this);
        setCanAttack(false, 0);

        if (player.getMainDeck().getItem() != null &&
                player.getMainDeck().getItem().getActivationTimeOfItem() == ActivationTimeOfItem.ON_ATTACK &&
                player.getMainDeck().getItem().getTarget().checkTheOneWhoDoesTheThing(this)) {
            player.getMainDeck().getItem().setTarget(player);
            player.getMainDeck().getItem().getChange().affect(player, player.getMainDeck().getItem().getTarget().getTargets());
        }
        return true;
    }

    public boolean canAttack(Card opponentCard) {
        return withinRange(opponentCard.position.getCoordinate(), attackRange) && canAttack &&
                !player.getCardsOnLand().contains(opponentCard);
    }

    public void changeHp(int number) {
        hp += number;
        if (hp <= 0) {
            player.getGraveYard().addCardToGraveYard(this, position);
            BattleScene.getSingleInstance().removeCard(this);
            //BattleScene.getSingleInstance().removeNodeFromBoard(groupInBattle);
            position = null;
        }
    }


    public boolean counterAttack(Card theOneWhoAttacked) {
        boolean canCounterAttack = this.canCounterAttack && (
                (counterAttack.equals("melee") && getNormalDistance(theOneWhoAttacked.getPosition().getCoordinate()) == 1)
                        || (counterAttack.equals("ranged") && getNormalDistance(theOneWhoAttacked.getPosition().getCoordinate()) != 1)
                        || (counterAttack.equals("hybrid"))
        );

        if (canCounterAttack)
            theOneWhoAttacked.changeHp(-ap);
        if (theOneWhoAttacked instanceof Minion) {
            if (((Minion) theOneWhoAttacked).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_ATTACK ||
                    ((Minion) theOneWhoAttacked).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_SPAWN ||
                    ((Minion) theOneWhoAttacked).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_DEFEND) {
                useSpecialPower(theOneWhoAttacked.getPosition());
            }
        }
        return canCounterAttack;
    }

    public Card findNearestOne(Target target) {
        int x = position.getXCoordinate();
        int y = position.getYCoordinate();
        int distance = 1;
        boolean[] check = {false, false, false, false, false, false, false, false};
        while (true) {

            boolean allChecked = true;
            for (int i = 0; i < 8; i++)
                if (!check[i]) {
                    allChecked = false;
                    break;
                }

            if (allChecked) {
                distance++;
                if (x + distance >= LandOfGame.getNumberOfRows() && x - distance < 0 &&
                        y + distance >= LandOfGame.getNumberOfColumns() && y - distance < 0)
                    return null;
                for (int i = 0; i < 8; i++)
                    check[i] = false;
            }

            int[] dx = {1, 0, -1, 0, 1, 1, -1, -1};
            int[] dy = {0, 1, 0, -1, -1, 1, 1, -1};
            Random random = new Random();
            int randomNumber = random.nextInt(8);

            if (check[randomNumber])
                continue;

            check[randomNumber] = true;

            if (x + dx[randomNumber] * distance >= LandOfGame.getNumberOfRows() || x + dx[randomNumber] * distance < 0)
                continue;

            if (y + dy[randomNumber] * distance >= LandOfGame.getNumberOfColumns() || y + dy[randomNumber] * distance < 0)
                continue;

            if (landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasHeroAndPassIt() == null) {
                if (landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasMinionAndPassIt() == null)
                    continue;
                if (!target.checkIfAttackedCardIsValid(landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]], change.getTargetType()))
                    continue;
                return landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]].squareHasMinionAndPassIt();
            }
            if (!target.checkIfAttackedCardIsValid(landOfGame.getSquares()[x + dx[randomNumber]][y + dy[randomNumber]], change.getTargetType()))
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

    public int getTurnOfCanNotAttack() {
        return turnOfCanNotAttack;
    }

    public void setTurnOfCanNotAttack(int number) {
        turnOfCanNotAttack = number;
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

    public void changeAp(int number) {
        ap += number;
    }

    public void useSpecialPower(Square cardSquare) {
        System.out.println("Card.useSpecialPower");
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
                BattleScene.getSingleInstance().showSpecialPowerUsed("Minion");
                return;
            }

        }

        if (this instanceof Hero) {
            if (((Hero) this).getHaveSpecialPower()) {
                if (((Hero) this).getTurnNotUsedSpecialPower() <= ((Hero) this).getCoolDown()) {
                    setTarget(cardSquare);
                    change.affect(player, target.getTargets());
                    ((Hero) this).setTurnNotUsedSpecialPower(0);
                    BattleScene.getSingleInstance().showSpecialPowerUsed("Hero");
                    return;
                }
                return;
            }
        }
        error = ErrorType.DO_NOT_HAVE_SPECIAL_POWER;
        error.printMessage();
    }

    public void setHpChangeAfterAttack(int number) {
        hpChangeAfterAttack += number;
    }

    public void setLandOfGame(LandOfGame landOfGame) {
        this.landOfGame = landOfGame;
    }

    public void setCounterAttack(String counterAttack) {
        this.counterAttack = counterAttack;
    }


    public int getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public int getAnimationRow() {
        return animationRow;
    }

    public void setAnimationRow(int animationRow) {
        this.animationRow = animationRow;
    }

    public int getCountOfAnimation() {
        return countOfAnimation;
    }

    public void setCountOfAnimation(int countOfAnimation) {
        this.countOfAnimation = countOfAnimation;
    }

    public String getPathOfAnimation() {
        if (pathOfAnimation == null) {
            if (this instanceof Hero)
                pathOfAnimation = "pics/" + "Hero/" + name + ".gif";
            if (this instanceof Minion)
                pathOfAnimation = "pics/" + "Minion/" + name + ".gif";
            if (this instanceof Spell)
                pathOfAnimation = "pics/" + "Spell/" + name + ".gif";
        }
        return pathOfAnimation;
    }

    public void setPathOfAnimation(String pathOfAnimation) {
        this.pathOfAnimation = pathOfAnimation;
    }

    public String getPathOfThePicture() {
        if (pathOfAnimation == null) {
            if (this instanceof Hero)
                pathOfAnimation = "pics/" + "Hero/" + name + ".png";
            if (this instanceof Minion)
                pathOfAnimation = "pics/" + "Minion/" + name + ".png";
            if (this instanceof Spell)
                pathOfAnimation = "pics/" + "Spell/" + name + ".png";
        }
        return pathOfThePicture;
    }

    public void setPathOfThePicture(String pathOfThePicture) {
        this.pathOfThePicture = pathOfThePicture;
    }

    public Change getChange() {
        return change;
    }

    public ArrayList<Square> getCanMoveToSquares() {
        ArrayList<Square> returnSquares = new ArrayList<>();
        Square[][] squares = landOfGame.getSquares();
        for (int i = 0; i < LandOfGame.getNumberOfRows(); i++)
            for (int j = 0; j < LandOfGame.getNumberOfColumns(); j++)
                if (withinRange(squares[i][j].getCoordinate(), 2)
                        && canMoveToCoordination(squares[i][j].getCoordinate()))
                    returnSquares.add(squares[i][j]);
        return returnSquares;
    }

    public ArrayList<Square> getCanPutInSquares() {
        ArrayList<Square> squares = new ArrayList<>();
        for (int i = -2; i <= 2; i++)
            for (int j = -2; j <= 2; j++) {
                int x = player.getHero().getPosition().getXCoordinate() + i;
                int y = player.getHero().getPosition().getYCoordinate() + j;
                if (x < 0 || x >= LandOfGame.getNumberOfRows() || y < 0 || y >= LandOfGame.getNumberOfColumns())
                    continue;
                if (Math.abs(i) + Math.abs(j) <= 2 && !landOfGame.getSquares()[x][y].squareHasMinionOrHero())
                    squares.add(landOfGame.getSquares()[x][y]);
            }
        return squares;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public ArrayList<Card> getTheCardsInRange() {
        ArrayList<Card> cardsInRange = new ArrayList<>();
        for (Card card : player.getOpponent().getCardsOnLand()) {
            if (withinRange(card.getPosition().getCoordinate(), attackRange))
                cardsInRange.add(card);
        }
        return cardsInRange;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public void setBuffsOnThisCard(ArrayList<Buff> buffsOnThisCard) {
        this.buffsOnThisCard = buffsOnThisCard;
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


        return stringMakerForDesc(description);
    }

    public static String stringMakerForDesc(String string) {
        StringBuilder outPut = new StringBuilder();
        String[] strings = string.split(" ");
        for (int i = 0; i < strings.length; i++) {
            if (i != 0 && i % 3 == 0) {
                outPut.append("\n");
            }
            outPut.append(" ").append(strings[i]);
        }
        return outPut.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCounterAttackName() {
        return counterAttack;
    }

    public Boolean getCanMove() {
        return canMove;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
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

    public void setTarget(Square cardSquare) {
        boolean isSquare = change.getTargetType().equals("square");
        boolean isCard = change.getTargetType().equals("force") || change.getTargetType().equals("minion") ||
                change.getTargetType().equals("hero");
        ArrayList<Square> targets = new ArrayList<>();

        if (isSquare)
            targets.add(cardSquare);

        else if (isCard) {
            if (target.isOne() && checkTarget(cardSquare, change.getTargetType())) {
                targets.add(cardSquare);
            } else if (target.isAll()) {
                for (int i = 0; i < LandOfGame.getNumberOfRows(); i++)
                    for (int j = 0; j < LandOfGame.getNumberOfColumns(); j++) {
                        Square check = landOfGame.getSquares()[i][j];
                        if (checkTarget(check, change.getTargetType()))
                            targets.add(check);
                    }

            } else if (target.isRow()) {

                for (int j = 0; j < LandOfGame.getNumberOfColumns(); j++) {
                    Square check = landOfGame.getSquares()[cardSquare.getXCoordinate()][j];
                    if (checkTarget(check, change.getTargetType()))
                        targets.add(check);
                }

            } else if (target.isColumn()) {

                for (int i = 0; i < LandOfGame.getNumberOfRows(); i++) {
                    Square check = landOfGame.getSquares()[i][cardSquare.getYCoordinate()];
                    if (checkTarget(check, change.getTargetType()))
                        targets.add(check);
                }

            } else if (target.isRandom()) {

                int i, j;
                Random random = new Random();
                Square check = null;
                if (target.getDistance() != DEFAULT) {
                    i = position.getXCoordinate() + random.nextInt(2 * target.getDistance() + 1) - 1;
                    j = position.getYCoordinate() + random.nextInt(2 * target.getDistance() + 1) - 1;
                    if (i == -1) i += 2;
                    if (j == -1) j += 2;
                    if (i == LandOfGame.getNumberOfRows()) i -= 2;
                    if (j == LandOfGame.getNumberOfColumns()) j -= 2;
                    check = landOfGame.getSquares()[i][j];
                } else {
                    while (!checkTarget(check, change.getTargetType())) {
                        i = position.getXCoordinate() + random.nextInt(LandOfGame.getNumberOfRows());
                        j = position.getYCoordinate() + random.nextInt(LandOfGame.getNumberOfColumns());
                        check = landOfGame.getSquares()[i][j];
                    }
                }
                if (checkTarget(check, change.getTargetType()))
                    targets.add(check);

            } else {
                if (target.getDistance() != DEFAULT) {
                    int distance = 0;
                    while (distance <= target.getDistance()) {
                        for (int i = -distance; i <= distance; i++) {
                            if (cardSquare.getXCoordinate() + i >= LandOfGame.getNumberOfRows() || cardSquare.getXCoordinate() + i < 0)
                                continue;
                            for (int j = -distance; j <= distance; j++) {
                                if (cardSquare.getYCoordinate() + j >= LandOfGame.getNumberOfColumns() ||
                                        cardSquare.getXCoordinate() + j < 0)
                                    continue;
                                Square check = landOfGame.getSquares()[cardSquare.getXCoordinate() + i][cardSquare.getYCoordinate() + j];
                                if (checkTarget(check, change.getTargetType()))
                                    targets.add(check);
                            }
                        }
                        distance++;
                    }
                }
                if (target.isSelf())
                    targets.add(position);
            }
        }
        target.setTargets(targets);

    }

    public int getMillis() {
        return millis;
    }
}
