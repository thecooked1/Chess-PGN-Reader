package Board;

import Pieces.*;


public class Board {

    private final Piece[][] grid;
    private int[] enPassantTarget; // {row, col}


    public Board(){
        grid = new Piece[8][8];
        setupInitialPosition();

    }

    public boolean applyMove(PGNParser.Move move, boolean isWhiteTurn) {
        String pieceType = move.getPiece(); // "P", "N", "B", "R", "Q", "K"
        char targetFile = move.getTargetFile(); // 'a' to 'h'
        int targetRank = move.getTargetRank();  // 1 to 8

        int targetCol = targetFile - 'a';
        int targetRow = 8 - targetRank;

        Colour currentColour = isWhiteTurn ? Colour.WHITE : Colour.BLACK;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null &&
                        piece.getColor() == currentColour &&
                        Character.toString(piece.getSymbol()).equalsIgnoreCase(pieceType)) {

                    if (piece.isValidMove(row, col, targetRow, targetCol, grid)) {

                        // Handle en passant capture
                        if (piece instanceof Pawn &&
                                targetCol != col &&
                                grid[targetRow][targetCol] == null &&
                                enPassantTarget != null &&
                                enPassantTarget[0] == targetRow &&
                                enPassantTarget[1] == targetCol) {

                            int pawnRow = currentColour == Colour.WHITE ? targetRow + 1 : targetRow - 1;
                            grid[pawnRow][targetCol] = null; // Remove captured pawn
                        }

                        // Handle promotion
                        if (piece instanceof Pawn && (targetRow == 0 || targetRow == 7)) {
                            switch (move.getPromotion()) {
                                case "Q": piece = new Queen(currentColour); break;
                                case "R": piece = new Rook(currentColour); break;
                                case "B": piece = new Bishop(currentColour); break;
                                case "N": piece = new Knight(currentColour); break;
                            }
                        }

                        // Handle castling
                        if (piece instanceof King && Math.abs(targetCol - col) == 2) {
                            King king = (King) piece;

                            // Castling
                            if (!king.hasMoved()) {
                                int rookCol = (targetCol == 6) ? 7 : 0;
                                Piece rookPiece = grid[row][rookCol];
                                if (rookPiece instanceof Rook) {
                                    Rook rook = (Rook) rookPiece;

                                    if (!rook.hasMoved()) {
                                        int direction = (targetCol == 6) ? 1 : -1;

                                        // Ensure path is clear
                                        boolean clear = true;
                                        for (int c = col + direction; c != rookCol; c += direction) {
                                            if (grid[row][c] != null) {
                                                clear = false;
                                                break;
                                            }
                                        }

                                        // Optional: Check if king is in check or would pass through check

                                        if (clear) {
                                            // Move king
                                            grid[targetRow][targetCol] = king;
                                            grid[row][col] = null;

                                            // Move rook
                                            int rookTargetCol = (targetCol == 6) ? 5 : 3;
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

                        // Handle en passant target square (double pawn push)
                        if (piece instanceof Pawn && Math.abs(targetRow - row) == 2) {
                            enPassantTarget = new int[]{(row + targetRow) / 2, col};
                        } else {
                            enPassantTarget = null;
                        }

                        // Apply the move
                        grid[targetRow][targetCol] = piece;
                        grid[row][col] = null;

                        if (piece instanceof King) {
                            ((King) piece).setMoved(true);
                        }
                        if (piece instanceof Rook) {
                            ((Rook) piece).setMoved(true);
                        }

                        return true;
                    }
                }
            }
        }

        return false; // No legal piece found to apply this move
    }

    public void setupInitialPosition(){

        // Setting up White pieces

        grid[0][0] = new Rook(Colour.WHITE);
        grid[0][1] = new Knight(Colour.WHITE);
        grid[0][2] = new Bishop(Colour.WHITE);
        grid[0][3] = new Queen(Colour.WHITE);
        grid[0][4] = new King(Colour.WHITE);
        grid[0][5] = new Bishop(Colour.WHITE);
        grid[0][6] = new Knight(Colour.WHITE);
        grid[0][7] = new Rook(Colour.WHITE);

        // Setting up Both player Pawns

        for (int i = 0; i < 8; i++){
            grid[1][i] = new Pawn(Colour.WHITE);
            grid[6][i] = new Pawn(Colour.BLACK);
        }

        // Setting up Black pieces

        grid[7][0] = new Rook(Colour.BLACK);
        grid[7][1] = new Knight(Colour.BLACK);
        grid[7][2] = new Bishop(Colour.BLACK);
        grid[7][3] = new Queen(Colour.BLACK);
        grid[7][4] = new King(Colour.BLACK);
        grid[7][5] = new Bishop(Colour.BLACK);
        grid[7][6] = new Knight(Colour.BLACK);
        grid[7][7] = new Rook(Colour.BLACK);

    }

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
