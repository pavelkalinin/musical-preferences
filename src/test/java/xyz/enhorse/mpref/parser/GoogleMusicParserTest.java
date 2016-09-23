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
public class GoogleMusicParserTest {

    @Test
    public void testProcessValid() throws Exception {
        URL url = new URL("https://play.google.com/store/music/album/Muse_Black_Holes_And_Revelations?id=Bee5egllvct7kjzbrw3goapfx5y&hl=en");
        Album album = new Album(
                new Artist("Muse"),
                "Black Holes And Revelations",
                new ReleaseDate("31 December 2006"),
                new Cover("https://lh4.googleusercontent.com/FJoC8wsmGml7caaAwFk4KGwt91pdgUTuW8QWTLiMIrqqP-RrNPdUgyY2RWXIa2D8HCApL5_S7g=w300-rw"));
        Parser parser = new GoogleMusicParser(url);
        assertEquals(album, parser.process());
    }


    @Test
    public void testGetSource() throws Exception {
        URL url = new URL("https://play.google.com/store/music/album/Muse_Black_Holes_And_Revelations?id=Bee5egllvct7kjzbrw3goapfx5y&hl=en");
        Parser parser = new GoogleMusicParser(url);
        assertEquals(url, parser.getSource());
    }

    @Rule
    public ExpectedException thrownInapplicable = ExpectedException.none();

    @Test
    public void testNewWithNull() throws Exception {
        URL url = null;
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new GoogleMusicParser(url);
    }


    @Test
    public void testNewWithAbsolutelyInapplicableURL() throws Exception {
        URL url = new URL("http://google.com");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new GoogleMusicParser(url);
    }


    @Test
    public void testNewWithURLWithoutMandatoryElement() throws Exception {
        URL url = new URL("https://play.google.com/store/music/Muse_Black_Holes_And_Revelations?id=Bee5egllvct7kjzbrw3goapfx5y&hl=en");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new GoogleMusicParser(url);

    }


    @Test
    public void testNewWithURLWithoutHost() throws Exception {
        URL url = new URL("https://google.com/store/music/album/Muse_Black_Holes_And_Revelations?id=Bee5egllvct7kjzbrw3goapfx5y&hl=en");
        thrownInapplicable.expect(InapplicableURLException.class);
        Parser parser = new GoogleMusicParser(url);
    }
}