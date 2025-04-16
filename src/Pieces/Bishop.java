package Pieces;

public class Bishop extends Piece {

    public Bishop(Colour colour) {
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (rowDiff != colDiff) return false;

        int rowStep = (toRow - fromRow) > 0 ? 1 : -1;
        int colStep = (toCol - fromCol) > 0 ? 1 : -1;

        for (int i = 1; i < rowDiff; i++) {
            if (board[fromRow + i * rowStep][fromCol + i * colStep] != null) return false;
        }

        Piece target = board[toRow][toCol];
        return target == null || target.getColor() != colour;
    }

    @Override
    public char getSymbol() {
        return 'B';
    }
}
