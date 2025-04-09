package rpg.things.player;

import java.util.ArrayList;
import java.util.List;
import rpg.RPGGame;
import rpg.things.Item;
import rpg.things.Position;
import rpg.types.ItemType;

public class PlayerCreator {
    public static Player createPlayer() {
        int points = 30;

        System.out.println("\nEnter your character's name: ");
        String name = RPGGame.scan.nextLine();

        int health = setAttributes("Health", points);
        points -= health;

        while (health == 0) {
            System.out.println("Health cannot be 0. Please allocate at least 1 point to Health.");
            health = setAttributes("Health", points);
            points -= health;
        }

        int attack = setAttributes("Attack", points);
        points -= attack;

        int defense = setAttributes("Defense", points);
        points -= defense;

        int magic = setAttributes("Magic", points);
        points -= magic;

        int speed = points;
        if (speed > 10) {
            speed = 10;
        }
        System.out.println("Speed is automatically set to: " + speed);

        List<Item> inventory = getInitialItems();

        return new Player(name, health, health, attack, defense, magic, speed, inventory, new ArrayList<>(),
                new Position(), 0,
                1);
    }

    private static List<Item> getInitialItems() {
        List<Item> inventory = new ArrayList<>();

        System.out.println("Pick 1 of the following items: ");
        System.out.println("1. Sword (Damage +2)");
        System.out.println("2. Shield (Defense +2)");
        System.out.println("3. Staff (Magic +2)");

        int choice = RPGGame.scan.nextInt();

        if (choice == 1) {
            System.out.print("You got a sword");
            inventory.add(new Item("Sword", "A sharp sword,", 2, 0, 0, ItemType.WEAPON, true));
        } else if (choice == 2) {
            System.out.print("You got a shield");
            inventory.add(new Item("Shield", "A sturdy shield,", 0, 0, 2, ItemType.SHIELD, true));
        } else if (choice == 3) {
            System.out.print("You got a staff");
            inventory.add(new Item("Staff", "A magical staff,", 0, 0, 0, ItemType.WEAPON, true));
        } else {
            System.out.print("Invalid answer! You got a sword by default,");
            inventory.add(new Item("Sword", "A sharp sword,", 2, 0, 0, ItemType.WEAPON, true));
        }
        System.out.print(" clothes, and a health potion.\n");

        inventory.add(new Item("Boots", "A pair of sturdy boots", 0, 0, 0, ItemType.ACCESSORY, true));
        inventory.add(new Item("Clothes", "A set of comfortable clothes", 0, 0, 0, ItemType.ACCESSORY, true));
        inventory.add(new Item("Health Potion", "A potion that restores health", 0, 10, 0, ItemType.USABLE, true));

        return inventory;
    }

    private static int setAttributes(String statName, int availablePoints) {
        int value;
        do {
            System.out.println("Enter " + statName + " (1-10, remaining points: " + availablePoints + "): ");
            value = RPGGame.scan.nextInt();

            if (value < 0 || value > 10) {
                System.out.println("Invalid input! " + statName + " must be between 1 and 10.");
            } else if (value > availablePoints) {
                System.out.println("Not enough points! You only have " + availablePoints + " remaining.");
            }
        } while (value < 0 || value > 10 || value > availablePoints);

        return value;
    }
}
