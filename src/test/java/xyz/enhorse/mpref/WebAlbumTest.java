package xyz.enhorse.mpref;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         28.12.2015
 */
public class WebAlbumTest {
    private static final String source = "https://itunes.apple.com/ru/album/the-reason-ep/id864677208?l=en";
    private static final WebAlbum validWebAlbum = new WebAlbum(source);

    @Test
    public void testGetCorrectSource() throws Exception {
        if (validWebAlbum != null)
            assertEquals(source, validWebAlbum.getSource());
    }

    @Test
    public void testGetCorrectName() throws Exception {
        if (validWebAlbum != null)
            assertEquals("The Reason - EP", validWebAlbum.getName());
    }

    @Test
    public void testGetCorrectArtist() throws Exception {
        if (validWebAlbum != null)
            assertEquals("X Ambassadors", validWebAlbum.getArtist());
    }

    @Test
    public void testGetCorrectDate() throws Exception {
        if (validWebAlbum != null)
            assertEquals(2014, validWebAlbum.getDate());
    }

    @Test
    public void testGetCorrectCover() throws Exception {
        if (validWebAlbum != null)
            assertTrue(validWebAlbum.getCover() != null);
    }

    @Test
    public void testGetCorrectDescription() throws Exception {
        if (validWebAlbum != null)
            assertEquals("X Ambassadors-The Reason - EP(2014)", validWebAlbum.getDescription());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetFromNullArgument() throws Exception {
        exception.expect(IllegalArgumentException.class);
        WebAlbum webAlbum = new WebAlbum(null);
    }

    @Test
    public void testGetIllegalArgument() throws Exception {
        exception.expect(UnknownError.class);
        WebAlbum webAlbum = new WebAlbum("http://google.com");
    }

}