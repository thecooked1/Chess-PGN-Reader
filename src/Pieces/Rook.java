package Pieces;

public class Rook extends Piece {

    public Rook(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        if (fromRow != toRow && fromCol != toCol) return false;

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int row = fromRow + rowStep;
        int col = fromCol + colStep;

        while (row != toRow || col != toCol) {
            if (board[row][col] != null) return false;
            row += rowStep;
            col += colStep;
        }

        return board[toRow][toCol] == null || board[toRow][toCol].getColor() != this.getColor();
    }

    @Override
    public char getSymbol() {
        return 'R';
    }

}


