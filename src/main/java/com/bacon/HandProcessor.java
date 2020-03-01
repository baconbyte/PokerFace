package com.bacon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HandProcessor {
    private String filename;

    public HandProcessor(String filename) {
        this.filename = filename;
    }

    public void processFile() {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                try {
                    String cardDefinitions = scanner.nextLine();
                    Hand hand = new Hand(cardDefinitions);
                    System.out.println(cardDefinitions + " => " + hand.getName());
                } catch (AssertionError ae) {
                    //Invalid line, ignoring and continuing with processing
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("File not Found");
        }
    }
}
