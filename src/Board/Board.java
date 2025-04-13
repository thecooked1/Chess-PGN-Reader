package Board;

import Pieces.Colour;
import Pieces.Piece;
import Pieces.Rook;



public class Board {

    private Piece[][] grid;

    public Board(){
        grid = new Piece[8][8];
        setupInitialPosition();

    }

    public void setupInitialPosition(){
        grid [0][0] = new Rook(Colour.colour.WHITE);
    }
}
