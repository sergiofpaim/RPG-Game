package rpg.models;

import java.util.List;

public class Player extends Character {
    private int experience;
    private int level;

    public Player(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, int positionX, int positionY, int experience, int level) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, positionX, positionY);
        this.experience = experience;
        this.level = level;
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
}
