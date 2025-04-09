package rpg.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import rpg.Game;
import rpg.things.Item;
import rpg.things.NPC;
import rpg.things.Position;
import rpg.things.player.Player;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "thing")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Player.class, name = "player"),
        @JsonSubTypes.Type(value = NPC.class, name = "npc"),
        @JsonSubTypes.Type(value = Item.class, name = "item")
})
public interface IThing {
    public String draw();

    public String showStats();

    public Position getPosition();

    public String getName();

    public String getDescription();

    public void setGame(Game game);
}