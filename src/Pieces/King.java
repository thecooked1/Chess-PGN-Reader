package Pieces;

public class King extends Piece {

    public King(Colour colour){
        super(colour);
    }


    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'K';
    }

}
