package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Interface;
import rpg.things.Item;
import rpg.things.player.Player;
import rpg.types.Command;

public class ItemInteraction extends Interaction {
    private Item item;

    public ItemInteraction(Player player, Item item) {
        super(player);
        this.item = item;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.PICK_UP_ITEM, "Pick Up"),
                new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacton")));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.PICK_UP_ITEM) {
            messages.add("\nYou picked up " + item.getName() + ".");
            player.addToInventory(item);
            Interface.remove(this);
        }

        else if (command == Command.LOOK) {
            return Arrays.asList("\nYou see: " + item.getDescription());
        }

        else if (command == Command.STOP_INTERACTION) {
            messages.add("\nYou stopped interacting with " + item.getName() + ".");
            Interface.remove(this);
        }

        return messages;
    }
}