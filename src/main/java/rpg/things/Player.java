package rpg.things;

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

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Init'");
    }

    @Override
    public String draw() {
        return "\uD83C\uDFC7";
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
    public String getStats() {
        return "Total Health: " +
                this.getHealthPoints() + " - Current Health: "
                + this.getCurrentHealthPoints() + " - Attack: "
                + this.getAttack()
                + " - Defense: " + this.getDefense() + " - Magic: "
                + this.getMagic()
                + " - Speed: " + this.getSpeed() + "\n";
    }
}
