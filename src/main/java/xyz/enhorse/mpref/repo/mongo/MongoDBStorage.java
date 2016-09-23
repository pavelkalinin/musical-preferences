package xyz.enhorse.mpref.repo.mongo;

import com.mongodb.client.MongoDatabase;

/**
 * Class represents a connection to MongoDB Database
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         24.12.2015
 */
public class MongoDBStorage {

    private MongoDatabase database;


    MongoDBStorage(MongoDBHost host, String name) {
        init(host, name);
    }


    MongoDatabase getDatabase() {
        return database;
    }


    private void init(MongoDBHost host, String name) {
        database = host.getStorage(name);
    }
}
