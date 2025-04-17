package Game;
import Board.Board;
import PGNParser.Interpreter;
import PGNParser.Move;
import PGNParser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Game {

    private final Board board;
    private final Parser parser;
    private final Interpreter interpreter;
    private boolean whiteToMove = true;

    public Game() {
        this.board = new Board();
        this.parser = new Parser();
        this.interpreter = new Interpreter();
    }


    public boolean loadAndPlayPGN(String pgnContent) {
        try {
            // Remove tags like [Event "..."]
            StringBuilder movesOnly = new StringBuilder();
            String[] lines = pgnContent.split("\\r?\\n");

            for (String line : lines) {
                if (!line.startsWith("[") && !line.isBlank()) {
                    movesOnly.append(line.trim()).append(" ");
                }
            }

            // Remove result at the end if present (1-0, 0-1, 1/2-1/2, *)
            String cleanedMoves = movesOnly.toString()
                    .replaceAll("\\d+\\.\\.\\.", "")         // remove "1..."
                    .replaceAll("\\d+\\.", "")               // remove "1."
                    .replaceAll("1-0|0-1|1/2-1/2|\\*", "")   // remove results
                    .trim();

            parser.loadPGNFromString(cleanedMoves);
            List<String> moveTokens = parser.getMoves();

            int moveNumber = 1;
            for (String token : moveTokens) {
                Move move = interpreter.parseMove(token);

                System.out.println((whiteToMove ? moveNumber + ". " : "") +
                        (whiteToMove ? "White" : "Black") + ": " + token);

                boolean moveApplied = board.applyMove(move, whiteToMove);

                if (!moveApplied) {
                    System.err.println("Illegal move at " + (whiteToMove ? "White" : "Black") +
                            " move " + moveNumber + ": " + token);
                    return false; // Invalid game
                }

                whiteToMove = !whiteToMove;
                if (whiteToMove) moveNumber++;
            }

            System.out.println("Game finished. Result: " + parser.getResult());
            return true;

        } catch (Exception e) {
            System.err.println("Error while parsing game: " + e.getMessage());
            return false;
        }
    }
}

