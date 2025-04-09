package rpg.things;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import rpg.Game;
import rpg.Interface;
import rpg.interactions.InventoryInteraction;
import rpg.interactions.ItemInteraction;
import rpg.interactions.NPCInteraction;
import rpg.interfaces.IInteractable;
import rpg.interfaces.IThing;
import rpg.types.Command;

public class Player extends Character implements IInteractable {
    protected List<Item> equipedItems = null;
    private int experience;
    private int level;
    private List<IThing> interactableThings = new ArrayList<IThing>();
    private boolean interactingWithMap = false;
    private boolean interactingWithInventory = false;

    public Player() {
    }

    public Player(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, List<Item> equipedItems, Position position, int experience, int level) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, position);
        this.equipedItems = equipedItems;
        this.experience = experience;
        this.level = level;
    }

    public List<Item> getEquipedItems() {
        return equipedItems;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public void setInteractingWithInventory(boolean interacting) {
        this.interactingWithInventory = interacting;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGame(Game game) {
        this.game = game;

        for (Item item : inventory) {
            item.setGame(game);
        }
        for (Item item : equipedItems) {
            item.setGame(game);
        }
    }

    public void setInteractingWithMap(boolean interacting) {
        this.interactingWithMap = interacting;
    }

    public void setEquipedItems(Item item) {
        item.pick();
        this.equipedItems.add(item);
    }

    public void addToInventory(Item item) {
        item.pick();
        this.inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        this.inventory.remove(item);
        this.equipedItems.remove(item);

        item.setPosition(new Position(this.position.getX(), this.position.getY() + 1));
        item.drop();
    }

    public void addEquipedItem(Item equiped) {
        if (checkEquiped(equiped))
            this.equipedItems.add(equiped);
    }

    private boolean checkEquiped(Item item) {
        Optional<Item> existingItem = this.equipedItems.stream()
                .filter(o -> o.getType().equals(item.getType()))
                .findFirst();

        if (existingItem != null)
            return false;

        return true;
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

    public String showStats() {
        return "Total Health: " +
                this.getHealthPoints() + " - Current Health: "
                + this.getCurrentHealthPoints() + " - Attack: "
                + this.getAttack()
                + " - Defense: " + this.getDefense() + " - Magic: "
                + this.getMagic()
                + " - Speed: " + this.getSpeed() + "\n";
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        if (interactingWithMap) {
            List<Entry<Command, String>> menu = new ArrayList<>();
            for (int i = 0; i < interactableThings.size(); i++) {
                menu.add(new AbstractMap.SimpleEntry<>(
                        Command.fromKey(String.valueOf(i + 1)),
                        "Interact with " + (interactableThings.get(i).getName())));
            }
            menu.add(new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacting"));
            return menu;
        }

        else if (interactingWithInventory) {
            List<Entry<Command, String>> menu = new ArrayList<>();
            for (int i = 0; i < inventory.size(); i++) {
                menu.add(new AbstractMap.SimpleEntry<>(
                        Command.fromKey(String.valueOf(i + 1)),
                        (inventory.get(i).getName())));
            }
            menu.add(new AbstractMap.SimpleEntry<>(Command.STOP_INTERACTION, "Stop Interacting"));
            return menu;
        }

        else {
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

        if (command == Command.INVENTORY) {
            if (inventory.size() > 0) {
                interactingWithInventory = true;
                messages.add("\nYou have got these things in your inventory:\n");
                messages.add("\n--- Inventory ---\n");

                for (Item item : inventory) {
                    if (equipedItems.contains(item)) {
                        messages.add(item.draw() + " " + item.getName() +
                                " - equiped\n");
                    } else
                        messages.add(
                                item.draw() + " " + item.getName());
                }
                messages.add("------------------\n");
            } else
                messages.add("\nThere is nothing in you inventory.");
        }

        else if (command == Command.INTERACT) {
            if (interactableThings.size() > 0) {
                interactingWithMap = true;
                messages.add("\nYou are next to the following things, pick one to interact with:\n");

                for (IThing thing : interactableThings) {
                    messages.add(
                            thing.draw() + " " + thing.getName());
                }
            } else
                messages.add("\nThere is nothing here to interact with.");

        }

        else if (command == Command.STOP_INTERACTION) {
            interactingWithMap = false;
            interactingWithInventory = false;
        }

        else if (interactingWithMap && command.getKey() != null && command.getKey().matches("\\d+")) {
            processMapInteractionSelection(command);
        }

        else if (interactingWithInventory && command.getKey() != null && command.getKey().matches("\\d+")) {
            processInventoryInteractionSelection(command);
        }

        else {
            move(command);
        }

        return messages;
    }

    private void processInventoryInteractionSelection(Command command) {
        int index = Integer.parseInt(command.getKey()) - 1;

        if (index >= 0 && index < inventory.size()) {

            IThing selectedItem = inventory.get(index);

            InventoryInteraction interaction = new InventoryInteraction(this, (Item) selectedItem);
            Interface.add(interaction);
        }
    }

    private void processMapInteractionSelection(Command command) {

        int index = Integer.parseInt(command.getKey()) - 1;

        if (index >= 0 && index < interactableThings.size()) {

            IThing selectedThing = interactableThings.get(index);

            if (selectedThing instanceof NPC) {
                NPCInteraction interaction = new NPCInteraction(this, (NPC) selectedThing);
                Interface.add(interaction);
            }

            else if (selectedThing instanceof Item) {
                ItemInteraction interaction = new ItemInteraction(this, (Item) selectedThing);
                Interface.add(interaction);
            }
        }
    }
}