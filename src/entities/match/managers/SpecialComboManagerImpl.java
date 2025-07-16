package entities.match.managers;

import entities.match.interfaces.DeckManager;
import entities.match.interfaces.ScoreManager;
import entities.match.interfaces.SpecialComboManager;
import entities.match.interfaces.TurnManager;

public class SpecialComboManagerImpl implements SpecialComboManager {

    private final ScoreManager scoreManager;
    private final TurnManager turnManager;
    private final DeckManager deckManager;
    public SpecialComboManagerImpl(ScoreManager scoreManager, TurnManager turnManager, DeckManager deckManager) {
        this.scoreManager = scoreManager;
        this.turnManager = turnManager;
        this.deckManager = deckManager;
    }

    @Override
    public boolean isSpecialCombination() {
        int firstPrioritizedPoints = scoreManager.getPrioritizePoints(turnManager, deckManager.getTrumpSuit())[0];
        int secondPrioritizedPoints =scoreManager.getPrioritizePoints(turnManager, deckManager.getTrumpSuit())[1];
        return firstPrioritizedPoints != 0 || secondPrioritizedPoints != 0;
    }
}
