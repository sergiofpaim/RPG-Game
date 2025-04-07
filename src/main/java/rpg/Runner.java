package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rpg.interfaces.IInteractable;
import rpg.interfaces.IThing;
import rpg.things.Item;
import rpg.things.NPC;
import rpg.things.Player;

public class Runner {
    public static Boolean isRunning = true;

    public static Game game = null;
    public static Player player = null;
    public static List<IInteractable> interactables = new ArrayList<>();

    public static void run(Game newGame) {
        game = newGame;
        player = (Player) game.listCharacters().get(0);
        player.setGame(game);

        displayMap();
        showCommands();

        while (isRunning) {
            String key = RPGGame.scan.next().trim().toLowerCase();
            processInput(key);
        }
    }

    public static void processInput(String key) {
        if (key.equals("k"))
            Sessions.save(game);
        else if (key.equals("m"))
            System.out.println(player.processInput(key));
        else {
            System.out.println(player.processInput(key));
            displayMap();
            showCommands();
            checkInteraction();
        }
    }

    private static boolean checkInteraction() {
        boolean interaction = false;
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

        for (IThing thing : game.getThings()) {
            for (int[] offset : offsets) {
                if (thing.getPosition().getY() == player.getPosition().getY() + offset[0]
                        && thing.getPosition().getX() == player.getPosition().getX() + offset[1]) {
                    interactiveThings.add(thing);
                    interaction = true;
                }
            }
        }

        if (interaction) {
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
                        System.out.println("\n" + counter + "- " + ((NPC) thing).getName() + ": "
                                + ((NPC) thing).getDescription());
                    } else if (thing instanceof Item) {
                        System.out.println("\n" + counter + "- " + ((Item) thing).getName() + ": "
                                + ((Item) thing).getDescription());
                    }
                }
                counter++;
            }

            Interaction newInteraction = new Interaction(interactiveThings.get(RPGGame.scan.nextInt() - 1));

            Map.Entry<Game, String> resultInteraction = newInteraction.manageInteraction(game);

            game = resultInteraction.getKey();
            System.out.println(resultInteraction.getValue());

            if (!game.listCharacters().contains(player))
                isRunning = false;
            else
                player = (Player) game.listCharacters().get(0);
        }

        return interaction;
    }

    private static void showCommands() {
        System.out.println(
                "\nYou are the '\uD83C\uDFC7', Enter a direction (w/a/s/d), 'h' for help, 'b' to see you inventory, 'i' to interact, 'x' to quit and 'k' to save:\n");

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