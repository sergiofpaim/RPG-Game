package rpg.player;

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
}