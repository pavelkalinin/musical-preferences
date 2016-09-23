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
@SuppressWarnings({"unused", "null"})
public class AlbumTest {

    @Test
    public void testSetValidParameters() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 2015");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album = new Album(artist, name, date, cover);
        assertEquals(artist, album.getArtist());
        assertEquals(name, album.getName());
        assertEquals(date, album.getReleaseDate());
        assertEquals(cover, album.getCover());
    }

    @Rule
    public ExpectedException thrownInvalidParameter = ExpectedException.none();

    @Test
    public void testSetNullName() throws Exception {
        Artist artist = new Artist("Artist");
        String name = null;
        ReleaseDate date = new ReleaseDate("Dec 22, 2015");
        Cover cover = null;
        thrownInvalidParameter.expect(IllegalArgumentException.class);
        Album album = new Album(artist, name, date, cover);
    }

    @Test
    public void testSetEmptyName() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "    ";
        ReleaseDate date = new ReleaseDate("Dec 22, 2015");
        Cover cover = null;
        thrownInvalidParameter.expect(IllegalArgumentException.class);
        Album album = new Album(artist, name, date, cover);
    }

    @Test
    public void testSetNullArtist() throws Exception {
        Artist artist = null;
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 2015");
        Cover cover = null;
        thrownInvalidParameter.expect(IllegalArgumentException.class);
        Album album = new Album(artist, name, date, cover);
    }

    @Test
    public void testSetNullDate() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = null;
        Cover cover = null;
        thrownInvalidParameter.expect(IllegalArgumentException.class);
        Album album = new Album(artist, name, date, cover);
    }

    @Test
    public void testSetNullCover() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 2015");
        Cover cover = null;
        Album album = new Album(artist, name, date, cover);
        assertTrue(album.getCover() == null);
    }

    @Test
    public void testDescription() throws Exception {
        String description = "Artist-Album(1978)";
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1978");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album = new Album(artist, name, date, cover);
        assertEquals(description, album.description());
    }

    @Test
    public void testEqualsSameAlbums() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1978");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist, name, date, cover);
        Album album2 = new Album(artist, name, date, cover);
        assertEquals(album1, album2);
    }

    @Test
    public void testEqualsDifferentByNamesAlbums() throws Exception {
        Artist artist = new Artist("Artist");
        String name1 = "Album1";
        String name2 = "Album2";
        ReleaseDate date = new ReleaseDate("Dec 22, 1978");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist, name1, date, cover);
        Album album2 = new Album(artist, name2, date, cover);
        assertNotEquals(album1, album2);
    }


    @Test
    public void testEqualsDifferentByArtistAlbums() throws Exception {
        Artist artist1 = new Artist("Artist1");
        Artist artist2 = new Artist("Artist2");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1978");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist1, name, date, cover);
        Album album2 = new Album(artist2, name, date, cover);
        assertNotEquals(album1, album2);
    }

    @Test
    public void testEqualsDifferentByDateAlbums() throws Exception {
        Artist artist = new Artist("Artist1");
        String name = "Album";
        ReleaseDate date1 = new ReleaseDate("Dec 22, 1978");
        ReleaseDate date2 = new ReleaseDate("Dec 22, 1980");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist, name, date1, cover);
        Album album2 = new Album(artist, name, date2, cover);
        assertNotEquals(album1, album2);
    }


    @Test
    public void testEqualsDifferentByCoverAlbums() throws Exception {
        Artist artist = new Artist("Artist1");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1978");
        Cover cover1 = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Cover cover2 = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist, name, date, cover1);
        Album album2 = new Album(artist, name, date, cover2);
        assertEquals(album1, album2);
    }


    @Test
    public void testHashcodeSameAlbums() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1978");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist, name, date, cover);
        Album album2 = new Album(artist, name, date, cover);
        assertEquals(album1.hashCode(), album2.hashCode());
    }

    @Test
    public void testHashcodeDifferentAlbums() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date1 = new ReleaseDate("Dec 22, 1978");
        ReleaseDate date2 = new ReleaseDate("Dec 22, 1908");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album1 = new Album(artist, name, date1, cover);
        Album album2 = new Album(artist, name, date2, cover);
        assertNotEquals(album1.hashCode(), album2.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1908");
        Cover cover = new Cover("http://a2.mzstatic.com"
                + "/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg");
        Album album = new Album(artist, name, date, cover);
        assertEquals("Artist \"Album\" (1908-12-22) " +
                "[http://a2.mzstatic.com/us/r30/Music6/v4/8c/91/5d/8c915d9b-d9e4-f735-1b91-81ca1b6e6312/cover170x170.jpeg]",
                album.toString());
    }

    @Test
    public void testAsJsonString() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1908");
        Cover cover = new Cover("http://mzstatic.com//cover.jpg");
        Album album = new Album(artist, name, date, cover);
        Album expected = new Album(artist, name, date, cover);
        JsonString json = expected.toJsonString();
        Album actual = new Album(json);

        assertEquals(expected, actual);
    }
}