package rpg.things;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rpg.Game;
import rpg.templateData.NPCData;
import rpg.types.NPCType;

public class NPC extends Character {
    private String type;
    private String description;
    private String dialog;

    public NPC() {
    }

    public NPC(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, Position position, String type, String description, String dialog) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, position);
        this.type = type;
        this.description = description;
        this.dialog = dialog;
    }

    public NPC(Game game) {
        Random random = new Random();

        String name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        String description = NPCData.DESCRIPTIONS[random.nextInt(NPCData.DESCRIPTIONS.length)];
        String dialog = NPCData.DIALOGS[random.nextInt(NPCData.DIALOGS.length)];
        NPCType type = NPCType.values()[random.nextInt(NPCType.values().length)];

        this.name = name;
        this.healthPoints = random.nextInt(10) + 3;
        this.currentHealthPoints = healthPoints;
        this.attack = random.nextInt(10) + 5;
        this.defense = random.nextInt(5) + 3;
        this.magic = random.nextInt(5) + 2;
        this.speed = random.nextInt(5) + 1;
        this.inventory = new ArrayList<>();
        this.position = new Position(random.nextInt(game.getMapHeight()), random.nextInt(game.getMapWidth()));
        this.type = type.name();
        this.description = description;
        this.dialog = dialog;
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDialog() {
        return dialog;
    }

    public void setType(String type) {
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
        if (this.type.equals("Enemy")) {
            return "\uD83D\uDC79";
        } else {
            return "\uD83E\uDDD9";
        }
    }
}
