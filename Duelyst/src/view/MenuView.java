package view;

import model.Item.Flag;
import model.account.Account;
import model.account.AllAccount;
import model.battle.Game;
import model.battle.Hand;
import model.battle.Match;
import model.battle.Player;
import model.card.Card;
import model.card.Hero;
import model.card.Minion;
import model.card.Spell;
import view.enums.ErrorType;

public class MenuView {
    private static MenuView singleInstance = null;

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
        System.out.println("Enter Exit - Log out from game");
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

private void printInfoForEachPlayer(Player player,Match match){
    System.out.println("player : UserName: "+player.getUserName());
    System.out.println("mana : "+ player.getMana());
    switch (match.getMode()){
        case "DeathMode":
            System.out.println("health point : "+player.getHero().getHp());
            break;
        case "SaveFlagMode":

            break;
        case "CollectFlagMode":
            for(Flag flag : match.getFlags()){
                System.out.println(flag.getOwnerCard().getName());
            }
            break;
    }
}

    public void printGameInfo(Match match) {
        printInfoForEachPlayer(match.getPlayers()[0],match);
        printInfoForEachPlayer(match.getPlayers()[1],match);
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

    public void showHand(Hand hand) {
        //to hand function showNextCard baraye card badi darim :D
        AccountView.getInstance().DeckAndHandView(null, null, hand.getGameCards());
        showNextCard(hand);

    }

    public void showNextCard(Hand hand) {
        System.out.println("Next card :");
//        if (card instanceof Spell) {
//            System.out.print("     ");
//            AccountView.getInstance().showEachSpell((Spell) card, counterOfCards);
//            System.out.println("\n");
//
//        } else if (card instanceof Minion) {
//            System.out.print("     ");
//            AccountView.getInstance().showEachMinion((Minion) card, counterOfCards);
//            System.out.println("\n");
//        }
        //todo
    }

    public void showCollectableItems(Player player) {

    }

    public void showBattleMenu() {

    }

    /*public void showCardInfo(Card card) {
    }*/

    public void showItemInfo(Hand hand, String collectableId) {

    }
}
