package entities;

import enums.Face;
import enums.Suit;

import java.util.Objects;

public class Card {
    private final Suit suit;
    private final Face face;
    private final int scoreValue;
    private final int powerRank;


    public Card(Suit suit, Face face) {
        this.suit = suit;
        this.face = face;
        scoreValue = switch (face) {
            case J -> 2;
            case Q -> 3;
            case K -> 4;
            case TEN -> 10;
            case A -> 11;
            default -> 0;
        };

        powerRank = switch (face) {
            case A -> 8;
            case TEN -> 7;
            case K -> 6;
            case Q -> 5;
            case J -> 4;
            case NINE -> 3;
            case EIGHT -> 2;
            case SEVEN -> 1;
            case SIX -> 0;
        };
    }

    public boolean beats(Card other, Suit trumpSuit) {
        boolean thisIsTrump = this.suit == trumpSuit;
        boolean otherIsTrump = other.suit == trumpSuit;

        if (thisIsTrump && !otherIsTrump) return true;
        if (!thisIsTrump && otherIsTrump) return false;
        if (this.suit != other.suit) return false;

        return this.powerRank > other.powerRank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Face getFace() {
        return face;
    }
    
    public int getPower(boolean isTrumpSuit) {
        return isTrumpSuit ? scoreValue + 11 : scoreValue;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    public String toString() {
        return "[" + face + " " + suit + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) return false;
        return scoreValue == card.scoreValue && suit == card.suit && face == card.face;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, face, scoreValue);
    }
}
