package model.battle;

import model.card.ActivationTimeOfSpecialPower;
import model.card.Card;
import model.card.Minion;
import model.card.Spell;
import model.item.Flag;
import model.land.Square;
import view.AccountView;
import view.BattleView;
import view.Graphic.BattleScene;

import java.util.ArrayList;

public class GraveYard {

    private ArrayList<Card> cards = new ArrayList<>();
    private Player player;

    GraveYard(Player player) {
        this.player = player;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card cardHaveBeenExistInGraveYard(String cardId) {
        for (Card card : cards) {
            if (card.getCardId().getCardIdAsString().equals(cardId))
                return card;
        }
        return null;
    }

    public void addCardToGraveYard(Card card, Square position) {
        if(card instanceof Spell){
            cards.add(card);
            return;
        }
        if (card instanceof Minion) {
            if (((Minion) card).getActivationTimeOfSpecialPower() == ActivationTimeOfSpecialPower.ON_DEATH) {
                card.useSpecialPower(position);
            }
        }
        ArrayList<Flag> flags = player.getOwnFlags();
        //String mode = player.getMatch().getMode();
        position.setObject(null);

        for (Flag flag : flags) {
            if (flag.getOwnerCard() != null && flag.getOwnerCard().equalCard(card.getCardId().getCardIdAsString())) {
                player.setTurnForSavingFlag(0);
                player.getOwnFlags().remove(flag);
                //player.setFlagSaver(null);
                position.addToFlags(flag);
                flag.setOwnerCard(null);
                BattleScene.getSingleInstance().addNodeToBoard(card.getPosition().getXCoordinate(),
                        card.getPosition().getYCoordinate(), flag.getImageView());
                flag.getImageView().setOpacity(1);
                break;
            }
        }
        cards.add(card);
    }

    public void showGraveYard() {
        AccountView.getInstance().cardsAndItemsView(Card.getSpells(cards),
                Card.getMinions(cards), Card.getHeroes(cards), new ArrayList<>());
    }

    public void showInfo(Card card) {
        BattleView.getInstance().showCardInfo(card);
    }


}
