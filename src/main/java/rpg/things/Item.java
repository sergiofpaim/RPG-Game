package rpg.things;

import java.util.Random;

import rpg.Game;
import rpg.interfaces.IThing;
import rpg.templateData.ItemData;
import rpg.types.ItemType;

public class Item implements IThing {
    private String id;
    private String name;
    private String description;
    private int damage;
    private int cure;
    private int defense;
    private ItemType type;
    private Position position = new Position();
    private Game game;
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

    public Item(Game game) {
        Random random = new Random();

        Item randomItem = ItemData.defaultItems[random.nextInt(ItemData.defaultItems.length)];

        this.id = randomItem.getId();
        this.name = randomItem.getName();
        this.description = randomItem.getDescription();
        this.damage = randomItem.getDamage();
        this.cure = randomItem.getCure();
        this.defense = randomItem.getDefense();
        this.type = randomItem.getType();
        this.setPosition(new Position(random.nextInt(game.getMapHeight()), random.nextInt(game.getMapWidth())));
        this.setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.setCarried(false);
        this.setGame(game);
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

    public Position getPosition() {
        return position;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void drop() {
        this.setCarried(false);
        this.game.add(this);
    }

    public void pick() {
        this.setCarried(true);
        this.game.remove(this);
    }

    public void setCarried(boolean isCarried) {
        this.isCarried = isCarried;
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
}