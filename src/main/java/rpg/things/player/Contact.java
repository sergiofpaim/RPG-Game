package rpg.things.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rpg.interfaces.IInteractable;
import rpg.interfaces.IThing;
import rpg.types.Command;

public class Contact implements IInteractable {

    private List<IThing> things = new ArrayList<>();

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
