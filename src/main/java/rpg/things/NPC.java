package rpg.things;

import java.util.List;
import java.util.Random;

import rpg.Game;
import rpg.templateData.NPCData;
import rpg.types.NPCType;

public class NPC extends Character {
    private NPCType type;
    private String description;
    private String dialog;

    public NPC() {
    }

    public NPC(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, Position position, NPCType type, String description, String dialog) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, position);
        this.type = type;
        this.description = description;
        this.dialog = dialog;
    }

    public NPC(Game game) {
        Random random = new Random();

        name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        healthPoints = random.nextInt(10) + 3;
        currentHealthPoints = healthPoints;
        attack = random.nextInt(10) + 5;
        defense = random.nextInt(5) + 3;
        magic = random.nextInt(5) + 2;
        speed = random.nextInt(5) + 1;
        position = new Position(random.nextInt(game.getMapHeight()), random.nextInt(game.getMapWidth()));
        type = NPCType.NPC;
        description = NPCData.DESCRIPTIONS[random.nextInt(NPCData.DESCRIPTIONS.length)];
        dialog = NPCData.DIALOGS[random.nextInt(NPCData.DIALOGS.length)];
        this.game = game;
    }

    public NPC(Game game, Position position) {
        Random random = new Random();

        name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        healthPoints = random.nextInt(10) + 3;
        currentHealthPoints = healthPoints;
        attack = random.nextInt(10) + 5;
        defense = random.nextInt(10) + 3;
        magic = random.nextInt(10) + 2;
        speed = random.nextInt(10) + 1;
        this.position = position;
        type = NPCType.ENEMY;
        description = "THE BIG BOSS!";
        dialog = "KILL ME IF YOU WANT TO ACCESS THE DOOR! HAHAHAHA!";
        this.game = game;
    }

    public NPCType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDialog() {
        return dialog;
    }

    public void setType(NPCType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String draw() {
        if (this.type == NPCType.ENEMY) {
            return "\uD83D\uDC79";
        } else {
            return "\uD83E\uDDD9";
        }
    }

    @Override
    public String showStats() {
        return "Total Health: " +
                getHealthPoints() + " - Current Health: "
                + getCurrentHealthPoints() + " - Attack: "
                + getAttack()
                + " - Defense: " + getDefense() + " - Magic: "
                + getMagic()
                + " - Speed: " + getSpeed() + "\n";
    }

    public void destroy() {
        game.remove(this);
    }
}
