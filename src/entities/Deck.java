package entities;

import enums.Face;
import enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Deck {
    private LinkedList<Card> cards;

    public Deck() {
        initialDeck();
    }


    public void initialDeck() {
        cards = new LinkedList<>();
        for (Suit suit : Suit.values()) {
            for (Face face : Face.values()) {
                cards.add(new Card(suit, face));
            }
        }
        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }


    public List<Card> getCardsFromDeck(int count) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            hand.add(cards.poll());
        }
        return hand;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }


    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
}
