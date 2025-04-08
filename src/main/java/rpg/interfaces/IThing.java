package rpg.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import rpg.Game;
import rpg.things.Item;
import rpg.things.NPC;
import rpg.things.Player;
import rpg.things.Position;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "thing")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Player.class, name = "player"),
        @JsonSubTypes.Type(value = NPC.class, name = "npc"),
        @JsonSubTypes.Type(value = Item.class, name = "item")
})
public interface IThing {
    public String description = null;

    public String name = null;

    public Game game = null;

    public String draw();

    public String showStats();

    public Position getPosition();

    public String getName();

    public String getDescription();

    public void setGame(Game game);
}