package rpg.types;

import java.util.List;
import java.util.Arrays;

public class Command {
    private final String command;
    private final String key;

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
    public static final Command ATTACK = new Command("ATTACK", "T");
    public static final Command DEFEND = new Command("DEFEND", "F");
    public static final Command MAGIC = new Command("MAGIC", "M");
    public static final Command LOOK = new Command("LOOK", "L");
    public static final Command USE_ITEM = new Command("USE_ITEM", "U");
    public static final Command PICK_UP_ITEM = new Command("PICK_UP", "P");
    public static final Command DROP_ITEM = new Command("DROP_ITEM", "D");
    public static final Command TALK = new Command("TALK", "Q");
    public static final Command RUN = new Command("RUN", "R");
    public static final Command OPEN = new Command("OPEN", "O");
    public static final Command CLOSE = new Command("CLOSE", "C");
    public static final Command SELECT_1 = new Command("SELECT_1", "1");
    public static final Command SELECT_2 = new Command("SELECT_2", "2");
    public static final Command SELECT_3 = new Command("SELECT_3", "3");
    public static final Command SELECT_4 = new Command("SELECT_4", "4");
    public static final Command SELECT_5 = new Command("SELECT_5", "5");
    public static final Command SELECT_6 = new Command("SELECT_6", "6");
    public static final Command SELECT_7 = new Command("SELECT_7", "7");
    public static final Command SELECT_8 = new Command("SELECT_8", "8");
    public static final Command SELECT_9 = new Command("SELECT_9", "9");

    public static List<Command> all() {
        return Arrays.asList(UP, DOWN, LEFT, RIGHT, INVENTORY, INTERACT, SAVE, HELP, EXIT,
                ATTACK, DEFEND, MAGIC, PICK_UP_ITEM, USE_ITEM, DROP_ITEM, TALK, LOOK, RUN, OPEN, CLOSE,
                SELECT_1, SELECT_2, SELECT_3, SELECT_4, SELECT_5, SELECT_6, SELECT_7, SELECT_8, SELECT_9);
    }

    public static Command fromKey(String key) {
        Command command = all().stream()
                .filter(c -> c.key.equalsIgnoreCase(key))
                .findFirst().orElse(null);

        return command;
    }
}