package Board;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import Pieces.*;
import PGNParser.Move;


class BoardTest {

    private Board board;
    private Move move;

    @BeforeEach
    void setUp() {
        board = new Board();
        move = new Move("dummy");
    }

    @Test
    void testInitialSetup() {
        // White pieces
        assertTrue(board.getPiece(7, 0) instanceof Rook && board.getPiece(7, 0).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 1) instanceof Knight && board.getPiece(7, 1).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 2) instanceof Bishop && board.getPiece(7, 2).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 3) instanceof Queen && board.getPiece(7, 3).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 4) instanceof King && board.getPiece(7, 4).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 5) instanceof Bishop && board.getPiece(7, 5).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 6) instanceof Knight && board.getPiece(7, 6).getColor() == Colour.WHITE);
        assertTrue(board.getPiece(7, 7) instanceof Rook && board.getPiece(7, 7).getColor() == Colour.WHITE);
        for (int c = 0; c < 8; c++) {
            assertTrue(board.getPiece(6, c) instanceof Pawn && board.getPiece(6, c).getColor() == Colour.WHITE);
        }

        // Black pieces
        assertTrue(board.getPiece(0, 0) instanceof Rook && board.getPiece(0, 0).getColor() == Colour.BLACK);
        assertTrue(board.getPiece(0, 1) instanceof Knight && board.getPiece(0, 1).getColor() == Colour.BLACK);
        assertTrue(board.getPiece(0, 7) instanceof Rook && board.getPiece(0, 7).getColor() == Colour.BLACK);
        for (int c = 0; c < 8; c++) {
            assertTrue(board.getPiece(1, c) instanceof Pawn && board.getPiece(1, c).getColor() == Colour.BLACK);
        }

        // Empty squares
        for (int r = 2; r < 6; r++) {
            for (int c = 0; c < 8; c++) {
                assertNull(board.getPiece(r, c));
            }
        }
    }

    @Test
    void testGetPieceValid() {
        assertNotNull(board.getPiece(0, 0));
        assertNotNull(board.getPiece(6, 4));
        assertNull(board.getPiece(4, 4));
    }

    @Test
    void testGetPieceInvalid() {
        assertNull(board.getPiece(-1, 0));
        assertNull(board.getPiece(8, 8));
        assertNull(board.getPiece(0, 8));
    }

    @Test
    void testSimplePawnMove() {
        Move e4 = createMove("P", 'e', 4, false, "");
        assertTrue(board.applyMove(e4, true));
        assertTrue(board.getPiece(4, 4) instanceof Pawn);
        assertNull(board.getPiece(6, 4));
    }

    @Test
    void testPawnDoubleMove() {
        Move e4 = createMove("P", 'e', 4, false, "");
        assertTrue(board.applyMove(e4, true));
        assertNotNull(board.getEnPassantTarget());
        assertEquals(5, board.getEnPassantTarget()[0]);
        assertEquals(4, board.getEnPassantTarget()[1]);
    }

    @Test
    void testEnPassantSetupAndTargetClear() {

        Move e4 = createMove("P", 'e', 4, false, "");
        board.applyMove(e4, true);
        assertNotNull(board.getEnPassantTarget());
        Move nf6 = createMove("N", 'f', 6, false, "g8");
        assertTrue(board.applyMove(nf6, false));
        assertNull(board.getEnPassantTarget(), "EP target should be null after non-pawn move");
    }

    @Test
    void testEnPassantCapture() {
        // Setup
        applyMoves("e4", "a6", "e5", "d5");
        assertNotNull(board.getEnPassantTarget());
        assertEquals(2, board.getEnPassantTarget()[0]);
        assertEquals(3, board.getEnPassantTarget()[1]);
        Move exd6 = createMove("P", 'd', 6, true, "e");
        assertTrue(board.applyMove(exd6, true), "White should capture en passant");
        // Verify board state
        assertTrue(board.getPiece(2, 3) instanceof Pawn && board.getPiece(2, 3).getColor() == Colour.WHITE, "White pawn should be on d6");
        assertNull(board.getPiece(3, 4), "White pawn should not be on e5");
        assertNull(board.getPiece(3, 3), "Black pawn on d5 should be captured");
        assertNull(board.getEnPassantTarget(), "EP target should be null after capture");
    }

    @Test
    void testSimpleKnightMove() {
        Move nf3 = createMove("N", 'f', 3, false, "g");
        assertTrue(board.applyMove(nf3, true));
        assertTrue(board.getPiece(5, 5) instanceof Knight);
        assertNull(board.getPiece(7, 6));
    }

    @Test
    void testSimpleCapture() {
        applyMoves("e4", "d5");
        Move exd5 = createMove("P", 'd', 5, true, "e");
        assertTrue(board.applyMove(exd5, true));
        assertTrue(board.getPiece(3, 3) instanceof Pawn && board.getPiece(3, 3).getColor() == Colour.WHITE); // White pawn on d5
        assertNull(board.getPiece(4, 4));
        assertNull(board.getPiece(1, 3));

    }

    @Test
    void testMoveLeavesKingInCheck() {
        // Setup
        board.setupInitialPosition(); // Reset
        ((Piece[][]) TestUtils.getPrivateField(board, "grid"))[6][4] = null;
        ((Piece[][]) TestUtils.getPrivateField(board, "grid"))[5][3] = new Queen(Colour.BLACK);

        // Try moving white King e1 to e2 , which is illegal
        Move ke2 = createMove("K", 'e', 2, false, "");
        assertFalse(board.applyMove(ke2, true), "Moving king into check should be illegal");
        assertTrue(board.getPiece(7, 4) instanceof King, "King should remain on e1");
        assertNull(board.getPiece(6, 4), "e2 should remain empty");
    }

    @Test
    void testIsNotInCheck() {
        assertFalse(board.isInCheck(Colour.WHITE));
        assertFalse(board.isInCheck(Colour.BLACK));
    }

    @Test
    void testCheckmateScholarsMate() {
        applyMoves("e4","e5","Qh5","Nc6","Bc4","Nf6");
        Move qxf7 = createMove("Q", 'f', 7, true, "");
        qxf7.setCheckmate(true);
        assertTrue(board.applyMove(qxf7, true));

        assertTrue(board.isInCheck(Colour.BLACK), "Black King must be in check");
        assertFalse(board.hasAnyLegalMoves(Colour.BLACK), "Black should have no legal moves");
        assertTrue(qxf7.isCheckmate(), "Move object should reflect checkmate");
    }


    @Test
    void testKingsideCastlingWhiteValid() {
        // Setup
        ((Piece[][]) TestUtils.getPrivateField(board, "grid"))[7][5] = null; // Bf1
        ((Piece[][]) TestUtils.getPrivateField(board, "grid"))[7][6] = null; // Ng1

        Move o_o = createMove("K", 'g', 1, false, "");
        o_o.setKingsideCastle(true);

        assertTrue(board.applyMove(o_o, true), "Kingside castling should be valid");
        assertTrue(board.getPiece(7, 6) instanceof King && board.getPiece(7, 6).getColor() == Colour.WHITE, "King should be on g1");
        assertTrue(board.getPiece(7, 5) instanceof Rook && board.getPiece(7, 5).getColor() == Colour.WHITE, "Rook should be on f1");
        assertNull(board.getPiece(7, 4), "e1 should be empty");
        assertNull(board.getPiece(7, 7), "h1 should be empty");
        assertTrue(((King)board.getPiece(7,6)).hasMoved(), "King should be marked as moved");
        assertTrue(((Rook)board.getPiece(7,5)).hasMoved(), "Rook should be marked as moved");

    }

    @Test
    void testKingsideCastlingWhiteInvalidBlocked() {
        // Path blocked by Knight on g1
        Move o_o = createMove("K", 'g', 1, false, "");
        o_o.setKingsideCastle(true);
        assertFalse(board.applyMove(o_o, true), "Kingside castling should be invalid (blocked)");
        assertTrue(board.getPiece(7, 4) instanceof King, "King should remain on e1");
        assertTrue(board.getPiece(7, 7) instanceof Rook, "Rook should remain on h1");
    }

    //Helper Methods
    private Move createMove(String piece, char targetFile, int targetRank, boolean isCapture, String disambiguation) {
        Move m = new Move(piece + disambiguation + (isCapture ? "x" : "") + targetFile + targetRank);
        m.setPiece(piece);
        m.setTargetFile(targetFile);
        m.setTargetRank(targetRank);
        m.setCapture(isCapture);
        m.setDisambiguation(disambiguation);
        return m;
    }
    private void applyMoves(String... sanMoves) {
        boolean whiteTurn = true;
        for (String san : sanMoves) {
            //basic SAN parsing
            Move currentMove;
            if (san.equals("O-O")) {
                currentMove = createMove("K", 'g', whiteTurn ? 1 : 8, false, "");
                currentMove.setKingsideCastle(true);
            } else if (san.equals("O-O-O")) {
                currentMove = createMove("K", 'c', whiteTurn ? 1 : 8, false, "");
                currentMove.setQueensideCastle(true);
            } else {
                // basic parsing
                String piece = "P";
                String temp = san;
                if (Character.isUpperCase(temp.charAt(0)) && "KQRBN".indexOf(temp.charAt(0)) != -1) {
                    piece = String.valueOf(temp.charAt(0));
                    temp = temp.substring(1);
                }
                boolean capture = temp.contains("x");
                temp = temp.replace("x", "");

                // Basic disambiguation detection
                String dis = "";
                if (temp.length() > 2) {
                    if (temp.length() == 3) {
                        dis = String.valueOf(temp.charAt(0));
                        temp = temp.substring(1);
                    } else if (temp.length() == 4 && piece.equals("P")) { // Pawn capture exd5
                        dis = String.valueOf(temp.charAt(0));
                        temp = temp.substring(1);
                    }
                }
                char targetFile = temp.charAt(0);
                int targetRank = Character.getNumericValue(temp.charAt(1));
                currentMove = createMove(piece, targetFile, targetRank, capture, dis);
                // Handle Promotion
                if (san.contains("=")) {
                    currentMove.setPromotion(san.substring(san.indexOf("=") + 1));
                }
            }
            boolean success = board.applyMove(currentMove, whiteTurn);
            if (!success) {
                board.printBoard();
                fail("Test setup failed: Illegal move '" + san + "' for " + (whiteTurn ? "White" : "Black"));
            }
            whiteTurn = !whiteTurn;
        }
    }
}

// Helper utility
class TestUtils {
    public static Object getPrivateField(Object obj, String fieldName) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not access private field", e);
        }
    }

    public static void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not set private field", e);
        }
    }

    // Helper to clear the board
    public static void clearBoard(Board board) {
        Piece[][] grid = (Piece[][]) getPrivateField(board, "grid");
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                grid[r][c] = null;
            }
        }
        setPrivateField(board, "enPassantTarget", null);
    }
}