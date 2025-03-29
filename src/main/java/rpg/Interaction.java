package rpg;

import java.util.Map;
import rpg.interfaces.IThing;
import rpg.things.Item;
import rpg.things.Player;
import java.util.AbstractMap;

public class Interaction {
    private IThing target;
    private String message = "";

    public Interaction(IThing target) {
        this.target = target;
    }

    public Map.Entry<Game, String> manageInteraction(Game game) {
        Player player = (Player) game.getCharacters().get(0);

        if (target != null) {
            if (target instanceof rpg.things.Character) {
                rpg.things.Character character = (rpg.things.Character) target;
                message = "Interacting with " + character.getName();
            } else if (target instanceof Item) {
                Item item = (Item) target;
                System.out.println("You can:");
                System.out.println("1 - See the item");
                System.out.println("2 - Pick the item");

                if (RPGGame.scan.hasNextLine()) {
                    RPGGame.scan.nextLine();
                }
                int choice = RPGGame.scan.nextInt();

                if (choice == 1)
                    message = item.getDescription();

                if (choice == 2) {
                    item.setCarried(true);
                    player.getInventory().add(item);
                    game.getItems().remove(item);
                    message = "You're now carrying the item";
                }
            }
        } else {
            message = "No target found for interaction.";
        }

        return new AbstractMap.SimpleEntry<Game, String>(game, message);
    }
}
