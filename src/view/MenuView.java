package view;

import model.account.Account;
import model.account.AllAccount;
import model.battle.Deck;
import model.battle.Hand;
import model.battle.Match;
import model.battle.Player;
import model.card.Card;
import model.card.Minion;
import model.card.Spell;
import model.item.Collectible;
import model.item.Flag;
import view.enums.ErrorType;

import java.util.ArrayList;

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
        for (Account account : allAccount.getAccounts()) {
            System.out.println(account.getUserName());
        }
    }

    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void showLevelsForStory() {
        System.out.println("First level: hero: WhiteBeast / mode: Death mode ");
        System.out.println("Second level: hero: Zahhak / mode: save flag mode");
        System.out.println("Third level: hero: Arash / mode: collect flag mode");
    }

    private void printInfoForEachPlayer(Player player, int playerNumber, Match match) {
        System.out.println("player " + playerNumber + ":");
        System.out.println("UserName: " + player.getUserName());
        System.out.println("Mana: " + player.getMana());
        System.out.println("Hero: " + player.getHero().getName()
                + " - cardId: " + player.getHero().getCardId().getCardIdAsString());
        System.out.println("health point : " + player.getHero().getHp());
        System.out.println("number of flaged save: " + player.getNumberOfFlagsSaved());
        System.out.println("Turn For Saving Flag: " + player.getTurnForSavingFlag());
    }

    public void printGameInfo(Match match) {
        printInfoForEachPlayer(match.getPlayers()[0], 1, match);
        System.out.println();
        printInfoForEachPlayer(match.getPlayers()[1], 2, match);
        System.out.println();
     /*   switch (match.getMode()) {
            case "DeathMode":
                break;
            case "SaveFlagMode": {
                Flag flag = match.getFlags().get(0);
                System.out.println("flag position: (" + flag.getSquare().getXCoordinate() + "," + flag.getSquare().getYCoordinate() + ")");
                if (flag.getOwnerCard() != null) {
                    Account ownerAccount = flag.getOwnerCard().getCardId().getAccount();
                    if(ownerAccount == null)
                        System.out.println("UserName of owner: Computer");
                    else
                        System.out.println(ownerAccount.getUserName());
                    System.out.println("owner cardID: " + flag.getOwnerCard().getCardId().getCardIdAsString());
                }
                break;
            }
            case "CollectFlagMode": {

                break;
            }
        }*/
        for (Flag flag : match.getFlags()) {
            System.out.println("flag position: (" + flag.getSquare().getXCoordinate() + "," + flag.getSquare().getYCoordinate() + ")");
            if (flag.getOwnerCard() != null) {
                if (flag.getOwnerCard() != null) {
                    Account ownerAccount = flag.getOwnerCard().getCardId().getAccount();
                    if (ownerAccount == null)
                        System.out.println("Username of owner: Computer");
                    else
                        System.out.println("Username of owner: " + ownerAccount.getUserName());
                    System.out.println("Owner cardID: " + flag.getOwnerCard().getCardId().getCardIdAsString());
                }
            }
        }
    }

    public void showMyMinions(Player player) {
        for (Card card : player.getCardsOnLand()) {
            if (card instanceof Minion) {
                System.out.printf("%s: %s, health: %d, location: (%d, %d), power: %d\n",
                        card.getCardId().getCardIdAsString(), card.getName(), card.getHp(),
                        card.getPosition().getXCoordinate(), card.getPosition().getYCoordinate(),
                        card.getAp());
            }
        }
    }

    private void showSpellOrMinion(Card card, int counterOfCards) {
        if (card instanceof Spell) {
            System.out.print("     ");
            AccountView.getInstance().showEachSpell((Spell) card, counterOfCards);
            System.out.println(" - cardID: " + card.getCardId().getCardIdAsString());
            System.out.println("\n");

        } else if (card instanceof Minion) {
            System.out.print("     ");
            AccountView.getInstance().showEachMinion((Minion) card, counterOfCards);
            System.out.println(" - cardID: " + card.getCardId().getCardIdAsString());
            System.out.println("\n");
        }
    }

    public void showHand(Player player) {
        //nmikham hero o ina on balash bashe mosalaman :|
        int counterOfCards = 1;
        ArrayList<Card> cards = player.getHand().getGameCards();
        if (cards != null && cards.size() != 0) {
            for (Card card : cards) {
                showSpellOrMinion(card, counterOfCards);
                counterOfCards++;
            }
        }
        showNextCard(player.getMainDeck());
    }

    public void showNextCard(Deck deck) {
        System.out.println("Next card :");
        Card nextCard = deck.passNextCard(false);
        if (nextCard instanceof Spell) {
            showSpellOrMinion(nextCard, NOT_VALID);
            return;
        }
        showSpellOrMinion(nextCard, 1);
    }

    public void showACollectibleItem(Collectible item, int number){
        System.out.print("     ");
        System.out.println(number + ": Name: " + item.getName() + " - Desc: " + item.getDescription() + " - itemID: " + item.getCollectibleId().getCollectibleIdAsString());
        System.out.println("\n");
    }

    public void showCollectibleItems(Player player) {
        ArrayList<Collectible> items = player.getHand().getCollectibleItems();
        System.out.println("Collectible items :");
        int number = 1;
        if (items != null && items.size() != 0) {
            for (Collectible item : items) {
                if (item == null)
                    continue;
                showACollectibleItem(item, number);
                number++;
            }
        }
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
        System.out.println("Show collectables - show collectible items");
        System.out.println("Select item [collectible id] - select an item");
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
        showACollectibleItem(collectible, 1);
    }

    public void helpForSelectMode() {
        System.out.println("Enter single player");
        System.out.println("Enter multi player");
    }

    public void helpForSingleMode() {
        System.out.println("Enter story");
        System.out.println("Enter custom game");
    }

}
