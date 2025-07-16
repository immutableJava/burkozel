package entities.match.interfaces;

import entities.Card;
import entities.Deck;
import entities.Player;
import enums.Suit;

import java.util.List;

public interface DeckManager {
    void refreshDeck();

    void nextTrump();

    Deck getDeck();

    void addAllCardsToPlayerFromWinner(Player roundWinner, List<Card> allCards);

    void distributeCards();

    void addHandToPlayer(Player player, Deck deck, int count);

    Card findMinCardToBeat(Card card, List<Card> cards);

    List<Card> getTrumps(List<Card> cards);

    double getPlayerMinTrumpPower(List<Card> playerTrumps);

    void refreshIfNoTrumps(MoveManager moveManager);

    Card getMinTrumpSuitCard(List<Card> cards);

    Suit getTrumpSuit();

    boolean canPlayerDefense(List<Integer> pickedIndices, TurnManager turnManager);
}
