package entities.match.interfaces;

import java.util.List;

public interface MoveManager {

    boolean validateAttackSuit(List<Integer> pickedIndices, TurnManager turnManager);

    boolean validateDefense(List<Integer> pickedIndices, DeckManager deckManager, TurnManager turnManager);

    void playAttack(List<Integer> pickedIndices, TurnManager turnManager);

    boolean canInitializeCurrentPlayer(DeckManager deckManager, TurnManager turnManager);

    void playDefense(List<Integer> pickedNumbers, TurnManager turnManager);
}
