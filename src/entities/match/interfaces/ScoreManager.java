package entities.match.interfaces;

import enums.Suit;

public interface ScoreManager {
     int[] getPrioritizePoints(TurnManager turnManager, Suit trumpSuit);
     void calculatePlayersMatchScore(TurnManager turnManager);
}
