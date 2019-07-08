package controller.server;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.BattleMessage;
import controller.RequestEnum;
import controller.Transmitter;
import javafx.scene.Group;
import model.account.*;
import model.battle.Deck;
import model.battle.Game;
import model.battle.Match;
import model.card.Card;
import model.card.makeFile.MakeNewFile;
import model.item.Item;
import model.item.Collectible;
import model.item.Usable;
import model.requirment.GeneralLogicMethods;
import view.enums.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RequestEnumController {
    private static ArrayList<SocketClass> chatPerson = new ArrayList<>();
    private static HashMap<SocketClass/*opponent*/, SocketClass/*waiter*/> waiterHashMap = new HashMap<>();

    public synchronized static void main(RequestEnum requestEnum, SocketClass socketClass, Transmitter clientTransmitter) {
        Transmitter transmitter = socketClass.getTransmitter();
        transmitter.requestEnum = clientTransmitter.requestEnum;
        transmitter.transmitterId = clientTransmitter.transmitterId;
        AllAccount allAccount = AllAccount.getInstance();
        Account account = socketClass.getAccount();
        switch (requestEnum) {
            case END_OF_CLIENT:
                socketClass.getClientHandlerServer().stop();
                break;
            case SIGN_UP:
                boolean canSignUp = allAccount.signUp(clientTransmitter.name, clientTransmitter.password);
                if (!canSignUp)
                    transmitter.errorType = ErrorType.USER_NAME_ALREADY_EXIST;
                else {
                    socketClass.setAccount(allAccount.getAccountByName(clientTransmitter.name));
                    account.setAuthToken(AllAccount.getInstance().getAuthToken(account));
                }
                transfer(socketClass);
                break;
            case LOGIN:
                if (!allAccount.userNameHaveBeenExist(clientTransmitter.name))
                    transmitter.errorType = ErrorType.USER_NAME_NOT_FOUND;
                else if (!allAccount.passwordMatcher(clientTransmitter.name, clientTransmitter.password))
                    transmitter.errorType = ErrorType.PASSWORD_DOES_NOT_MATCH;
                else {
                    socketClass.setAccount(allAccount.getAccountByName(clientTransmitter.name));
                    account = socketClass.getAccount();
                    account.setAuthToken(AllAccount.getInstance().getAuthToken(account));
                }
                transfer(socketClass);
                break;
            case LOGOUT:
                AllAccount.getInstance().saveAccount(account);
                account.setAuthToken(null);
                socketClass.setAccount(null);
                break;
            case PROFILE:
                transmitter.path = account.getAccountImagePath();
                transmitter.name = account.getUserName();
                transmitter.matchInfos = account.getMatchHistory();
                transmitter.accounts = AllAccount.getInstance().showLeaderBoard();
                transfer(socketClass);
                break;
            case SHOP_BUY_AND_SELL:
                break;
            case SHOP_BUY:
                transmitter.errorType = Shop.getInstance().buy(account, clientTransmitter.name);
                transmitter.daric = account.getDaric();
                transfer(socketClass);
                break;
            case SHOP_SELL:
                transmitter.errorType = Shop.getInstance().sell(account, clientTransmitter.name);
                transmitter.daric = account.getDaric();
                transfer(socketClass);
                break;
            case SHOP_CARDS:
                transmitter.cards = Shop.getInstance().getCards();
                transfer(socketClass);
                break;
            case SHOP_HELP:
                transmitter.string = Shop.getInstance().help();
                transfer(socketClass);
                break;
            case SHOP_SEARCH:
                transmitter.object = Shop.getInstance().search(account, clientTransmitter.name);
                transmitter.name = account.getUserName();
                if (transmitter.object == null) {
                    transmitter.errorType = ErrorType.NO_SUCH_CARD_OR_ITEM_IN_SHOP;
                } else {
                    if (transmitter.object instanceof Card)
                        transmitter.cardId = transmitter.name + "_" + clientTransmitter.name + "_" +
                                account.getCollection().getNumberOfCardId((Card) transmitter.object);
                    else
                        transmitter.cardId = transmitter.name + "_" + clientTransmitter.name + "_" +
                                account.getCollection().getNumberOfItemId((Item) transmitter.object);
                }
                transfer(socketClass);
                break;
            case SHOP_ITEMS:
                ArrayList<Usable> items = Shop.getInstance().getItems();
                transmitter.items = new ArrayList<>();
                transmitter.items.addAll(items);
                transfer(socketClass);
                break;
            case SHOP_DARIC:
                transmitter.daric = account.getDaric();
                transfer(socketClass);
                break;

            case GET_COLLECTION:
                transmitter.collection = account.getCollection();
                transfer(socketClass);
                break;
                /*
            case COLLECTION_SHOW:
                transmitter.cards = account.getCollection().getAllCards();
                transmitter.items = new ArrayList<>(Arrays.asList(account.getCollection().getItems()));
                transfer(socketClass);
                break;
            case COLLECTION_DECKS:
                transmitter.decks = account.getDecks();
                transmitter.collection = account.getCollection();
                for (Card card : transmitter.collection.getAllCards())
                    System.out.println(card.getName() + " " + card.getCardId());
                transfer(socketClass);
                break;
            case NEW_DECK:
                transmitter.errorType = account.getCollection().createDeck(clientTransmitter.name);
                break;
            case COLLECTION_DELETE_DECK:
                transmitter.errorType = account.getCollection().deleteDeck(clientTransmitter.name);
                break;
                */
            case EXPORT_DECK:
                Deck deck = clientTransmitter.deck;
                String path = "exportedDeck/" + account.getUserName()
                        + "." + deck.getName() + ".json";
                GeneralLogicMethods.saveInFile(path, deck);
                break;
            case IMPORT_DECK:
                InputStream input;
                try {
                    input = new FileInputStream("exportedDeck/"
                            + account.getUserName() + "." + clientTransmitter.deck.getName() + ".json");
                    Reader reader = new InputStreamReader(input);
                    YaGson mapper = new YaGson();
                    Deck deckImported = mapper.fromJson(reader, Deck.class);//load the deck
                    transmitter.deck = deckImported;
                    if (!account.getCollection().checkTheDeckForImport(deckImported)) {
                        transmitter.errorType =
                                ErrorType.HAVE_NOT_CARDS_IN_COLLECTION_FOR_IMPORT;

                    }
                } catch (FileNotFoundException e) {
                    transmitter.errorType = ErrorType.INVALID_NAME_FOR_IMPORTED_DECK;
                } finally {
                    transfer(socketClass);
                }

                break;
                /*
            case COLLECTION_ADD_CARD_TO_DECK:
                //todo
                break;
            case COLLECTION_SELECT_MAIN_DECK:
                transmitter.errorType =
                        account.getCollection().selectADeckAsMainDeck(transmitter.deck.getName());
                break;
                */
            case COLLECTION_UPDATE:
                account.setCollection(clientTransmitter.collection);
                break;
            case MAIN_DECK:
                transmitter.deck = account.getMainDeck();
                if (transmitter.deck == null || !transmitter.deck.validate())
                    transmitter.errorType = ErrorType.DONT_HAVE_MAIN_DECK;
                transfer(socketClass);
                break;
                /*
            case COLLECTION_CARDS:
                transmitter.cards = account.getCollection().getAllCards();
                transfer(socketClass);
                break;
            case COLLECTION_ITEMS:
                transmitter.items = new ArrayList<>(Arrays.asList(account.getCollection().getItems()));
                transfer(socketClass);
                break;
            case COLLECTION_HELP:
                transmitter.string = account.getCollection().helpOfCollection();
                transfer(socketClass);
                break;
            case COLLECTION_SEARCH_ITEM:
                transmitter.ids = account.getCollection().searchItemName(clientTransmitter.name);
                transfer(socketClass);
                break;
            case COLLECTION_SEARCH_CARD:
                transmitter.ids = account.getCollection().searchCardName(clientTransmitter.name);
                transfer(socketClass);
                break;
                */
            case ENTER_CHAT:
                chatPerson.add(socketClass);
                transmitter.name = account.getUserName();
                transmitter.path = account.getAccountImagePath();
                transfer(socketClass);
                break;
            case SEND_MESSAGE:
//                groupTexts.add(group);
                for (SocketClass person : chatPerson) {
                    person.changeTransmitter();
                    Transmitter personTransmitter = person.getTransmitter();
                    personTransmitter.transmitterId = 0;
                    personTransmitter.requestEnum = RequestEnum.NEW_MESSAGE;
                    personTransmitter.profile = clientTransmitter.profile;
                    personTransmitter.name = clientTransmitter.name;
                    personTransmitter.message = clientTransmitter.message;
                    personTransmitter.path = clientTransmitter.path;
                    transfer(person);
                }
                break;
            case DECKS:
                transmitter.decks = account.getDecks();
                transfer(socketClass);
                break;
            case START_STORY_GAME: {
                Game game = new Game();
                socketClass.setGame(game);
                ErrorType errorType = game.checkPlayerDeck(account, clientTransmitter.playerNumber);
                transmitter.errorType = errorType;
                if (errorType == null) {
                    transmitter.match = socketClass.setMatch(game.makeNewStoryGame(clientTransmitter.level));
                    if (clientTransmitter.level == 3)
                        transmitter.match.setFlagsRandomly(3);
                    if (clientTransmitter.level == 2)
                        transmitter.match.setFlagsRandomly(2);
                    transmitter.match.setCollectiblesRandomly();
                    for (Collectible collectible : transmitter.match.getCollectibles())
                        System.out.println(collectible.getCollectibleId().getCollectibleIdAsString());
                    transmitter.game = game;
                }
                transfer(socketClass);
                break;
            }
            case EXIT_FROM_CHAT:
                chatPerson.remove(socketClass);
                break;
            case ALL_ACCOUNT:
                transmitter.accounts = AllAccount.getInstance().getAccounts();
                transfer(socketClass);
                break;
            case START_CUSTOM_GAME: { /*Custom Game*/
                Game game = new Game();
                socketClass.setGame(game);
                ErrorType errorType = game.checkPlayerDeck(account, clientTransmitter.playerNumber);
                transmitter.errorType = errorType;
                if (errorType == null) {
                    transmitter.match = socketClass.setMatch(
                            game.makeNewCustomGame(account, clientTransmitter.name,
                                    clientTransmitter.mode, clientTransmitter.numberOfFlag));
                    if (clientTransmitter.level == 3)
                        transmitter.match.setFlagsRandomly(3);
                    if (clientTransmitter.level == 2)
                        transmitter.match.setFlagsRandomly(2);
                    transmitter.match.setCollectiblesRandomly();
                    transmitter.game = game;
                }
                transfer(socketClass);
                break;
            }
            case MAKE_NEW_CARD: {
                MakeNewFile makeNewFile = new MakeNewFile();
                makeNewFile.setSpriteNumber(clientTransmitter.spriteNumber, clientTransmitter.spriteNumberCount);
                makeNewFile.setHashMaps(clientTransmitter.hashMapsWithStrings);
                socketClass.getTransmitter().errorType = makeNewFile.makeNewCard(FilesType.getEnum(clientTransmitter.type), account);
                transfer(socketClass);
                break;
            }
            case NEW_CARD_ARRAY_LISTS: {
                MakeNewFile makeNewFile = new MakeNewFile();
                transmitter.fieldNames = makeNewFile.getFieldNames(FilesType.getEnum(clientTransmitter.type));
                transmitter.changeFieldNames = makeNewFile.getChangeFieldNames();
                transmitter.targetFieldNames = makeNewFile.getTargetFieldNames();
                transmitter.buffFieldNames = makeNewFile.getBuffFieldNames();
                transfer(socketClass);
                break;
            }
            case START_MATCH:
                String opponent = clientTransmitter.name;
                socketClass.setMode(clientTransmitter.mode);
                socketClass.setNumberOfFlag(clientTransmitter.numberOfFlag);
                socketClass.setReward(clientTransmitter.reward);
                SocketClass opponentSocketClass = Server.getSocketClassByName(opponent);
                if (opponentSocketClass != null) {
                    waiterHashMap.put(opponentSocketClass, socketClass);
                    opponentSocketClass.changeTransmitter();
                    Transmitter personTransmitter = opponentSocketClass.getTransmitter();
                    personTransmitter.transmitterId = 0;
                    personTransmitter.requestEnum = RequestEnum.PLAY_REQUEST;
                    opponentSocketClass.getTransmitter().message = "Play?";
                    transfer(opponentSocketClass);
                }
                break;
            case DECLINE_PLAY: {
                SocketClass waiter = waiterHashMap.get(socketClass);
                waiter.changeTransmitter();
                Transmitter waiterTransmitter = waiter.getTransmitter();
                waiterTransmitter.message = "decline";
                waiterTransmitter.requestEnum = RequestEnum.DECLINE_PLAY;
                waiterTransmitter.transmitterId = 0;
                transfer(waiter);
                break;
            }
            case ACCEPT_PLAY: {
                SocketClass waiter = waiterHashMap.get(socketClass);
                waiter.changeTransmitter();
                socketClass.changeTransmitter();
                Game game = new Game();
                game.checkPlayerDeck(waiter.getAccount(), 1);
                game.checkPlayerDeck(socketClass.getAccount(), 2);
                socketClass.setGame(game);
                Match match = socketClass.setMatch(
                        game.makeNewMultiGame(waiter.getMode(), waiter.getNumberOfFlag(), waiter.getReward()));
                if (clientTransmitter.level == 3)
                    transmitter.match.setFlagsRandomly(3);
                if (clientTransmitter.level == 2)
                    transmitter.match.setFlagsRandomly(2);
                transmitter.match.setCollectiblesRandomly();
                int numberOfMap = new Random().nextInt(12) + 1;
                sendAcceptPlayForBoth(waiter, socketClass, match, game, numberOfMap, true);
                sendAcceptPlayForBoth(socketClass, waiter, match, game, numberOfMap, false);

                //todo save them in client
                /**
                 * goes to transferController func:fromServer...
                 */
                break;
            }
            case CANCEL_START_MATCH:
                transmitter.requestEnum = RequestEnum.CANCEL_START_MATCH;
                transfer(socketClass);
                break;
            case NEW_BID: {
                transmitter.errorType = Bid.newBid(account, clientTransmitter.cardId, 100);
                transfer(socketClass);
                break;
            }
            case GET_BIDS: {
                for (Bid bid : Bid.getBids()) {
                    bid.sendBid();
                }
                break;
            }
            case BID_NEW_COST: {
                Bid bid = Bid.getBidByCard(clientTransmitter.card);
                int newCost = clientTransmitter.cost;
                if (newCost > bid.getCard().getCost())
                    transmitter.errorType = ErrorType.INVALID_COST;
                else if (newCost < bid.getCost())
                    transmitter.errorType = ErrorType.INVALID_COST2;
                else if(newCost > account.getDaric()){
                    transmitter.errorType = ErrorType.NOT_ENOUGH_MONEY;
                }
                else {
                    bid.setCost(newCost);
                    bid.setBuyerAccount(account);
                    bid.sendBid();
                }
                transfer(socketClass);
                break;
            }
            case ADD_A_BID: {
                transmitter = socketClass.getTransmitter();
                transmitter.requestEnum = RequestEnum.ADD_A_BID;
                transmitter.transmitterId = 0;
                transmitter.card = clientTransmitter.card;
                transmitter.cost = clientTransmitter.cost;
                transmitter.time = clientTransmitter.time;
                transmitter.string = transmitter.card.getDescription();
                transfer(socketClass);
                break;
            }
        }
        socketClass.changeTransmitter();

    }

    private static void sendAcceptPlayForBoth(SocketClass socketClass, SocketClass waiter,
                                              Match match, Game game, int numberOfMap, boolean imPlayer0) {
        Transmitter transmitter = socketClass.getTransmitter();
        transmitter.transmitterId = 0;
        transmitter.requestEnum = RequestEnum.BATTLE;
        transmitter.battleMessage = new BattleMessage();
        transmitter.battleMessage.imPlayer0 = imPlayer0;
        socketClass.socketClasses = new SocketClass[]{socketClass, waiter};
        transmitter.match = match;
        transmitter.game = game;
        transmitter.numberOfMap = numberOfMap;
        transfer(socketClass);
    }

    private static void transfer(SocketClass socketClass) {
        System.out.println();
        System.out.println("RequestEnumController.transfer");
        Transmitter transmitter = socketClass.getTransmitter();
        System.out.println("transmitter = " + transmitter);
        System.out.println("transmitter.requestEnum = " + transmitter.requestEnum);
        System.out.println("transmitter.transmitterId = " + transmitter.transmitterId);
        try {
            YaGson altMapper = new YaGsonBuilder().create();
            String json = altMapper.toJson(socketClass.getTransmitter());
            socketClass.getOut().println(json);
            socketClass.getOut().flush();

            System.out.println("RequestEnumController.transfer");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
