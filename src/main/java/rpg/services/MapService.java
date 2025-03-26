package rpg.services;

import java.util.List;
import java.util.Random;

import com.mongodb.client.model.Filters;

import rpg.RPGGame;
import rpg.infrastructure.IGameRepo;
import rpg.infrastructure.MongoDBRepo;
import rpg.models.Map;

public class MapService {
    private static IGameRepo gameRepo = new MongoDBRepo();

    public static Map loadMap() {
        List<Map> maps = gameRepo.get(Map.class, Filters.eq("playerId", RPGGame.currentCharacter.getId()),
                clazz -> "Map", false, null);

        if (maps.isEmpty()) {
            Map map = new Map(new Random().nextInt(50) + 1,
                    new Random().nextInt(50) + 1, RPGGame.currentCharacter.getId());
            return insertMap(map);
        } else {
            return maps.get(0);
        }
    }

    public static Map insertMap(Map map) {
        return gameRepo.create(map);
    }

    public static Map saveMap(Map currentMap) {
        return gameRepo.update(currentMap);
    }
}