package rpg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import rpg.interfaces.IThing;
import rpg.templateData.*;
import rpg.things.*;
import rpg.things.Character;
import rpg.types.NPCType;

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
        this.mapWidth = new Random().nextInt(25) + 1;
        this.mapHeight = new Random().nextInt(25) + 1;
        this.playerId = player.getId();
        this.things.addAll(generateNPCs());
        this.things.add(0, player);
        this.things.addAll(generateItems());

        player.setGame(this);
    }

    private List<Item> generateItems() {
        Random random = new Random();
        List<Item> items = new ArrayList<Item>();

        for (int j = 0; j < new Random().nextInt(2) + 1; j++) {
            Item item = ItemData.defaultItems[random.nextInt(ItemData.defaultItems.length)];
            item.setPosition(new Position(random.nextInt(this.mapHeight), random.nextInt(this.mapWidth)));
            item.setId(String.valueOf(new Random().nextInt(1000) + 1));
            item.setCarried(false);
            items.add(item);
        }

        return items;
    }

    private List<Character> generateNPCs() {
        List<Character> npcs = new ArrayList<Character>();

        Random random = new Random();

        String name = NPCData.NAMES[random.nextInt(NPCData.NAMES.length)];
        String description = NPCData.DESCRIPTIONS[random.nextInt(NPCData.DESCRIPTIONS.length)];
        String dialog = NPCData.DIALOGS[random.nextInt(NPCData.DIALOGS.length)];
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
                    new Position(random.nextInt(this.mapHeight), random.nextInt(this.mapWidth)),
                    type.name(),
                    description,
                    dialog);
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

        if (newX < 0 || newX >= mapWidth || newY < 0 || newY >= mapHeight) {
            return new AbstractMap.SimpleEntry<>(false, null);
        }
        for (IThing thing : things) {
            if (thing.getPosition().getX() == newX && thing.getPosition().getY() == newY) {
                return new AbstractMap.SimpleEntry<>(false, null);
            }
        }

        return new AbstractMap.SimpleEntry<>(true, null);
    }

    public boolean hasChanged() {
        if (changed) {
            changed = false;
            return true;
        }
        return false;
    }

    // private static boolean checkInteraction() {
    // boolean interaction = false;
    // List<IThing> interactiveThings = new ArrayList<>();

    // int[][] offsets = {
    // { -1, -1 },
    // { -1, 0 },
    // { -1, 1 },
    // { 0, -1 },
    // { 0, 1 },
    // { 1, -1 },
    // { 1, 0 },
    // { 1, 1 }
    // };

    // for (IThing thing : game.getThings()) {
    // for (int[] offset : offsets) {
    // if (thing.getPosition().getY() == player.getPosition().getY() + offset[0]
    // && thing.getPosition().getX() == player.getPosition().getX() + offset[1]) {
    // interactiveThings.add(thing);
    // interaction = true;
    // }
    // }
    // }

    // if (interaction) {
    // System.out.println("Do you want to start an interaction? (y/n)");
    // if (RPGGame.scan.hasNextLine()) {
    // RPGGame.scan.nextLine();
    // }
    // String choice = RPGGame.scan.nextLine().trim().toLowerCase();

    // if (choice.equals("y")) {

    // int counter = 1;

    // System.out.println("\nType the number of the thing you want to interact with:
    // ");

    // for (IThing thing : interactiveThings) {
    // if (thing instanceof NPC) {
    // System.out.println("\n" + counter + "- " + ((NPC) thing).getName() + ": "
    // + ((NPC) thing).getDescription());
    // } else if (thing instanceof Item) {
    // System.out.println("\n" + counter + "- " + ((Item) thing).getName() + ": "
    // + ((Item) thing).getDescription());
    // }
    // }
    // counter++;
    // }

    // Interaction newInteraction = new
    // Interaction(interactiveThings.get(RPGGame.scan.nextInt() - 1));

    // Map.Entry<Game, String> resultInteraction =
    // newInteraction.manageInteraction(game);

    // game = resultInteraction.getKey();
    // System.out.println(resultInteraction.getValue());

    // if (!game.listCharacters().contains(player))
    // isRunning = false;
    // else
    // player = (Player) game.listCharacters().get(0);
    // }

    // return interaction;
    // }
}