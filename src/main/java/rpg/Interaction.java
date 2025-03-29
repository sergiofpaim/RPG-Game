package rpg;

import rpg.interfaces.IThing;
import rpg.things.Item;
import rpg.things.Player;

public class Interaction {
    private IThing target;

    private String message;

    public Interaction(IThing target) {
        this.target = target;
    }

    public String startInteraction(Player player) {
        if (target != null) {
            if (target instanceof rpg.things.Character) {
                rpg.things.Character character = (rpg.things.Character) target;
                message = "Interacting with " + character.getName();
            } else if (target instanceof Item) {
                Item item = (Item) target;
                message = "Interacting with item: " + item.getName();
            }
        } else {
            message = "No target found for interaction.";
        }

        return message;
    }
}
