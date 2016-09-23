package xyz.enhorse.mpref.repo.mongo;

import xyz.enhorse.mpref.repo.DBRepositoryBuilder;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         25.12.2015
 */
public class MongoDBRepositoryBuilder implements DBRepositoryBuilder {

    private String host;
    private String storage;
    private String source;


    public MongoDBRepositoryBuilder() {
    }


    @Override
    public DBRepositoryBuilder setHost(final String uri) {
        host = uri;
        return this;
    }


    @Override
    public DBRepositoryBuilder setStorage(final String name) {
        storage = name;
        return this;
    }


    @Override
    public DBRepositoryBuilder setSource(final String name) {
        source = name;
        return this;
    }


    @Override
    public <T> MongoDBRepository<T> build(final String name, final Class<T> type)
            throws UnknownError, IllegalArgumentException {
        checkParameter("MongoDB Field name", name);
        if (type == null) throw new IllegalArgumentException("MongoDB Field type cannot be null");

        MongoDBHost client = new MongoDBHost(host);

        checkParameter("MongoDB Database name", storage);
        MongoDBStorage database = new MongoDBStorage(client, storage);

        checkParameter("MongoDB Collection name", source);
        MongoDBSource collection = new MongoDBSource(database, source);

        MongoDBField<T> field = new MongoDBField<>(collection, name, type);
        return new MongoDBRepository<>(field);
    }


    private void checkParameter(String name, String value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException(name + " cannot be null!");
        if (value.trim().isEmpty()) throw new IllegalArgumentException(name + " cannot be empty!");
    }
}