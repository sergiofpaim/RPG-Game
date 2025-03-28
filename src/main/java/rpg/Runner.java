package rpg;

import java.util.ArrayList;
import java.util.List;

import rpg.interfaces.IThing;
import rpg.things.Character;
import rpg.things.Player;

public class Runner {
    public static Game game = null;
    public static Player player = null;

    public static void start(Game newGame) {
        game = newGame;
        player = (Player) game.getCharacters().get(0);
        displayMap();
        showCommands();
    }

    public static void processInput(String key) {
        if (key.equals("k"))
            Sessions.save(game);
        else
            System.out.println(player.processInput(key));

        checkInteraction();
        displayMap();
        showCommands();
    }

    private static void checkInteraction() {
        boolean beginInteraction = false;
        int interactions = 0;
        List<IThing> interactiveThings = new ArrayList<>();

        int[][] uShapeOffsets = {
                { 0, -1 },
                { 0, 1 },
                { -1, 0 },
                { 1, -1 },
                { 1, 1 }
        };

        for (rpg.things.Item item : game.getItems()) {
            for (int[] ofset : uShapeOffsets) {
                if (item.getPositionX() == player.getPositionX() + ofset[0]
                        && item.getPositionY() == player.getPositionY() + ofset[0]) {
                    System.out.println("You found a " + item.getName() + "!");
                    System.out.println("Press " + interactions + " to interact with the item.");
                    interactiveThings.add(item);
                    interactions++;
                    beginInteraction = true;
                }
            }
        }
        for (Character npc : game.getCharacters().subList(1, game.getCharacters().size())) {
            for (int[] ofset : uShapeOffsets) {
                if (npc.getPositionX() == player.getPositionX() + ofset[0]
                        && npc.getPositionY() == player.getPositionY() + ofset[0]) {
                    System.out.println("You are near " + npc.getName() + "!");
                    System.out.println("Press " + interactions + " to interact with them.");
                    interactiveThings.add(npc);
                    interactions++;
                    beginInteraction = true;
                }
            }
        }

        if (beginInteraction) {
            System.out.println("Do you want to start an interaction? (y/n)");
            String choice = RPGGame.scan.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                Interaction interaction = new Interaction(interactiveThings.get(RPGGame.scan.nextInt()));

                System.out.println(interaction.startInteraction(player));
            }
        }
    }

    private static void showCommands() {
        System.out.println(
                "\nYou are the '@', Enter a direction (w/a/s/d), 'm' to see you inventory, 'i' to interact, 'b' to atack and 'k' to save:\n");

        System.out.println(player.showStats());
    }

    private static void displayMap() {
        System.out.println("\n--- Game Map ---");

        game.startDrawing();

        while (game.nextRow()) {
            while (game.nextCol()) {
                System.out.print(game.drawCell() + " ");
            }
            System.out.println();
        }
    }
}