package rpg.game;

import rpg.RPGGame;
import rpg.game.map.MapController;
import rpg.game.player.PlayerController;

public class GameRunner {
    private static boolean running = true;

    public static void runGame() {
        MapController.buildMap();

        while (running) {
            displayMap();
            System.out.println(
                    "\nYou are the '@', Enter a direction (w/a/s/d), 'm' to see you inventory, 'i' to interact, 'b' to atack and 'k' to save:\n");
            System.out.println("Total Health: " +
                    RPGGame.currentCharacter.getHealthPoints() + " - Current Health: "
                    + RPGGame.currentCharacter.getCurrentHealthPoints() + " - Attack: "
                    + RPGGame.currentCharacter.getAttack()
                    + " - Defense: " + RPGGame.currentCharacter.getDefense() + " - Magic: "
                    + RPGGame.currentCharacter.getMagic()
                    + " - Speed: " + RPGGame.currentCharacter.getSpeed() + "\n");
            String choice = RPGGame.scan.next().trim().toLowerCase();
            PlayerController.playerAction(choice);
        }
    }

    private static void displayMap() {
        System.out.println("\n--- Game Map ---");
        for (char[] row : RPGGame.currentMap) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}