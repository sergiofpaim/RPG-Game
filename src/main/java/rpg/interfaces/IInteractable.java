package rpg.interfaces;

import java.util.List;
import java.util.Map;

import rpg.types.Command;

public interface IInteractable {

    public List<Map.Entry<Command, String>> retrieveMenu();

    public List<String> processCommand(Command command);
}