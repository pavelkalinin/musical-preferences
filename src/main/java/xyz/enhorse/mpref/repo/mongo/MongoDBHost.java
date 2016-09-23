package xyz.enhorse.mpref.repo.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class represents a connection to MongoDB Client
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         24.12.2015
 */
public class MongoDBHost {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBHost.class);

    private static final String MONGO_DB_DEFAULT_HOST = "mongodb://localhost";


    private MongoClient client;


    MongoDBHost(final String mongoURI) throws IllegalArgumentException {
        setClient(mongoURI);
    }


    MongoDatabase getStorage(final String name) {
        return client.getDatabase(name);
    }


    private void setClient(final String value) throws IllegalArgumentException {

        String uri = ((value == null) || (value.trim().isEmpty()))
                ? MONGO_DB_DEFAULT_HOST
                : value;

        MongoClientURI clientURI;
        try {
            clientURI = new MongoClientURI(uri);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid Mongo DB URI : \"" + uri + "\"; message = " + ex.getMessage());
        }

        try {
            client = new MongoClient(clientURI);
            MongoDatabase database = client.getDatabase("local");
            Document serverStatus = database.runCommand(new Document("serverStatus", 1));
            LOGGER.debug("Mongo DB server \"" + client.getAddress().toString() +  "\" status: " + serverStatus.toString());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not connect to \"" + uri + "\"; message = " + ex.getMessage());
        }
    }
}