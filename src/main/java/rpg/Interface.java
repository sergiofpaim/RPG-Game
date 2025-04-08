package rpg;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

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

    public static Command getCommand() {
        Command command = null;
        while (command == null) {
            System.out.print("> ");
            String key = scan.next().trim().toLowerCase();

            Optional<Entry<Command, String>> matchedOption = options.stream()
                    .filter(entry -> entry.getKey().getKey().toLowerCase().equals(key))
                    .findFirst();

            if (!matchedOption.isPresent())
                System.out.println("Invalid command! Please try again.");
            else
                command = matchedOption.get().getKey();

        }
        return command;
    }

    public static void interact() {
        Command command = getCommand();

        for (IInteractable interactable : new ArrayList<>(interactables)) {
            List<String> messages = interactable.processCommand(command);
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }

    public static void add(IInteractable interactable) {
        interactables.add(interactable);
    }

    public static void remove(IInteractable interactable) {
        interactables.remove(interactable);
    }
}
