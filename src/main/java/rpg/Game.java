package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rpg.templateData.*;
import rpg.things.*;
import rpg.things.Character;
import rpg.types.NPCType;

public class Game extends Thing {
    private int mapWidth;
    private int mapHeight;
    private String playerId;
    private List<Character> characters;
    private List<Item> items;

    private int currentRow = -1;
    private int currentCol = -1;

    // #region Serialization
    public Game() {

    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public String getPlayerId() {
        return playerId;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setMapWidth(int width) {
        this.mapWidth = width;
    }

    public void setMapHeight(int height) {
        this.mapHeight = height;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // #endregion

    public Game(Player player) {
        setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.mapWidth = new Random().nextInt(25) + 1;
        this.mapHeight = new Random().nextInt(25) + 1;
        this.playerId = player.getId();
        this.characters = generateNPCs();
        this.characters.add(0, player);
        this.items = generateItems();
    }

    private List<Item> generateItems() {
        Random random = new Random();
        List<Item> items = new ArrayList<Item>();

        for (int j = 0; j < new Random().nextInt(2) + 1; j++) {
            Item item = ItemData.defaultItems[random.nextInt(ItemData.defaultItems.length)];
            items.add(item);
        }

        return items;
    }

    private List<Character> generateNPCs() {
        List<Character> npcs = new ArrayList<Character>();

        Random random = new Random();

        String name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        String description = NPCData.DESCRIPTIONS[random.nextInt(NPCData.DESCRIPTIONS.length)];
        NPCType type = NPCType.values()[random.nextInt(NPCType.values().length)];

        for (int j = 0; j < new Random().nextInt(4) + 1; j++) {
            NPC npc = new NPC(
                    name,
                    100,
                    100,
                    random.nextInt(10) + 5,
                    random.nextInt(5) + 3,
                    random.nextInt(5) + 2,
                    random.nextInt(5) + 1,
                    new ArrayList<>(),
                    random.nextInt(this.mapHeight),
                    random.nextInt(this.mapWidth),
                    type.name(),
                    description);
            npcs.add(npc);
        }
        return npcs;
    }

    public void startDrawing() {
        currentRow = -1;
        currentCol = -1;
    }

    public boolean nextRow() {
        if (currentRow < mapHeight - 1) {
            currentRow++;
            return true;
        }
        return false;
    }

    public boolean nextCol() {
        if (currentCol < mapWidth - 1) {
            currentCol++;
            return true;
        }

        else {
            currentCol = -1;
            return false;
        }
    }

    public String drawCell() {
        for (Character character : characters) {
            if (character.getPositionX() == currentRow && character.getPositionY() == currentCol) {
                return character.draw();
            }
        }
        for (Item item : items) {
            if (!item.isCarried() && item.getPositionX() == currentRow && item.getPositionY() == currentCol) {
                return item.draw();
            }
        }
        return ".";
    }

    public boolean positionAllowed(int newX, int newY) {
        if (newX < 0 || newX >= mapHeight || newY < 0 || newY >= mapWidth) {
            return false;
        }
        for (Character character : characters) {
            if (character.getPositionX() == newX && character.getPositionY() == newY) {
                return false;
            }
        }
        for (Item item : items) {
            if (!item.isCarried() && item.getPositionX() == newX && item.getPositionY() == newY) {
                return false;
            }
        }
        return true;
    }
}