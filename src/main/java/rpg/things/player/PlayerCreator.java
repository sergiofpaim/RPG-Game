package rpg.things.player;

import java.util.ArrayList;
import java.util.List;

import rpg.RPGGame;
import rpg.things.Item;
import rpg.things.Position;
import rpg.types.ItemType;

public class PlayerCreator {
    public static Player createPlayer() {
        int points = 10;

        System.out.println("\nEnter your character's name: ");
        String name = RPGGame.scan.nextLine();

        int attack = setAttributes("Attack", points);
        points -= attack;

        int defense = setAttributes("Defense", points);
        points -= defense;

        int magic = setAttributes("Magic", points);
        points -= magic;

        int speed = points + 1;

        System.out.println("Speed is automatically set to: " + speed);

        List<Item> inventory = getInitialItems();

        return new Player(name, 7, 7, attack, defense + 7, magic, speed, inventory, new Position(), 0, 1);
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
        int value = -1;
        do {
            System.out.println("Enter " + statName + " (1-3, remaining points: " + availablePoints + "): ");

            if (RPGGame.scan.hasNextInt()) {
                value = RPGGame.scan.nextInt();
                if (value < 1 || value > 3) {
                    System.out.println("Invalid input! " + statName + " must be between 1 and 3.");
                } else if (value > availablePoints) {
                    System.out.println("Not enough points! You only have " + availablePoints + " remaining.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                RPGGame.scan.next();
            }
        } while (value < 1 || value > 3 || value > availablePoints);

        return value;
    }

}
