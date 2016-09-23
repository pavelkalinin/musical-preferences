package xyz.enhorse.mpref.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import xyz.enhorse.mpref.domain.Album;
import xyz.enhorse.mpref.domain.Artist;
import xyz.enhorse.mpref.domain.Cover;
import xyz.enhorse.mpref.domain.ReleaseDate;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         21.12.2015
 */
public class YandexMusicParserTest {

    @Test
    public void testProcessValid() throws Exception {
        URL url = new URL("https://music.yandex.ru/album/1188198");
        Album album = new Album(
                new Artist("Cynthia Ukatung"),
                "Vessel",
                new ReleaseDate("2004"),
                new Cover("https://avatars.yandex.net/get-music-content/4a2eeabd.a.1188198-1/200x200"));
        Parser parser = new YandexMusicParser(url);
        assertEquals(album, parser.process());
    }


    @Test
    public void testGetSource() throws Exception {
        URL url = new URL("https://music.yandex.ru/album/1188198");
        Parser parser = new YandexMusicParser(url);
        assertEquals(url, parser.getSource());
    }

    @Rule
    public ExpectedException thrownInapplicable = ExpectedException.none();

    @Test
    public void testNewWithNull() throws Exception {
        URL url = null;
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new YandexMusicParser(url);
    }


    @Test
    public void testNewWithAbsolutelyInapplicableURL() throws Exception {
        URL url = new URL("http://google.com");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new YandexMusicParser(url);
    }


    @Test
    public void testNewWithURLWithoutMandatoryElement() throws Exception {
        URL url = new URL("https://music.yandex.ru/1188198");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new YandexMusicParser(url);

    }


    @Test
    public void testNewWithURLWithoutHost() throws Exception {
        URL url = new URL("https://yandex.ru/album/1188198");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new YandexMusicParser(url);
    }
}