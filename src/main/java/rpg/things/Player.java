package rpg.things;

import java.util.List;

import rpg.Game;
import rpg.types.PlayerMovement;

public class Player extends Character {
    private int experience;
    private int level;
    private Game game;

    public Player(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, int positionX, int positionY, int experience, int level) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, positionX, positionY);
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

    public String processInput(String key) {

        String message = "\n";

        switch (key) {
            case "w":
                move(PlayerMovement.UP);
                break;
            case "s":
                move(PlayerMovement.DOWN);
                break;
            case "a":
                move(PlayerMovement.LEFT);
                break;
            case "d":
                move(PlayerMovement.RIGHT);
                break;
            case "m":
                message = showInventory();
            case "i":
                break;
            case "b":
                break;

            default:
                message = "\nInvalid action!";
        }
        return message;
    }

    public void move(PlayerMovement movement) {
        int currentX = this.getPositionX();
        int currentY = this.getPositionY();
        int newX = currentX;
        int newY = currentY;

        switch (movement) {
            case UP:
                newX = currentX - 1;
                break;
            case DOWN:
                newX = currentX + 1;
                break;
            case LEFT:
                newY = currentY - 1;
                break;
            case RIGHT:
                newY = currentY + 1;
                break;
        }
        if (this.game.checkMovement(newX, newY).getKey()) {
            this.setPositionX(newX);
            this.setPositionY(newY);
        }
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Init'");
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Destroy'");
    }

    @Override
    public void startInteraction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'StartInteraction'");
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
}
