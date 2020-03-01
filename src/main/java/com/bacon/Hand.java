package com.bacon;

import static com.bacon.HandName.HIGH_CARD;

public class Hand {
    private HandName name;

    public Hand(String cards) {
        name = HIGH_CARD;
    }

    HandName getName() {
        return name;
    }
}
