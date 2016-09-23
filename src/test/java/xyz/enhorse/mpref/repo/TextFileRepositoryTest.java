package xyz.enhorse.mpref.repo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         22.12.2015
 */
@SuppressWarnings("unused")
public class TextFileRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextFileRepositoryTest.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testGetAllValid() throws Exception {
        URL filename = TextFileRepositoryTest.class.getResource("/datasource.txt");
        Repository<String> ds = new TextFileRepository(new File(filename.getFile()));
        Set<String> albumList = ds.getAll();
        assertEquals(9, albumList.size());
    }


    @Test
    public void testCreateFileNotExist() throws Exception {
        exception.expect(IllegalArgumentException.class);
        Repository<String> ds = new TextFileRepository(new File("dummy"));
    }


    @Test
    public void testCreateNullFile() throws Exception {
        exception.expect(IllegalArgumentException.class);
        Repository<String> ds = new TextFileRepository(null);
    }


    @Test
    public void testGetAllEmptyFile() throws Exception {
        URL filename = TextFileRepositoryTest.class.getResource("/empty.txt");
        Repository<String> ds = new TextFileRepository(new File(filename.getFile()));
        Set<String> albumList = ds.getAll();
        assertEquals(0, albumList.size());
    }


    @Test
    public void testPut() throws Exception {
        File file = File.createTempFile("test_", ".tmp");
        Repository<String> ds = new TextFileRepository(file);
        String url = "http://www.allmusic.com/album/voyeur-mw0002884041";
        ds.put(url);
        url = "http://www.allmusic.com/album/tell-me-im-pretty-mw0002892411";
        ds.put(url);
        assertEquals(2, ds.getAll().size());

        if (file.exists()) {
            LOGGER.debug("testPut() temp file deleted: \"" + file.delete() + "\"");
        }
    }

    
    @Test
    public void testRemove() throws Exception {
        File file = File.createTempFile("test_", ".tmp");
        Repository<String> ds = new TextFileRepository(file);
        String url = "http://www.allmusic.com/album/voyeur-mw0002884041";
        ds.put(url);
        url = "http://www.allmusic.com/album/tell-me-im-pretty-mw0002892411";
        ds.put(url);
        url = "http://www.allmusic.com/album/tell-me-im-pretty-mw0002892412";
        ds.put(url);

        assertTrue(ds.remove(url));
        assertEquals(2, ds.getAll().size());

        if (file.exists()) {
            LOGGER.debug("testRemove() temp file deleted: \"" + file.delete() + "\"");
        }
    }


    @Test
    public void testRemoveNotExistRecord() throws Exception {
        File file = File.createTempFile("test_", ".tmp");
        Repository<String> ds = new TextFileRepository(file);
        String url = "http://www.allmusic.com/album/voyeur-mw0002884041";
        ds.put(url);

        assertTrue(ds.remove("i am not there"));
        assertEquals(1, ds.getAll().size());

        if (file.exists()) {
            LOGGER.debug("testRemoveNotExistRecord() temp file deleted: \"" + file.delete() + "\"");
        }
    }
}