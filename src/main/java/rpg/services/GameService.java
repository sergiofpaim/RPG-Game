package rpg.services;

import rpg.RPGGame;
import rpg.models.Map;

public class GameService {

    public static void loadGame() {
        CharacterService.loadCharacters();
        MapService.loadMap();
    }

    public static void saveGame() {
        CharacterService.saveCharacter(RPGGame.currentCharacter);
        MapService.saveMap(Map.FactorFrom(RPGGame.currentMap));
    }
}