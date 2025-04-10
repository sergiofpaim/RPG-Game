package rpg.things.player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import rpg.Game;
import rpg.Interface;
import rpg.interfaces.IInteractive;
import rpg.things.Character;
import rpg.things.Item;
import rpg.things.Position;
import rpg.types.Command;

public class Player extends Character implements IInteractive {
    protected Contact contact = new Contact(this);
    private int experience;
    private int level;

    public Player() {
    }

    public Player(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, Position position, int experience, int level) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, position);
        this.experience = experience;
        this.level = level;
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

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGame(Game game) {
        this.game = game;

        inventory.setGame(game);
        inventory.setPlayer(this);
    }

    public void addToInventory(Item item) {
        item.pick();
        inventory.addItem(item);
    }

    public void dropFromInventory(Load load) {
        inventory.dropLoad(load);
    }

    private void move(Command movement) {
        int currentX = this.position.getX();
        int currentY = this.position.getY();
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

        if (this.game.checkMovement(newX, newY)) {
            this.position.setX(newX);
            this.position.setY(newY);
        }
    }

    @Override
    public String draw() {
        return "\uD83C\uDFC7";
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.UP, "Move Up"),
                new AbstractMap.SimpleEntry<>(Command.DOWN, "Move Down"),
                new AbstractMap.SimpleEntry<>(Command.LEFT, "Move Left"),
                new AbstractMap.SimpleEntry<>(Command.RIGHT, "Move Right"),
                new AbstractMap.SimpleEntry<>(Command.INVENTORY, "Show Inventory"),
                new AbstractMap.SimpleEntry<>(Command.SHOW_STATS, "Show Stats"),
                new AbstractMap.SimpleEntry<>(Command.INTERACT, "Interact"));
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();

        if (command == Command.INVENTORY) {
            Interface.add(inventory);
            messages.addAll(inventory.showInventory());
        } else if (command == Command.INTERACT) {
            contact.setThings(game.checkSurroundings(this.position.getX(), this.position.getY()));
            Interface.add(contact);

            messages.addAll(contact.showSurroundings());
        } else if (command == Command.SHOW_STATS) {
            messages.add("\n--- Player Stats ---\n");
            messages.add(this.showStats());
            messages.add("---------------------\n");
        } else {
            move(command);
        }

        return messages;
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

    public void destroy() {
        game.remove(this);
    }

    public void useFromInventory(Load load) {
        inventory.useLoad(load);
    }
}