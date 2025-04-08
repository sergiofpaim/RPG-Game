package rpg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import rpg.interfaces.IInteractable;
import rpg.interfaces.IThing;
import rpg.things.Player;
import rpg.types.Command;

public class Runner implements IInteractable {
    public static Boolean isRunning = true;
    public static Boolean showingHelp = false;

    public static Game game = null;
    public static Player player = null;

    public void run(Game newGame) {
        game = newGame;
        player = (Player) game.listCharacters().get(0);

        for (IThing thing : game.getThings())
            thing.setGame(game);

        Interface.add(this);
        Interface.add(player);

        render();

        while (true) {
            Interface.interact();
            if (isRunning)
                render();
            else
                break;
        }
    }

    private void render() {
        displayMap();
        Interface.showCommands(shouldShowHelp());
    }

    private static void displayMap() {
        if (game.hasChanged()) {
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

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.HELP, "Help"),
                new AbstractMap.SimpleEntry<>(Command.EXIT, "Exit"),
                new AbstractMap.SimpleEntry<>(Command.SAVE, "Save"));
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();
        if (command == Command.SAVE)
            messages.add(Sessions.save(game));
        else if (command == Command.EXIT) {
            messages.add("Exiting game...");
            isRunning = false;
        } else if (command == Command.HELP) {
            showHelp();
        }
        return messages;
    }

    private boolean shouldShowHelp() {
        if (showingHelp) {
            showingHelp = false;
            return true;
        }
        return false;
    }

    private void showHelp() {
        showingHelp = true;
    }
}