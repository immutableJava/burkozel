package entities.match;

import entities.Player;
import entities.match.interfaces.*;
import enums.Result;

import java.util.*;

public class Match {
    private final List<Round> rounds = new ArrayList<>();
    private List<Player> players;
    private final ConsoleUIManager consoleUIManager;
    private final TurnManager turnManager;
    private final SpecialComboManager specialComboManager;
    private final TableManager tableManager;
    private final MoveManager moveManager;

    public Match(ConsoleUIManager consoleUIManager, TurnManager turnManager, SpecialComboManager specialComboManager, TableManager tableManager, MoveManager moveManager) {

        this.consoleUIManager = consoleUIManager;
        this.players = consoleUIManager.getPlayers();
        this.turnManager = turnManager;
        this.specialComboManager = specialComboManager;
        this.tableManager = tableManager;
        this.moveManager = moveManager;
    }

    public void start(DeckManager deckManager, ScoreManager scoreManager) {

        consoleUIManager.setMatch(this, turnManager);

        players = consoleUIManager.getPlayers();
        deckManager.refreshDeck();
        deckManager.getDeck().shuffleDeck();
        int roundNumber = 1;

        while (!deckManager.getDeck().getCards().isEmpty()) {
            rounds.add(new Round(players, turnManager, deckManager, specialComboManager, tableManager, moveManager));
            rounds.get(rounds.size() - 1).startRound(roundNumber);
            roundNumber++;
        }
        scoreManager.calculatePlayersMatchScore(turnManager);
        System.out.printf("Match Score - %s: %d%nMatch score - %s: %d%n", turnManager.getCurrentPlayer().getName(), turnManager.getCurrentPlayer().getMatchScore(), turnManager.getAnotherPlayer().getName(), turnManager.getAnotherPlayer().getMatchScore());


    }

    public Result getMatchResult() {
        List<Player> players = turnManager.getPlayers();
        players.sort(Collections.reverseOrder());
        if(players.get(0).getRoundScore() > players.get(1).getRoundScore()) {
            return Result.WINNER1;
        } else if(players.get(0).getRoundScore() < players.get(1).getRoundScore()) {
            return Result.WINNER2;
        } else {
            return Result.DRAW;
        }
    }

    public void calculateScoreForPlayers() {
        List<Player> players = turnManager.getPlayers();
        players.forEach(Player::calculateRoundScore);

    }

    public void addRound(Round round) {
        rounds.add(round);
    }

    public void addRound(DeckManager deckManager) {
        rounds.add(new Round(players, turnManager, deckManager, specialComboManager, tableManager, moveManager));
    }
}
