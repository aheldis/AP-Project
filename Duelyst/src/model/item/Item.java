package model.item;

import model.battle.Player;
import model.card.Card;
import model.card.Change;
import model.card.Target;
import model.land.Square;

import java.util.ArrayList;
import java.util.Random;

public abstract class Item {
    private String name = new String();
    private String type; //usable Collectible
    private String description;
    private int cost;
    private int itemNumber;
    protected Target target = new Target();
    protected Change change = new Change();
    //todo if you wanna add player set player in match constructor

    public Item() {

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
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

        if (target.getCardType().equals("hero")) {
            if (target.isEnemy())
                targets.add(player.getOpponent().getHero().getPosition());
            if (target.isAlly())
                targets.add(player.getHero().getPosition());
        }

        if (target.getCardType().equals("minion") || target.getCardType().equals("force")) {

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
                        if (target.getCardType().equals("minion"))
                            randomNumber = random.nextInt(player.getCardsOnLand().size());
                        else
                            randomNumber = random.nextInt(player.getCardsOnLand().size() + 1);
                        if (randomNumber == player.getCardsOnLand().size() && target.checkIfAttackedCardIsValid(player.getHero()))
                            targets.add(player.getHero().getPosition());
                        else if (target.checkIfAttackedCardIsValid(player.getCardsOnLand().get(randomNumber)))
                            targets.add(player.getCardsOnLand().get(randomNumber).getPosition());
                    }

                    if (target.isEnemy()) {
                        if (target.getCardType().equals("minion"))
                            randomNumber = random.nextInt(player.getOpponent().getCardsOnLand().size());
                        else
                            randomNumber = random.nextInt(player.getOpponent().getCardsOnLand().size() + 1);
                        if (randomNumber == player.getOpponent().getCardsOnLand().size() &&
                                target.checkIfAttackedCardIsValid(player.getOpponent().getHero()))
                            targets.add(player.getOpponent().getHero().getPosition());
                        else if (target.checkIfAttackedCardIsValid(player.getOpponent().getCardsOnLand().get(randomNumber)))
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
        return target;
    }
}
