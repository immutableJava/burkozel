package entities.match.managers;

import entities.Card;
import entities.Player;
import entities.match.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class TableManagerImpl implements TableManager {

    private final TurnManager turnManager;
    private final DeckManager deckManager;
    private final ConsoleUIManager consoleUIManager;
    private final MoveManager moveManager;
    private final List<Card> attackerCards;
    private final List<Card> defenderCards;
    private final List<Card> allCardsOnTable;

    public TableManagerImpl(TurnManager turnManager, DeckManager deckManager, ConsoleUIManager consoleUIManager, MoveManager moveManager) {
        this.turnManager = turnManager;
        this.deckManager = deckManager;
        this.consoleUIManager = consoleUIManager;
        this.moveManager = moveManager;
        attackerCards = turnManager.getAttackerCardsOnTable();
        defenderCards = turnManager.getDefenderCardsOnTable();
        allCardsOnTable = new ArrayList<>(attackerCards);
        allCardsOnTable.addAll(defenderCards);
    }

    @Override
    public Player putAllCardsOnTable() {

        Player attacker = turnManager.getAttacker();
        Player defender = turnManager.getDefender();
        System.out.println(attacker.getHand() + " " + attacker.getName() + "'s cards, he is attacker" );
        System.out.println(defender.getHand() + " " + defender.getName() + "'s cards, he is defender" );
        List<Card> playerWhoAttacksHand = attacker.getHand();
        List<Card> playerWhoDefencesHand = defender.getHand();
        List<Card> defenderCardsCopy = new ArrayList<>(defender.getHand());

        for (Card attackerCard : playerWhoAttacksHand) {
            Card card = deckManager.findMinCardToBeat(attackerCard, defenderCardsCopy);
            if (card == null) {
                return attacker;
            }
            defenderCardsCopy.remove(card);
        }

        return defender;

    }


    @Override
    public void attackerPickCards() {
        Player attacker = turnManager.getAttacker();

        System.out.printf("%s, pick a cards between 1 and 4, separate it by comma. When you will pick all your cards press enter. %n", attacker.getName());
        System.out.println(attacker.getHand());


        int maxCardsAllowed = attacker.maxCardCountOfSameSuit();
        consoleUIManager.pickCardsToMove(maxCardsAllowed, true, moveManager, deckManager);
        allCardsOnTable.addAll(turnManager.getAttackerCardsOnTable());
    }


    @Override
    public Player playDefenseAndResolve(boolean defenseSuccess, TableManager tableManager) {
        Player winner = defenseSuccess ? turnManager.getDefender() : turnManager.getAttacker();


        deckManager.addAllCardsToPlayerFromWinner(winner, getAllCardsOnTable());

        turnManager.getAttackerCardsOnTable().clear();
        turnManager.getDefenderCardsOnTable().clear();
        tableManager.getAllCardsOnTable().clear();
        turnManager.setCurrentPlayer(winner);

        System.out.println(winner.getName() + " won the round!");
        return winner;
    }


    @Override
    public boolean defenderPickCardsAndResolve() {
        Player defender = turnManager.getDefender();
        Player attacker = turnManager.getAttacker();
        System.out.printf("%s, pick a cards between 1 and 4, separate it by comma. When you will pick all your cards press enter. %n", defender.getName());
        System.out.println("You should beat " + turnManager.getAttackerCardsOnTable() + " cards");
        System.out.println(defender.getHand() + " defender player hand inside table");

        boolean allBeaten = true;
        int maxCardsAllowed = turnManager.getAttackerCardsOnTable().size();
        consoleUIManager.pickCardsToMove(maxCardsAllowed, false, moveManager, deckManager);
        allCardsOnTable.addAll(turnManager.getDefenderCardsOnTable());
        for (Card attackerCard : attackerCards) {
            Card foundCard = deckManager.findMinCardToBeat(attackerCard, defenderCards);
            if (foundCard == null) {
                allBeaten = false;
                break;
            } else {
                defenderCards.remove(foundCard);
            }
        };



        return allBeaten;
    }



    @Override
    public List<Card> getAllCardsOnTable() {
        return allCardsOnTable;
    }
}
