package rpg.things;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Game;
import rpg.interactions.ItemInteraction;
import rpg.interactions.NPCInteraction;
import rpg.interfaces.IInteractable;
import rpg.interfaces.IThing;
import rpg.types.Command;

public class Player extends Character implements IInteractable {
    private int experience;
    private int level;
    private Game game;
    private List<IThing> interactableThings = new ArrayList<IThing>();
    private boolean interacting = false;

    public Player(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, Position position, int experience, int level) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, position);
        this.experience = experience;
        this.level = level;
    }

    public Player() {
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void move(Command movement) {
        int currentX = this.position.x;
        int currentY = this.position.y;
        int newX = currentX;
        int newY = currentY;

        if (movement == Command.UP)
            newY = currentY - 1;
        else if (movement == Command.DOWN)
            newY = currentY + 1;
        else if (movement == Command.LEFT)
            newX = currentX - 1;
        else if (movement == Command.RIGHT)
            newX = currentX + 1;

        if (this.game.checkMovement(newX, newY).getKey()) {
            this.position.setX(newX);
            this.position.setY(newY);

            interactableThings = game.checkMovement(newX, newY).getValue();
        }
    }

    @Override
    public String draw() {
        return "\uD83C\uDFC7";
    }

    @Override
    public String showStats() {
        return "Total Health: " +
                this.getHealthPoints() + " - Current Health: "
                + this.getCurrentHealthPoints() + " - Attack: "
                + this.getAttack()
                + " - Defense: " + this.getDefense() + " - Magic: "
                + this.getMagic()
                + " - Speed: " + this.getSpeed() + "\n";
    }

    private String showInventory() {
        StringBuilder inventory = new StringBuilder();
        inventory.append("\n--- Inventory ---\n");
        inventory.append("You have " + this.getInventory().size() + " items:\n");
        for (Item item : this.getInventory()) {
            inventory.append(item.getName() + " - " + item.getDescription() + "\n");
        }
        inventory.append("------------------\n");
        return inventory.toString();
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        if (interacting) {
            List<Entry<Command, String>> menu = new ArrayList<>();
            for (int i = 0; i < interactableThings.size(); i++) {
                menu.add(new AbstractMap.SimpleEntry<>(
                        Command.fromKey(String.valueOf(i + 1)), "Select " + (i + 1)));
            }
            menu.add(new AbstractMap.SimpleEntry<>(Command.INTERACT, "Stop Interacting"));
            return menu;
        } else {
            return Arrays.asList(
                    new AbstractMap.SimpleEntry<>(Command.UP, "Move Up"),
                    new AbstractMap.SimpleEntry<>(Command.DOWN, "Move Down"),
                    new AbstractMap.SimpleEntry<>(Command.LEFT, "Move Left"),
                    new AbstractMap.SimpleEntry<>(Command.RIGHT, "Move Right"),
                    new AbstractMap.SimpleEntry<>(Command.INVENTORY, "Show Inventory"),
                    new AbstractMap.SimpleEntry<>(Command.INTERACT, "Interact"));
        }
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.INVENTORY)
            messages.add(showInventory());
        else if (command == Command.INTERACT) {
            if (interacting) {
                interacting = false;
                messages.add("You stopped checking things to interact around you.");
            } else {
                interacting = true;
                messages.add("You are next to the following things, pick one to interact with:\n");

                for (IThing thing : interactableThings) {
                    if (thing instanceof NPC)
                        messages.add(
                                ((NPC) thing).draw() + " " + ((NPC) thing).getName() + " - "
                                        + ((NPC) thing).getDescription());
                    else if (thing instanceof Item)
                        messages.add(
                                ((Item) thing).draw() + " " + ((Item) thing).getName() + " - "
                                        + ((Item) thing).getDescription());
                }
            }
        }
        if (command == Command.SELECT_1 || command == Command.SELECT_2 || command == Command.SELECT_3
                || command == Command.SELECT_4 || command == Command.SELECT_5) {
            int index = Integer.parseInt(command.getKey()) - 1;
            if (index >= 0 && index < interactableThings.size()) {
                IThing selectedThing = interactableThings.get(index);
                if (selectedThing instanceof NPC) {
                    NPCInteraction interaction = new NPCInteraction(this, (NPC) selectedThing);
                } else if (selectedThing instanceof Item) {
                    ItemInteraction interaction = new ItemInteraction(this, (Item) selectedThing);
                }
                interacting = false;
            } else {
                messages.add("Invalid selection.");
            }
        } else {
            move(command);
        }

        return messages;
    }
}
