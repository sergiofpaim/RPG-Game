package rpg.interactions;

import java.util.Map.Entry;

import rpg.Interface;
import rpg.things.NPC;
import rpg.things.Player;
import rpg.types.Command;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPCInteraction extends Interaction {
    private NPC npc;
    private boolean battling = false;

    public NPCInteraction(Player player, NPC npc) {
        super(player);
        this.npc = npc;

        Interface.remove(player);
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        if (!battling)
            menu.addAll(Arrays.asList(
                    new AbstractMap.SimpleEntry<>(Command.BATTLE, "Battle"),
                    new AbstractMap.SimpleEntry<>(Command.TALK, "Talk"),
                    new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interaction")));
        else
            menu.addAll(Arrays.asList(
                    new AbstractMap.SimpleEntry<>(Command.ATTACK, "Attack"),
                    new AbstractMap.SimpleEntry<>(Command.DEFEND, "Defend"),
                    new AbstractMap.SimpleEntry<>(Command.MAGIC, "Magic"),
                    new AbstractMap.SimpleEntry<>(Command.USE_ITEM, "Use Item"),
                    new AbstractMap.SimpleEntry<>(Command.RUN, "Run")));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.TALK)
            messages.add("\n" + npc.draw() + " " + npc.getName() + ": " + npc.getDialog());
        else if (command == Command.LOOK)
            messages.add("\nYou see: " + npc.getDescription());
        else if (command == Command.STOP_INTERACTION) {
            messages.add("\nYou stopped interacting with " + npc.getName() + ".");
            Interface.remove(this);
            Interface.add(player);
        }

        return messages;
    }
}