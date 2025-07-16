package entities.match.managers;

import entities.Card;
import entities.Deck;
import entities.Player;
import entities.match.interfaces.DeckManager;
import entities.match.interfaces.MoveManager;
import entities.match.interfaces.TurnManager;
import enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class DeckManagerImpl implements DeckManager {

    private static int trumpSuitIndex = 0;
    private Suit trumpSuit = Suit.values()[trumpSuitIndex];
    private Deck deck;
    private final TurnManager turnManager;

    public DeckManagerImpl(TurnManager turnManager) {
        this.turnManager = turnManager;
        deck = new Deck();
    }

    @Override
    public void refreshDeck() {
        deck = new Deck();
    }

    @Override
    public void nextTrump() {

        trumpSuitIndex++;
        if (trumpSuitIndex == 4) {
            trumpSuitIndex = 0;
        }
        trumpSuit = Suit.values()[trumpSuitIndex];


    }

    @Override
    public Deck getDeck() {
        return deck;
    }

    @Override
    public void addAllCardsToPlayerFromWinner(Player roundWinner, List<Card> allCards) {
        roundWinner.addCardsToCollected(allCards);
    }

    @Override
    public void distributeCards() {
        final int MAX_HAND_SIZE = 4;
        Player currentPlayer = turnManager.getCurrentPlayer();
        Player anotherPlayer = turnManager.getAnotherPlayer();

//        int currentPlayerNeeds = MAX_HAND_SIZE - currentPlayer.getHand().size();
//        int anotherPlayerNeeds = MAX_HAND_SIZE - anotherPlayer.getHand().size();

//        int currentPlayerCardsToAdd = Math.min(currentPlayerNeeds, deck.getCards().size());
//        for (int i = 0; i < currentPlayerCardsToAdd; i++) {
//            addHandToPlayer(currentPlayer, deck, 1);
//        }
//
//        int anotherPlayerCardsToAdd = Math.min(anotherPlayerNeeds, deck.getCards().size());
//        for (int i = 0; i < anotherPlayerCardsToAdd; i++) {
//            addHandToPlayer(anotherPlayer, deck, 1);
//        }
//
        List.of(currentPlayer, anotherPlayer).forEach(player -> {
            while (player.getHand().size() < MAX_HAND_SIZE) {
                addHandToPlayer(player, deck, 1);
            }
        });
    }


    @Override
    public void refreshIfNoTrumps(MoveManager moveManager) {
        do {
            deck = new Deck();
            deck.shuffleDeck();
            for (Player player : turnManager.getPlayers()) {
                player.setHand(new ArrayList<>());
            }
            this.distributeCards();

        } while (!moveManager.canInitializeCurrentPlayer(this, turnManager));
    }


    @Override
    public Card findMinCardToBeat(Card attackerCard, List<Card> defenderCards) {
        Card minDefenderCard = null;
        for (Card currentCard : defenderCards) {

            if (!currentCard.beats(attackerCard, trumpSuit)) continue;

            if (minDefenderCard == null || minDefenderCard.beats(currentCard, trumpSuit)) {
                minDefenderCard = currentCard;
            }
        }

        return minDefenderCard;
    }

    @Override
    public List<Card> getTrumps(List<Card> cards) {
        List<Card> trumps = new ArrayList<>();
        for (Card card : cards) {

            if (card.getSuit() == trumpSuit) {
                trumps.add(card);
            }
        }
        return trumps;

    }


    @Override
    public double getPlayerMinTrumpPower(List<Card> playerTrumps) {
        Card minCard = playerTrumps.get(0);
        for (Card trump : playerTrumps) {
            if (minCard.beats(trump, trumpSuit)) {
                minCard = trump;
            }
        }

        return minCard.getPower(true);

    }

    @Override
    public void addHandToPlayer(Player player, Deck deck, int count) {
        player.addCardsToHand(deck.getCardsFromDeck(count));
    }

    @Override
    public Card getMinTrumpSuitCard(List<Card> cards) {
        Card minCard = null;
        for (Card card : cards) {
            if (card.getSuit().equals(getTrumpSuit())) {
                if (minCard == null) {
                    minCard = card;
                } else if (minCard.beats(card, trumpSuit)) {
                    minCard = card;
                }
            }
        }

        return minCard;
    }

    @Override
    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    @Override
    public boolean canPlayerDefense(List<Integer> pickedIndices, TurnManager turnManager) {
        List<Card> pickedCards = new ArrayList<>();
        for (int index : pickedIndices) {
            pickedCards.add(turnManager.getDefender().getHand().get(index));
        }
        for (Card attackerCard : turnManager.getAttackerCardsOnTable()) {
            Card foundCard = this.findMinCardToBeat(attackerCard, pickedCards);
            if (foundCard == null) {
                return false;
            }
            pickedCards.remove(foundCard);
        }
        return true;
    }
}
