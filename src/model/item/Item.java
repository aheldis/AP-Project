package model.item;

import model.battle.Player;
import model.card.Card;
import model.card.Change;
import model.card.Target;
import model.land.Square;

import java.util.ArrayList;
import java.util.Random;

public abstract class Item {
    private String name;
    private String type; //usable Collectible
    private String description;
    private int cost;
    private int itemNumber;
    protected Target target = new Target();
    protected Change change = new Change();
    private String activationTimeOfItemString = "EACH_ROUND";
    private ActivationTimeOfItem activationTimeOfItem = ActivationTimeOfItem.EACH_ROUND;
    //todo if you wanna add player set player in match constructor
    //todo age mikhastim bara item ha player o land o ina bezarim byd to constructor e match meqdar bedim :D

    {
        for (ActivationTimeOfItem activationTime : ActivationTimeOfItem.values()) {
            if (activationTimeOfItemString.equals(activationTime.name())) {
                activationTimeOfItem = activationTime;
            }
        }
    }

    public Item() {
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return
               Card.stringMakerForDesc( description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Change getChange() {
        return change;
    }

    public void setTarget(Player player) {
        ArrayList<Square> targets = new ArrayList<>();

        if (change.getTargetType().equals("hero")) {
            if (target.isEnemy())
                targets.add(player.getOpponent().getHero().getPosition());
            if (target.isAlly())
                targets.add(player.getHero().getPosition());
        }

        if ( change.getTargetType().equals("minion") || target.getCardType().equals("force")) {

            if (target.isOne() && target.isRandom()) {

                Random random = new Random();
                int randomNumber;
                while (targets.size() == 0) {
                    if (target.getDistance() == 0) {
                        randomNumber = random.nextInt(player.getCardsOnLand().size());
                        targets.add(player.getCardsOnLand().get(randomNumber).findNearestOne(target).getPosition());
                        target.setTargets(targets);
                        return;
                    }

                    if (target.isAlly()) {
                        randomNumber = random.nextInt(player.getCardsOnLand().size());
                        if (target.checkIfAttackedCardIsValid(player.getCardsOnLand().get(randomNumber)))
                            targets.add(player.getCardsOnLand().get(randomNumber).getPosition());
                    }

                    if (target.isEnemy()) {
                        randomNumber = random.nextInt(player.getOpponent().getCardsOnLand().size());
                        if (target.checkIfAttackedCardIsValid(player.getOpponent().getCardsOnLand().get(randomNumber)))
                            targets.add(player.getOpponent().getCardsOnLand().get(randomNumber).getPosition());
                    }
                }

            }

            if (target.isOne() && this instanceof Collectible)
                targets.add(((Collectible) this).getSquare());

            if (target.isAll() && target.isAlly())
                for (Card card : player.getCardsOnLand())
                    if (target.checkIfAttackedCardIsValid(card))
                        targets.add(card.getPosition());

        }

        target.setTargets(targets);
    }

    public Target getTarget() {
        if (target == null)
            System.out.println("you're ugly");
        return target;
    }

    public ActivationTimeOfItem getActivationTimeOfItem() {
        return activationTimeOfItem;
    }

}
