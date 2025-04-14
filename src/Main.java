import PGNParser.Parser;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            parser.loadPGN(new File("path/to/sample.pgn"));

            System.out.println(parser.getHeaders());
            System.out.println(parser.getMoves());

        } catch (IOException e) {
            System.out.println("Failed to read PGN file:" + e.getMessage());

        }
    }
}