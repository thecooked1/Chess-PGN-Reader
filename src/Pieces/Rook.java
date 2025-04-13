package Pieces;

public class Rook extends Piece {

    public Rook(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'R';
    }

}


