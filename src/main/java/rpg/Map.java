package rpg;

import rpg.interfaces.IThing;
import rpg.utils.StringHelper;

import java.util.List;

public class Map {
    private int width;
    private int height;
    private int currentRow = -1;
    private int currentCol = -1;

    public Map() {
    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWitdh(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void startDrawing() {
        currentRow = -1;
        currentCol = -1;
    }

    public boolean nextRow() {
        if (currentRow < height - 1) {
            currentRow++;
            return true;
        }
        return false;
    }

    public boolean nextCol() {
        if (currentCol < width - 1) {
            currentCol++;
            return true;
        } else {
            currentCol = -1;
            return false;
        }
    }

    public String drawCell(List<IThing> things) {
        for (IThing thing : things) {
            if (thing.getPosition().getY() == currentRow && thing.getPosition().getX() == currentCol) {
                return thing.draw();
            }
        }
        return ". ";
    }

    public void displayMap(Game game) {
        if (game.hasChanged()) {
            int mapWidth = width * 2;

            System.out.println("┌" + StringHelper.centerWithPad(" Game Map ", mapWidth, '─') + "┐");

            startDrawing();
            while (nextRow()) {
                System.out.print("│");
                while (nextCol()) {
                    System.out.print(drawCell(game.getThings()));
                }
                System.out.println("│");
            }

            System.out.println("└" + StringHelper.repeatChar('─', mapWidth) + "┘");
        }
    }
}
