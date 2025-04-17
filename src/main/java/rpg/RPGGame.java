package rpg;

import java.util.List;
import java.util.Scanner;

import rpg.things.player.Player;
import rpg.things.player.PlayerFactory;

public class RPGGame {

    public static final Scanner scan = new Scanner(System.in);

    private static final List<String> sessions = Sessions.listSessionsNames();

    public static void main(String[] args) {
        System.out.println("\n" +
                "***************************************\n" +
                "   WELCOME TO THE WONDERS AND DRAGONS  \n" +
                "        RPG ADVENTURE AWAITS!         \n" +
                "***************************************\n");

        Game game = gamePicker();
        new Runner().run(game);
    }

    private static Game gamePicker() {
        Player player = null;
        Game game = null;

        while (player == null) {
            System.out.print("Would you like to create a new character? (y/n) ");
            String choice = scan.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                player = PlayerFactory.create();
                game = new Game(player);
            }

            else if (choice.equals("n")) {
                if (sessions.isEmpty())
                    System.out.println("\nNo characters available. You must create one first.");
                else {
                    game = selectSavedGame();
                    return game;
                }
            }

            else {
                System.out.println("\nInvalid input. Please enter 'y' or 'n'.");
            }
        }
        System.out.println("\n" + player.getName() + " selected!");

        return game;
    }

    private static Game selectSavedGame() {
        while (true) {
            System.out.println("\nSelect a character to play with:\n");

            for (String character : sessions) {
                System.out.println(character + "\n");
            }

            String choice = scan.nextLine().trim();

            for (String session : sessions) {
                if (session.equalsIgnoreCase(choice)) {
                    return Sessions.load(choice);
                }
            }
            System.out.println("\nCharacter not found.\n");
        }
    }
}