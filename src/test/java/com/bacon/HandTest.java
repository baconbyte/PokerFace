package com.bacon;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandTest {

    @Test
    public void shouldDetermineHighCard() {
        Hand hand = new Hand("3H JS 4C 7C 5D");
        assertThat(hand.getName()).isEqualTo("High Card");
    }

}