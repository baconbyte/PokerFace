package com.bacon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

class HandTest {

    @DisplayName("Determine Hand Names")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvFileSource(resources = "/hand-names.csv")
    void shouldDetermineCorrectHands(String cardDescriptions, HandName handName) {
        Hand hand = new Hand(cardDescriptions);
        assertThat(hand.getName()).isEqualTo(handName);
    }
}