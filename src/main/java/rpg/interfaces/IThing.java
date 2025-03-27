package rpg.interfaces;

public interface IThing {
    public void init();

    public String draw();

    public String getStats();

    public void destroy();

    public void startInteraction();
}
