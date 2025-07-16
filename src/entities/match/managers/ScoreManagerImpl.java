package entities.match.managers;

import entities.Player;
import entities.match.interfaces.ScoreManager;
import entities.match.interfaces.TurnManager;
import enums.Suit;

public class ScoreManagerImpl implements ScoreManager {

    private int drawMultiplier = 1;

    public int[] getPrioritizePoints(TurnManager turnManager, Suit trumpSuit) {
        Player currentPlayer = turnManager.getCurrentPlayer();
        Player anotherPlayer = turnManager.getAnotherPlayer();

        int firstPrioritizedPoints = 0;
        if (currentPlayer.isBura(trumpSuit) || currentPlayer.isMolotka(trumpSuit) || currentPlayer.isMoscow(trumpSuit)) {
            firstPrioritizedPoints = currentPlayer.getPrioritizePoints();
        }

        int secondPrioritizedPoints = 0;
        if (anotherPlayer.isBura(trumpSuit) || anotherPlayer.isMolotka(trumpSuit) || anotherPlayer.isMoscow(trumpSuit)) {
            secondPrioritizedPoints = anotherPlayer.getPrioritizePoints();
        }
        return new int[]{firstPrioritizedPoints, secondPrioritizedPoints};
    }

    @Override
    public void calculatePlayersMatchScore(TurnManager turnManager) {
        Player player1 = turnManager.getCurrentPlayer();
        Player player2 = turnManager.getAnotherPlayer();

        int score1 = player1.getRoundScore();
        int score2 = player2.getRoundScore();

        if (score1 == score2) {
            drawMultiplier++;
            return;
        }

        Player winner = score1 > score2 ? player1 : player2;
        Player loser = score1 > score2 ? player2 : player1;
        int diff = Math.abs(score1 - score2);

        applyMatchPoints(winner, loser, diff);
    }

    private void applyMatchPoints(Player winner, Player loser, int diff) {
        if (diff == 120) {
            if (loser.getCollectedCards().isEmpty()) {
                winner.addMatchScore(6 * drawMultiplier);
            } else {
                winner.addMatchScore(4 * drawMultiplier);
            }
        } else if (loser.getRoundScore() < 31) {
            winner.addMatchScore(4 * drawMultiplier);
        } else {
            winner.addMatchScore(2 * drawMultiplier);
        }

        drawMultiplier = 1;
    }
}
