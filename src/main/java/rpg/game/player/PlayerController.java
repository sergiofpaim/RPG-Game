package rpg.game.player;

import rpg.Game;
import rpg.GameRunner;
import rpg.RPGGame;
import rpg.things.Item;

public class PlayerController {

    public static void playerAction(String choice) {

        switch (choice) {
            case "w":
                movePlayer("up");
                break;
            case "s":
                movePlayer("down");
                break;
            case "a":
                movePlayer("left");
                break;
            case "d":
                movePlayer("right");
                break;
            case "m":
                for (Item item : GameRunner.player.getInventory()) {
                    System.out.println("\n" + item.getName() + " - " + item.getDescription());
                }
                break;
            case "i":
                if (GameRunner.player.getPositionX() == 2 && GameRunner.player.getPositionY() == 3) {
                    System.out.println("You found a treasure chest!");
                } else {
                    System.out.println("Nothing to interact with here.");
                }
                break;

            case "b":
                System.out.println("Let's battle!");
                break;

            default:
                System.out.println("\nInvalid action!");
                return;
        }
    }

    public static void movePlayer(String movement) {
        int currentX = GameRunner.player.getPositionX();
        int currentY = GameRunner.player.getPositionY();
        int newX = currentX;
        int newY = currentY;

        switch (movement) {
            case "up":
                newX = currentX - 1;
                if (newX >= 0 && GameRunner.game[newX][currentY]) {
                    GameRunner.player.setPositionX(newX);
                }
                break;
            case "down":
                newX = currentX + 1;
                if (newX < RPGGame.map.length && RPGGame.currentMap[newX][currentY]) {
                    GameRunner.player.setPositionX(newX);
                }
                break;
            case "left":
                newY = currentY - 1;
                if (newY >= 0 && RPGGame.currentMap[currentX][newY]) {
                    GameRunner.player.setPositionY(newY);
                }
                break;
            case "right":
                newY = currentY + 1;
                if (newY < RPGGame.currentMap[0].length && RPGGame.currentMap[currentX][newY]) {
                    GameRunner.player.setPositionY(newY);
                }
                break;
            default:
                System.out.println("Invalid move!");
                return;
        }
    }
}