package rpg.interfaces;

import rpg.things.Position;

public interface IThing {
    public void init();

    public String draw();

    public String showStats();

    public void destroy();

    public void startInteraction();

    public Position position = new Position();
}
