package entities.match.interfaces;

import entities.Card;
import entities.Player;

import java.util.List;

public interface TableManager {
    Player putAllCardsOnTable();

    void attackerPickCards();

    Player playDefenseAndResolve(boolean defenseSuccess, TableManager tableManager);

    boolean defenderPickCardsAndResolve();

    List<Card> getAllCardsOnTable();
}
