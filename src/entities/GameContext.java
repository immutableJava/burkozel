package entities;

import entities.match.interfaces.*;

public class GameContext {
    public final DeckManager deckManager;
    public final MoveManager moveManager;
    public final ScoreManager scoreManager;
    public final SpecialComboManager specialComboManager;
    public final TableManager tableManager;
    public final TurnManager turnManager;

    public GameContext(DeckManager deckManager, MoveManager moveManager, ScoreManager scoreManager, SpecialComboManager specialComboManager, TableManager tableManager, TurnManager turnManager) {
        this.deckManager = deckManager;
        this.moveManager = moveManager;
        this.scoreManager = scoreManager;
        this.specialComboManager = specialComboManager;
        this.tableManager = tableManager;
        this.turnManager = turnManager;
    }
}
