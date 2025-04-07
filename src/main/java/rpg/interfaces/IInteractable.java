package rpg.interfaces;

import java.util.List;

public interface IInteractable {
    public List<String> getMenu();

    public List<String> processInput();
}
