package xyz.enhorse.mpref.repo.mongo;

import xyz.enhorse.mpref.repo.Repository;

import java.util.Set;


/**
 * {@code Repository} implementation as {@code MongoDatabase}
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         22.12.2015
 */
public class MongoDBRepository<T> implements Repository<T> {

    private MongoDBSource dbSource;
    private String name;
    private Class<T> type;

    MongoDBRepository(MongoDBField<T> field) {
        init(field);
    }


    private void init(MongoDBField<T> value) {
        dbSource = value.getDbSource();
        name = value.getName();
        type = value.getType();
    }


    @Override
    public Set<T> getAll() {
        return dbSource.getAll(name, type);
    }


    @Override
    public boolean put(final T value) {
        return dbSource.put(name, value);
    }


    @Override
    public boolean remove(final T value) {
        return dbSource.remove(name, value) > 0;
    }
}
