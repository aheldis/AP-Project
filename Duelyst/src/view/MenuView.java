package view;

import model.item.Collectable;
import model.item.Flag;
import model.account.Account;
import model.account.AllAccount;
import model.battle.*;
import model.card.Card;
import model.card.Minion;
import model.card.Spell;
import view.enums.ErrorType;

public class MenuView {
    private static MenuView singleInstance = null;
    private static final int NOT_VALID = -1;

    private MenuView() {
    }

    public static MenuView getInstance() {
        if (singleInstance == null)
            singleInstance = new MenuView();
        return singleInstance;
    }

    public void helpForAccountMenu() {
        System.out.println("Enter Collection - Go to your collection");
        System.out.println("Enter Shop - Go to shop");
        System.out.println("Enter Battle - Start a game");
        System.out.println("Enter Help - Show help of main menu");
        System.out.println("Enter Exit - Log out from your account");
    }

    public void helpForMainMenu() {
        System.out.println("Enter Create Account - Create a new account");
        System.out.println("Enter Save - To save ");
        System.out.println("Enter Show LeaderBoard");
        System.out.println("Enter Login - To log in to your account");
        System.out.println("Enter Help - To show the orders");
    }

    public void printer(String string) {
        System.out.println(string);
    }

    public void showDecksAndModes(Account account) {
        showModes();
        account.getCollection().showAllDecksName();
    }

    public void showModes() {
        System.out.println("1. Death mode");
        System.out.println("2. SaveFlag for 6 turn");
        System.out.println("3. Collect half if flags");

    }

    public void showAllAccount() {
        AllAccount allAccount = AllAccount.getInstance();
        for (Account account : allAccount.accounts) {
            System.out.println(account.getUserName());
        }
    }

    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void showLevelsForStory() {
        System.out.println("first level : hero:whiteBeast / mode:Death mode ");
        System.out.println("second level : hero:zahak / mode:save flag mode");
        System.out.println("third level : hero:arash / mode:collect flag mode");
    }

    private void printInfoForEachPlayer(Player player, Match match) {
        System.out.println("player : UserName: " + player.getUserName());
        System.out.println("mana : " + player.getMana());
        switch (match.getMode()) {
            case "DeathMode":
                System.out.println("health point : " + player.getHero().getHp());
                break;
            case "SaveFlagMode":

                break;
            case "CollectFlagMode":
                for (Flag flag : match.getFlags()) {
                    System.out.println(flag.getOwnerCard().getName());
                }
                break;
        }
    }

    public void printGameInfo(Match match) {
        printInfoForEachPlayer(match.getPlayers()[0], match);
        printInfoForEachPlayer(match.getPlayers()[1], match);
    }

    public void showMyMinions(Player player) {
        for (Card card : player.getCardsOnLand()) {
            if (card instanceof Minion) {
                System.out.printf("%s: %s, health: %d, location: (%d, %d), power: %d\n",
                        card.getCardId(), card.getName(), card.getHp(),
                        card.getPosition().getXCoordinate(), card.getPosition().getYCoordinate(),
                        card.getAp());
            }
        }
    }

    public void showHand(Player player) {
        //to hand function showNextCard baraye card badi darim :D
        AccountView.getInstance().DeckAndHandView(null, null, player.getHand().getGameCards());
        showNextCard(player.getMainDeck());

    }

    public void showNextCard(Deck deck) {
        System.out.println("Next card :");
        Card nextCard = deck.passNextCard();
        if (nextCard instanceof Spell) {
            System.out.print("     ");
            AccountView.getInstance().showEachSpell((Spell) nextCard, NOT_VALID);
            System.out.println("\n");

        } else if (nextCard instanceof Minion) {
            System.out.print("     ");
            AccountView.getInstance().showEachMinion((Minion) nextCard, NOT_VALID);
            System.out.println("\n");
        }
    }

    public void showCollectableItems(Player player) {
        AccountView.getInstance().DeckAndHandView(null, player.getHand().getCollectableItems(), null);
    }

    public void showBattleMenu() {

    }

    /*public void showCardInfo(Card card) {
    }*/

    public void showItemInfo(Hand hand, String collectableId) {
        System.out.println("item : ");
        Collectable collectable = hand.passCollectableInHand(collectableId);
        AccountView.getInstance().showEachItem(collectable, NOT_VALID);
    }

    public void helpForSelectMode(){
        System.out.println("Enter single player");
        System.out.println("Enter multi player");
    }
}
