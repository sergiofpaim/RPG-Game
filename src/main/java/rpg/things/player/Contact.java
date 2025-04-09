package rpg.things.player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rpg.interfaces.IInteractive;
import rpg.interfaces.IThing;
import rpg.types.Command;

public class Contact implements IInteractive {

    private List<IThing> things = new ArrayList<>();

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>();
        for (int i = 0; i < things.size(); i++) {
            menu.add(new AbstractMap.SimpleEntry<>(
                    Command.fromKey(String.valueOf(i + 1)),
                    "Interact with " + (things.get(i).getName())));
        }
        menu.add(new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacting"));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processCommand'");
    }

}
