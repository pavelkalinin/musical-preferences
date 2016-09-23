package xyz.enhorse.mpref.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.domain.Album;
import xyz.enhorse.mpref.domain.Artist;
import xyz.enhorse.mpref.domain.Cover;
import xyz.enhorse.mpref.domain.ReleaseDate;

import java.io.IOException;
import java.net.URL;

/**
 * Implementation of {@code Parser} for parsing @see <a href="http://www.allmusic.com">AllMusic.com</a>
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         21.12.2015
 */
@Parsers(source = "AllMusic.com")
public class AllMusicParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllMusicParser.class);

    private static final String HOST = "www.allmusic.com";
    private static final String MANDATORY_PATH_ELEMENT = "album";

    private static final String SELECTOR_ALBUM = "#cmn_wrap > div.content-container > div.content > header > hgroup > h1";
    private static final String SELECTOR_ARTIST = "#album-artist-link > span > a";
    private static final String SELECTOR_DATE = "div.release-date > span";
    private static final String SELECTOR_COVER = "div.album-cover > div > img";
    private static final String ATTRIBUTE_COVER = "src";

    private URL source;


    AllMusicParser(final URL source) throws InapplicableURLException {
        setSource(source);
    }


    @Override
    public Album process() {
        Album result = null;
        try {
            CSSDocument document = new CSSDocument(source);

            String artist = document.getSelectorText(SELECTOR_ARTIST);
            String album = document.getSelectorText(SELECTOR_ALBUM);
            String date = document.getSelectorText(SELECTOR_DATE);
            String cover = document.getAttributeText(SELECTOR_COVER, ATTRIBUTE_COVER);

            LOGGER.debug(String.format("%s-%s(%s)[%s]", artist, album, date, cover));

            result = new Album(new Artist(artist), album, new ReleaseDate(date), new Cover(cover));
        } catch (IOException e) {
            LOGGER.error("ERROR parsing [" + source.toString() + "] " + e.getMessage());
        }
        return result;
    }


    @Override
    public URL getSource() {
        return source;
    }


    private void setSource(URL value) throws InapplicableURLException {
        if (!Parser.fitTo(value, HOST, MANDATORY_PATH_ELEMENT)) {
            throw new InapplicableURLException(value);
        }
        source = value;
    }
}
