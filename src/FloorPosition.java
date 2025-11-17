public class FloorPosition {
    private int row;
    private int col;

    public FloorPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

    @Override
    public String toString() {
        return "[" + row + "," + col + "]";
    }
}
