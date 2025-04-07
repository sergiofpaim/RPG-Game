package rpg.things;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Game;
import rpg.interfaces.IInteractable;
import rpg.types.Command;

public class Player extends Character implements IInteractable {
    private int experience;
    private int level;
    private Game game;

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

    private String tryInteract() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tryInteract'");
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
    public List<Entry<Command, String>> getMenu() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.UP, "Move Up"),
                new AbstractMap.SimpleEntry<>(Command.DOWN, "Move Down"),
                new AbstractMap.SimpleEntry<>(Command.LEFT, "Move Left"),
                new AbstractMap.SimpleEntry<>(Command.RIGHT, "Move Right"),
                new AbstractMap.SimpleEntry<>(Command.INVENTORY, "Show Inventory"),
                new AbstractMap.SimpleEntry<>(Command.INTERACT, "Interact"));
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.INVENTORY)
            messages.add(showInventory());
        else if (command == Command.INTERACT)
            messages.add(tryInteract());
        else
            move(command);

        return messages;
    }
}
