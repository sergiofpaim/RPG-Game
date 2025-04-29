package rpg.things;

import java.util.ArrayList;
import java.util.Random;

import rpg.Game;
import rpg.templateData.NPCData;
import rpg.things.player.Load;
import rpg.things.player.Player;
import rpg.types.ItemType;
import rpg.types.NPCType;

public class NPC extends Character {
    private static final int BASE_EXPERIENCE = 10;
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
        Position newPosition;

        name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        healthPoints = (random.nextInt(player.getLevel()) + 2) * 3;
        currentHealthPoints = healthPoints;
        attack = random.nextInt(player.getAttack()) + 1;
        defense = (random.nextInt(player.getLevel()) / 2) + 8;
        magic = random.nextInt(player.getLevel()) + 1;
        speed = random.nextInt(player.getSpeed()) + 2;

        if (position == null) {
            do {
                newPosition = new Position(random.nextInt(game.getMap().getWidth()),
                        random.nextInt(game.getMap().getHeight()));
            } while (!game.checkPositionAvailable(newPosition) && newPosition == new Position(0, 0));

            this.position = newPosition;
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
        this.setExperience((random.nextInt(player.getLevel() + 1)) * BASE_EXPERIENCE);
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
        inventory.setCharacter(this);
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
        dropWholeInventory();
        super.destroy();
    }

    private void dropWholeInventory() {
        Position newPosition = null;

        for (int[] offset : rpg.Map.offsets) {
            newPosition = new Position(
                    getPosition().getCol() + offset[0],
                    getPosition().getRow() + offset[1]);

            if (game.checkPositionAvailable(newPosition)) {
                break;
            }
        }

        if (newPosition != null) {
            for (Load load : new ArrayList<>(getInventory().getLoads())) {
                inventory.dropLoad(load, newPosition);
            }
        }
    }

    public int decideAction() {
        Random random = new Random();
        int decision = random.nextInt(3) + 1;

        return decision;
    }
}
