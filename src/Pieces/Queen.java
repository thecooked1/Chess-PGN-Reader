package Pieces;

public class Queen extends Piece{

    public Queen(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'Q';
    }

}
