import PGNParser.Parser;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            parser.loadPGN(new File("Tbilisi2015.pgn"));

            System.out.println("=== Headers ===");
            parser.getHeaders().forEach((key, value) -> System.out.println(key + ": " + value));

            System.out.println("\n=== Moves ===");
            int moveNum = 1;
            for (int i = 0; i < parser.getMoves().size(); i += 2) {
                String whiteMove = parser.getMoves().get(i);
                String blackMove = (i + 1 < parser.getMoves().size()) ? parser.getMoves().get(i + 1) : "...";
                System.out.printf("%d. %s %s\n", moveNum++, whiteMove, blackMove);
            }

            System.out.println("\n=== Result ===");
            System.out.println(parser.getResult());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}