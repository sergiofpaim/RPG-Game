package rpg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.interfaces.IInteractive;
import rpg.interfaces.IThing;
import rpg.things.player.Player;
import rpg.types.Command;
import rpg.utils.StringHelper;

public class Runner implements IInteractive {
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
            int width = game.getMapWidth() * 2;

            System.out.println("┌" + StringHelper.centerWithPad(" Game Map ", width, '─') + "┐");

            game.startDrawing();
            while (game.nextRow()) {
                System.out.print("│");
                while (game.nextCol()) {
                    System.out.print(game.drawCell());
                }
                System.out.println("│");
            }

            System.out.println("└" + StringHelper.repeatChar('─', width) + "┘");
        }
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.HELP, "Help"),
                new AbstractMap.SimpleEntry<>(Command.SAVE, "Save game"),
                new AbstractMap.SimpleEntry<>(Command.EXIT, "Exit game"));
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

    public static void newWorld() {
        game.redefineMap(player);

        for (IThing thing : game.getThings())
            thing.setGame(game);
    }

    @Override
    public String retrieveLabel() {
        return "General";
    }

}
