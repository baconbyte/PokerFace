# Poker Face
## Background
The Poker Face application reads input from a file and convert the specified hands into the name of the corresponding poker hand.

Each line of the input file will contain 5 valid card descriptions. Each description is in the form CS, where C is the name of the card (2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A) and S is the suit (H, D, S, C for Hearts, Diamonds, Spades and Clubs respectively).

Example input: 
```
3H JS 3C 7C 5D  
JH 2C JD 2H 4C  
9H 9D 3S 9S 9C  
9C 3H 9S 9H 3S
```
Example output:  
```
3H JS 3C 7C 5D => One pair  
JH 2C JD 2H 4C => Two pair  
9H 9D 3S 9S 9C => Four of a kind   
9C 3H 9S 9H 3S => Full house  
```
## Build
The Poker Face application is packaged as an executable jar file using
```
mvn clean install
```

## Run
The Poker Face application takes the input file name as a parameter and can be run as follows:

```
java -jar target/poker-face-1.0-SNAPSHOT.jar input.txt
```