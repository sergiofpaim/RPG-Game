package rpg.interactions;

import java.util.Map.Entry;

import rpg.Interface;
import rpg.things.Item;
import rpg.things.Player;
import rpg.types.Command;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                new AbstractMap.SimpleEntry<>(Command.USE_ITEM, "Use"),
                new AbstractMap.SimpleEntry<>(Command.DROP_ITEM, "Drop")));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.PICK_UP_ITEM) {
            player.addToInventory(item);
            messages.add("You picked up " + item.getName() + ".");
            Interface.remove(this);
        }

        return messages;
    }
}