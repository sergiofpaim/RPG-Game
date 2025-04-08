package rpg;

import rpg.interactions.NPCInteraction;
import rpg.interfaces.IInteractable;
import rpg.things.NPC;
import rpg.things.Player;

public class BattleManager extends NPCInteraction implements IInteractable {

    public BattleManager(Player player, NPC npc) {
        super(player, npc);
    }

    public void ControlBattle() {

    }
}
