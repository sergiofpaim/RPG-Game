package rpg.things.player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rpg.Interface;
import rpg.interactions.ItemInteraction;
import rpg.interactions.NPCInteraction;
import rpg.interfaces.IInteractive;
import rpg.interfaces.IThing;
import rpg.things.Item;
import rpg.things.NPC;
import rpg.types.Command;

public class Contact implements IInteractive {

    private Player player;
    private List<IThing> things = new ArrayList<>();

    public Contact() {

    }

    public Contact(Player player) {
        this.player = player;
    }

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
        List<String> messages = new ArrayList<>();

        if (command == Command.STOP_INTERACTION) {
            Interface.remove(this);
        }

        else if (command.getKey().matches("\\d+")) {
            processMapInteractionSelection(command);
        }

        return messages;
    }

    public List<String> showSurroundings() {
        List<String> messages = new ArrayList<>();

        if (things.size() > 0) {
            messages.add("\nYou are next to the following things, pick one to interact with:\n");

            for (IThing thing : things)
                messages.add(thing.draw() + " " + thing.getName());

        } else
            messages.add("\nThere is nothing here to interact with.");

        return messages;
    }

    private void processMapInteractionSelection(Command command) {

        int index = Integer.parseInt(command.getKey()) - 1;

        IThing selectedThing = things.get(index);

        if (selectedThing instanceof NPC) {
            NPCInteraction interaction = new NPCInteraction(player, (NPC) selectedThing);
            Interface.add(interaction);
        }

        else if (selectedThing instanceof Item) {
            ItemInteraction interaction = new ItemInteraction(player, (Item) selectedThing);
            Interface.add(interaction);
        }
    }

    public List<IThing> getThings() {
        return things;
    }

    public void setThings(List<IThing> things) {
        this.things = things;
    }
}
