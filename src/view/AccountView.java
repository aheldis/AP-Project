package view;

import model.item.*;
import model.account.Account;
import model.battle.Deck;
import model.battle.MatchInfo;
import model.card.*;
import view.enums.ErrorType;
import java.util.ArrayList;
import java.util.Date;

public class AccountView {

    private static AccountView singleInstance = null;
    private static final int NOT_VALID = -1;

    private AccountView() {
    }

    public static AccountView getInstance() {
        if (singleInstance == null) {
            singleInstance = new AccountView();
        }
        return singleInstance;
    }

    private class Duration {
        long seconds;
        long minutes;
        long hours;
        long days;
        long months;
        long years;
        long milliSecond;

        Duration(long milliSecond) {//joda joda taqsim she daqiq tare :D
            this.milliSecond = milliSecond;
            seconds = milliSecond / 1000 % 60;
            minutes = milliSecond / (60 * 1000) % 60;
            hours = milliSecond / (60 * 60 * 1000);
            days = milliSecond / (1000 * 60 * 60 * 24);
            months = days / 30;
            years = months / 12;


        }
    }

    public void cardsAndItemsView(ArrayList<Spell> spells, ArrayList<Minion> minions, ArrayList<Hero> heroes, ArrayList<Usable> items) {
        int counter = 1;
        System.out.println("Heroes : ");
        for (Hero hero : heroes) {
            System.out.print("          ");
            showEachHero(hero, counter);
            if (hero != null)
                System.out.println(". - Sell Cost : " + hero.getCost());
            counter++;
        }
        counter = 1;
        System.out.println("Items : ");
        for (Item item : items) {
            if (item == null)
                continue;
            if(item instanceof Usable) {
                System.out.print("          ");
                showEachItem(item, counter);
                System.out.println(" – Sell Cost : " + item.getCost());
                counter++;
            }
        }
        counter = 1;
        System.out.println("Spells : ");
        for (Spell spell : spells) {
            System.out.print("          ");
            showEachSpell(spell, counter);
            if (spell != null)
                System.out.println(" – Sell Cost : " + spell.getCost());
            counter++;
        }
        counter = 1;
        System.out.println("Minion : ");
        for (Minion minion : minions) {
            System.out.print("          ");
            showEachMinion(minion, counter);
            if (minion != null)
                System.out.println(" – Sell Cost : " + minion.getCost());
            counter++;
        }
    }

    private void showEachHero(Hero hero, int counterOfCard) {
        if (hero == null)
            return;
        System.out.print(counterOfCard + " : Name : " + hero.getName() +
                " - AP : " + hero.getAp() +
                " – HP : " + hero.getHp() +
                " – Class : " + hero.getCounterAttackName() +
                " – Special power: " + hero.getSpecialPowerInfo()
        );
    }

    private void showEachItem(Item item, int counter) {
        if (item == null)
            return;
        System.out.print(counter + " : Name : " + item.getName() +
                " – Desc : " + item.getDescription()
        );
    }

    void showEachSpell(Spell spell, int counterOfCards) {
        if (counterOfCards != NOT_VALID)
            System.out.print(counterOfCards);
        System.out.print(" : Type : Spell");
        if (spell == null)
            return;
        System.out.print(" - Name : " + spell.getName() +
                " - MP : " + spell.getMp() +
                " – Desc : " + spell.getDescription()
        );
    }

    void showEachMinion(Minion minion, int counterOfCards) {
        if (counterOfCards != NOT_VALID)
            System.out.print(counterOfCards);
        System.out.print(" : Type : Minion");
        if (minion == null)
            return;
        System.out.print(" : Name : " + minion.getName() +
                " – Class: " + minion.getCounterAttackName() +
                " - AP : " + minion.getAp() +
                " – HP : " + minion.getHp() +
                " - MP : " + minion.getMp() +
                " – Class : " + minion.getCounterAttackName() +
                " – Special power: " + minion.getSpecialPowerInfo()
        );
    }

    public void decksNameView(ArrayList<Deck> decks) {
        for (int i = decks.size() - 1; i >= 0; i--) {
            System.out.println(decks.get(i).getName());
        }
    }

