package Pieces;

public class Knight extends Piece {

    public Knight(Colour colour) {
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            Piece target = board[toRow][toCol];
            return target == null || target.getColor() != colour;
        }

        return false;
    }

    @Override
    public char getSymbol() {
        return 'N';
    }
}
