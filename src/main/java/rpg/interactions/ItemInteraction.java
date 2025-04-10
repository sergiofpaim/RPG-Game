package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Interface;
import rpg.Runner;
import rpg.things.Item;
import rpg.things.player.Player;
import rpg.types.Command;
import rpg.types.ItemType;

public class ItemInteraction extends Interaction {
    private Item item;

    public ItemInteraction(Player player, Item item) {
        super(player);
        this.item = item;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());

        if (item.getType() != ItemType.DOOR) {
            menu.addAll(Arrays.asList(
                    new AbstractMap.SimpleEntry<>(Command.PICK_UP_ITEM, "Pick Up"),
                    new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacton")));
        } else
            menu.addAll(Arrays.asList(new AbstractMap.SimpleEntry<>(Command.ENTER_DOOR, "Enter Door")));

        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.LOOK) {
            messages.add(
                    "\n Item: " + item.getName() +
                            "\nDescription: " + item.getDescription()
                            + "\nStats: " + item.showStats());

            Interface.remove(this);
        }

        else if (command == Command.PICK_UP_ITEM) {
            messages.add("\nYou picked up " + item.getName() + ".");
            player.addToInventory(item);
            Interface.remove(this);
        }

        else if (command == Command.STOP_INTERACTION) {
            messages.add("\nYou stopped interacting with " + item.getName() + ".");
            Interface.remove(this);
        }

        else if (command == Command.ENTER_DOOR) {
            messages.add("\nYou entered a new Dungeon!");
            Runner.newWorld();
            Interface.remove(this);
        }

        return messages;
    }
}