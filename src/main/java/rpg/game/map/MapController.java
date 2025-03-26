package rpg.game.map;

import rpg.RPGGame;
import rpg.models.Map;
import rpg.services.MapService;

public class MapController {
    private static int playerX = 2;
    private static int playerY = 2;

    public static int getPlayerX() {
        return playerX;
    }

    public static int getPlayerY() {
        return playerY;
    }

    private static void setPlayerX(int x) {
        playerX = x;
    }

    private static void setPlayerY(int y) {
        playerY = y;
    }

    public static void buildMap() {
        Map mapModel = MapService.loadMap();

        int width = mapModel.getWidth();
        int height = mapModel.getHeight();

        char[][] mapArray = new char[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                mapArray[row][col] = '.';
            }
        }

        mapArray[playerX][playerY] = '@';

        RPGGame.currentMap = mapArray;
    }

    public static void movePlayer(String movement) {
        RPGGame.currentMap[playerX][playerY] = '.';

        switch (movement) {
            case "up":
                if (playerX > 1 && RPGGame.currentMap[playerX - 1][playerY] != '#') {
                    setPlayerX(playerX - 1);
                }
                break;
            case "down":
                if (playerX < RPGGame.currentMap.length - 2 && RPGGame.currentMap[playerX + 1][playerY] != '#') {
                    setPlayerX(playerX + 1);
                }
                break;
            case "left":
                if (playerY > 1 && RPGGame.currentMap[playerX][playerY - 1] != '#') {
                    setPlayerY(playerY - 1);
                }
                break;
            case "right":
                if (playerY < RPGGame.currentMap[0].length - 2 && RPGGame.currentMap[playerX][playerY + 1] != '#') {
                    setPlayerY(playerY + 1);
                }
                break;

            default:
                System.out.println("Invalid move!");
                return;
        }

        RPGGame.currentMap[playerX][playerY] = '@';
    }
}
