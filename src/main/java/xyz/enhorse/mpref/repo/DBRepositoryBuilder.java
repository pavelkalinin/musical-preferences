package xyz.enhorse.mpref.repo;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         25.12.2015
 */
public interface DBRepositoryBuilder {

    DBRepositoryBuilder setHost(final String uri);

    DBRepositoryBuilder setStorage(final String name);

    DBRepositoryBuilder setSource(final String name);

    <T> Repository<T> build(final String name, final Class<T> type);
}