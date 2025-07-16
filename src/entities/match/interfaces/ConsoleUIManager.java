package entities.match.interfaces;

import entities.Player;
import entities.match.Match;

import java.util.List;
import java.util.Scanner;

public interface ConsoleUIManager {
    Scanner scanner = new Scanner(System.in);

    List<Player> getPlayers();
    void setMatch(Match match, TurnManager turnManager);
    void pickCardsToMove(int cardCount, boolean isAttacker, MoveManager moveManager, DeckManager deckManager);
    void printWelcomeMessage();
}
