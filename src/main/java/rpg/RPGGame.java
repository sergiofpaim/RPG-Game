package rpg;

import java.util.List;
import java.util.Scanner;

import rpg.game.GameRunner;
import rpg.game.player.CharacterCreator;
import rpg.models.Character;
import rpg.services.CharacterService;

public class RPGGame {

    public static final Scanner scan = new Scanner(System.in);

    public static char[][] currentMap = null;
    public static Character currentCharacter = null;

    private static final List<Character> characters = CharacterService.loadCharacters();

    public static void main(String[] args) {
        System.out.println("Welcome to the Wonders and Dragons RPG!\n");
        characterPicker();
        GameRunner.runGame();
    }

    private static void characterPicker() {
        while (currentCharacter == null) {
            System.out.println("\nWould you like to create a new character? (y/n)\n");
            String choice = scan.nextLine().trim().toLowerCase();

            if (choice.equals("y"))
                currentCharacter = CharacterService.insertCharacter(CharacterCreator.createCharacter());

            else if (choice.equals("n")) {
                if (characters.isEmpty())
                    System.out.println("\nNo characters available. You must create one first.\n");
                else
                    currentCharacter = selectCharacter();
            }

            else {
                System.out.println("\nInvalid input. Please enter 'y' or 'n'.\n");
            }
        }
        System.out.println("\n" + currentCharacter.getName() + " selected!\n");
    }

    private static Character selectCharacter() {
        System.out.println("\nSelect a character to play with:\n");

        for (Character character : characters) {
            System.out.println(character.getName() + "\n");
        }

        String choice = scan.nextLine().trim();

        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(choice)) {
                return character;
            }
        }
        System.out.println("\nCharacter not found.\n");

        return null;
    }
}