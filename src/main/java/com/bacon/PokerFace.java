package com.bacon;

public class PokerFace {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Missing input file argument");
            System.err.println("Proper Usage is: java PokerFace input.txt");
        } else {
            new PokerFace().processHands(args[0]);
        }
    }

    private void processHands(String filename) {
        new HandProcessor(filename).processFile();
    }
}
