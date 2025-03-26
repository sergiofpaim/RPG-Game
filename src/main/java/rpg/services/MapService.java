package rpg.services;

import com.mongodb.client.model.Filters;

import rpg.infrastructure.IGameRepo;
import rpg.infrastructure.MongoDBRepo;
import rpg.models.DungeonMap;

public class MapService {
    private static IGameRepo gameRepo = new MongoDBRepo();

    public static DungeonMap loadMap() {
        DungeonMap map = gameRepo.get(DungeonMap.class, Filters.empty(), clazz -> "Map", false, null).get(0);

        if (map == null) {
            map = new DungeonMap(10, 10, 0);
            return map = insertMap(map);
        }

        else
            return map;
    }

    public static DungeonMap insertMap(DungeonMap map) {
        return gameRepo.create(map);
    }

    public static DungeonMap saveMap(DungeonMap currentMap) {
        return gameRepo.update(currentMap);
    }
}