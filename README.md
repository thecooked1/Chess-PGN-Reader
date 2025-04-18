# Chess PGN Game Validator

## Overview

This Java-based project provides implementation for 
reading, parsing and interpreting chess PGN (Portable Game Notation) files 
commonly used for recording chess games. It includes core capable of validating moves, 
tracking game state and detecting conditions. The goal is to replay games from a PGN file 
and verify the legality of each move according to the rules of chess.

# How to run 

To compile first, From the command line, navigate to the `src` directory 
(or the project root if using a build tool like Maven/Gradle) and compile:

```bash
# Example (adjust classpath and output directory as needed)
javac -d out Main.java Game/*.java Board/*.java Pieces/*.java PGNParser/*.java
```
If using an IDE (like IntelliJ IDEA, Eclipse), it will handle this automatically.

Execute the `Main` class. Make sure the PGN file (e.g., `Tbilisi2015.pgn`) 
is accessible in the specified path. 

```bash
    # Example (if compiled to 'out' directory)
    java -cp out Main
   ```
The program will load the PGN file, attempt to replay each game, and print the progress or any errors encountered to the console.

# Example I/O

The `Main.java` class provides a basic example:

```java
import PGNParser.Parser;
import Game.Game;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Specify the path to your PGN file
        File pgnFile = new File("path/to/your/Tbilisi2015.pgn");
        Parser parser = new Parser();
        Game game = new Game();
        try {
            parser.loadPGN(pgnFile); // Load games from the file
            game.playAllGames(parser); // Replay and validate all loaded games
        } catch (IOException e) {
            System.err.println("Error reading PGN file: " + e.getMessage());
        }
    }
}
```
Output should look like this (For one game and If no errors were found)
```
=== Playing Game #1 ===
White: Kasimdzhanov,R
Black: Grischuk,A
Event: Tbilisi FIDE GP 2015
  a b c d e f g h
 +-----------------+
8|R N B Q K B N R |8
7|P P P P P P P P |7
6|. . . . . . . . |6
5|. . . . . . . . |5
4|. . . . . . . . |4
3|. . . . . . . . |3
2|P P P P P P P P |2
1|R N B Q K B N R |1
 +-----------------+
  a b c d e f g h
1. White: d4
1... Black: d5
2. White: c4
2... Black: e6
3. White: Nc3
3... Black: Bb4
4. White: Nf3
4... Black: dxc4
5. White: e3
5... Black: b5
6. White: a4
6... Black: c6
7. White: Bd2
7... Black: a5
8. White: axb5
8... Black: Bxc3
9. White: Bxc3
9... Black: cxb5
10. White: b3
10... Black: Bb7
11. White: bxc4
11... Black: b4
12. White: Bb2
12... Black: Nf6
13. White: Bd3
13... Black: Nbd7
14. White: O-O
14... Black: O-O
15. White: Re1
15... Black: Ne4
16. White: c5
16... Black: Bc6
17. White: Qc2
17... Black: f5
18. White: Bc4
18... Black: Qe7
19. White: Bb3
19... Black: Kh8
20. White: Red1
20... Black: Qe8
21. White: Ba4
21... Black: Bxa4
22. White: Rxa4
22... Black: Ndf6
23. White: Rda1
23... Black: Qb5
24. White: Ne5
24... Black: Ng4
25. White: Nxg4
25... Black: fxg4
26. White: Qxe4
26... Black: Qe2
27. White: Rf1
27... Black: Qxb2
28. White: Qxe6
28... Black: b3
29. White: Qc4
29... Black: Rf6
30. White: Qc1
30... Black: Qe2
31. White: c6
31... Black: b2
32. White: Qc4
32... Black: Qd2
33. White: c7
33... Black: Rc8
34. White: Qb3
34... Black: Rcf8
35. White: Qd1
35... Black: Rxf2
  a b c d e f g h
 +-----------------+
8|. . . . . R . K |8
7|. . P . . . P P |7
6|. . . . . . . . |6
5|P . . . . . . . |5
4|R . . P . . P . |4
3|. . . . P . . . |3
2|. P . Q . R P P |2
1|. . . Q . R K . |1
 +-----------------+
  a b c d e f g h
Game #1 finished successfully. Result: 0-1
```

