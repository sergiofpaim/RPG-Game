package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import rpg.Interface;
import rpg.Runner;
import rpg.things.NPC;
import rpg.things.player.Player;
import rpg.types.Command;

public class BattleInteraction extends Interaction {
    private NPC npc;

    private boolean firstTurn = true;
    private boolean initiative = true;

    public BattleInteraction(Player player, NPC npc) {
        super(player);
        this.npc = npc;
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.ATTACK, "Attack"),
                new AbstractMap.SimpleEntry<>(Command.DEFEND, "Defend"),
                new AbstractMap.SimpleEntry<>(Command.MAGIC, "Magic"),
                new AbstractMap.SimpleEntry<>(Command.INVENTORY, "Open Inventory"),
                new AbstractMap.SimpleEntry<>(Command.RUN, "Run")));
        return menu;
    }

    @Override
    public List<String> processCommand(Command command) {
        List<String> messages = new ArrayList<>();
        Random random = new Random();

        if (command == Command.HELP) {
            return messages;
        }

        else if (command == Command.INVENTORY) {
            Interface.add(player.getInventory());
            messages.addAll(player.getInventory().showInventory());
        }

        else
            controlBattle(command, messages, random);

        return messages;
    }

    private void controlBattle(Command command, List<String> messages, Random random) {
        if (firstTurn) {
            int playerInitiative = random.nextInt(20) + player.getSpeed();
            int npcInitiative = random.nextInt(20) + npc.getSpeed();

            messages.add("\nPlayer initiative: " + playerInitiative);
            messages.add("\nEnemy initiative: " + npcInitiative);

            initiative = playerInitiative >= npcInitiative;
            firstTurn = false;
        }

        if (initiative) {
            messages.addAll(playerTurn(command));
            if (npc.getCurrentHealthPoints() > 0)
                messages.addAll(npcAttack());
        } else {
            messages.addAll(npcAttack());
            if (player.getCurrentHealthPoints() > 0)
                messages.addAll(playerTurn(command));
        }
    }

    private List<String> playerTurn(Command command) {
        Random random = new Random();
        List<String> messages = new ArrayList<>();

        if (command == Command.ATTACK) {
            if (random.nextInt(20) + player.getSpeed() > npc.getDefense()) {
                npc.setCurrentHealthPoints(npc.getCurrentHealthPoints() - player.getAttack());
                messages.add("\nYou attack the enemy, causing: " + player.getAttack() + " damage");
            } else {
                messages.add("\nYou miss the attack, causing: 0 damage");
            }

        } else if (command == Command.DEFEND) {
            player.setDefense(player.getDefense() + 3);
            messages.add("\nYou defend and increase your defense by 3");

        } else if (command == Command.MAGIC) {
            if (random.nextInt(20) + player.getSpeed() > npc.getDefense()) {
                npc.setCurrentHealthPoints(npc.getCurrentHealthPoints() - player.getMagic());
                messages.add("\nYou use magic on the enemy, causing: " + player.getMagic() + " damage");
            } else {
                messages.add("\nYou miss the magic, causing: 0 damage");
            }

        } else if (command == Command.RUN) {
            if (random.nextInt(20) + player.getSpeed() > random.nextInt(20) + npc.getSpeed()) {
                messages.add("\nYou run away from the fight");
                Interface.remove(this);
                return messages;
            } else {
                messages.add("\nYou try to escape the fight, but you can't");
            }
        }

        if (npc.getCurrentHealthPoints() <= 0) {
            messages.add("You defeated: " + npc.getName());
            npc.destroy();
            Interface.remove(this);
        }

        return messages;
    }

    private List<String> npcAttack() {
        Random random = new Random();
        int npcDecision = npc.decideAction();
        List<String> messages = new ArrayList<>();

        if (npcDecision == 1) {
            if (random.nextInt(20) + npc.getSpeed() > player.getDefense()) {
                player.setCurrentHealthPoints(player.getCurrentHealthPoints() - npc.getAttack());
                messages.add("\nThe enemy attacks you, causing: " + npc.getAttack() + " damage");
            } else
                messages.add("\nThe enemy misses the attack, causing: 0 damage");
        } else if (npcDecision == 2) {
            player.setDefense(player.getDefense() + 3);
            messages.add("\nThe enemy defended and increased his defense by 3");
        } else if (npcDecision == 3) {
            if (random.nextInt(20) + npc.getSpeed() > player.getDefense()) {
                player.setCurrentHealthPoints(player.getCurrentHealthPoints() - npc.getMagic());
                messages.add("\nThe enemy used magic on you, causing: " + npc.getMagic() + " damage");
            } else
                messages.add("\nThe enemy misses the magic, causing: 0 damage");
        } else if (npcDecision == 4) {
        }

        if (player.getCurrentHealthPoints() <= 0) {
            messages.add("You were defeated by:" + npc.getName());
            player.destroy();

            Runner.isRunning = false;
        }

        return messages;
    }

    @Override
    public String retrieveLabel() {
        return "Battle";
    }
}