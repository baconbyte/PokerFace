package com.bacon;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bacon.HandName.*;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Hand {
    private HandName name;

    public Hand(String cardDefinitions) {
        name = parseDefinitions(cardDefinitions);
    }

    private HandName parseDefinitions(String cardDefinitions) {
        List<Card> cards = Stream.of(cardDefinitions.split(" ")).map(Card::new).collect(Collectors.toList());
        assert (cards.size() == 5);

        Map<Character, Long> rankCount =
                cards.stream().collect(groupingBy(Card::getRank, counting()));

        if (Collections.frequency(rankCount.values(), 2L) == 2) {
            return TWO_PAIR;
        }
        if (rankCount.containsValue(2L)) {
            return ONE_PAIR;
        }
        return HIGH_CARD;
    }

    HandName getName() {
        return name;
    }

    private static class Card {
        private char rank;
        private char suit;

        public Card(String definition) {
            assert (definition.length() == 2);
            // further card validation could be carried out here
            this.rank = definition.charAt(0);
            this.suit = definition.charAt(1);
        }

        public char getRank() {
            return rank;
        }

        public char getSuit() {
            return suit;
        }
    }
}
