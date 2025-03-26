package rpg.models;

import java.util.Random;

import rpg.infrastructure.GameModel;
import rpg.infrastructure.ValidationResult;

public class DungeonMap extends GameModel {
    private int width;
    private int height;
    private int playerId;
    private String[][] coordinates;

    public DungeonMap(int width, int height, int playerId) {
        setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.width = width;
        this.height = height;
        this.playerId = playerId;
        this.coordinates = new String[height][width];
        initializeMap();
    }

    private void initializeMap() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                coordinates[row][col] = "empty";
            }
        }
    }

    public String getCoordinate(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return coordinates[y][x];
        }
        return null;
    }

    public void setCoordinate(int x, int y, String value) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            coordinates[y][x] = value;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void printMap() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                System.out.print(coordinates[row][col] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public ValidationResult validate() {
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    public static DungeonMap FactorFrom(char[][] map) {
        int height = map.length;
        int width = map[0].length;

        DungeonMap newMap = new DungeonMap(width, height, 0);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newMap.setCoordinate(x, y, String.valueOf(map[y][x]));
            }
        }

        return newMap;
    }
}
