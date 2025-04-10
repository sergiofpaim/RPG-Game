package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Interface;
import rpg.things.player.Load;
import rpg.things.player.Player;
import rpg.types.Command;

public class InventoryInteraction extends Interaction {
    private Load load;

    public InventoryInteraction(Player player, Load item) {
        super(player);
        this.load = item;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.USE_ITEM, "Use"),
                new AbstractMap.SimpleEntry<>(Command.EQUIP_ITEM, "Equip"),
                new AbstractMap.SimpleEntry<>(Command.UNEQUIP_ITEM, "Unequip"),
                new AbstractMap.SimpleEntry<>(Command.DROP_ITEM, "Drop"),
                new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacton")));

        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.LOOK) {
            messages.add(
                    "\n Item: " + load.getItem().getName() + " - Description: "
                            + load.getItem().getDescription());

            Interface.remove(this);
        }

        else if (command == Command.EQUIP_ITEM) {
            messages.add("\nYou equipped: " + load.getItem().getName() + ".");
            equipItem(load);
            Interface.remove(this);
        }

        else if (command == Command.USE_ITEM) {
            messages.add("\nYou used: " + load.getItem().getName() + ".");
            if (load.getItem().getCure() > 0)
                player.setCurrentHealthPoints(player.getCurrentHealthPoints() + load.getItem().getCure());
            else
                messages.add("\nYou cannot use this item on yourself");

            Interface.remove(this);
        }

        else if (command == Command.UNEQUIP_ITEM) {
            messages.add("\nYou unequipped: " + load.getItem().getName() + ".");
            unequipItem(load);
            Interface.remove(this);
        }

        else if (command == Command.DROP_ITEM) {
            messages.add("\nYou dropped " + load.getItem().getName() + ".");
            player.removeFromInventory(load);
            Interface.remove(this);
        }

        else if (command == Command.STOP_INTERACTION) {
            Interface.remove(this);
        }

        return messages;
    }

    public void equipItem(Load load) {
        if (!load.getEquipped()) {
            load.setEquipped(true);
            player.setAttack(player.getAttack() + load.getItem().getDamage());
            player.setDefense(player.getDefense() + load.getItem().getDefense());
        }
    }

    public void unequipItem(Load load) {
        if (load.getEquipped()) {
            load.setEquipped(false);
            player.setAttack(player.getAttack() - load.getItem().getDamage());
            player.setDefense(player.getDefense() - load.getItem().getDefense());
        }
    }
}