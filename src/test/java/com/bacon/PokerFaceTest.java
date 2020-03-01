package com.bacon;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class PokerFaceTest extends PokerFace {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldHandleNoArgs() {
        main(new String[0]);
        assertThat(errContent.toString()).isEqualTo("Missing input file argument\n" +
                "Proper Usage is: java PokerFace input.txt\n");
    }

    @Test
    public void shouldProcessSingleHand() {
        String[] args = {"src/test/resources/single-hand.txt"};
        main(args);
        assertThat(outContent.toString()).isEqualTo("3H JS 4C 7C 5D => High Card\n");
    }

    @Test
    public void shouldProcessMultipleHand() {
        String[] args = {"src/test/resources/multiple-hands.txt"};
        main(args);
        assertThat(outContent.toString()).isEqualTo("3H JS 3C 7C 5D => One Pair\n" +
                "JH 2C JD 2H 4C => Two Pairs\n" +
                "9H 9D 3S 9S 9C => Four of a Kind\n" +
                "9C 3H 9S 9H 3S => Full House\n");
    }


    @Test
    public void shouldIgnoreInvalidLines() {
        String[] args = {"src/test/resources/invalid-lines.txt"};
        main(args);
        assertThat(outContent.toString()).isEqualTo("JH 2C JD 2H 4C => Two Pairs\n" +
                "9C 3H 9S 9H 3S => Full House\n");
    }

}