package view;

import model.item.Collectible;
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

    public void showCollectibleItems(Player player) {
        AccountView.getInstance().DeckAndHandView(null, player.getHand().getCollectibleItems(), null);
    }

    public void showBattleMenu() {
        System.out.println("Game info - show manas and flags");
        System.out.println("Show my minions - show minions info");
        System.out.println("Show opponent minions - show opponent minions info");
        System.out.println("Show card info [card id]");
        System.out.println("Select card [card id] - select s card for move or attack ");
        System.out.println("Move to ([x], [y]) - move selected card");
        System.out.println("Attack [opponent card id] - attack selected card to opponent");
        System.out.println("Attack combo [opponent card id] [my card id] [my card id] [...] - attack combo");
        System.out.println("Use special power (x, y) - use special power for hero or minion");
        System.out.println("Show hand  - show your hand");
        System.out.println("Insert [card name] in (x, y) - put a card on land");
        System.out.println("End turn");
        System.out.println("Show collectables - show collectable items");
        System.out.println("Select item [collectable id] - select an item");
        System.out.println("show info - show item info after select it");
        System.out.println("Use [location x, y] - use item after select it");
        System.out.println("Show Next Card - show next card in hand");
        System.out.println("Enter graveyard - graveYard:your dead card is here");
        System.out.println("Show info [card id] - show card info in the graveYard ");
        System.out.println("Show cards - show all cards in graveYard");
        System.out.println("Help - show what you can do");
        System.out.println("End Game - dispense with game");
        System.out.println("show menu - show help for battle");
    }

    public void showItemInfo(Hand hand, String CollectibleId) {
        System.out.println("item : ");
        Collectible collectible = hand.passCollectibleInHand(CollectibleId);
        AccountView.getInstance().showEachItem(collectible, NOT_VALID);
    }

    public void helpForSelectMode(){
        System.out.println("Enter single player");
        System.out.println("Enter multi player");
    }
}
