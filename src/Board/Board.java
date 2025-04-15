package Board;

import Pieces.*;


public class Board {

    private final Piece[][] grid;

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
                        // Make the move
                        grid[targetRow][targetCol] = piece;
                        grid[row][col] = null;
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
