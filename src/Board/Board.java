package Board;

import Pieces.*;
import PGNParser.Move;

public class Board {

    private final Piece[][] grid;
    private int[] enPassantTarget; // {row, col}

    public Board() {
        grid = new Piece[8][8];
        setupInitialPosition();
    }

    public boolean applyMove(Move move, boolean isWhiteTurn) {
        Colour currentColour = isWhiteTurn ? Colour.WHITE : Colour.BLACK;

        // Handle castling
        if (piece instanceof King && Math.abs(targetCol - col) == 2) {
            King king = (King) piece;

            if (!king.hasMoved() && !isInCheck(currentColour)) {
                int rookCol = (targetCol == 6) ? 7 : 0;
                Piece rookPiece = grid[row][rookCol];

                if (rookPiece instanceof Rook) {
                    Rook rook = (Rook) rookPiece;

                    if (!rook.hasMoved()) {
                        int direction = (targetCol == 6) ? 1 : -1;
                        boolean pathClear = true;

                        for (int c = col + direction; c != rookCol; c += direction) {
                            if (grid[row][c] != null) {
                                pathClear = false;
                                break;
                            }
                        }

                        if (pathClear) {
                            // Check if any square the king passes through is attacked
                            int[] pathCols = (targetCol == 6) ? new int[]{4, 5, 6} : new int[]{4, 3, 2};
                            boolean pathSafe = true;

                            for (int c : pathCols) {
                                Piece temp = grid[row][c];
                                grid[row][c] = new King(currentColour); // simulate king
                                if (isInCheck(currentColour)) {
                                    pathSafe = false;
                                }
                                grid[row][c] = temp;
                                if (!pathSafe) break;
                            }

                            if (pathSafe) {
                                // Perform castling
                                int rookTargetCol = (targetCol == 6) ? 5 : 3;

                                grid[targetRow][targetCol] = king;
                                grid[row][col] = null;

                                grid[row][rookTargetCol] = rook;
                                grid[row][rookCol] = null;

                                king.setMoved(true);
                                rook.setMoved(true);
                                return true;
                            }
                        }
                    }
                }
            }
        }


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null &&
                        piece.getColor() == currentColour &&
                        Character.toString(piece.getSymbol()).equalsIgnoreCase(pieceType)) {

                    // Disambiguation handling
                    String dis = move.getDisambiguation();
                    if (!dis.isEmpty()) {
                        if (dis.length() == 1) {
                            char d = dis.charAt(0);
                            if (Character.isDigit(d) && 8 - Character.getNumericValue(d) != row) continue;
                            if (Character.isLetter(d) && d - 'a' != col) continue;
                        } else if (dis.length() == 2) {
                            int dRow = 8 - Character.getNumericValue(dis.charAt(1));
                            int dCol = dis.charAt(0) - 'a';
                            if (dRow != row || dCol != col) continue;
                        }
                    }

                    // Is this a valid move for the piece?
                    if (piece.isValidMove(row, col, targetRow, targetCol, grid)) {

                        // En passant capture
                        if (piece instanceof Pawn &&
                                targetCol != col &&
                                grid[targetRow][targetCol] == null &&
                                enPassantTarget != null &&
                                enPassantTarget[0] == targetRow &&
                                enPassantTarget[1] == targetCol) {
                            int capturedPawnRow = currentColour == Colour.WHITE ? targetRow + 1 : targetRow - 1;
                            grid[capturedPawnRow][targetCol] = null;
                        }

                        // Handle promotion
                        if (piece instanceof Pawn && (targetRow == 0 || targetRow == 7) && move.getPromotion() != null) {
                            switch (move.getPromotion()) {
                                case "Q": piece = new Queen(currentColour); break;
                                case "R": piece = new Rook(currentColour); break;
                                case "B": piece = new Bishop(currentColour); break;
                                case "N": piece = new Knight(currentColour); break;
                            }
                        }

                        // Set en passant target square
                        if (piece instanceof Pawn && Math.abs(targetRow - row) == 2) {
                            enPassantTarget = new int[]{(row + targetRow) / 2, col};
                        } else {
                            enPassantTarget = null;
                        }

                        // Move piece
                        grid[targetRow][targetCol] = piece;
                        grid[row][col] = null;

                        if (piece instanceof King) ((King) piece).setMoved(true);
                        if (piece instanceof Rook) ((Rook) piece).setMoved(true);

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isInCheck(Colour color) {
        int kingRow = -1, kingCol = -1;

        // Find the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        if (kingRow == -1) return false; // Shouldn't happen unless the king is missing

        // Check every enemy piece to see if it can move to the king's square
        Colour enemyColor = (color == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece attacker = grid[row][col];
                if (attacker != null && attacker.getColor() == enemyColor) {
                    if (attacker.isValidMove(row, col, kingRow, kingCol, grid)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public void setupInitialPosition() {
        // White pieces
        grid[0][0] = new Rook(Colour.WHITE);
        grid[0][1] = new Knight(Colour.WHITE);
        grid[0][2] = new Bishop(Colour.WHITE);
        grid[0][3] = new Queen(Colour.WHITE);
        grid[0][4] = new King(Colour.WHITE);
        grid[0][5] = new Bishop(Colour.WHITE);
        grid[0][6] = new Knight(Colour.WHITE);
        grid[0][7] = new Rook(Colour.WHITE);

        for (int i = 0; i < 8; i++) grid[1][i] = new Pawn(Colour.WHITE);

        // Black pieces
        grid[7][0] = new Rook(Colour.BLACK);
        grid[7][1] = new Knight(Colour.BLACK);
        grid[7][2] = new Bishop(Colour.BLACK);
        grid[7][3] = new Queen(Colour.BLACK);
        grid[7][4] = new King(Colour.BLACK);
        grid[7][5] = new Bishop(Colour.BLACK);
        grid[7][6] = new Knight(Colour.BLACK);
        grid[7][7] = new Rook(Colour.BLACK);

        for (int i = 0; i < 8; i++) grid[6][i] = new Pawn(Colour.BLACK);
    }

    // Helpers
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        grid[row][col] = piece;
    }
}
