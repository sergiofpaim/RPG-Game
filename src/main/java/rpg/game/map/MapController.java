package rpg.game.map;

import rpg.RPGGame;
import rpg.models.Map;
import rpg.services.MapService;

public class MapController {

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

        mapArray[RPGGame.currentCharacter.getPositionX()][RPGGame.currentCharacter.getPositionY()] = '@';

        RPGGame.currentMap = mapArray;
    }

    public static void movePlayer(String movement) {
        RPGGame.currentMap[RPGGame.currentCharacter.getPositionX()][RPGGame.currentCharacter.getPositionY()] = '.';

        int currentX = RPGGame.currentCharacter.getPositionX();
        int currentY = RPGGame.currentCharacter.getPositionY();
        int newX = currentX;
        int newY = currentY;

        switch (movement) {
            case "up":
                newX = currentX - 1;
                if (newX >= 0 && RPGGame.currentMap[newX][currentY] != '#') {
                    RPGGame.currentCharacter.setPositionX(newX);
                }
                break;
            case "down":
                newX = currentX + 1;
                if (newX < RPGGame.currentMap.length && RPGGame.currentMap[newX][currentY] != '#') {
                    RPGGame.currentCharacter.setPositionX(newX);
                }
                break;
            case "left":
                newY = currentY - 1;
                if (newY >= 0 && RPGGame.currentMap[currentX][newY] != '#') {
                    RPGGame.currentCharacter.setPositionY(newY);
                }
                break;
            case "right":
                newY = currentY + 1;
                if (newY < RPGGame.currentMap[0].length && RPGGame.currentMap[currentX][newY] != '#') {
                    RPGGame.currentCharacter.setPositionY(newY);
                }
                break;
            default:
                System.out.println("Invalid move!");
                return;
        }

        RPGGame.currentMap[RPGGame.currentCharacter.getPositionX()][RPGGame.currentCharacter.getPositionY()] = '@';
    }
}
