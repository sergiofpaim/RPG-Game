package rpg.game.player;

import rpg.RPGGame;
import rpg.game.map.MapController;
import rpg.models.Item;
import rpg.services.CharacterService;

public class PlayerController {

    public static void playerAction(String choice) {

        switch (choice) {
            case "w":
                MapController.movePlayer("up");
                break;
            case "s":
                MapController.movePlayer("down");
                break;
            case "a":
                MapController.movePlayer("left");
                break;
            case "d":
                MapController.movePlayer("right");
                break;
            case "m":
                for (Item item : RPGGame.currentCharacter.getInventory()) {
                    System.out.println("\n" + item.getName() + " - " + item.getDescription());
                }
                break;
            case "i":
                if (RPGGame.currentCharacter.getPositionX() == 2 && RPGGame.currentCharacter.getPositionY() == 3) {
                    System.out.println("You found a treasure chest!");
                } else {
                    System.out.println("Nothing to interact with here.");
                }
                break;

            case "b":
                System.out.println("Let's battle!");
                break;

            case "k":
                CharacterService.saveCharacter(RPGGame.currentCharacter);
                System.out.println("\nGame saved!");
                break;

            default:
                System.out.println("\nInvalid action!");
                return;
        }
    }
}