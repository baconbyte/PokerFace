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
        assertThat(outContent.toString()).isEqualTo("3H JS 4C 7C 5D => High Card");
    }

}