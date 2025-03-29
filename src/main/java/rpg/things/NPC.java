package rpg.things;

import java.util.List;

public class NPC extends Character {
    private String type;
    private String description;
    private String dialog;

    public NPC(String name, int health, int currentHealth, int attack, int defense, int magic, int speed,
            List<Item> inventory, int positionX, int positionY, String type, String description, String dialog) {
        super(name, health, currentHealth, attack, defense, magic, speed, inventory, positionX, positionY);
        this.type = type;
        this.description = description;
        this.dialog = dialog;
    }

    public NPC() {
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDialog() {
        return dialog;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Init'");
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Destroy'");
    }

    @Override
    public void startInteraction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'StartInteraction'");
    }

    @Override
    public String draw() {
        if (this.type.equals("Enemy")) {
            return "\uD83D\uDC79";
        } else {
            return "\uD83E\uDDD9\u200D\u2642";
        }
    }

    @Override
    public String showStats() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayProfile'");
    }
}
