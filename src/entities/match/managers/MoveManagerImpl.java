package entities.match.managers;

import entities.Card;
import entities.Player;
import entities.match.interfaces.DeckManager;
import entities.match.interfaces.MoveManager;
import entities.match.interfaces.TurnManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MoveManagerImpl implements MoveManager {

    private final List<Player> players;

    public MoveManagerImpl(List<Player> players) {
        this.players = players;
    }


    @Override
    public boolean canInitializeCurrentPlayer(DeckManager deckManager, TurnManager turnManager) {
        List<Card> firstPlayerTrumps = deckManager.getTrumps(players.get(0).getHand());
        List<Card> secondPlayerTrumps = deckManager.getTrumps(players.get(1).getHand());


        if (firstPlayerTrumps.isEmpty() && secondPlayerTrumps.isEmpty()) {
            return false;
        } else if (firstPlayerTrumps.isEmpty()) {
            turnManager.setCurrentPlayerIndex(1);
        } else if (secondPlayerTrumps.isEmpty()) {
            turnManager.setCurrentPlayerIndex(0);
        } else {
            boolean firstCardGreater = deckManager.getPlayerMinTrumpPower(firstPlayerTrumps) > deckManager.getPlayerMinTrumpPower(secondPlayerTrumps);
            turnManager.setCurrentPlayerIndex(firstCardGreater ? 1 : 0);
        }

        return true;

    }


    @Override
    public boolean validateAttackSuit(List<Integer> pickedIndices, TurnManager turnManager) {
        return turnManager.getAttacker().isCardsTheSameSuit(pickedIndices);
    }

    @Override
    public boolean validateDefense(List<Integer> pickedIndices, DeckManager deckManager, TurnManager turnManager) {
        return deckManager.canPlayerDefense(pickedIndices, turnManager);
    }

    @Override
    public void playAttack(List<Integer> pickedIndices, TurnManager turnManager) {
        Player attacker = turnManager.getAttacker();
        List<Card> hand = attacker.getHand();


        turnManager.getAttackerCardsOnTable().addAll(sortAndRemove(pickedIndices, hand));
    }

    @Override
    public void playDefense(List<Integer> pickedIndices, TurnManager turnManager) {
        Player defender = turnManager.getDefender();
        List<Card> defenderHand = defender.getHand();

        turnManager.getDefenderCardsOnTable().addAll(sortAndRemove(pickedIndices, defenderHand));
    }

    private List<Card> sortAndRemove(List<Integer> pickedIndices, List<Card> defenderHand) {
        List<Card> pickedCards = new ArrayList<>();

        for (Integer index : pickedIndices) {
            pickedCards.add(defenderHand.get(index));
        }

        pickedIndices.sort(Comparator.reverseOrder());
        for (Integer index : pickedIndices) {
            defenderHand.remove((int) index);
        }

        return pickedCards;
    }


//    @Override
//    public void validateAttack(List<Card> cardsOnHand, TurnManager turnManager) {
//        Player currentPlayer = turnManager.getCurrentPlayer();
//        List<Card> attackerCardsOnTable = turnManager.getAttackerCardsOnTable();
//        while (true) {
//            if (canPlayerGo(cardsOnHand)) {
//                currentPlayer.getHand().removeAll(cardsOnHand);
//                attackerCardsOnTable.addAll(cardsOnHand);
//                break;
//            } else {
//                System.out.println("Pick correct cards please. You can pick cards only of one suit to go.");
//            }
//        }
//    }
//
//    @Override
//    public void validateDefense(List<Card> pickedCards, TurnManager turnManager, DeckManager deckManager) {
//        Player currentPlayer = turnManager.getCurrentPlayer();
//        Player anotherPlayer = turnManager.getAnotherPlayer();
//        List<Card> attackerCardsOnTable = turnManager.getAttackerCardsOnTable();
//        List<Card> defenderCardsOnTable = turnManager.getDefenderCardsOnTable();
//        List<Card> allCards = new ArrayList<>(attackerCardsOnTable);
//        allCards.addAll(defenderCardsOnTable);
//        currentPlayer.getHand().removeAll(defenderCardsOnTable);
//        anotherPlayer.getHand().removeAll(attackerCardsOnTable);
//        if (canPlayerDefense(pickedCards, turnManager, deckManager)) {
//            currentPlayer.addCardsToCollected(allCards);
//            System.out.println("You gave all cards on table to defender");
//        } else {
//            anotherPlayer.addCardsToCollected(allCards);
//            System.out.println("You gave all cards on table to attacker");
//        }
//    }

    //
//    @Override
//    public boolean isAttackOrDefenseValid(List<Integer> pickedNumbers, TurnManager turnManager, DeckManager deckManager, boolean isAttacker) {
//            if (isAttacker) {
//                boolean isSame = turnManager.getAttacker().isCardsTheSameSuit(pickedNumbers);
//                if (!isSame) {
//                    System.out.println("Please, pick cards of one suit");
//                    return false;
//                }
//            } else {
//                boolean canDefense = deckManager.canPlayerDefense(turnManager.getDefenderCardsOnTable(), turnManager);
//                if (!canDefense) {
//                    System.out.println("You lost this round and give your cards to attacker.");
//                    return false;
//                } else {
//                    System.out.println("You win this round and claimed attackers cards!");
//                    return true;
//                }
//            }
//
//    }

//    @Override
//    public boolean canPlayerGo(List<Card> cards) {
//        if (cards.size() == 1) {
//            return true;
//        }
//        Suit cardSuit = cards.get(0).getSuit();
//        for (Card card : cards) {
//            if (card.getSuit() != cardSuit) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//


}
