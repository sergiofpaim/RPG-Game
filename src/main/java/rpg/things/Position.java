package rpg.things;

public class Position {
    int y = 0;
    int x = 0;

    public Position() {
    }

    public Position(int x, int y) {
        this.y = x;
        this.x = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int x) {
        this.y = x;
    }

    public void setX(int y) {
        this.x = y;
    }
}
