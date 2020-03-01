package com.bacon;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class HandProcessorTest {

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
    void shouldHandleUnknownFile() {
        HandProcessor objectToTest = new HandProcessor("unknown.txt");
        objectToTest.processFile();
        assertThat(errContent.toString()).isEqualTo("File not Found\n");
    }

    @Test
    void shouldReadAvailableFile() {
        HandProcessor objectToTest = new HandProcessor("src/test/resources/single-hand.txt");
        objectToTest.processFile();
        assertThat(outContent.toString()).isEqualTo("3H JS 4C 7C 5D => High Card");
    }
}