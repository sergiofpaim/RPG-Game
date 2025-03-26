package rpg.infrastructure;

import org.bson.codecs.pojo.annotations.BsonId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GameModel {

    @BsonId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract ValidationResult validate();
}