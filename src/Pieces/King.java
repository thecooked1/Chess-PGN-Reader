package Pieces;

public class King extends Piece {

    private boolean hasMoved = false;

    public King(Colour colour) {
        super(colour);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }


    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (rowDiff <= 1 && colDiff <= 1) {
            Piece target = board[toRow][toCol];
            return target == null || target.getColor() != colour;
        }


        return false;
    }

    @Override
    public char getSymbol() {
        return 'K';
    }
}
