package Board;


public class Tile {
    private int rowCoordinate;
    private int colCoordinate;

    public Tile (int rowCoordinate, int colCoordinate){
        this.rowCoordinate = rowCoordinate;
        this.colCoordinate = colCoordinate;
    }

    // translate from Algebraic

    public static Tile translate(String n){
        int col = n.charAt(0) - 'a';
        int row = 8 - Character.getNumericValue(n.charAt(1));
        return new Tile(row,col);
    }

    public String toAlgebraic() {
        char file = (char) ('a' + colCoordinate);
        int rank = 8 - rowCoordinate;
        return "" + file + rank;
    }

    public int getRow () { return rowCoordinate; }
    public int getCol () { return colCoordinate; }
}
