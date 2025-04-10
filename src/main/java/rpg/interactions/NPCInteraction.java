package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Interface;
import rpg.things.NPC;
import rpg.things.player.Player;
import rpg.types.Command;

public class NPCInteraction extends Interaction {
    private NPC npc;

    public NPCInteraction(Player player, NPC npc) {
        super(player);
        this.npc = npc;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());

        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.BATTLE, "Battle"),
                new AbstractMap.SimpleEntry<>(Command.TALK, "Talk"),
                new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interaction")));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.LOOK)
            messages.add("\nYou see: " + npc.getDescription());
        else if (command == Command.TALK)
            messages.add("\n" + npc.draw() + " " + npc.getName() + ": " + npc.getDialog());
        else if (command == Command.STOP_INTERACTION) {
            messages.add("\nYou stopped interacting with " + npc.getName() + ".");
            Interface.remove(this);
        } else if (command == Command.BATTLE) {
            messages.add("\nYou started a battle with " + npc.getName() + "!\n" +
                    "\nYour status are: " + player.showStats() +
                    "\nAnd the enemy's status are: " + npc.showStats());
            Interface.add(new BattleInteraction(player, npc));
            Interface.remove(this);
        }

        return messages;
    }
}