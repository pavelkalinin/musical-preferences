package xyz.enhorse.mpref.repo.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.repo.Repository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.After;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         25.12.2015
 */
@SuppressWarnings("unused")
public class MongoDBRepositoryBuilderTest {

    public static final String TEST_HOST = "mongodb://localhost";
    public static final String TEST_STORAGE = "test_db";
    public static final String TEST_SOURCE = "test_collection";
    public static final String TEST_FIELD = "test_field";

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBRepositoryBuilderTest.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @After
    public void clean() {
        try {
            MongoClient client = new MongoClient(TEST_HOST);
            client.getDatabase(TEST_STORAGE).getCollection(TEST_SOURCE).drop();
        } catch (MongoException ex) {
            LOGGER.error("Error dropping collection \""
                    + TEST_SOURCE + "\" in \"" + TEST_STORAGE + "\" on \"" + TEST_HOST + "\"");
        }
    }

    @Test
    public void testBuildValid() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setHost(TEST_HOST);
        builder.setStorage(TEST_STORAGE);
        builder.setSource(TEST_SOURCE);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
        assertTrue(repository != null);
    }

    @Test
    public void testBuildInvalidPreparationOrder() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
        assertTrue(repository != null);
    }

    @Test
    public void testBuildEarlyBuildWithoutAll() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildEarlyBuildWithoutHost() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
        assertTrue(repository != null);
    }

    @Test
    public void testBuildEarlyBuildWithoutStorage() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildEarlyBuildWithoutSource() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildNullFieldType() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, null);
    }

    @Test
    public void testBuildNullFieldName() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(null, String.class);
    }

    @Test
    public void testBuildEmptyFieldName() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build("", null);
    }

    @Test
    public void testBuildFieldNameWithSpacesOnly() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(" ", null);
    }

    @Test
    public void testBuildNullSource() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(null);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildSourceWithSpacesOnly() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource("   ");
        builder.setStorage(TEST_STORAGE);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildNullStorage() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(null);
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildStorageWithSpacesOnly() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage("   ");
        builder.setHost(TEST_HOST);
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildNullHost() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost(null);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
        assertTrue(repository != null);
    }

    @Test
    public void testBuildHostWithSpacesOnly() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost("   ");
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
        assertTrue(repository != null);
    }

    @Test
    public void testBuildUnknownHost() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost("mongodb://192.168.1:5000");
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

    @Test
    public void testBuildMalformedHost() throws Exception {
        MongoDBRepositoryBuilder builder = new MongoDBRepositoryBuilder();
        builder.setSource(TEST_SOURCE);
        builder.setStorage(TEST_STORAGE);
        builder.setHost("foo");
        exception.expect(IllegalArgumentException.class);
        Repository<String> repository = builder.build(TEST_FIELD, String.class);
    }

}