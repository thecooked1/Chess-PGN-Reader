package Board;


public class Tile {
    private final int rowCoordinate;
    private final int colCoordinate;

    public Tile (int rowCoordinate, int colCoordinate){
        if (rowCoordinate < 0 || rowCoordinate > 7 || colCoordinate < 0 || colCoordinate > 7) {
            throw new IllegalArgumentException("Invalid tile coordinates: " + rowCoordinate +
                    ", " + colCoordinate);
        }
        this.rowCoordinate = rowCoordinate;
        this.colCoordinate = colCoordinate;
    }

    // translate from Algebraic

    public static Tile translate(String n){
        if (n == null || n.length() != 2) {
            throw new IllegalArgumentException("Invalid tile: " + n);
        }

        char file = n.charAt(0);
        char rank = n.charAt(1);

        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') {
            throw new IllegalArgumentException("Invalid tile: " + n);
        }

        int col = file - 'a';
        int row = 8 - Character.getNumericValue(rank);
        return new Tile(row, col);
    }

    public String toAlgebraic() {
        char file = (char) ('a' + colCoordinate);
        int rank = 8 - rowCoordinate;
        return "" + file + rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return rowCoordinate == tile.rowCoordinate && colCoordinate == tile.colCoordinate;
    }

    @Override
    public int hashCode() {
        return 31 * rowCoordinate + colCoordinate;
    }

    @Override
    public String toString() {
        return toAlgebraic();
    }

    public int getRow () { return rowCoordinate; }
    public int getCol () { return colCoordinate; }
}
