package rtdm.domain;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class Entity {

    @Id
    @ObjectId
    private String key;

    public String getKey() {
        return key;
    }

    public Entity setKey(final String key) {
        this.key = key;
        return this;
    }
}
