package Pieces;

public class Bishop extends Piece{

    public Bishop(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'B';
    }


}
