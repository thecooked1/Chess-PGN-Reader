import PGNParser.Parser;
import Game.Game;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File pgnFile = new File("Tbilisi2015.pgn"); // change to actual path

        try {
            List<String> allGames = splitIntoGames(pgnFile);
            int gameNumber = 1;

            for (String singleGame : allGames) {
                System.out.println("Validating Game " + gameNumber + ":");

                Parser parser = new Parser();
                parser.loadPGNFromString(singleGame); // new method

                Game game = new Game();
                boolean isValid = game.loadAndPlayPGN(singleGame);

                if (isValid) {
                    System.out.println("✅ Game " + gameNumber + " is valid.\n");
                } else {
                    System.out.println("❌ Game " + gameNumber + " has invalid moves.\n");
                }

                gameNumber++;
            }

        } catch (IOException e) {
            System.err.println("Error reading PGN file: " + e.getMessage());
        }
    }

    // File-level utility, should stay in Main
    private static List<String> splitIntoGames(File file) throws IOException {
        List<String> games = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder currentGame = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("[Event ")) {
                if (currentGame.length() > 0) {
                    games.add(currentGame.toString().trim());
                    currentGame.setLength(0);
                }
            }
            currentGame.append(line).append("\n");
        }

        if (currentGame.length() > 0) {
            games.add(currentGame.toString().trim());
        }

        return games;
    }
}
