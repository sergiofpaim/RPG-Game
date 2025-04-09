package rpg.things.player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rpg.Game;
import rpg.Interface;
import rpg.interactions.InventoryInteraction;
import rpg.interfaces.IInteractive;
import rpg.things.Item;
import rpg.things.Position;
import rpg.types.Command;

public class Inventory implements IInteractive {

    private Player player;
    private List<Load> loads = new ArrayList<Load>();

    public Inventory() {

    }

    public Inventory(rpg.things.Character character) {
        if (character instanceof Player)
            this.player = (Player) character;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>();
        for (int i = 0; i < loads.size(); i++) {
            menu.add(new AbstractMap.SimpleEntry<>(
                    Command.fromKey(String.valueOf(i + 1)),
                    (loads.get(i).getItem().getName())));
        }
        menu.add(new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacting"));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {

        if (command == Command.STOP_INTERACTION) {
            Interface.remove(this);
        } else if (command.isSelect()) {
            int index = Integer.parseInt(command.getKey()) - 1;

            if (index >= 0 && index < loads.size()) {
                InventoryInteraction interaction = new InventoryInteraction(player, loads.get(index));
                Interface.add(interaction);
            }
        }
        return showInventory();
    }

    public List<String> showInventory() {
        List<String> messages = new ArrayList<>();

        messages.add("\nYou have got these things in your inventory:\n");
        messages.add("\n--- Inventory ---\n");

        for (Load load : loads) {
            if (load.getEquipped()) {
                messages.add(load.getItem().draw() + " " + load.getItem().getName() +
                        " - equipped\n");
            } else
                messages.add(
                        load.getItem().draw() + " " + load.getItem().getName());
        }
        messages.add("------------------\n");

        return messages;
    }

    public void addItem(Item item) {
        loads.add(new Load(item));
    }

    public void removeItem(Load load) {
        loads.remove(load);
        // TODO: Check if item is still on the map after being thrown
        load.getItem().setPosition(new Position(player.getPosition().getX(), player.getPosition().getY() + 1));
        load.getItem().drop();
    }

    public List<Load> getLoads() {
        return loads;
    }

    public void setLoads(List<Load> loads) {
        this.loads = loads;
    }

    public void setItems(List<Item> items) {
        this.loads = new ArrayList<>();
        for (Item item : items) {
            this.loads.add(new Load(item));
        }
    }

    public void setGame(Game game) {
        for (Load load : loads) {
            load.getItem().setGame(game);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
