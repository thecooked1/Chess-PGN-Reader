package Pieces;

public class Queen extends Piece {

    public Queen(Colour colour) {
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        Rook rookLogic = new Rook(colour);
        Bishop bishopLogic = new Bishop(colour);
        return rookLogic.isValidMove(fromRow, fromCol, toRow, toCol, board) ||
                bishopLogic.isValidMove(fromRow, fromCol, toRow, toCol, board);
    }

    @Override
    public char getSymbol() {
        return 'Q';
    }
}
