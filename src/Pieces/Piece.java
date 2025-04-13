package Pieces;


public abstract class Piece {
    protected Colour colour;

    public Piece(Colour colour){
        this.colour = colour;
    }

    public Colour getColor() {
        return colour ;
    }

    public abstract boolean isValidMove();
    public abstract char getSymbol();





}
