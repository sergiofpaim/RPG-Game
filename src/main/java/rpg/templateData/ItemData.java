package rpg.templateData;

import rpg.things.Item;
import rpg.types.ItemType;

public class ItemData {
    public static final Item[] defaultItems = {
            new Item("Potion", "Heals 50 HP", 0, 15, 0, ItemType.USABLE, false),
            new Item("Shield", "A sturdy shield", 0, 0, 2, ItemType.SHIELD, false),
            new Item("Armor", "Protects your body", 0, 0, 5, ItemType.ARMOR, false),
            new Item("Bomb", "Deals damage to all enemies", 4, 0, 0, ItemType.USABLE, false),
            new Item("Sword", "A sharp blade", 2, 0, 0, ItemType.WEAPON, false),
            new Item("Staff", "A magical staff", 3, 0, 1, ItemType.WEAPON, false),
            new Item("Bow", "A long-range weapon", 2, 0, 0, ItemType.WEAPON, false),
            new Item("Amulet", "Increases health", 0, 0, 0, ItemType.ACCESSORY, false),
            new Item("Ring", "Increases magic", 0, 0, 0, ItemType.ACCESSORY, false),
    };
}