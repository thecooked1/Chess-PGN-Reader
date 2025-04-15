package Pieces;


public abstract class Piece {
    protected Colour colour;

    public Piece(Colour colour){
        this.colour = colour;
    }

    public Colour getColor() {
        return colour ;
    }

    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board);
    public abstract char getSymbol();





}
