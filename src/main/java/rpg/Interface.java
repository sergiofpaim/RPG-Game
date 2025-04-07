package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import rpg.interfaces.IInteractable;
import rpg.types.Command;

public class Interface {
    public static final Scanner scan = new Scanner(System.in);

    private static List<IInteractable> interactables = new ArrayList<>();
    private static List<Entry<Command, String>> options;

    public static void showCommands(boolean showDetails) {
        options = new ArrayList<>();

        for (IInteractable interactable : interactables) {
            options.addAll(interactable.retrieveMenu());
        }

        System.out.println("\n--- Commands ---");
        if (showDetails) {
            int counter = 0;
            for (Entry<Command, String> entry : options) {
                System.out.print(
                        String.format("%-25s", entry.getKey().getKey().toLowerCase() + " - " + entry.getValue() + " "));
                counter++;
                if (counter % 3 == 0) {
                    System.out.println();
                }
            }
        } else {
            for (Entry<Command, String> entry : options) {
                System.out.print(entry.getKey().getKey().toLowerCase() + " ");
                if (entry.getKey() == Command.HELP)
                    System.out.print(" (help)\n");
            }
            System.out.println();
        }
    }

    public static void add(IInteractable interactable) {
        interactables.add(interactable);
    }

    public static Command getCommand() {
        String key = scan.next().trim().toLowerCase();
        Command command = Command.fromKey(key);

        if (command == null) {
            System.out.println("Invalid command! Please try again.");
            return getCommand();
        } else {
            return command;
        }
    }

    public static void interact() {
        Command command = getCommand();

        for (IInteractable interactable : interactables) {
            List<String> messages = interactable.processCommand(command);
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }
}
