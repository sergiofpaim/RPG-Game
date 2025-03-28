package rpg.models;

import java.util.List;
import java.util.Random;

import rpg.infrastructure.GameModel;
import rpg.infrastructure.ValidationResult;

public class Character extends GameModel {
    private String name;
    private int healthPoints;
    private int currentHealthPoints;
    private int attack;
    private int defense;
    private int magic;
    private int speed;
    private List<Item> inventory;

    public Character() {
    }

    public Character(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory) {
        this.setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.name = name;
        this.healthPoints = health;
        this.currentHealthPoints = currentHealth;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;
        this.speed = speed;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagic() {
        return magic;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealthPoints(int health) {
        this.healthPoints = health;
    }

    public void setCurrentHealthPoints(int currentHealth) {
        this.currentHealthPoints = currentHealth;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    @Override
    public ValidationResult validate() {
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }
}
