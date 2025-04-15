package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import rpg.interfaces.IThing;
import rpg.things.Character;
import rpg.things.Item;
import rpg.things.NPC;
import rpg.things.Thing;
import rpg.things.player.Player;
import rpg.types.ItemType;

public class Game extends Thing {
    private boolean changed = true;
    private List<IThing> things = new ArrayList<IThing>();
    private Player player;
    private Map map;

    // #region Serialization
    public Game() {
    }

    public Map getMap() {
        return map;
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

    public void setThings(List<IThing> things) {
        this.things = things;
    }

    // #endregion

    public Game(Player player) {
        setId(String.valueOf(new Random().nextInt(1000) + 1));
        this.map = new Map();
        this.player = player;
        this.things.addAll(generateItems());
        this.things.addAll(generateNPCs());
        this.things.addAll(generateBoss());
        this.things.add(0, player);

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
            NPC npc = new NPC(this, player);
            npcs.add(npc);
        }

        return npcs;
    }

    private List<IThing> generateBoss() {
        List<IThing> things = new ArrayList<IThing>();
        Item door = new Item("Door", "The passage to a new adventure!", ItemType.DOOR, this);
        NPC boss = new NPC(this, player, door.getPosition());

        things.add(boss);
        things.add(door);

        return things;
    }

    public Boolean checkMovement(int newX, int newY) {
        changed = true;

        if (!map.isWithinBounds(newX, newY))
            return false;

        for (IThing thing : things)
            if (thing.getPosition().getX() == newX && thing.getPosition().getY() == newY)
                return false;

        return true;
    }

    public List<IThing> checkSurroundings(int x, int y) {
        List<IThing> interactiveThings = retrieveNearThings(x, y);

        return interactiveThings;
    }

    private List<IThing> retrieveNearThings(int x, int y) {
        List<IThing> interactiveThings = new ArrayList<>();
        List<rpg.things.Position> addedPositions = new ArrayList<rpg.things.Position>();

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

        for (int[] offset : offsets) {
            int targetY = y + offset[0];
            int targetX = x + offset[1];
            rpg.things.Position position = new rpg.things.Position(targetX, targetY);

            for (IThing thing : this.getThings()) {
                if (thing.getPosition().getY() == targetY && thing.getPosition().getX() == targetX)
                    if (!addedPositions.contains(position)
                            && !(thing instanceof Item && ((Item) thing).getType() == ItemType.BAG)) {
                        interactiveThings.add(thing);
                        addedPositions.add(position);
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

    public boolean checkPositionAvailable(rpg.things.Position newPosition) {
        for (IThing thing : this.getThings())
            if ((thing.getPosition().getX() == newPosition.getX() && thing.getPosition().getY() == newPosition.getY())
                    || !map.isWithinBounds(newPosition.getX(), newPosition.getY())
                    || newPosition.getX() == 0 && newPosition.getY() == 0)
                return false;

        return true;
    }

    public void redefineMap(Player player) {
        this.map = new Map();
        this.player = player;
        this.things.clear();
        this.things.addAll(generateItems());
        this.things.addAll(generateNPCs());
        this.things.addAll(generateBoss());
        this.things.add(0, player);

        player.setGame(this);
        changed = true;
    }
}