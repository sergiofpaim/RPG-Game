package rpg.templateData;

import rpg.things.Item;
import rpg.types.ItemType;

public class ItemData {
    public static final Item[] defaultItems = {
            new Item("Potion", "Heals 50 HP", 0, 50, 0, ItemType.POTION),
            new Item("Sword", "A sharp blade", 10, 0, 0, ItemType.WEAPON),
            new Item("Shield", "A sturdy shield", 0, 0, 10, ItemType.ARMOR),
            new Item("Staff", "A magical staff", 5, 0, 5, ItemType.WEAPON),
            new Item("Bow", "A long-range weapon", 8, 0, 0, ItemType.WEAPON),
            new Item("Helmet", "Protects your head", 0, 0, 5, ItemType.ARMOR),
            new Item("Armor", "Protects your body", 0, 0, 15, ItemType.ARMOR),
            new Item("Boots", "Increases speed", 0, 0, 2, ItemType.ARMOR),
            new Item("Ring", "Increases magic", 0, 0, 3, ItemType.ACCESSORY),
            new Item("Amulet", "Increases health", 0, 0, 5, ItemType.ACCESSORY),
            new Item("Bomb", "Deals damage to all enemies", 20, 0, 0, ItemType.WEAPON),
    };
}