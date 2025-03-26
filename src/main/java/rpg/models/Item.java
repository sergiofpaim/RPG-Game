package rpg.models;

import rpg.game.items.ItemType;

public class Item {
    private String id;
    private String name;
    private String description;
    private int damage;
    private int cure;
    private ItemType type;

    public Item() {
    }

    public Item(String name, String description, int damage, int cure, ItemType type) {
        this.id = String.valueOf((int) (Math.random() * 1000) + 1);
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.cure = cure;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

    public int getCure() {
        return cure;
    }

    public ItemType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCure(int cure) {
        this.cure = cure;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}