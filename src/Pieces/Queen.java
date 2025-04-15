package Pieces;

public class Queen extends Piece{

    public Queen(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'Q';
    }

}
