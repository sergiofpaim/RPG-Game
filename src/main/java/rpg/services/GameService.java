package rpg.services;

import rpg.RPGGame;
import rpg.models.DungeonMap;

public class GameService {

    public static void loadGame() {
        CharacterService.loadCharacters();
        MapService.loadMap();
    }

    public static void saveGame() {
        CharacterService.saveCharacter(RPGGame.currentCharacter);
        MapService.saveMap(DungeonMap.FactorFrom(RPGGame.currentMap));
    }
}