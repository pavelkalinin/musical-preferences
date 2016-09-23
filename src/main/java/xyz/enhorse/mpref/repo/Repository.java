package xyz.enhorse.mpref.repo;

import java.util.Set;

/**
 * Interface for access to data for reading and writing
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         22.12.2015
 */
public interface Repository<T> {
    Set<T> getAll();

    boolean put(final T value);

    boolean remove(final T value);
}
