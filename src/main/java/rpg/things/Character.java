package rpg.things;

import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rpg.Game;
import rpg.interfaces.IThing;
import rpg.things.player.Inventory;

public abstract class Character extends Thing implements IThing {
    protected String name;
    protected int healthPoints;
    protected int currentHealthPoints;
    protected int attack;
    protected int defense;
    protected int magic;
    protected int speed;
    protected Inventory inventory = new Inventory(this);
    protected Position position;
    protected String description;
    protected Game game;

    public Character() {
    }

    public Character(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, Position position) {
        this.setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.name = name;
        this.healthPoints = health;
        this.currentHealthPoints = currentHealth;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;
        this.speed = speed;
        this.inventory.setItems(inventory);
        this.position = position;
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

    public Inventory getInventory() {
        return inventory;
    }

    public Position getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealthPoints(int health) {
        this.healthPoints = health;
    }

    public void setCurrentHealthPoints(int currentHealth) {
        this.currentHealthPoints = Math.min(currentHealth, this.healthPoints);
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonIgnore
    public boolean isOnMapLimits() {
        if (position.getY() == game.getMapHeight() - 1)
            return true;

        return false;
    }

    public String showStats() {
        return "Total Health: " +
                this.getHealthPoints() + "\nCurrent Health: "
                + this.getCurrentHealthPoints() + "\nAttack: "
                + this.getAttack()
                + "\nDefense: " + this.getDefense() + "\nMagic: "
                + this.getMagic()
                + "\nSpeed: " + this.getSpeed();
    }
}