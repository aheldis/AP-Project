package model.account;

import controller.RequestEnum;
import controller.Transmitter;
import controller.server.RequestEnumController;
import controller.server.Server;
import controller.server.SocketClass;
import model.card.Card;
import model.card.CardId;
import view.enums.ErrorType;

import java.util.ArrayList;
import java.util.Date;

public class Bid {
    private static ArrayList<Bid> bids = new ArrayList<>();
    private Account sellerAccount;
    private Account buyerAccount = null;
    private Card card = null;
    private int cost;
    private long startTime;

    public Bid(Account account, Card card, int cost) {
        this.sellerAccount = account;
        this.card = card;
        this.cost = cost;
        startTime = System.currentTimeMillis();
        bids.add(this);
        sendBid();
        runTimer();
    }

    public void sendBid() {
        new Thread(() -> {
            Transmitter transmitter = new Transmitter();
            transmitter.transmitterId = 0;
            transmitter.requestEnum = RequestEnum.ADD_A_BID;
            transmitter.card = card;
            transmitter.cost = cost;
            transmitter.time = startTime;
            for (SocketClass socketClass : Server.getSockets()) {
                RequestEnumController.main(RequestEnum.ADD_A_BID, socketClass, transmitter);
            }
        }).start();

    }

    public void runTimer() {
        new Thread(() -> {
            long elapsedTime = 0L;
            while (elapsedTime < 3 * 60 * 1000) {
                elapsedTime = (new Date()).getTime() - startTime;
            }

            if (buyerAccount != null) {
                Card newCard = Shop.getInstance().getNewCardByName(card.getName());
                new CardId(buyerAccount, newCard, buyerAccount.getCollection().getNumberOfCardId(newCard));
                buyerAccount.getCollection().addToCards(newCard);
                buyerAccount.changeValueOfDaric(-cost);

                Shop.getInstance().sellCard(sellerAccount, card, cost, false);
            }
        }).start();
    }

    public static ArrayList<Bid> getBids() {
        return bids;
    }

    public static ErrorType newBid(Account account, String id, int cost) {
        Collection collection = account.getCollection();
        Card card = collection.passCardByCardId(id);
        if (card == null) {
            return ErrorType.NO_SUCH_CARD_OR_ITEM_IN_COLLECTION;
        }
        if(getBidByCard(card) != null){
            return ErrorType.ALREADY_BID;
        }

        new Bid(account, card, cost);
        return null;
    }

    public static Bid getBidByCard(Card card) {
        for (Bid bid : bids) {
            if (bid.getCard().equalCard(card.getCardId().getCardIdAsString()))
                return bid;
        }
        return null;

    }

    public Card getCard() {
        return card;
    }

    public void setBuyerAccount(Account buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public Account getAccount() {
        return sellerAccount;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public long getStartTime() {
        return startTime;
    }


}
