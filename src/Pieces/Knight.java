package Pieces;

public class Knight extends Piece{

    public Knight(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'N';
    }


}
