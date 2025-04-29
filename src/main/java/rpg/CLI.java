package rpg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import rpg.interfaces.IInteractive;
import rpg.types.Command;
import rpg.utils.InputHelper;

public class CLI {
    public static final Scanner scan = new Scanner(System.in);

    private static List<IInteractive> interactive = new ArrayList<>();
    private static List<Entry<String, List<Entry<Command, String>>>> labels;

    public static void showCommands(boolean showDetails) {
        labels = new ArrayList<>();

        IInteractive first = interactive.get(0);
        labels.add(new AbstractMap.SimpleEntry<>(
                first.retrieveLabel(),
                first.retrieveMenu()));

        if (interactive.size() > 1) {
            IInteractive last = interactive.get(interactive.size() - 1);
            labels.add(new AbstractMap.SimpleEntry<>(
                    last.retrieveLabel(),
                    last.retrieveMenu()));

            System.out.println("\n────── Commands ──────");
            if (showDetails) {
                List<Entry<Command, String>> options = labels.stream()
                        .flatMap(label -> label.getValue().stream())
                        .collect(Collectors.toList());

                int columns = 4;
                int total = options.size();
                int rows = (int) Math.ceil((double) total / columns);

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < columns; col++) {
                        int index = col * rows + row;
                        if (index < total) {
                            Entry<Command, String> entry = options.get(index);
                            String text = entry.getKey().getKey() + " - " + entry.getValue();
                            System.out.print(String.format("%-25s", text));
                        }
                    }
                    System.out.println();
                }
            } else {
                for (Entry<String, List<Entry<Command, String>>> label : labels) {
                    System.out.print(label.getKey() + ": ");
                    for (Entry<Command, String> entry : label.getValue()) {
                        System.out.print(entry.getKey().getKey() + " ");
                        if (entry.getKey() == Command.HELP)
                            System.out.print("(help) ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public static Command getCommand() {
        Command command = null;
        while (command == null) {
            System.out.println();
            System.out.print("> ");
            char key = InputHelper.readKey();
            System.out.print(key);
            System.out.println();

            Optional<Entry<Command, String>> matchedOption = labels.stream()
                    .flatMap(entry -> entry.getValue().stream())
                    .filter(innerEntry -> innerEntry.getKey().getKey() == key)
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

        List<IInteractive> targets = new ArrayList<>();

        // Gets first item of the interactables list
        targets.add(interactive.get(0));

        // Gets last item of the interactables list
        if (interactive.size() > 1)
            targets.add(interactive.get(interactive.size() - 1));

        for (IInteractive interactable : targets) {
            List<String> messages = interactable.processCommand(command);
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }

    public static void add(IInteractive interactable) {
        interactive.add(interactable);
    }

    public static void remove(IInteractive interactable) {
        interactive.remove(interactable);
    }
}