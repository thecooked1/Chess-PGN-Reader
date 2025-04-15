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



    public void loadAndPlayPGN(File file) {
        try {
            parser.loadPGN(file);
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
                    return;
                }

                whiteToMove = !whiteToMove;
                if (whiteToMove) moveNumber++; // Increment after Black moves
            }

            System.out.println("Game finished. Result: " + parser.getResult());

        } catch (IOException e) {
            System.err.println("Failed to read PGN file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during game execution: " + e.getMessage());
        }
    }
}

