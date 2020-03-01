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
import static com.bacon.HandName.STRAIGHT;
import static com.bacon.HandName.STRAIGHT_FLUSH;
import static com.bacon.HandName.THREE_OF_A_KIND;
import static com.bacon.HandName.TWO_PAIR;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Hand {
    private HandName name;
    private Map<Character, Long> rankCount;
    private Map<Character, Long> suitCount;
    private List<Integer> positions;
    private final static String RANK_ORDER = "A23456789TJQKA";

    public Hand(String cardDefinitions) {
        name = parseDefinitions(cardDefinitions);
    }

    private HandName parseDefinitions(String cardDefinitions) {
        List<Card> cards = Stream.of(cardDefinitions.split(" ")).map(Card::new).collect(Collectors.toList());
        determineCounts(cards);

        return determineName();
    }

    private HandName determineName() {
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
        boolean hasAce = false;
        if (positions.get(0) == 0) {
            hasAce = true;
        }

        boolean inSequence = isInSequence();
        if (!inSequence && hasAce) {
            positions.remove(0);
            positions.add(13);
            inSequence = isInSequence();
        }

        return inSequence;
    }

    private boolean isInSequence() {
        for (int i = 0; i < positions.size() - 1; i++) {
            if (positions.get(i) != (positions.get(i + 1) - 1)) {
                return false;
            }
        }
        return true;
    }

    private void determineCounts(List<Card> cards) {
        assert (cards.size() == 5);
        rankCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        suitCount = cards.stream().collect(groupingBy(Card::getSuit, counting()));
        positions = cards.stream().map(Card::getPosition).sorted().collect(Collectors.toList());
    }

    HandName getName() {
        return name;
    }

    private static class Card {
        private char rank;
        private char suit;
        private int position;

        public Card(String definition) {
            assert (definition.length() == 2);
            // if required, further card validation could be carried out here
            this.rank = definition.charAt(0);
            this.suit = definition.charAt(1);
            this.position = RANK_ORDER.indexOf(rank);
        }

        public char getRank() {
            return rank;
        }

        public char getSuit() {
            return suit;
        }

        public int getPosition() {
            return position;
        }
    }
}
