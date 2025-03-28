package rpg;

import java.util.List;
import java.util.Scanner;

import rpg.player.PlayerCreator;
import rpg.things.Player;

public class RPGGame {

    public static final Scanner scan = new Scanner(System.in);

    private static final List<String> sessions = Sessions.listSessionsNames();

    public static void main(String[] args) {

        System.out.println("Emoji: \uD83D\uDE80"); // 🚀

        System.out.println("\n" +
                "***************************************\n" +
                "   WELCOME TO THE WONDERS AND DRAGONS  \n" +
                "        RPG ADVENTURE AWAITS!         \n" +
                "***************************************\n");

        Game game = gamePicker();
        Runner.start(game);
        ReadInput();
    }

    private static void ReadInput() {
        while (true) {
            String key = RPGGame.scan.next().trim().toLowerCase();
            Runner.processInput(key);
        }
    }

    private static Game gamePicker() {
        Player player = null;
        Game game = null;

        while (player == null) {
            System.out.println("\nWould you like to create a new character? (y/n)\n");
            String choice = scan.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                player = PlayerCreator.createPlayer();
                game = new Game(player);
            }

            else if (choice.equals("n")) {
                if (sessions.isEmpty())
                    System.out.println("\nNo characters available. You must create one first.\n");
                else {
                    game = selectSavedGame();
                    player = (Player) game.getCharacters().get(0);
                }
            }

            else {
                System.out.println("\nInvalid input. Please enter 'y' or 'n'.\n");
            }
        }
        System.out.println("\n" + player.getName() + " selected!\n");

        return game;
    }

    private static Game selectSavedGame() {
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

        return null;
    }
}