    public void decksView(ArrayList<Deck> decks) {
        int counterOfCards = 1, counterOfDeck = 1;
        Hero hero;
        Item item;
        ArrayList<Card> cards;
        for (int i = decks.size() - 1; i >= 0; i--) {
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

            cards = decks.get(i).getCardsOfDeck();
            System.out.println("     Cards :");
            if (cards.size() != 0) {
                for (Card card : cards) {
                    if (card instanceof Spell) {
                        System.out.print("          ");
                        showEachSpell((Spell) card, counterOfCards);
                        System.out.println("\n");
                    } else if (card instanceof Minion) {
                        System.out.print("          ");
                        showEachMinion((Minion) card, counterOfCards);
                        System.out.println("\n");
                    }
                    counterOfCards++;
                }
            }
            counterOfCards = 1;
            counterOfDeck++;

        }
    }

    public void DeckAndHandView(Hero hero, ArrayList<Item> items, ArrayList<Card> cards) {
        int counterOfCards = 1;
        System.out.println("Heroes :");
        if (hero != null) {
            System.out.print("     ");
            showEachHero(hero, counterOfCards);
            System.out.println("\n");
        }

        System.out.println("Items :");
        if (items != null && items.size() != 0) {
            for (Item item : items) {
                if(item == null )
                    continue;
                System.out.print("     ");
                showEachItem(item, counterOfCards);
                System.out.println("\n");
            }
        }

        System.out.println("Cards :");
        if (cards != null && cards.size() != 0) {
            for (Card card : cards) {
                if (card instanceof Spell) {
                    System.out.print("     ");
                    showEachSpell((Spell) card, counterOfCards);
                    System.out.println("\n");

                } else if (card instanceof Minion) {
                    System.out.print("     ");
                    showEachMinion((Minion) card, counterOfCards);
                    System.out.println("\n");
                }
                counterOfCards++;
            }
        }
    }

    public String helpViewForCollection() {
        String strings;
        strings=
        "Show - Show all cards and items " +"\n"+
                "in collection"+"\n"+
        "Save - Save the changes"+"\n"+
        "Create deck [deckName] - Create a deck"+"\n"+
        "Delete deck [deckName] - Delete a deck"+"\n"+
        "Add [cardId|HeroId|ItemId} to " +"\n"+
                "deck [deckName]:" +"\n"+
                " Add card or item to deck"+"\n"+
        "Remove card [card id] from deck " +"\n"+
                "[deckName]: " +"\n"+
                " Delete a card from deck"+"\n"+
        "Remove item [item id] from deck " +"\n"+
                "[deckName]:" +"\n"+
                "Delete an item from deck"+"\n"+
        "Validate deck [deckName] :" +"\n"+
                " Have 20 cards and a hero"+"\n"+
        "Show all decks - Show all cards," +"\n"+
                "hero and items of decks in the collection"+"\n"+
        "Show deck [deckName]:" +"\n"+
                " Show all cards,hero and items of deck"+"\n";
        return strings;

    }

    public void viewAMatch(MatchInfo matchInfo, Account me) {
        String opponent;
        if (matchInfo.winner.equals(me.getUserName())) {
            opponent = matchInfo.loser;
            System.out.println(opponent);
            System.out.println("win");
            printDate(matchInfo.date);
        } else {
            opponent = matchInfo.winner;
            System.out.println(opponent);
            System.out.println("loss");
            printDate(matchInfo.date);
        }

//
//        System.out.print(opponent.getAccount().getUserName());
//        if (match.getWinner().equals(opponent)) {
//            System.out.println(" loss ");
//        } else {
//            System.out.println(" win ");
//        }
//        System.out.println(match.getTime()); //todo time-e java ???
    }

    private void printDate(Date date) {
        Date currentDate = new Date();
        long diff = date.getTime() - currentDate.getTime();
        Duration duration = new Duration(diff);
        if (duration.years != 0) {
            System.out.println(duration.years + "years ago");
        } else {
            if (duration.months != 0) {
                System.out.println(duration.months + "months ago");
            } else {
                if (duration.days != 0) {
                    System.out.println(duration.days + "days ago");
                } else {
                    if (duration.hours != 0) {
                        System.out.println(duration.hours + " hours ago");
                    } else {
                        if (duration.minutes != 0) {
                            System.out.println(duration.minutes + " minutes ago");
                        } else {
                            System.out.println(duration.seconds + "seconds ago");
                        }
                    }
                }
            }
        }

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

    public String viewHelpOfShop() {
        String strings;
//        strings.add("show collection - show cards and items in collection");
       strings = "search [item name| card name]"+"\n"+
       "search collection [item name| card name]"+"\n"+
        "buy [item name| card name]"+"\n"+
       "sell [card id]"+"\n"+
        "show - show all cards and items"+"\n"+
        "exit - exit from shop"+"\n";
        return strings;
    }

    public void print(String string) {
        System.out.println(string);
    }

}
