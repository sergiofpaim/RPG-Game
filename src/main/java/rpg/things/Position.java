package rpg.things;

public class Position {
    private int col = 0;
    private int row = 0;

    public Position() {
    }

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int row) {
        this.col = row;
    }

    public void setRow(int col) {
        this.row = col;
    }
}
