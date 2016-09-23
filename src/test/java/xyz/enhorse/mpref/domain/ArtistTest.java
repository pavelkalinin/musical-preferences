package xyz.enhorse.mpref.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import xyz.enhorse.mpref.json.JsonString;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */
@SuppressWarnings("unused")
public class ArtistTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSetValidName() throws Exception {
        String name = "Artist";
        Artist artist = new Artist(name);
        assertEquals(name, artist.getName());
    }

    @Test
    public void testToString() throws Exception {
        String name = "Artist";
        Artist artist = new Artist(name);
        assertEquals(name, artist.toString());
    }

    @Test
    public void testAsJsonString() throws Exception {
        String name = "Artist";
        Artist expected = new Artist(name);
        JsonString json = expected.toJsonString();
        Artist actual = new Artist(json);
        assertEquals(expected, actual);
    }

    @Test
    public void testHashcodeSameArtists() throws Exception {
        Artist artist1 = new Artist("Artist");
        Artist artist2 = new Artist("Artist");
        assertEquals(artist1.hashCode(), artist2.hashCode());
    }

    @Test
    public void testHashcodeDifferentArtists() throws Exception {
        Artist artist1 = new Artist("Artist1");
        Artist artist2 = new Artist("Artist2");
        assertNotEquals(artist1.hashCode(), artist2.hashCode());
    }


    @Test
    public void testEqualsSameArtists() throws Exception {
        Artist artist1 = new Artist("Artist");
        Artist artist2 = new Artist("Artist");
        assertTrue(artist1.equals(artist2));
    }

    @Test
    public void testEqualsDifferentArtists() throws Exception {
        Artist artist1 = new Artist("Artist1");
        Artist artist2 = new Artist("Artist2");
        assertFalse(artist1.equals(artist2));
    }

    @Test
    public void testCreateFromValidJson() throws Exception {
        Artist expected = new Artist("Artist");
        String json = "{\"name\" : \"Artist\"}";
        Artist actual = new Artist(new JsonString(json));
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateFromNullJson() throws Exception {
        JsonString json = null;
        exception.expect(IllegalArgumentException.class);
        Artist actual = new Artist(json);
    }

    @Test
    public void testCreateFromInapplicableJson() throws Exception {
        String json = "{\"artist\" : \"Artist\"}";
        exception.expect(IllegalArgumentException.class);
        Artist actual = new Artist(new JsonString(json));
    }


    @Test
    public void testSetNullName() throws Exception {
        String name = null;
        exception.expect(IllegalArgumentException.class);
        Artist artist = new Artist(name);
    }

    @Test
    public void testSetEmptyName() throws Exception {
        String name = "          ";
        exception.expect(IllegalArgumentException.class);
        Artist artist = new Artist(name);
    }
}