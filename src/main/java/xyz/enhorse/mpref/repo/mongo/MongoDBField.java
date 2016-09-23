package xyz.enhorse.mpref.repo.mongo;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         25.12.2015
 */
public class MongoDBField<T> {

    private MongoDBSource db;
    private String field;
    private Class<T> type;


    MongoDBField(MongoDBSource source, String name, Class<T> type) {
        init(source, name, type);
    }


    MongoDBSource getDbSource() {
        return db;
    }


    String getName() {
        return field;
    }


    Class<T> getType() {
        return type;
    }


    private void init(MongoDBSource source, String name, Class<T> value) {
        db = source;
        field = name;
        type = value;
    }
}