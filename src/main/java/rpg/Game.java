package rpg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import rpg.interfaces.IThing;
import rpg.things.*;
import rpg.things.Character;

public class Game extends Thing {
    private int mapWidth;
    private int mapHeight;
    private String playerId;
    private boolean changed = true;
    private List<IThing> things = new ArrayList<IThing>();

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

    public List<IThing> getThings() {
        return things;
    }

    public List<Character> listCharacters() {
        return things.stream()
                .filter(t -> t instanceof Character)
                .map(t -> (Character) t)
                .collect(Collectors.toList());
    }

    public List<Item> listItems() {
        return things.stream()
                .filter(t -> t instanceof Item)
                .map(t -> (Item) t)
                .collect(Collectors.toList());
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

    public void setThings(List<IThing> things) {
        this.things = things;
    }

    // #endregion

    public Game(Player player) {
        setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.mapWidth = new Random().nextInt(10) + 10;
        this.mapHeight = new Random().nextInt(10) + 10;
        this.playerId = player.getId();
        this.things.addAll(generateNPCs());
        this.things.add(0, player);
        this.things.addAll(generateItems());

        player.setGame(this);
    }

    private List<Item> generateItems() {
        List<Item> items = new ArrayList<Item>();

        for (int j = 0; j < new Random().nextInt(2) + 1; j++) {
            Item item = new Item(this);
            items.add(item);
        }

        return items;
    }

    private List<Character> generateNPCs() {
        List<Character> npcs = new ArrayList<Character>();

        for (int j = 0; j < new Random().nextInt(4) + 1; j++) {
            NPC npc = new NPC(this);
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
        for (IThing thing : things) {
            if (thing.getPosition().getY() == currentRow && thing.getPosition().getX() == currentCol) {
                return thing.draw();
            }
        }
        return ".";
    }

    public Map.Entry<Boolean, List<IThing>> checkMovement(int newX, int newY) {
        changed = true;
        List<IThing> interactiveThings = retrieveNearThings(newX, newY);

        if (newX < 0 || newX >= mapWidth || newY < 0 || newY >= mapHeight) {
            return new AbstractMap.SimpleEntry<>(false, interactiveThings);
        }
        for (IThing thing : things) {
            if (thing.getPosition().getX() == newX && thing.getPosition().getY() == newY) {
                return new AbstractMap.SimpleEntry<>(false, interactiveThings);
            }
        }

        return new AbstractMap.SimpleEntry<>(true, interactiveThings);
    }

    private List<IThing> retrieveNearThings(int newX, int newY) {
        List<IThing> interactiveThings = null;

        int[][] offsets = {
                { -1, -1 },
                { -1, 0 },
                { -1, 1 },
                { 0, -1 },
                { 0, 1 },
                { 1, -1 },
                { 1, 0 },
                { 1, 1 }
        };

        for (IThing thing : this.getThings()) {
            for (int[] offset : offsets) {
                if (thing.getPosition().getY() == newY + offset[0] && thing.getPosition().getX() == newX + offset[1]) {
                    interactiveThings = new ArrayList<>();
                    interactiveThings.add(thing);
                }
            }
        }
        return interactiveThings;
    }

    public boolean hasChanged() {
        if (changed) {
            changed = false;
            return true;
        }
        return false;
    }

    public void remove(IThing thing) {
        this.things.remove(thing);
        changed = true;
    }

    public void add(IThing thing) {
        this.things.add(thing);
        changed = true;
    }
}