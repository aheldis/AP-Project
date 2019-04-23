package view;


import model.Item.Item;
import model.Item.Usable;
import model.battle.NormalDeck;
import model.battle.Player;
import model.battle.Deck;
import model.battle.Match;
import model.card.*;

import java.util.ArrayList;

import view.enums.ErrorType;

public class AccountView {

    private static AccountView singleInstance = null;

    private AccountView() {
    }

    public static AccountView getInstance() {
        if (singleInstance == null) {
            singleInstance = new AccountView();
        }
        return singleInstance;
    }


    public void cardsAndItemsView(ArrayList<Spell> spells, ArrayList<Minion> minions, ArrayList<Hero> heroes, ArrayList<Usable> items) {
        int counter = 1;
        System.out.print("Heroes : ");
        for (Hero hero : heroes) {
            System.out.print("          ");
            showEachHero(hero, counter);
            System.out.println(". - Sell Cost : " + hero.getCost());
            counter++;
        }
        counter = 1;
        for (Usable item : items) {
            System.out.print("          ");
            showEachItem(item, counter);
            System.out.println(" – Sell Cost : " + item.getCost());
            counter++;
        }
        counter = 1;
        for (Spell spell : spells) {
            System.out.print("          ");
            showEachSpell((Card) spell, counter);
            System.out.println(" – Sell Cost : " + spell.getCost());
            counter++;
        }
        counter = 1;
        for (Minion minion : minions) {
            System.out.print("          ");
            showEachMinion((Card) minion, counter);
            System.out.println(" – Sell Cost : " + minion.getCost());
            counter++;
        }
    }

    private void showEachHero(Hero hero, int counterOfCard) {
        System.out.print(counterOfCard + " : Name : " + hero.getName() +
                " - AP : " + hero.getAp() +
                " – HP : " + hero.getHp() +
                " – Class : " + hero.getCounterAttackClassName() +
                " – Special power: " + hero.getSpecialPowerInfo()
        );
    }

    private void showEachMinion(Card card, int counterOfCards) {
        System.out.print(counterOfCards + " : Type : Minion");
        System.out.print(" : Name : " + card.getName() +
                " – Class: " + card.getCounterAttackClassName() +
                " - AP : " + card.getAp() +
                " – HP : " + card.getHp() +
                " - MP : " + card.getMp() +
                " – Class : " + card.getCounterAttackClassName() +
                " – Special power: " + card.getSpecialPowerInfo()
        );
    }

    private void showEachItem(Item item, int counter) {
        System.out.print(counter + " : Name : " + item.getName() +
                " – Desc : " + item.getDescription()
        );
    }

    private void showEachSpell(Card card, int counterOfCards) {
        System.out.print(counterOfCards + " : Type : Spell");
        System.out.print(" - Name : " + card.getName() +
                " - MP : " + card.getMp() +
                " – Desc : " + card.getDescription()
        );
    }

    public void decksView(ArrayList<NormalDeck> decks) {
        int counterOfCards = 1, counterOfDeck = 1;
        Hero hero;
        Item item;
        ArrayList<Card> cards;
        for (int i = decks.size(); i > 0; i++) {
            System.out.println(counterOfDeck + " : " + decks.get(i).getName() + " :");

            hero = decks.get(i).getHero();
            System.out.println("     Heroes :");
            if (hero != null) {
                System.out.print("          ");
                showEachHero(hero, counterOfCards);
                System.out.println("\n");
            }

            System.out.println("     Items :");
            item = decks.get(i).getItem();
            if (item != null) {
                System.out.println("          ");
                showEachItem(item, counterOfCards);
                System.out.println("\n");
            }

            cards = decks.get(i).getCardsOfdeck();
            System.out.println("     Cards :");
            if (cards.size() != 0) {
                for (Card card : cards) {
                    if (card instanceof Spell) {
                        System.out.print("          ");
                        showEachSpell(card, counterOfCards);
                        System.out.println("\n");
                    } else if (card instanceof Minion) {
                        System.out.print("          ");
                        showEachMinion(card, counterOfCards);
                        System.out.println("\n");
                    }
                    counterOfCards++;
                }
            }
            counterOfCards = 1;
            counterOfDeck++;

        }
    }

    public void deckView(Deck deck) {
        if (deck == null)
            return;
        int counterOfCards = 1;
        Hero hero = deck.getHero();
        System.out.println("Heroes :");
        if (hero != null) {
            System.out.print("     ");
            showEachHero(hero, counterOfCards);
            System.out.println("\n");
        }

        System.out.println("Items :");
        Item item = deck.getItem();
        if (item != null) {
            System.out.print("     ");
            showEachItem(item, counterOfCards);
            System.out.println("\n");

        }

        ArrayList<Card> cards = deck.getCardsOfdeck();
        System.out.println("Cards :");
        if (cards.size() != 0) {
            for (Card card : cards) {
                if (card instanceof Spell) {
                    System.out.print("     ");
                    showEachSpell(card, counterOfCards);
                    System.out.println("\n");

                } else if (card instanceof Minion) {
                    System.out.print("     ");
                    showEachMinion(card, counterOfCards);
                    System.out.println("\n");
                }
                counterOfCards++;
            }
        }
    }

    public void helpViewForCollection() {
        System.out.println("exit - exit from collection");
        System.out.println("show - show all cards and items in collection");
        System.out.println("save - save the changes");
        System.out.println("create deck [deckName] - create a deck");
        System.out.println("delete deck [deckName] - delete a deck");
        System.out.println("add [cardId|HeroId|ItemId} to deck [deckName] - add model.card or item to deck");
        System.out.println("remove [model.card id|heroId|ItemId] from deck [deckName] - delete a model.card or item from deck");
        System.out.println("validate deck [deckName] - have 20 cards and a hero");
        System.out.println("show all decks - show all cards,hero and items of decks in the collection");
        System.out.println("show deck [deckName] - show all cards,hero and items of deck");
        System.out.println("help - help of collection");

    }

    public void viewAMatch(Match match, Player opponent) {
        System.out.print(opponent.getAccount().getUserName());
        if (match.getWinner().equals(opponent)) {
            System.out.println(" loss ");
        } else {
            System.out.println(" win ");
        }
        System.out.println(match.getTime()); //todo time-e java ???
    }

    public void printError(ErrorType error) {
        System.out.println(error.getMessage());
    }

    public void viewAccount(int rank, String userName, int wins) {
        System.out.println(rank + "- UserName: " + userName + " - Wins: " + wins);
    }

    public void viewHelpOfAccount() {
        System.out.println("Create Account [user name]");
        System.out.println("login [user name]");
        System.out.println("show leaderboard");
        System.out.println("save");
        System.out.println("logout");
    }

    public void viewHelpOfShop() {
        System.out.println("show collection");
        System.out.println("search [item name| model.card name]");
        System.out.println("search collection [item name| model.card name]");
        System.out.println("buy [item name| model.card name]");
        System.out.println("sell [model.card id]");
        System.out.println("show");
        System.out.println("exit");
    }

    public void print(String string) {
        System.out.println(string);
    }

}
