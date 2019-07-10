package controller.client;

import controller.BattleEnum;
import controller.RequestEnum;
import controller.Transmitter;
import javafx.application.Platform;
import model.battle.ComputerPlayer;
import model.battle.Match;
import model.battle.Player;
import model.card.Card;
import model.land.Square;
import model.requirment.Coordinate;
import view.Graphic.*;

import java.util.ArrayList;

public class TransferController {
    private static Transmitter fromServerTransmitter;
    private static ClientIOHandler clientIOhandler = Client.getClientIOHandler();
    private static ArrayList<Transmitter> messages = new ArrayList<>();

    public static Transmitter main(RequestEnum requestEnum, Transmitter transmitter) {
        System.out.println("sending: " + requestEnum.name());
        fromServerTransmitter = transmitter;
        transmitter.requestEnum = requestEnum;
        switch (requestEnum) {
            case LOGOUT:
            case COLLECTION_UPDATE:
            case EXPORT_DECK:
            case SEND_MESSAGE:
            case NEW_CARD_ID:
            case ENTER_COLLECTION:
            case EXIT_FROM_CHAT:
            case END_OF_CLIENT:
            case ACCEPT_PLAY:
            case DECLINE_PLAY:
            case CANCEL_START_MATCH:
            case START_MATCH:
            case GET_BIDS:
            case CHANGE_TURN:
                fromServerTransmitter = clientIOhandler.transfer(false, transmitter);
                return fromServerTransmitter;
            case SIGN_UP:
            case LOGIN:
            case IMPORT_DECK:
            case SHOP_BUY:
            case SHOP_HELP:
            case SHOP_SEARCH:
            case SHOP_CARDS:
            case SHOP_ITEMS:
            case SHOP_DARIC:
            case PROFILE:
            case DECKS:
            case START_STORY_GAME:
            case ENTER_CHAT:
            case START_CUSTOM_GAME:
            case SHOP_SELL:
            case MAKE_NEW_CARD:
            case NEW_CARD_ARRAY_LISTS:
            case MAIN_DECK:
            case GET_COLLECTION:
            case ALL_ACCOUNT:
            case NEW_BID:
            case BID_NEW_COST:
            case BATTLE:
                fromServerTransmitter = clientIOhandler.transfer(true, transmitter);
                return fromServerTransmitter;
            case CHECK_NEW_MESSAGE:
                if (messages.size() != 0) {
                    fromServerTransmitter = messages.get(0);
                    messages.remove(0);
                }
                return fromServerTransmitter;

        }
        return fromServerTransmitter;

    }


    static void fromServerTransmitter(Transmitter transmitter) {
        System.out.println(transmitter);
        switch (transmitter.requestEnum) {
            case NEW_MESSAGE:
                System.out.println("new message");
                GlobalChatScene.getNewMessage(transmitter);
                break;
            case PLAY_REQUEST:
                System.out.println("play request - transfer controller");
                StageLauncher.getNewRequest(transmitter);
                break;
            case DECLINE_PLAY:
                SelectGameScene.decline();
                break;
            case BATTLE:
                battleHandler(transmitter);
                break;
            case CANCEL_START_MATCH:
                StageLauncher.deleteRequestGroup();
                break;
            case ADD_A_BID:
                System.out.println("ADD A BID in fromServerblah");
                ShopScene.addABidRow(transmitter.card, transmitter.cost, transmitter.time);
                break;
            case CHANGE_TURN:
                Match match = BattleScene.getSingleInstance().getMatch();
                Platform.runLater(() -> {
                    match.yourTurnAnimation(-1);
                    match.changeTurn(false, false);
                });
                break;
        }
    }

    private static void battleHandler(Transmitter transmitter) {
        System.out.println("battleHandler");
        if (transmitter.battleEnum == BattleEnum.START_GAME) {
            SelectGameScene.startGame(transmitter.match,
                    transmitter.numberOfMap, transmitter.imPlayer0);
            if (!transmitter.imPlayer0)
                Platform.runLater(() -> transmitter.match.waitGraphic(0));
            return;
        }
        BattleScene battleScene = BattleScene.getSingleInstance();
        Player player = battleScene.getMatch().getPlayers()[1 - battleScene.getPlayerNumber()];
        Card card = Card.getCardById(transmitter.name, player.getMainDeck().getCardsOfDeck());
        if (card == null && player.getMainDeck().getHero().getCardId().getCardIdAsString().equals(transmitter.name))
            card = player.getMainDeck().getHero();
        assert card != null;
        Card finalCard = card;
        Coordinate coordinate = transmitter.desPosition;
        switch (transmitter.battleEnum) {
            case INSERT: {
                System.out.println("put: " + card.getCardId().getCardIdAsString());
                Platform.runLater(() -> {
                    player.putCardOnLand(finalCard, coordinate, battleScene.getMatch().getLand(), false);
                    battleScene.addCardToBoard(coordinate.getX(), coordinate.getY(), finalCard,
                            "Breathing", null, false, !battleScene.isImPlayer1(), false);
                });
                break;
            }
            case MOVE: {
                System.out.println("move: " + card.getCardId().getCardIdAsString());
                Square firstPosition = card.getPosition();
                Platform.runLater(() -> {
                    finalCard.move(coordinate, false);
                    ComputerPlayer.moveAnimation(firstPosition.getXCoordinate(),
                            firstPosition.getYCoordinate(), finalCard);
                });
                break;
            }
            case ATTACK:
                System.out.println("attack");
                Platform.runLater(() -> {
                    battleScene.addCardToBoard(finalCard.getPosition().getXCoordinate(), finalCard.getPosition().getYCoordinate(),
                            finalCard, "ATTACK", battleScene.getCardsHashMap().get(finalCard), false,
                            !battleScene.isImPlayer1(), false);
                    Player me = battleScene.getMatch().getPlayers()[battleScene.getPlayerNumber()];
                    Card opponentCard = Card.getCardById(transmitter.cardId, me.getCardsOnLand());
                    assert opponentCard != null;
                    if (opponentCard.counterAttack(finalCard))
                        battleScene.addCardToBoard(opponentCard.getPosition().getXCoordinate(),
                                opponentCard.getPosition().getYCoordinate(), opponentCard, "ATTACK",
                                battleScene.getCardsHashMap().get(opponentCard),
                                false, battleScene.isImPlayer1(), true);
                });
                break;

        }
    }

}
