import PGNParser.Parser;
import Game.Game;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File pgnFile = new File("Tbilisi2015.pgn");
        Parser parser = new Parser();
        Game game = new Game();
        try {
            parser.loadPGN(pgnFile);
            game.playAllGames(parser);
        } catch (IOException e) {
            System.err.println("Error reading PGN file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
