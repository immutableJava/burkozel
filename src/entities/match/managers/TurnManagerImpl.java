package entities.match.managers;

import entities.Card;
import entities.Player;
import entities.match.interfaces.TurnManager;

import java.util.ArrayList;
import java.util.List;

public class TurnManagerImpl implements TurnManager {
    private int currentPlayerIndex;
    private int anotherPlayerIndex;
    private List<Player> players;
    private List<Card> attackerCardsOnTable;
    private List<Card> defenderCardsOnTable;
    private boolean currentPlayerAttacks;;


    public TurnManagerImpl(List<Player> players) {
        this.players = players;
        attackerCardsOnTable = new ArrayList<>();
        defenderCardsOnTable = new ArrayList<>();
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }



    @Override
    public Player getAnotherPlayer() {
        return players.get(currentPlayerIndex == 0 ? 1 : 0);
    }

    @Override
    public Player getAttacker() {
        return currentPlayerAttacks ? getCurrentPlayer() : getAnotherPlayer();
    }

    @Override
    public Player getDefender() {
        return currentPlayerAttacks ? getAnotherPlayer() : getCurrentPlayer();
    }


    @Override
    public void swipeCurrentPlayer() {
        setCurrentPlayerIndex(anotherPlayerIndex);
    }

    @Override
    public void setCurrentPlayer(Player player) {
        int index = players.indexOf(player);
        setCurrentPlayerIndex(index);
    }

    @Override
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        this.anotherPlayerIndex = currentPlayerIndex == 1 ? 0 : 1;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public List<Card> getAttackerCardsOnTable() {
        return attackerCardsOnTable;
    }
    @Override
    public void setAttackerCardsOnTable(List<Card> attackerCardsOnTable) {
        this.attackerCardsOnTable = attackerCardsOnTable;
    }

    @Override
    public List<Card> getDefenderCardsOnTable() {
        return defenderCardsOnTable;
    }
    @Override
    public void setDefenderCardsOnTable(List<Card> defenderCardsOnTable) {
        this.defenderCardsOnTable = defenderCardsOnTable;
    }
    @Override
    public boolean isCurrentPlayerAttacks() {
        return currentPlayerAttacks;
    }
    @Override
    public void setIsCurrentPlayerAttacks(boolean isCurrentPlayerAttacks) {
        this.currentPlayerAttacks = isCurrentPlayerAttacks;
    }


}
