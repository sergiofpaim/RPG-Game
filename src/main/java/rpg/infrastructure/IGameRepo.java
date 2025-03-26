package rpg.infrastructure;

import java.util.List;
import java.util.function.Function;
import org.bson.conversions.Bson;

public interface IGameRepo {

    <T extends GameModel> T create(T entity);

    <T extends GameModel> T update(T entity);

    <T extends GameModel> T getById(String id, Class<T> clazz);

    public <T extends GameModel> List<T> get(Class<T> clazz, Bson filter, Function<Class<T>, String> order,
            boolean descending, Integer take);
}