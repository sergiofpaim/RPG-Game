package rpg.models;

import java.util.Random;
import rpg.RPGGame;
import rpg.infrastructure.GameModel;
import rpg.infrastructure.ValidationResult;

public class Map extends GameModel {
    private int width;
    private int height;
    private String playerId;

    public Map() {
    }

    public Map(int width, int height, String playerId) {
        setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.width = width;
        this.height = height;
        this.playerId = playerId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public ValidationResult validate() {
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    public static Map FactorFrom(char[][] map) {
        int height = map.length;
        int width = map[0].length;

        Map newMap = new Map(width, height, RPGGame.currentCharacter.getId());

        return newMap;
    }
}