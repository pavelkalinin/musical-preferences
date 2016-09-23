package xyz.enhorse.mpref.repo.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         24.12.2015
 */
public class MongoDBSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBSource.class);

    private MongoCollection<Document> collection;


    MongoDBSource(MongoDBStorage storage, String name) {
        init(storage, name);
    }


    private void init(MongoDBStorage source, String name) {
        collection = source.getDatabase().getCollection(name);
    }


    <T> Set<T> getAll(String name, Class<T> type) {
        Set<T> result = new HashSet<>();
        for (Document doc : collection.find()) {
            T record = doc.get(name, type);
            result.add(record);
        }
        return result;
    }


    <T> boolean put(String name, final T value) {
        if (isExist(name, value)) return true;

        boolean result = true;
        try {
            Document doc = (new Document()).append(name, value);
            collection.insertOne(doc);
        } catch (MongoException ex) {
            LOGGER.error("Putting \"" + String.valueOf(value) + "\" in : "
                    + String.valueOf(collection) + "; message = " + ex.getMessage());
            result = false;
        }

        return result;
    }


    <T> long remove(String name, final T value) {
        long result = 0;
        try {
            Document doc = (new Document()).append(name, value);
            DeleteResult deleteResult = collection.deleteMany(doc);
            result = deleteResult.getDeletedCount();
        } catch (MongoException ex) {
            LOGGER.error("Removing \"" + String.valueOf(value) + "\" from : "
                    + String.valueOf(collection) + "; message = " + ex.getMessage());
        }

        return result;
    }


    <T> boolean isExist(String name, final T value) {
        Document doc = (new Document()).append(name, value);
        return (collection.find(doc).first() != null);
    }
}
