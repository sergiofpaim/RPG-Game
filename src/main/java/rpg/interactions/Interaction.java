package rpg.interactions;

import java.util.Map.Entry;
import rpg.interfaces.IInteractive;
import rpg.things.player.Player;
import rpg.types.Command;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

public class Interaction implements IInteractive {
    protected Player player;

    public Interaction(Player player) {
        this.player = player;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.LOOK, "Look"));
    }

    @Override
    public List<String> processCommand(Command command) {
        return null;
    }
}