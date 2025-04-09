package rpg.things.player;

import rpg.things.Item;

public class Load {
    Boolean equipped = false;
    Item item;

    public Load() {

    }

    public Load(Item item) {
        this.item = item;
    }

    public Boolean getEquipped() {
        return equipped;
    }

    public void setEquipped(Boolean equipped) {
        this.equipped = equipped;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
