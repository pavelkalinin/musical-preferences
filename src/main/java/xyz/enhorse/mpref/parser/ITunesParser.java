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
 * Implementation of {@code Parser} for parsing @see <a href="http://itunes.apple.com">iTunes Store</a>
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */
@Parsers(source = "ITunes Store")
public class ITunesParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ITunesParser.class);

    private static final String HOST = "itunes.apple.com";
    private static final String MANDATORY_PATH_ELEMENT = "album";

    private static final String SELECTOR_ALBUM = "#title > div.left > h1";
    private static final String SELECTOR_ARTIST = "#title > div.left > span > a > h2";
    private static final String SELECTOR_VARIOUS_ARTIST = "#title > div.left > span > h2";
    private static final String SELECTOR_DATE = "#left-stack > div.lockup.product.album.music > ul > li.release-date > span:nth-child(2)";
    private static final String SELECTOR_COVER = "#left-stack > div.lockup.product.album.music > a:nth-child(1) > div > img";
    private static final String ATTRIBUTE_COVER = "src-swap-high-dpi";

    private URL source;


    ITunesParser(final URL source) throws InapplicableURLException {
        setSource(source);
    }


    @Override
    public Album process() {
        Album result = null;
        try {
            CSSDocument document = new CSSDocument(source);

            String artist = parseArtist(document);
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


    private String parseArtist(CSSDocument document) {
        String result = document.getSelectorText(SELECTOR_ARTIST);
        return (!"".equals(result))
                ? result
                : document.getSelectorText(SELECTOR_VARIOUS_ARTIST);
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
