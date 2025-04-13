package Board;

import Pieces.*;


public class Board {

    private Piece[][] grid;

    public Board(){
        grid = new Piece[8][8];
        setupInitialPosition();

    }

    public void setupInitialPosition(){

        // Setting up White pieces

        grid[0][0] = new Rook(Colour.WHITE);
        grid[0][1] = new Knight(Colour.WHITE);
        grid[0][2] = new Bishop(Colour.WHITE);
        grid[0][3] = new Queen(Colour.WHITE);
        grid[0][4] = new King(Colour.WHITE);
        grid[0][5] = new Bishop(Colour.WHITE);
        grid[0][6] = new Knight(Colour.WHITE);
        grid[0][7] = new Rook(Colour.WHITE);

        // Setting up Both player Pawns

        for (int i = 0; i < 8; i++){
            grid[1][i] = new Pawn(Colour.WHITE);
            grid[6][i] = new Pawn(Colour.BLACK);
        }

        // Setting up Black pieces

        grid[7][0] = new Rook(Colour.BLACK);
        grid[7][1] = new Knight(Colour.BLACK);
        grid[7][2] = new Bishop(Colour.BLACK);
        grid[7][3] = new Queen(Colour.BLACK);
        grid[7][4] = new King(Colour.BLACK);
        grid[7][5] = new Bishop(Colour.BLACK);
        grid[7][6] = new Knight(Colour.BLACK);
        grid[7][7] = new Rook(Colour.BLACK);


    }
}
