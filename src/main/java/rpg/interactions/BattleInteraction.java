package rpg.interactions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import rpg.CLI;
import rpg.Runner;
import rpg.things.NPC;
import rpg.things.player.Player;
import rpg.types.Command;

public class BattleInteraction extends Interaction {
    private static final int DEFENSE_INCREMENT = 3;

    private NPC npc;

    private boolean firstTurn = true;
    private boolean initiative = true;
    private boolean usedDefensePlayer = false;
    private boolean usedNPCDefense = false;

    public BattleInteraction(Player player, NPC npc) {
        super(player);
        this.npc = npc;
        player.setIsBattling(true);
        npc.setIsBattling(true);
    }

    @Override
    public List<Entry<Command, String>> retrieveMenu() {
        List<Entry<Command, String>> menu = new ArrayList<>(super.retrieveMenu());
        menu.addAll(Arrays.asList(
                new AbstractMap.SimpleEntry<>(Command.SHOW_STATS, "Show fighters status"),
                new AbstractMap.SimpleEntry<>(Command.INVENTORY, "Open Inventory"),
                new AbstractMap.SimpleEntry<>(Command.ATTACK, "Attack"),
                new AbstractMap.SimpleEntry<>(Command.DEFEND, "Defend"),
                new AbstractMap.SimpleEntry<>(Command.MAGIC, "Magic"),
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

        else if (command == Command.SHOW_STATS) {
            messages.add("\n──── Player Stats ────\n" + player.showStats());
            messages.add("\n──── Enemy Stats ────\n" + npc.showStats());
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
        List<String> messages = new ArrayList<>();

        if (usedDefensePlayer) {
            player.setDefense(player.getDefense() - DEFENSE_INCREMENT);
            usedDefensePlayer = false;
        }

        if (command == Command.ATTACK) {
            if (rollD20(player.getSpeed(), npc.getDefense())) {
                npc.receiveDamage(player.getAttack());
                messages.add("\nYou attack the enemy, causing: " + player.getAttack() + " damage");
            } else
                messages.add("\nYou miss the attack, causing: 0 damage");

        }

        else if (command == Command.INVENTORY) {
            player.getInventory().defineAdversary(npc);
            CLI.add(player.getInventory());

            messages.addAll(player.getInventory().showInventory());
        }

        else if (command == Command.DEFEND) {
            player.setDefense(player.getDefense() + DEFENSE_INCREMENT);
            messages.add("\nYou defend and increase your defense by " + DEFENSE_INCREMENT);
            usedDefensePlayer = true;
        }

        else if (command == Command.MAGIC) {
            if (rollD20(player.getSpeed(), npc.getDefense())) {
                npc.receiveDamage(player.getMagic());
                messages.add("\nYou use magic on the enemy, causing: " + player.getMagic() + " damage");
            } else {
                messages.add("\nYou miss the magic, causing: 0 damage");
            }
        }

        else if (command == Command.RUN) {
            if (rollD20(player.getSpeed()) > rollD20(npc.getSpeed())) {
                messages.add("\nYou run away from the fight");
                finish();
                return messages;
            } else {
                messages.add("\nYou try to escape the fight, but you can't");
            }
        }

        if (npc.getCurrentHealthPoints() <= 0) {
            messages.add("You defeated: " + npc.getName());
            messages.addAll(player.rewardExperience(npc.getExperience()));
            npc.destroy();

            finish();
        }

        return messages;
    }

    private void finish() {
        CLI.remove(this);
        player.setIsBattling(false);
        npc.setIsBattling(false);
    }

    private List<String> npcAttack() {
        int npcDecision = npc.decideAction();
        List<String> messages = new ArrayList<>();

        if (usedNPCDefense) {
            npc.setDefense(npc.getDefense() - DEFENSE_INCREMENT);
            usedNPCDefense = false;
        }

        if (npcDecision == 1) {
            if (rollD20(npc.getSpeed(), player.getDefense())) {
                player.receiveDamage(npc.getAttack());
                messages.add("\nThe enemy attacks you, causing: " + npc.getAttack() + " damage");
            } else
                messages.add("\nThe enemy misses the attack, causing: 0 damage");
        }

        else if (npcDecision == 2) {
            npc.setDefense(npc.getDefense() + DEFENSE_INCREMENT);
            messages.add("\nThe enemy defended and increased his defense by " + DEFENSE_INCREMENT);
            usedNPCDefense = true;
        }

        else if (npcDecision == 3) {
            if (rollD20(npc.getSpeed(), player.getDefense())) {
                player.receiveDamage(npc.getMagic());
                messages.add("\nThe enemy used magic on you, causing: " + npc.getMagic() + " damage");
            } else
                messages.add("\nThe enemy misses the magic, causing: 0 damage");
        }

        if (player.getCurrentHealthPoints() <= 0) {
            messages.add("You were defeated by:" + npc.getName());
            player.destroy();

            finish();
            Runner.isRunning = false;
        }

        return messages;
    }

    private static boolean rollD20(int modifier, int saving) {
        return new Random().nextInt(20) + modifier > saving;
    }

    private static int rollD20(int modifier) {
        return new Random().nextInt(20) + modifier;
    }

    @Override
    public String retrieveLabel() {
        return "Battle";
    }
}