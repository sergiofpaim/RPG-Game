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
    private static final int HEALTH_UPGRADE = 5;
    private static final int EXPERIENCE_UPGRADE = 10;
    protected Contact contact = new Contact(this);
    private int level;

    private int upgradePoints = 0;

    public Player() {
    }

    public Player(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, Position position, int experience, int level) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, position, experience);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGame(Game game) {
        this.game = game;

        inventory.setGame(game);
        inventory.setCharacter(this);
    }

    public void setUpgradePoints(int totalPoints) {
        this.upgradePoints = totalPoints;
    }

    public List<String> rewardExperience(int experience) {
        List<String> messages = new ArrayList<>();

        messages.add("\nYou got " + experience + " points of experience");
        setExperience(getExperience() + experience);

        if (this.getExperience() >= getLevel() * EXPERIENCE_UPGRADE)
            messages.addAll(levelUp());

        return messages;
    }

    private List<String> levelUp() {
        List<String> messages = new ArrayList<>();

        int oldLevel = getLevel();

        setLevel(getLevel() + (getExperience() / (getLevel() * EXPERIENCE_UPGRADE)));
        setExperience(getExperience() % getLevel() * EXPERIENCE_UPGRADE);

        setHealthPoints(getHealthPoints() + HEALTH_UPGRADE);
        setCurrentHealthPoints(getCurrentHealthPoints() + HEALTH_UPGRADE);

        messages.add("And leveled up to level " + getLevel());
        messages.add("Select an attribute to increase upgrade\n");

        upgradePoints = getLevel() - oldLevel;

        return messages;
    }

    public void addToInventory(Item item) {
        item.pick();
        inventory.addItem(item);
    }

    public void dropFromInventory(Load load) {
        Position newPosition = null;

        for (int[] offset : rpg.Map.offsets) {
            newPosition = new Position(
                    getPosition().getCol() + offset[0],
                    getPosition().getRow() + offset[1]);

            if (game.checkPositionAvailable(newPosition)) {
                break;
            }
        }

        if (newPosition != null) {
            inventory.dropLoad(load, newPosition);
        }
    }

    private void move(Command movement) {
        int currentCol = this.position.getCol();
        int currentRow = this.position.getRow();

        int newCol = currentCol;
        int newRow = currentRow;

        if (movement == Command.UP)
            newRow = currentRow - 1;
        else if (movement == Command.DOWN)
            newRow = currentRow + 1;
        else if (movement == Command.LEFT)
            newCol = currentCol - 1;
        else if (movement == Command.RIGHT)
            newCol = currentCol + 1;

        if (this.game.checkMovement(newCol, newRow)) {
            this.position.setCol(newCol);
            this.position.setRow(newRow);
        }
    }

    @Override
    public String draw() {
        return "\uD83C\uDFC7";
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        if (upgradePoints > 0) {
            return Arrays.asList(
                    new AbstractMap.SimpleEntry<>(Command.UPGRADE_ATTACK, "Upgrade Attack"),
                    new AbstractMap.SimpleEntry<>(Command.UPGRADE_DEFENSE, "Upgrade Defense"),
                    new AbstractMap.SimpleEntry<>(Command.UPGRADE_MAGIC, "Upgrade Magic"),
                    new AbstractMap.SimpleEntry<>(Command.UPGRADE_SPEED, "Upgrade Speed"));
        }
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
            contact.setThings(game.checkSurroundings(this.position.getCol(), this.position.getRow()));
            Interface.add(contact);
            messages.addAll(contact.showSurroundings());
        } else if (command == Command.SHOW_STATS) {
            messages.add("\n──── Player Stats ────");
            messages.add(this.showStats());
        } else if (command.getCommand().startsWith("UPGRADE")) {
            messages.addAll(upgradeAttribute(command));
            upgradePoints--;
        } else {
            move(command);
        }

        return messages;
    }

    private List<String> upgradeAttribute(Command command) {
        List<String> messages = new ArrayList<>();
        if (command == Command.UPGRADE_ATTACK) {
            setAttack(getAttack() + 1);
            messages.add("You upgraded attack by 1 point");
        }
        if (command == Command.UPGRADE_DEFENSE) {
            setDefense(getDefense() + 1);
            messages.add("You upgraded defense by 1 point");
        }
        if (command == Command.UPGRADE_MAGIC) {
            setMagic(getMagic() + 1);
            messages.add("You upgraded magic by 1 point");
        }
        if (command == Command.UPGRADE_SPEED) {
            setSpeed(getSpeed() + 1);
            messages.add("You upgraded speed by 1 point");
        }

        return messages;
    }

    public void destroy() {
        game.remove(this);
    }

    public void useFromInventory(Load load) {
        inventory.useLoad(load);
    }

    @Override
    public String retrieveLabel() {
        return "Player";
    }
}