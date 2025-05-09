package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.CLI;
import rpg.things.player.Load;
import rpg.things.player.Player;
import rpg.types.Command;
import rpg.types.ItemType;

public class InventoryInteraction extends Interaction {
    private Load load;
    private rpg.things.Character npc;

    public InventoryInteraction(Player player, Load load) {
        this(player, load, null);
    }

    public InventoryInteraction(Player player, Load load, rpg.things.Character npc) {
        super(player);
        this.load = load;
        this.npc = npc;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        if (load.getItem().getType() != ItemType.USABLE) {
            menu.addAll(Arrays.asList(
                    new AbstractMap.SimpleEntry<>(Command.EQUIP_ITEM, "Equip"),
                    new AbstractMap.SimpleEntry<>(Command.UNEQUIP_ITEM, "Unequip"),
                    new AbstractMap.SimpleEntry<>(Command.DROP_ITEM, "Drop"),
                    new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacton")));
        } else {
            menu.addAll(Arrays.asList(new AbstractMap.SimpleEntry<>(Command.USE_ITEM, "Use"),
                    new AbstractMap.SimpleEntry<>(Command.DROP_ITEM, "Drop"),
                    new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacton")));
        }

        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.LOOK) {
            messages.add("\n──── Item Stats ────");
            messages.add(
                    "\nItem: " + load.getItem().getName() +
                            "\nDescription: " + load.getItem().getDescription() +
                            "\n" + load.getItem().showStats() + "\n");

            finish();
        }

        else if (command == Command.EQUIP_ITEM) {
            messages.addAll(equipItem(load));
            finish();
        }

        else if (command == Command.USE_ITEM) {
            if (!player.getIsBattling()) {
                if (load.getItem().getCure() > 0) {
                    messages.add("\nYou used: " + load.getItem().getName() + ".");
                    player.healDamage(load.getItem().getCure());
                    player.useFromInventory(load);
                } else
                    messages.add("\nYou can't use this item on yourself.");
            } else {
                messages.add("\nYou used: " + load.getItem().getName() + ".");
                if (load.getItem().getCure() > 0)
                    player.healDamage(load.getItem().getCure());
                else
                    npc.receiveDamage(load.getItem().getAttack());
                messages.add("\nYou caused: " + load.getItem().getAttack() + " damage to the enemy.");

                player.useFromInventory(load);
            }

            finish();
        }

        else if (command == Command.UNEQUIP_ITEM) {
            messages.addAll(unequipItem(load));
            finish();
        }

        else if (command == Command.DROP_ITEM) {
            messages.add("\nYou dropped " + load.getItem().getName() + ".");
            player.dropFromInventory(load);
            finish();
        }

        else if (command == Command.STOP_INTERACTION) {
            finish();
        }

        return messages;
    }

    private void finish() {
        CLI.remove(this);
        player.getInventory().defineAdversary(null);
    }

    public List<String> equipItem(Load load) {
        List<String> messages = new ArrayList<>();
        boolean equalType = false;

        if (!load.getEquipped()) {
            for (Load inventoryLoad : player.getInventory().getLoads())
                if (inventoryLoad.getEquipped() && inventoryLoad.getItem().getType() == load.getItem().getType())
                    equalType = true;

            if (!equalType) {
                load.setEquipped(true);
                player.setAttack(player.getAttack() + load.getItem().getAttack());
                player.setDefense(player.getDefense() + load.getItem().getDefense());

                messages.add("\nYou equipped: " + load.getItem().getName() + ".");
            } else
                messages.add("\nYou cannot equip two items of the same type");
        }

        else
            messages.add("\nYou cannot equip an item that is already equipped");

        return messages;
    }

    public List<String> unequipItem(Load load) {
        List<String> messages = new ArrayList<>();
        if (load.getEquipped()) {
            load.setEquipped(false);
            player.setAttack(player.getAttack() - load.getItem().getAttack());
            player.setDefense(player.getDefense() - load.getItem().getDefense());

            messages.add("\nYou can't unequip something that isn't equipped;");
        } else
            messages.add("\nYou unequipped: " + load.getItem().getName() + ".");

        return messages;
    }
}