package rpg;

import java.util.List;
import java.util.Random;

import rpg.interfaces.IThing;
import rpg.utils.StringHelper;

public class Map {
    private int width;
    private int height;
    private boolean visibility[][];
    private int currentRow = -1;
    private int currentCol = -1;

    public static final int[][] offsets = {
            { -1, -1 },
            { -1, 0 },
            { -1, 1 },
            { 0, -1 },
            { 0, 1 },
            { 1, -1 },
            { 1, 0 },
            { 1, 1 }
    };

    public Map() {
        Random random = new Random();
        width = random.nextInt(10) + 10;
        height = random.nextInt(10) + 10;
        visibility = new boolean[width][height];
        navigateTo(0, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[][] getVisibility() {
        return visibility;
    }

    public void setWitdh(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVisibility(boolean visibility[][]) {
        this.visibility = visibility;
    }

    public boolean isWithinBounds(int col, int row) {
        return col >= 0 && col < width && row >= 0 && row < height;
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
            if (thing.getPosition().getRow() == currentRow && thing.getPosition().getCol() == currentCol) {
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
                    if (isVisibleCurrentPosition())
                        System.out.print(drawCell(game.getThings()));
                    else
                        System.out.print("  ");
                }
                System.out.println("│");
            }

            System.out.println("└" + StringHelper.repeatChar('─', mapWidth) + "┘");
        }
    }

    private boolean isVisibleCurrentPosition() {
        return visibility[currentCol][currentRow];
    }

    public void navigateTo(int col, int row) {
        visibility[col][row] = true;

        for (int[] offset : offsets) {
            int targetRow = row + offset[0];
            int targetCol = col + offset[1];
            if (targetRow >= 0 && targetRow < height && targetCol >= 0 && targetCol < width)
                visibility[targetCol][targetRow] = true;
        }
    }
}