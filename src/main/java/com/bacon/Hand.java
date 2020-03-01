package com.bacon;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bacon.HandName.FLUSH;
import static com.bacon.HandName.FOUR_OF_A_KIND;
import static com.bacon.HandName.FULL_HOUSE;
import static com.bacon.HandName.HIGH_CARD;
import static com.bacon.HandName.ONE_PAIR;
import static com.bacon.HandName.ROYAL_FLUSH;
import static com.bacon.HandName.STRAIGHT;
import static com.bacon.HandName.STRAIGHT_FLUSH;
import static com.bacon.HandName.THREE_OF_A_KIND;
import static com.bacon.HandName.TWO_PAIR;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Hand {
    //Rank order, lowest to highest including low & high aces
    private final static String RANK_ORDER = "A23456789TJQKA";
    //Map containing the counts of all the ranks in the hand
    private Map<Character, Long> rankCount;
    //Map containing the counts of all the suits in the hand
    private Map<Character, Long> suitCount;
    //Sorted list of indexes of where the cards appear in the RANK_ORDER
    private List<Integer> rankIndexes;

    private HandName name;

    public Hand(String cardDefinitions) {
        init(cardDefinitions);
    }

    private void init(String cardDefinitions) {
        List<Card> cards = Stream.of(cardDefinitions.split(" ")).map(Card::new).collect(Collectors.toList());
        determineCounts(cards);
        name = determineName();
    }

    private HandName determineName() {
        if (isStraight() && suitCount.containsValue(5L) && rankIndexes.get(0) == 9) {
            return ROYAL_FLUSH;
        }
        if (isStraight() && suitCount.containsValue(5L)) {
            return STRAIGHT_FLUSH;
        }
        if (rankCount.containsValue(4L)) {
            return FOUR_OF_A_KIND;
        }
        if (rankCount.containsValue(3L) && rankCount.containsValue(2L)) {
            return FULL_HOUSE;
        }
        if (suitCount.containsValue(5L)) {
            return FLUSH;
        }
        if (isStraight()) {
            return STRAIGHT;
        }
        if (rankCount.containsValue(3L)) {
            return THREE_OF_A_KIND;
        }
        if (Collections.frequency(rankCount.values(), 2L) == 2) {
            return TWO_PAIR;
        }
        if (rankCount.containsValue(2L)) {
            return ONE_PAIR;
        }
        return HIGH_CARD;
    }

    private boolean isStraight() {
        boolean inSequence = isInSequence();
        if (!inSequence && hasAce()) {
            //try sequence again with ace in last position
            rankIndexes.remove(0);
            rankIndexes.add(13);
            inSequence = isInSequence();
        }

        return inSequence;
    }

    private boolean hasAce() {
        boolean hasAce = false;
        if (rankIndexes.get(0) == 0) {
            hasAce = true;
        }
        return hasAce;
    }

    private boolean isInSequence() {
        for (int i = 0; i < rankIndexes.size() - 1; i++) {
            if (rankIndexes.get(i) != (rankIndexes.get(i + 1) - 1)) {
                return false;
            }
        }
        return true;
    }

    private void determineCounts(List<Card> cards) {
        assert (cards.size() == 5);
        rankCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        suitCount = cards.stream().collect(groupingBy(Card::getSuit, counting()));
        rankIndexes = cards.stream().map(Card::getRankIndex).sorted().collect(Collectors.toList());
    }

    HandName getName() {
        return name;
    }

    private static class Card {
        private char rank;
        private char suit;
        private int rankIndex;

        public Card(String definition) {
            assert (definition.length() == 2);
            // if required, further card validation could be carried out here
            this.rank = definition.charAt(0);
            this.suit = definition.charAt(1);
            this.rankIndex = RANK_ORDER.indexOf(rank);
        }

        public char getRank() {
            return rank;
        }

        public char getSuit() {
            return suit;
        }

        public int getRankIndex() {
            return rankIndex;
        }
    }
}
