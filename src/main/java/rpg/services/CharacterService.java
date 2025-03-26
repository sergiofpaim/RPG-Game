package rpg.services;

import rpg.infrastructure.IGameRepo;
import rpg.infrastructure.MongoDBRepo;
import rpg.models.Character;

import com.mongodb.client.model.Filters;

import java.util.List;

public class CharacterService {
    private static IGameRepo gameRepo = new MongoDBRepo();

    public static List<Character> loadCharacters() {
        return gameRepo.get(Character.class, Filters.empty(), clazz -> "Character", false, null);
    }

    public static Character insertCharacter(Character character) {
        return gameRepo.create(character);
    }

    public static Character saveCharacter(Character currentCharacter) {
        return gameRepo.update(currentCharacter);
    }
}