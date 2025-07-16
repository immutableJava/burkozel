package entities.match;

import entities.Player;
import entities.match.interfaces.*;
import enums.Result;

import java.util.List;

public class Round {
    private final List<Player> players;
    private final TurnManager turnManager;
    private final DeckManager deckManager;
    private final SpecialComboManager specialComboManager;
    private final TableManager tableManager;
    private final MoveManager moveManager;
    private Player winner;

    public Round(List<Player> players, TurnManager turnManager, DeckManager deckManager, SpecialComboManager specialComboManager, TableManager tableManager, MoveManager moveManager) {
        this.players = players;
        this.turnManager = turnManager;
        this.deckManager = deckManager;
        this.specialComboManager = specialComboManager;
        this.tableManager = tableManager;
        this.moveManager = moveManager;
    }

    //repeat problem, fix!
    public void validateMove() {
        if (specialComboManager.isSpecialCombination()) {
            Player firstPlayer = players.get(0);
            Player secondPlayer = players.get(1);
            if(firstPlayer.getPrioritizePoints() > secondPlayer.getPrioritizePoints()) {
                setWinner(firstPlayer);
            } else if(secondPlayer.getPrioritizePoints() > firstPlayer.getPrioritizePoints()) {
                setWinner(secondPlayer);

            } else {
                Player player = tableManager.putAllCardsOnTable();
                setWinner(player);
            }
            turnManager.setCurrentPlayer(winner);
            printLogs();

        } else {
            tableManager.attackerPickCards();
            boolean defenseSuccess = tableManager.defenderPickCardsAndResolve();
            System.out.println(players.get(0).getHand() + " " + players.get(0).getName() + "'s hand");
            System.out.println(players.get(1).getHand() + " " + players.get(1).getName() + "'s hand");
            winner = tableManager.playDefenseAndResolve(defenseSuccess, tableManager);
            turnManager.setCurrentPlayer(winner);
            turnManager.setIsCurrentPlayerAttacks(!defenseSuccess);
            printLogs();
            setWinner(winner);
            if (defenseSuccess) {
                System.out.printf("%s successfully defended and won the round!%n", turnManager.getDefender().getName());
            } else {
                System.out.printf("%s failed to defend. %s wins the round!%n",
                        turnManager.getDefender().getName(),
                        turnManager.getAttacker().getName());
            }


            if (defenseSuccess) {
                turnManager.swipeCurrentPlayer();
            }


        }

        turnManager.getAttacker().setPrioritizePoints(0);
        turnManager.getDefender().setPrioritizePoints(0);
    }

    public void printLogs() {

        System.out.println(winner.getCollectedCards() + " - All collected cards of " + winner.getName());

        System.out.println(winner.getName() + " - is Winner");

        System.out.println(players.get(0).getHand() + " " + players.get(0).getName() + "'s hand remaining");
        System.out.println(players.get(1).getHand() + " " + players.get(1).getName() + "'s hand remaining");
    }


    public Player getWinner() {
        return winner;
    }

    private void setWinner(Player winner) {
        this.winner = winner;
    }

    public void startRound(int roundCounter) {
        deckManager.distributeCards();
        System.out.println("Round - " + roundCounter);
        if(roundCounter <= 1) {
            deckManager.refreshIfNoTrumps(moveManager);
        }

        validateMove();
    }


    public Result getResult() {
        if (players.get(0).getRoundScore() == players.get(1).getRoundScore()) {
            return Result.DRAW;
        } else if (players.get(0).getRoundScore() > players.get(1).getRoundScore()) {
            return Result.WINNER1;
        } else {
            return Result.WINNER2;
        }
    }


//    public void setPoints(int score) {
//        getWinner().setMatchScore(score * getMultiplierScore());
//    }

    public int getMultiplierScore() {
        if (getResult() == Result.DRAW) {
            return 0;
        }

        int scoreDifference = Math.abs(players.get(0).getRoundScore() - players.get(1).getRoundScore());

        if (scoreDifference == 120 && getWinner().getCollectedCards().size() == 120) {
            return 6;
        } else if (scoreDifference > 58) {
            return 4;
        } else {
            return 2;
        }
    }



}
