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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
