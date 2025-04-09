package rpg.things.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rpg.interfaces.IInteractive;
import rpg.things.Item;
import rpg.types.Command;

public class Inventory implements IInteractive {

    private List<Item> items = new ArrayList<Item>();

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveMenu'");
    }

    @Override
    public List<String> processCommand(Command command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processCommand'");
    }

}
