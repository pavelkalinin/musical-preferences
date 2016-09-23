package xyz.enhorse.mpref.domain;

import org.junit.Test;
import xyz.enhorse.mpref.json.JsonString;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         29.12.2015
 */
public class ExternalLinkedAlbumTest {

    @Test
    public void testAsJsonString() throws Exception {
        Artist artist = new Artist("Artist");
        String name = "Album";
        ReleaseDate date = new ReleaseDate("Dec 22, 1908");
        Cover cover = new Cover("http://mzstatic.com//cover.jpg");
        Album album = new Album(artist, name, date, cover);
        String link = "https://itunes.apple.com/ru/album/everyday-robots/id796955158";
        ExternalLinkedAlbum expected = new ExternalLinkedAlbum(link, album);
        JsonString json = expected.toJsonString();
        ExternalLinkedAlbum actual = new ExternalLinkedAlbum(json);

        assertEquals(expected, actual);
    }
}