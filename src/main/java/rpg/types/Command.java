package rpg.types;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

public class Command {
    private final String command;
    private final String key;

    // Private constructor to restrict instantiation
    private Command(String command, String key) {
        this.command = command;
        this.key = key;
    }

    public String getCommand() {
        return command;
    }

    public String getKey() {
        return key;
    }

    // Static predefined commands
    public static final Command UP = new Command("UP", "W");
    public static final Command DOWN = new Command("DOWN", "S");
    public static final Command LEFT = new Command("LEFT", "A");
    public static final Command RIGHT = new Command("RIGHT", "D");
    public static final Command INVENTORY = new Command("INVENTORY", "B");
    public static final Command INTERACT = new Command("INTERACT", "I");
    public static final Command SAVE = new Command("SAVE", "K");
    public static final Command HELP = new Command("HELP", "H");
    public static final Command EXIT = new Command("EXIT", "X");

    // Optionally: get all commands
    public static List<Command> all() {
        return Arrays.asList(UP, DOWN, LEFT, RIGHT, INVENTORY, INTERACT, SAVE, HELP, EXIT);
    }

    public static Command fromKey(String key) {
        Command command = all().stream()
                .filter(c -> c.key.equalsIgnoreCase(key))
                .findFirst().orElse(null);

        return command;
    }
}