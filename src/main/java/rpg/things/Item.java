package rpg.things;

import java.util.Random;

import rpg.Game;
import rpg.Runner;
import rpg.interfaces.IThing;
import rpg.templateData.ItemData;
import rpg.types.ItemType;

public class Item implements IThing {
    protected String id;
    protected String name;
    protected String description;
    protected int attack;
    protected int cure;
    protected int defense;
    protected ItemType type;
    protected Position position = new Position();
    protected boolean isCarried;
    protected Game game;

    public Item() {
    }

    public Item(String name, String description, int attack, int cure, int defense, ItemType type, boolean isCarried) {
        this.id = String.valueOf((int) (Math.random() * 1000) + 1);
        this.name = name;
        this.description = description;
        this.attack = attack;
        this.cure = cure;
        this.defense = defense;
        this.type = type;
        this.isCarried = isCarried;
    }

    public Item(Game game) {
        Random random = new Random();
        Position newPosition;

        do {
            newPosition = new Position(random.nextInt(game.getMap().getWidth()),
                    random.nextInt(game.getMap().getHeight()));
        } while (!game.checkPositionAvailable(newPosition));

        Item randomItem = ItemData.defaultItems[random.nextInt(ItemData.defaultItems.length)];

        this.id = randomItem.getId();
        this.name = randomItem.getName();
        this.description = randomItem.getDescription();
        this.attack = randomItem.getAttack() + random.nextInt(Runner.player.getLevel());
        this.cure = randomItem.getCure() + random.nextInt(Runner.player.getLevel());
        this.defense = randomItem.getDefense() + random.nextInt(Runner.player.getLevel());
        this.type = randomItem.getType();
        this.setPosition(newPosition);
        this.setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.setIsCarried(false);
        this.setGame(game);
    }

    public Item(String name, String description, ItemType type, Game game) {
        Random random = new Random();
        Position newPosition;

        do {
            newPosition = new Position(random.nextInt(game.getMap().getWidth()),
                    random.nextInt(game.getMap().getHeight()));
        } while (!game.checkPositionAvailable(newPosition) && newPosition == new Position(0, 0));

        Item randomItem = ItemData.defaultItems[random.nextInt(ItemData.defaultItems.length)];

        this.id = randomItem.getId();
        this.name = name;
        this.description = description;
        this.attack = 0;
        this.cure = 0;
        this.defense = 0;
        this.type = type;
        this.setPosition(newPosition);
        this.setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.setIsCarried(false);
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

    public int getAttack() {
        return attack;
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

    public boolean getIsCarried() {
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

    public void setAttack(int attack) {
        this.attack = attack;
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
        this.setIsCarried(false);
        this.game.add(this);
    }

    public void pick() {
        this.setIsCarried(true);
        this.game.remove(this);
    }

    public void setIsCarried(boolean isCarried) {
        this.isCarried = isCarried;
    }

    @Override
    public String draw() {
        if (this.type == ItemType.WEAPON) {
            return " \u2694";
        } else if (this.type == ItemType.SHIELD) {
            return " \u26E8";
        } else if (this.type == ItemType.ARMOR) {
            return "\uD83E\uDDE5";
        } else if (this.type == ItemType.USABLE) {
            return "\uD83D\uDD2E";
        } else if (this.type == ItemType.ACCESSORY) {
            return "\uD83D\uDC8E";
        } else if (this.type == ItemType.DOOR) {
            return "\uD83D\uDEAA";
        } else if (this.type == ItemType.BAG)
            return "\uD83D\uDC5C";
        else {
            return " \u2753";
        }
    }

    @Override
    public String showStats() {
        return "Attack: "
                + this.attack + "\nDefense: "
                + this.defense
                + "\nCure: " + this.cure;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Item other = (Item) obj;

        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}