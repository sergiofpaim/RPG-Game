package rpg.types;

import java.util.List;
import java.util.Arrays;

public class Command {
    private final String command;
    private final char key;

    private Command(String command, char key) {
        this.command = command;
        this.key = key;
    }

    public String getCommand() {
        return command;
    }

    public char getKey() {
        return key;
    }

    public static final Command UP = new Command("UP", 'W');
    public static final Command DOWN = new Command("DOWN", 'S');
    public static final Command LEFT = new Command("LEFT", 'A');
    public static final Command RIGHT = new Command("RIGHT", 'D');
    public static final Command INVENTORY = new Command("INVENTORY", 'B');
    public static final Command SHOW_STATS = new Command("SHOW_STATS", 'C');
    public static final Command INTERACT = new Command("INTERACT", 'I');
    public static final Command STOP_INTERACTION = new Command("Stop Interacton", 'I');
    public static final Command HELP = new Command("HELP", 'H');
    public static final Command SAVE = new Command("SAVE", 'K');
    public static final Command EXIT = new Command("EXIT", 'X');
    public static final Command BATTLE = new Command("BATTLE", 'T');
    public static final Command ATTACK = new Command("ATTACK", 'T');
    public static final Command DEFEND = new Command("DEFEND", 'F');
    public static final Command MAGIC = new Command("MAGIC", 'M');
    public static final Command LOOK = new Command("LOOK", 'L');
    public static final Command TALK = new Command("TALK", 'Q');
    public static final Command RUN = new Command("RUN", 'R');
    public static final Command USE_ITEM = new Command("USE_ITEM", 'U');
    public static final Command EQUIP_ITEM = new Command("EQUIP_ITEM", 'E');
    public static final Command UNEQUIP_ITEM = new Command("UNEQUIP_ITEM", 'N');
    public static final Command PICK_UP_ITEM = new Command("PICK_UP", 'P');
    public static final Command DROP_ITEM = new Command("DROP_ITEM", 'D');
    public static final Command SELECT_1 = new Command("SELECT_1", '1');
    public static final Command SELECT_2 = new Command("SELECT_2", '2');
    public static final Command SELECT_3 = new Command("SELECT_3", '3');
    public static final Command SELECT_4 = new Command("SELECT_4", '4');
    public static final Command SELECT_5 = new Command("SELECT_5", '5');
    public static final Command SELECT_6 = new Command("SELECT_6", '6');
    public static final Command SELECT_7 = new Command("SELECT_7", '7');
    public static final Command SELECT_8 = new Command("SELECT_8", '8');
    public static final Command SELECT_9 = new Command("SELECT_9", '9');
    public static final Command UPGRADE_ATTACK = new Command("UPGRADE_ATTACK", 'A');
    public static final Command UPGRADE_DEFENSE = new Command("UPGRADE_DEFENSE", 'D');
    public static final Command UPGRADE_MAGIC = new Command("UPGRADE_MAGIC", 'M');
    public static final Command UPGRADE_SPEED = new Command("UPGRADE_SPEED", 'S');
    public static final Command ENTER_DOOR = new Command("ENTER_DOOR", '0');

    public static List<Command> all() {
        return Arrays.asList(UP, DOWN, LEFT, RIGHT, INVENTORY, SHOW_STATS, INTERACT, STOP_INTERACTION, SAVE, HELP, EXIT,
                BATTLE,
                ATTACK, DEFEND, MAGIC, PICK_UP_ITEM, USE_ITEM, EQUIP_ITEM, UNEQUIP_ITEM, DROP_ITEM, TALK, LOOK, RUN,
                SELECT_1, SELECT_2, SELECT_3, SELECT_4, SELECT_5, SELECT_6, SELECT_7, SELECT_8, SELECT_9,
                UPGRADE_ATTACK, UPGRADE_DEFENSE, UPGRADE_MAGIC, UPGRADE_SPEED, ENTER_DOOR);
    }

    public Boolean isSelect() {
        return this.command.startsWith("SELECT_");
    }

    public static Command fromKey(char key) {
        Command command = all().stream()
                .filter(c -> c.key == Character.toUpperCase(key))
                .findFirst().orElse(null);

        return command;
    }
}