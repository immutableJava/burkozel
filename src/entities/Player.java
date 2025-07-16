package entities;

import enums.Face;
import enums.Suit;

import java.util.*;

public class Player implements Comparable<Player> {
    private final int id;
    private final String name;
    private final List<Card> collectedCards;
    private List<Card> hand;
    private boolean isWinner;
    private int prioritizePoints;
    private int roundScore;
    private int matchScore;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        roundScore = 0;
        matchScore = 0;
        prioritizePoints = 0;
        collectedCards = new LinkedList<>();
        hand = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addCardsToHand(List<Card> cards) {
        hand.addAll(cards);
    }

    public void addCardsToCollected(List<Card> cards) {
        collectedCards.addAll(cards);
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getCollectedCards() {
        return collectedCards;
    }

    public int getRoundScore() {
        return roundScore;
    }


    public int getMatchScore() {
        return matchScore;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void calculateRoundScore() {
        int score = 0;
        for (Card card : collectedCards) {
            score += card.getScoreValue();
        }
        roundScore = score;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public void addRoundScore(int addedScore) {
        roundScore += addedScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }


    public void addMatchScore(int addedScore) {
        matchScore += addedScore;
    }

    public void move() {

    }

    public int maxCardCountOfSameSuit() {
        int heartsCount = 0;
        int diamondsCount = 0;
        int clubsCount = 0;
        int spadesCount = 0;
        for (Card card : hand) {
            switch (card.getSuit()) {
                case HEARTS -> heartsCount++;
                case DIAMONDS -> diamondsCount++;
                case CLUBS -> clubsCount++;
                default -> spadesCount++;

            }
        }

        return Integer.max(Integer.max(heartsCount, diamondsCount), Integer.max(clubsCount, spadesCount));
    }

    public boolean isCardsTheSameSuit(List<Integer> indexes) {
        Suit firstSuit = hand.get(indexes.get(0)).getSuit();
        for (Integer index : indexes) {
            if(firstSuit != hand.get(index).getSuit()) {
                return false;
            }
        }
        return true;
    }


    public boolean isBura(Suit trumpSuit) {
        Suit currentSuit = hand.get(0).getSuit();

        for (Card card : hand) {
            if (card.getSuit() != currentSuit) {
                return false;
            }
        }
        if (currentSuit == trumpSuit) {
            prioritizePoints += 2;
        }
        return currentSuit == trumpSuit;
    }

    public boolean isMolotka(Suit trumpSuit) {
        Suit currentSuit = hand.get(0).getSuit();

        for (Card card : hand) {
            if (card.getSuit() != currentSuit) {
                return false;
            }
        }
        if (currentSuit != trumpSuit) {
            prioritizePoints += 1;
        }

        return currentSuit != trumpSuit;
    }
    public boolean isMoscow(Suit trumpSuit) {
        boolean flag = true;
        boolean isTrumpSuitFlag = false;
        int winnerCounter = 0;

        for (Card card : hand) {
            if (card.getFace() != Face.TEN && card.getFace() != Face.A) {
                flag = false;
                break;
            }
            if (card.getSuit() == trumpSuit) {
                isTrumpSuitFlag = true;
            }
            if (card.getFace() == Face.A) {
                winnerCounter++;
            }
        }

//        isWinner = isHandContainsFourA(winnerCounter);

        return flag && isTrumpSuitFlag;
    }




    public int getPrioritizePoints() {
        return prioritizePoints;
    }

    public void setPrioritizePoints(int prioritizePoints) {
        this.prioritizePoints = prioritizePoints;
    }

    public boolean isHandContainsFourA(int count) {
        return count > 3;
    }

    public boolean isWinner() {
        return isWinner;
    }


    @Override
    public int compareTo(Player player) {
        return Integer.compare(this.getRoundScore(), player.getRoundScore());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player player)) return false;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", collectedCards=" + collectedCards +
                ", hand=" + hand +
                ", isWinner=" + isWinner +
                ", prioritizePoints=" + prioritizePoints +
                ", roundScore=" + roundScore +
                ", matchScore=" + matchScore +
                '}';
    }


}
