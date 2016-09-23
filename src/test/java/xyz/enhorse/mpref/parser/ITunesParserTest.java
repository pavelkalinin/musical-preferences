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
 *         17.12.2015
 */
@SuppressWarnings({"unused", "null"})
public class ITunesParserTest {
    @Test
    public void testNewWithValidURL() throws Exception {
        URL url = new URL("http://itunes.apple.com/us/album/a-head-full-of-dreams/id1053933969");
        Parser parser = new ITunesParser(url);
        assertFalse(parser.getSource() == null);
    }


    @Rule
    public ExpectedException thrownInapplicable = ExpectedException.none();

    @Test
    public void testNewWithNull() throws Exception {
        URL url = null;
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new ITunesParser(url);
    }

    @Test
    public void testNewWithAbsolutelyInapplicableURL() throws Exception {
        URL url = new URL("http://google.com");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new ITunesParser(url);
    }

    @Test
    public void testNewWithURLWithoutMandatoryElement() throws Exception {
        URL url = new URL("http://itunes.apple.com/us/a-head-full-of-dreams/id1053933969");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new ITunesParser(url);

    }

    @Test
    public void testNewWithURLWithoutHost() throws Exception {
        URL url = new URL("http://apple.com/us/album/a-head-full-of-dreams/id1053933969");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new ITunesParser(url);
    }

    @Test
    public void testParseValidUrl() throws Exception {
        Album album = new Album(
                new Artist("Coldplay"), "A Head Full of Dreams",
                new ReleaseDate("Dec 04, 2015"),
                new Cover("http://a3.mzstatic.com/us/r30/Music69/v4/a8/62/9e/" +
                        "a8629eac-2c1e-b3fb-f242-a134657f10e3/cover340x340.jpeg"));

        URL url = new URL("http://itunes.apple.com/us/album/a-head-full-of-dreams/id1053933969");
        Parser parser = new ITunesParser(url);
        assertEquals(album, parser.process());
    }

    @Test
    public void testParseInvalidUrl() throws Exception {
        Album album = new Album(new Artist("Coldplay"), "A Head Full of Dreams", new ReleaseDate("Dec 22, 2015"),
                new Cover("http://a3.mzstatic.com/us/r30/Music69/v4/a8/62/9e/" +
                        "a8629eac-2c1e-b3fb-f242-a134657f10e3/cover340x340.jpeg"));

        URL url = new URL("https://itunes.apple.com/ru/album/sketches-in-spain/id890018582?l=en");
        Parser parser = new ITunesParser(url);
        assertNotEquals(album, parser.process());
    }
}