package rpg.interactions;

import java.util.Map.Entry;
import rpg.things.NPC;
import rpg.things.Player;
import rpg.types.Command;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

public class NPCInteraction extends Interaction {
    private NPC npc;

    public NPCInteraction(Player player, NPC npc) {
        super(player);
        this.npc = npc;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = super.retrieveMenu();
        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.ATTACK, "Attack"),
                new AbstractMap.SimpleEntry<>(Command.DEFEND, "Defend"),
                new AbstractMap.SimpleEntry<>(Command.TALK, "Talk"),
                new AbstractMap.SimpleEntry<>(Command.RUN, "Run")));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processCommand'");
    }
}