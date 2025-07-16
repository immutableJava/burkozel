package entities;

import entities.match.Match;
import entities.match.interfaces.*;
import entities.match.managers.*;

import java.util.ArrayList;
import java.util.List;


public class Game {

    private final List<Match> matches = new ArrayList<>();
    private Player winner;
    private Player loser;
    private final ConsoleUIManager consoleUIManager = new ConsoleUIManagerImpl();
    private final TurnManager turnManager = new TurnManagerImpl(consoleUIManager.getPlayers());
    private final MoveManager moveManager = new MoveManagerImpl(consoleUIManager.getPlayers());
    private final DeckManager deckManager = new DeckManagerImpl(turnManager);
    private final ScoreManager scoreManager = new ScoreManagerImpl();
    private final SpecialComboManager specialComboManager = new SpecialComboManagerImpl(scoreManager, turnManager, deckManager);
    private final TableManager tableManager = new TableManagerImpl(turnManager, deckManager, consoleUIManager, moveManager);

    public void startGame() {
        consoleUIManager.printWelcomeMessage();
        System.out.println("Game STARTED!");


        while (turnManager.getCurrentPlayer().getMatchScore() < 12 && turnManager.getAnotherPlayer().getMatchScore() < 12) {
            matches.add(new Match(consoleUIManager, turnManager, specialComboManager, tableManager, moveManager));
            matches.get(matches.size() - 1).start(deckManager, scoreManager);
        }

    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

}
