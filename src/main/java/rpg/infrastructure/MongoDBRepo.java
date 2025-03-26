package rpg.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import rpg.models.Character;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import java.util.*;
import rpg.models.DungeonMap;
import java.util.function.Function;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MongoDBRepo implements IGameRepo {
    private static final String DATABASE_NAME = "RPG";

    private static final Map<Class<?>, MongoCollection<? extends GameModel>> collections = new HashMap<>();

    private static final CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    private static final MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();

    private static final MongoClient mongoClient = MongoClients.create(settings);

    private static final MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME)
            .withCodecRegistry(pojoCodecRegistry);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.findAndRegisterModules();

        collections.put(Character.class, database.getCollection("Character", Character.class));
        collections.put(DungeonMap.class, database.getCollection("Game", DungeonMap.class));
    }

    private static <T extends GameModel> MongoCollection<T> getCollection(Class<T> clazz) {
        MongoCollection<?> collection = collections.get(clazz);

        if (collection == null) {
            throw new IllegalArgumentException("No collection found for class: " + clazz.getSimpleName());
        }

        if (!clazz.isAssignableFrom(collection.getDocumentClass())) {
            throw new IllegalArgumentException("Collection type mismatch for class: " + clazz.getSimpleName());
        }

        @SuppressWarnings("unchecked")
        MongoCollection<T> safeCollection = (MongoCollection<T>) collection;

        return safeCollection;
    }

    @Override
    public <T extends GameModel> T create(T entity) {
        @SuppressWarnings("unchecked")
        MongoCollection<T> collection = getCollection((Class<T>) entity.getClass());
        collection.insertOne(entity);
        return entity;
    }

    @Override
    public <T extends GameModel> T update(T entity) {
        @SuppressWarnings("unchecked")
        MongoCollection<T> collection = getCollection((Class<T>) entity.getClass());
        Bson filter = Filters.eq("_id", entity.getId());

        collection.replaceOne(filter, entity);
        return entity;
    }

    @Override
    public <T extends GameModel> T getById(String id, Class<T> clazz) {
        MongoCollection<T> collection = getCollection(clazz);

        T result = collection.find(Filters.eq("_id", id))
                .map(document -> clazz.cast(document))
                .first();
        return result;
    }

    @Override
    public <T extends GameModel> List<T> get(Class<T> clazz, Bson filter, Function<Class<T>, String> order,
            boolean descending, Integer take) {

        MongoCollection<T> collection = getCollection(clazz);

        List<T> results = new ArrayList<>();
        FindIterable<T> query = collection.find(filter);

        if (order != null) {
            query = query.sort(descending ? Sorts.descending(order.apply(clazz))
                    : Sorts.ascending(order.apply(clazz)));
        }

        if (take != null) {
            query = query.limit(take);
        }

        for (T doc : query) {
            results.add(clazz.cast(doc));
        }
        return results;
    }
}
