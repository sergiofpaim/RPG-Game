package rpg.interfaces;

public interface IThing {
    public void init();

    public String draw();

    public String showStats();

    public void destroy();

    public void startInteraction();
}
