package entities.match.managers;

import entities.Player;
import entities.match.Match;
import entities.match.interfaces.ConsoleUIManager;
import entities.match.interfaces.DeckManager;
import entities.match.interfaces.MoveManager;
import entities.match.interfaces.TurnManager;

import java.util.ArrayList;
import java.util.List;


public class ConsoleUIManagerImpl implements ConsoleUIManager {


    private final List<Player> players = new ArrayList<>();
    private Match match;
    private TurnManager turnManager;

    @Override
    public void setMatch(Match match, TurnManager turnManager) {
        this.match = match;
        this.turnManager = turnManager;
    }

    public void printWelcomeMessage() {
        System.out.println("------------------------------------");
        System.out.println("** Welcome to Burkozel card game! **");
        System.out.print("Enter name of player 1: ");
        String player1Name = scanner.nextLine();
        Player firstPlayer = new Player(0, player1Name);
        System.out.print("Enter name of player 2: ");
        String player2Name = scanner.nextLine();
        Player secondPlayer = new Player(1, player2Name);
        players.addAll(List.of(firstPlayer, secondPlayer));
    }

    public List<Player> getPlayers() {
        return players;
    }


    @Override
    public void pickCardsToMove(int cardCount, boolean isAttacker, MoveManager moveManager, DeckManager deckManager) {
        boolean validInput = false;
        String[] pickedNumbersStr;
        List<Integer> pickedNumbers = new ArrayList<>();
        while (!validInput) {
            pickedNumbers.clear();
            try {
                pickedNumbersStr = scanner.nextLine().split(",");
                if (pickedNumbersStr.length > cardCount) {
                    System.out.printf("You can only pick till %d cards%n", cardCount);
                    System.out.println(isAttacker ? turnManager.getAttacker().getHand() : turnManager.getDefender().getHand() + " Your hand");
                    continue;
                }

                boolean allValid = true;
                for (String numStr : pickedNumbersStr) {
                    int pickedNumber = Integer.parseInt(numStr.trim());
                    if (pickedNumber < 1 || pickedNumber > 4) {
                        System.out.printf("Invalid card number %d (must be between 1 and 4)%n", pickedNumber);
                        System.out.println(turnManager.getCurrentPlayer().getHand() + " Your hand");
                        allValid = false;
                        break;
                    }
                    pickedNumbers.add(pickedNumber - 1);
                }

                if (allValid) {
                    if (isAttacker) {
                        if (!moveManager.validateAttackSuit(pickedNumbers, turnManager)) {
                            System.out.println("Please, pick cards of one suit");
                            continue;
                        }
                        moveManager.playAttack(pickedNumbers, turnManager);
                    } else {
                        moveManager.playDefense(pickedNumbers, turnManager);
                    }
                    validInput = true;
                }

            } catch (NumberFormatException nfe) {
                System.out.println("Use only correct card numbers between 1 and 4!");
            }
        }
    }

}
