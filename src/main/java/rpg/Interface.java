package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;

import rpg.interfaces.IInteractive;
import rpg.types.Command;

public class Interface {
    public static final Scanner scan = new Scanner(System.in);

    private static List<IInteractive> interactables = new ArrayList<>();
    private static List<Entry<Command, String>> options;

    public static void showCommands(boolean showDetails) {
        options = new ArrayList<>();

        options.addAll(interactables.get(0).retrieveMenu());
        if (interactables.size() > 1)
            options.addAll(interactables.get(interactables.size() - 1).retrieveMenu());

        System.out.println("\n--- Commands ---");
        if (showDetails) {
            int columns = 4;
            int total = options.size();
            int rows = (int) Math.ceil((double) total / columns);

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    int index = col * rows + row;
                    if (index < total) {
                        Entry<Command, String> entry = options.get(index);
                        String text = entry.getKey().getKey().toLowerCase() + " - " + entry.getValue();
                        System.out.print(String.format("%-25s", text));
                    }
                }
                System.out.println();
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

        for (IInteractive interactable : new ArrayList<>(interactables)) {
            List<String> messages = interactable.processCommand(command);
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }

    public static void add(IInteractive interactable) {
        interactables.add(interactable);
    }

    public static void remove(IInteractive interactable) {
        interactables.remove(interactable);
    }
}