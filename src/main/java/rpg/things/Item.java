package rpg.things;

import rpg.interfaces.IThing;
import rpg.types.ItemType;

public class Item implements IThing {
    private String id;
    private String name;
    private String description;
    private int damage;
    private int cure;
    private int defense;
    private ItemType type;
    private int positionX;
    private int positionY;
    private boolean isCarried;

    public Item() {
    }

    public Item(String name, String description, int damage, int cure, int defense, ItemType type) {
        this.id = String.valueOf((int) (Math.random() * 1000) + 1);
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.cure = cure;
        this.defense = defense;
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

    public int getDefense() {
        return defense;
    }

    public ItemType getType() {
        return type;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean isCarried() {
        return isCarried;
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

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setCarried(boolean isCarried) {
        this.isCarried = isCarried;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Init'");
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
    public String draw() {
        if (this.type == ItemType.WEAPON) {
            return "\u2694";
        } else if (this.type == ItemType.ARMOR) {
            return "\u26E8";
        } else if (this.type == ItemType.POTION) {
            return "\uD83E\uDDEA";
        } else if (this.type == ItemType.ACCESSORY) {
            return "\uD83D\uDCE2";
        } else {
            return "\u2753";
        }
    }

    @Override
    public String showStats() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayProfile'");
    }
}