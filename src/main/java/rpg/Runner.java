package rpg;

import rpg.things.Player;

public class Runner {
    public static Player player = null;
    public static Game game = null;

    public static void start(Game newGame) {
        game = newGame;
    }

    public static void processInput(String key) {
        displayMap();

        System.out.println(
                "\nYou are the '@', Enter a direction (w/a/s/d), 'm' to see you inventory, 'i' to interact, 'b' to atack and 'k' to save:\n");

        System.out.println(player.showStats());

        if (key == "k")
            Sessions.save(game);
        else
            player.processInput(key);
    }

    private static void displayMap() {
        System.out.println("\n--- Game Map ---");

        game.startDrawing();

        while (game.nextRow()) {
            while (game.nextCol()) {
                System.out.print(game.drawCell() + " ");
            }
        }
    }
}