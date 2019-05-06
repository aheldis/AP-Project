package view;

import model.battle.ComputerPlayer;
import model.battle.Match;
import model.battle.Player;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import model.land.LandOfGame;
import model.land.Square;
import view.enums.ErrorType;

public class BattleView {
    private static BattleView singleInstance = null;

    private BattleView() {

    }

    public static BattleView getInstance() {
        if (singleInstance == null) {
            singleInstance = new BattleView();
        }
        return singleInstance;
    }

    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void print(String string) {
        System.out.println(string);
    }

    public void showCardId(String cardId) {
        System.out.println(cardId);
    }

    public void showCardInfo(Card card) {
        if (card == null) {
            ErrorType.INVALID_CARD_ID.printMessage();
            return;
        }
        System.out.println("Name: " + card.getName());
        System.out.println("Cost: " + card.getCost());
        System.out.println("Desc: " + card.getDescription());
        if (!(card instanceof Hero)) {
            System.out.println("MP: " + card.getMp());
        }
        if (!(card instanceof Spell)) {
            System.out.println("AP: " + card.getAp());
            System.out.println("HP: " + card.getHp());
            System.out.println("Range: " + card.getRange());
        }

        if (card instanceof Minion) {
            System.out.println("Combo-ability: " + ((Minion) card).getActivationTimeOfSpecialPower());
        }
    }

    public void endGameView(Match match) {
        System.out.println("GAME ENDED");
        Player winner = match.getWinner();
        Player loser = match.getLoser();
        if (!(winner instanceof ComputerPlayer)) {
            System.out.println(match.getWinner().getUserName() + " is winner");
        } else {
            System.out.println("Computer is winner");
        }

        if (!(loser instanceof ComputerPlayer)) {
            System.out.println(match.getWinner().getUserName() + " is winner");
        } else {
            System.out.println("Computer is loser");
        }
    }

    public void gameHelp(Player player) {

        System.out.println("You can move these cards:");
        for (Card card : player.getCardsOnLand()) {
            if (card.getCanMove())
                System.out.println("    cardId: " + card.getCardId().getCardIdAsString());
        }

        System.out.println("You can attack these cards:");
        for (Card card : player.getCardsOnLand()) {
            if (card.getCanMove()) {
                System.out.println("    cardId: " + card.getCardId().getCardIdAsString() + " can attack:");
                for (Card cardInRange : card.getTheCardsInRange())
                    System.out.println("        cardId: " + cardInRange.getCardId().getCardIdAsString());
            }
        }

        System.out.println("You can insert these cards: ");
        for (Card card : player.getHand().getGameCards()) {
            System.out.println("cardId: " + card.getCardId().getCardIdAsString());
        }

        System.out.println("in these squares: ");
        LandOfGame landOfGame = player.getMatch().getLand();
        Square[][] squares = landOfGame.getSquares();
        for (int i = 0; i < landOfGame.getNumberOfRows(); i++)
            for (int j = 0; j < landOfGame.getNumberOfColumns(); j++)
                if (!squares[i][j].squareHasMinionOrHero())
                    System.out.println("(" + i + "," + j + ")");

    }


}
