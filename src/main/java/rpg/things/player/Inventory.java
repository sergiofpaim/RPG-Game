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

    private rpg.things.Character character;
    private List<Load> loads = new ArrayList<Load>();

    public Inventory() {

    }

    public Inventory(rpg.things.Character character) {
        this.character = character;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>();
        for (int i = 0; i < loads.size(); i++) {
            menu.add(new AbstractMap.SimpleEntry<>(
                    Command.fromKey((char) (i + 49)),
                    (loads.get(i).getItem().getName())));
        }
        menu.add(new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacting"));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.STOP_INTERACTION) {
            Interface.remove(this);
        } else if (command.isSelect()) {
            int index = Character.getNumericValue(command.getKey()) - 1;

            if (index >= 0 && index < loads.size()) {
                if (character instanceof Player) {
                    InventoryInteraction interaction = new InventoryInteraction((Player) character, loads.get(index));
                    Interface.remove(this);
                    Interface.add(interaction);
                }
            }
        } else
            messages.addAll(showInventory());

        return messages;
    }

    public List<String> showInventory() {
        List<String> messages = new ArrayList<>();

        messages.add("\n───── Inventory ─────");

        int count = 0;
        for (Load load : loads) {
            if (load.getEquipped()) {
                messages.add(++count + ") "
                        + load.getItem().draw() + " " + load.getItem().getName() +
                        " - equipped\n");
            } else
                messages.add(
                        ++count + ") " + load.getItem().draw() + " " + load.getItem().getName());
        }

        return messages;
    }

    public void addItem(Item item) {
        loads.add(new Load(item));
    }

    public void dropLoad(Load load) {
        loads.remove(load);
        if (character.isOnMapLimits())
            load.getItem()
                    .setPosition(new Position(character.getPosition().getX(), character.getPosition().getY() - 1));
        else
            load.getItem()
                    .setPosition(new Position(character.getPosition().getX(), character.getPosition().getY() + 1));

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

    public void setCharacter(rpg.things.Character character) {
        this.character = character;
    }

    public void useLoad(Load load) {
        loads.remove(load);
    }

    @Override
    public String retrieveLabel() {
        return "Inventory";
    }
}
