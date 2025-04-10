package rpg.templateData;

import rpg.things.Item;
import rpg.types.ItemType;

public class ItemData {
    public static final Item[] defaultItems = {
            new Item("Potion", "Heals 50 HP", 0, 15, 0, ItemType.USABLE, false),
            new Item("Shield", "A sturdy shield", 0, 0, 10, ItemType.SHIELD, false),
            new Item("Armor", "Protects your body", 0, 0, 15, ItemType.ARMOR, false),
            new Item("Bomb", "Deals damage to all enemies", 8, 0, 0, ItemType.USABLE, false),
            new Item("Sword", "A sharp blade", 10, 0, 0, ItemType.WEAPON, false),
            new Item("Staff", "A magical staff", 5, 0, 5, ItemType.WEAPON, false),
            new Item("Bow", "A long-range weapon", 8, 0, 0, ItemType.WEAPON, false),
            new Item("Amulet", "Increases health", 0, 0, 5, ItemType.ACCESSORY, false),
            new Item("Ring", "Increases magic", 0, 0, 3, ItemType.ACCESSORY, false),
    };
}