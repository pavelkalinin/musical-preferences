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
public class AllMusicParserTest {

    @Test
    public void testProcessValid() throws Exception {
        URL url = new URL("http://www.allmusic.com/album/drones-mw0002836779");
        Album album = new Album(
                new Artist("Muse"),
                "Drones",
                new ReleaseDate("08 June 2015"),
                new Cover("http://cps-static.rovicorp.com/3/JPG_400/MI0003/860/MI0003860091.jpg?partner=allrovi.com"));
        Parser parser = new AllMusicParser(url);
        assertEquals(album, parser.process());
    }


    @Test
    public void testGetSource() throws Exception {
        URL url = new URL("http://www.allmusic.com/album/drones-mw0002836779");
        Parser parser = new AllMusicParser(url);
        assertEquals(url, parser.getSource());
    }

    @Rule
    public ExpectedException thrownInapplicable = ExpectedException.none();

    @Test
    public void testNewWithNull() throws Exception {
        URL url = null;
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new AllMusicParser(url);
    }


    @Test
    public void testNewWithAbsolutelyInapplicableURL() throws Exception {
        URL url = new URL("http://google.com");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new AllMusicParser(url);
    }


    @Test
    public void testNewWithURLWithoutMandatoryElement() throws Exception {
        URL url = new URL("http://www.allmusic.com/drones-mw0002836779");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new AllMusicParser(url);

    }


    @Test
    public void testNewWithURLWithoutHost() throws Exception {
        URL url = new URL("http://allmusic.com/album/drones-mw0002836779");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new AllMusicParser(url);
    }
}