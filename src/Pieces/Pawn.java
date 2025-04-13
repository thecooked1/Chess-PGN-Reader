package Pieces;

public class Pawn extends Piece{

    public Pawn(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

}
