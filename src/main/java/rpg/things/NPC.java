package rpg.things;

import java.util.Random;

import rpg.Game;
import rpg.templateData.NPCData;
import rpg.things.player.Load;
import rpg.things.player.Player;
import rpg.types.ItemType;
import rpg.types.NPCType;

public class NPC extends Character {
    private NPCType type;
    private String description;
    private String dialog;

    public NPC() {
    }

    public NPC(Game game, Player player) {
        this(game, player, null);
    }

    public NPC(Game game, Player player, Position position) {
        Random random = new Random();

        name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        healthPoints = (random.nextInt(player.getLevel()) + 2) * 3;
        currentHealthPoints = healthPoints;
        attack = random.nextInt(player.getAttack()) + 1;
        defense = (random.nextInt(player.getLevel()) / 2) + 8;
        magic = random.nextInt(player.getLevel()) + 1;
        speed = random.nextInt(player.getSpeed()) + 2;

        if (position == null) {
            this.position = new Position(
                    random.nextInt(game.getMapHeight()),
                    random.nextInt(game.getMapWidth()));
            type = NPCType.NPC;
            description = NPCData.DESCRIPTIONS[random.nextInt(NPCData.DESCRIPTIONS.length)];
            dialog = NPCData.DIALOGS[random.nextInt(NPCData.DIALOGS.length)];
        } else {
            this.position = position;
            type = NPCType.ENEMY;
            description = "THE BIG BOSS!";
            dialog = "KILL ME IF YOU WANT TO ACCESS THE DOOR! HAHAHAHA!";
        }

        inventory.addItem(new Item("Bag", "a simple leather bag", 0, 0, 0, ItemType.BAG, false));
        inventory.addItem(new Item(game));
        setGame(game);
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
        inventory.setGame(game);
    }

    @Override
    public String draw() {
        if (this.type == NPCType.ENEMY) {
            return "\uD83D\uDC79";
        } else {
            return "\uD83E\uDDD9";
        }
    }

    public void destroy() {
        for (Load load : getInventory().getLoads()) {
            // TODO: Generalizar inventory para Character
            // load.getItem().setPosition(position);
            load.getItem().drop();
        }
        game.remove(this);
    }

    public int decideAction() {
        Random random = new Random();
        int decision = random.nextInt(3) + 1;

        return decision;
    }

    public String takeDamage(int attack) {
        currentHealthPoints -= attack;
        return "\nYou attack the enemy, causing: " + attack + " damage";
    }
}
