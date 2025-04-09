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

public class InventoryInteraction extends Interaction {
    private Item item;

    public InventoryInteraction(Player player, Item item) {
        super(player);
        this.item = item;

        Interface.remove(player);
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.USE_ITEM, "Use"),
                new AbstractMap.SimpleEntry<>(Command.DROP_ITEM, "Drop"),
                new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacton")));

        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.LOOK) {
            return Arrays.asList("\n Item: " + item.getName() + " - Description: " + item.getDescription());
        }

        else if (command == Command.USE_ITEM) {
            messages.add("\nYou used " + item.getName() + ".");
            player.addEquipedItem(item);
            player.setInteractingWithMap(false);
            Interface.remove(this);
            Interface.add(player);
        }

        else if (command == Command.DROP_ITEM) {
            messages.add("\nYou dropped " + item.getName() + ".");

            player.removeFromInventory(item);
            player.setInteractingWithInventory(false);

            Interface.remove(this);
            Interface.add(player);
        }

        else if (command == Command.STOP_INTERACTION) {
            Interface.remove(this);
            Interface.add(player);
        }

        return messages;
    }
}