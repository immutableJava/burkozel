package entities.match.interfaces;

import entities.Card;
import entities.Player;

import java.util.List;

public interface TurnManager {

    Player getCurrentPlayer();

    Player getAnotherPlayer();

    Player getAttacker();

    Player getDefender();
    
    void swipeCurrentPlayer();

    void setCurrentPlayer(Player player);

    void setCurrentPlayerIndex(int currentPlayerIndex);

    List<Player> getPlayers();

    void setPlayers(List<Player> players);

    List<Card> getAttackerCardsOnTable();

    void setAttackerCardsOnTable(List<Card> attackerCardsOnTable);

    List<Card> getDefenderCardsOnTable();

    void setDefenderCardsOnTable(List<Card> defenderCardsOnTable);

    boolean isCurrentPlayerAttacks();

    void setIsCurrentPlayerAttacks(boolean isCurrentPlayerAttacks);

}
