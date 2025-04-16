package Pieces;

public class Pawn extends Piece{

    public Pawn(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int direction = (colour == Colour.WHITE) ? 1 : -1;
        int startRank = (colour == Colour.WHITE) ? 1 : 6;

        // Single move forward
        if (fromCol == toCol && toRow - fromRow == direction && board[toRow][toCol] == null) {
            return true;
        }

        // Double move forward from starting position
        if (fromCol == toCol && fromRow == startRank && toRow - fromRow == 2 * direction
                && board[fromRow + direction][fromCol] == null && board[toRow][toCol] == null) {
            return true;
        }

        // Capture
        if (Math.abs(toCol - fromCol) == 1 && toRow - fromRow == direction && board[toRow][toCol] != null
                && board[toRow][toCol].getColor() != colour) {
            return true;
        }


        return false;

    }

    @Override
    public char getSymbol() {
        return 'P';
    }

}
