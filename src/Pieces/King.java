package Pieces;

public class King extends Piece {

    public King(Colour colour) {
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (rowDiff <= 1 && colDiff <= 1) {
            Piece target = board[toRow][toCol];
            return target == null || target.getColor() != colour;
        }

        return false; // Castling can be added later
    }

    @Override
    public char getSymbol() {
        return 'K';
    }
}
