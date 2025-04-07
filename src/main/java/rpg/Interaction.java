package rpg;

import java.util.Map;
import java.util.Random;
import rpg.interfaces.IThing;
import rpg.things.Item;
import rpg.things.NPC;
import rpg.things.Player;
import java.util.AbstractMap;

public class Interaction {
    private IThing target;
    private String message = "";

    public Interaction(IThing target) {
        this.target = target;
    }

    public Map.Entry<Game, String> manageInteraction(Game game) {
        Player player = (Player) game.listCharacters().get(0);

        if (target != null) {
            if (target instanceof rpg.things.Character) {
                NPC npc = (NPC) target;
                System.out.println("You can:");
                System.out.println("1 - See the individual");
                System.out.println("2 - Talk to the individual");
                System.out.println("3 - Battle the individual");

                if (RPGGame.scan.hasNextLine()) {
                    RPGGame.scan.nextLine();
                }
                int choice = RPGGame.scan.nextInt();

                if (choice == 1)
                    message = "\nYou see: " + npc.getName() + " - " + npc.getDescription();

                else if (choice == 2) {
                    message = "\n" + npc.getName() + ": " + npc.getDialog();
                }

                else if (choice == 3) {
                    Player playerAfterFight = startBattle(player, npc);
                    if (playerAfterFight.getCurrentHealthPoints() > 0) {
                        game.listCharacters().remove(npc);
                        message = "You defeated " + npc.getName() + "!";
                    }

                    else {
                        message = "You died";
                        game.listCharacters().remove(player);
                    }
                }
            }

            else if (target instanceof Item) {
                Item item = (Item) target;
                System.out.println("You can:");
                System.out.println("1 - See the item");
                System.out.println("2 - Pick the item");

                if (RPGGame.scan.hasNextLine()) {
                    RPGGame.scan.nextLine();
                }
                int choice = RPGGame.scan.nextInt();

                if (choice == 1)
                    message = item.getDescription();

                else if (choice == 2) {
                    item.setCarried(true);
                    player.getInventory().add(item);
                    game.listItems().remove(item);

                    message = "You're now carrying the item";
                }
            }
        } else {
            message = "No target found for interaction.";
        }

        return new AbstractMap.SimpleEntry<Game, String>(game, message);
    }

    private Player startBattle(Player player, NPC npc) {
        int order[] = new int[2];
        Random random = new Random();

        int playerHP = player.getCurrentHealthPoints();
        int npcHP = npc.getCurrentHealthPoints();

        for (int i = 0; i < 2; i++)
            order[i] = random.nextInt(5);

        int initiative = (order[0] >= order[1]) ? 0 : 1;
        boolean isPlayerTurn = (initiative == 0);

        System.out.println("Battle starts! " + (isPlayerTurn ? "Player goes first!" : "NPC goes first!"));

        while (player.getCurrentHealthPoints() > 0 && npc.getCurrentHealthPoints() > 0) {
            if (isPlayerTurn) {
                System.out.println("\nYour turn! Choose an action:");
                System.out.println("1. Attack");
                System.out.println("2. Defend");

                int choice = RPGGame.scan.nextInt();

                if (choice == 1) {
                    npcHP -= player.getAttack();
                    npc.setCurrentHealthPoints(npcHP);
                    System.out.println("You attacked the NPC! NPC HP: " + npc.getCurrentHealthPoints());
                } else {
                    System.out.println("You defend but there is no attack!");
                }
            } else {
                System.out.println("\nNPC's turn!");
                int npcChoice = random.nextInt(2);

                if (npcChoice == 0) {
                    playerHP -= npc.getAttack();
                    player.setCurrentHealthPoints(playerHP);
                    System.out.println("NPC attacks you! Your HP: " + player.getCurrentHealthPoints());
                } else {
                    System.out.println("NPC defends.");
                }
            }

            isPlayerTurn = !isPlayerTurn;
        }

        return player;
    }
}