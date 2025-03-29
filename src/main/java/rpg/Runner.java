package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rpg.interfaces.IThing;
import rpg.things.Character;
import rpg.things.Item;
import rpg.things.NPC;
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
        else if (key.equals("m"))
            System.out.println(player.processInput(key));
        else {
            System.out.println(player.processInput(key));
            displayMap();
            checkInteraction();
            showCommands();
        }
    }

    private static void checkInteraction() {
        boolean beginInteraction = false;
        List<IThing> interactiveThings = new ArrayList<>();

        int[][] offsets = {
                { -1, -1 },
                { -1, 0 },
                { -1, 1 },
                { 0, -1 },
                { 0, 1 },
                { 1, -1 },
                { 1, 0 },
                { 1, 1 }
        };

        for (rpg.things.Item item : game.getItems()) {
            for (int[] offset : offsets) {
                if (item.getPositionX() == player.getPositionX() + offset[0]
                        && item.getPositionY() == player.getPositionY() + offset[1]) {
                    interactiveThings.add(item);
                    beginInteraction = true;
                }
            }
        }

        for (Character npc : game.getCharacters().subList(1, game.getCharacters().size())) {
            for (int[] offset : offsets) {
                if (npc.getPositionX() == player.getPositionX() + offset[0]
                        && npc.getPositionY() == player.getPositionY() + offset[1]) {
                    interactiveThings.add(npc);
                    beginInteraction = true;
                }
            }
        }

        if (beginInteraction) {
            System.out.println("Do you want to start an interaction? (y/n)");
            if (RPGGame.scan.hasNextLine()) {
                RPGGame.scan.nextLine();
            }
            String choice = RPGGame.scan.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {

                int counter = 1;

                System.out.println("\nType the number of the thing you want to interact with: ");

                for (IThing thing : interactiveThings) {
                    if (thing instanceof NPC) {
                        System.out.println(
                                "\n" + counter + "- " + ((NPC) thing).getName() + ": "
                                        + ((NPC) thing).getDescription());
                    } else if (thing instanceof Item) {
                        System.out.println("\n" + counter + "- " + ((Item) thing).getName() + ": "
                                + ((Item) thing).getDescription());
                    }
                    counter++;
                }

                Interaction interaction = new Interaction(interactiveThings.get(RPGGame.scan.nextInt() - 1));

                Map.Entry<Game, String> resultInteraction = interaction.manageInteraction(game);

                game = resultInteraction.getKey();
                player = (Player) game.getCharacters().get(0);

                System.out.println(resultInteraction.getValue());
            }
        }
    }

    private static void showCommands() {
        System.out.println(
                "\nYou are the '\uD83C\uDFC7', Enter a direction (w/a/s/d), 'm' to see you inventory and 'k' to save:\n");

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