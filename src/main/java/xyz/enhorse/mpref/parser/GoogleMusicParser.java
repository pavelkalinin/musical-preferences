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
 * Implementation of {@code Parser} for parsing @see <a href="http://play.google.com">Google Music</a>
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         21.12.2015
 */
@Parsers(source = "Google Music")
public class GoogleMusicParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMusicParser.class);

    private static final String HOST = "play.google.com";
    private static final String MANDATORY_PATH_ELEMENT = "album";

    private static final String SELECTOR_ALBUM = "div.info-box-top > h1 > span";
    private static final String SELECTOR_ARTIST = "div.info-box-top > div.document-subtitles > div.left-info > div:nth-child(1) > a";
    private static final String SELECTOR_DATE = "div.document-subtitle";
    private static final String SELECTOR_COVER = "div.main-content > div:nth-child(1) > div > div.details-info > div > div.cover-container > img";
    private static final String ATTRIBUTE_COVER = "src";
    private URL source;


    GoogleMusicParser(final URL source) throws InapplicableURLException {
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

            result = new Album(new Artist(artist), album,
                    new ReleaseDate(normalizeDate(date)),
                    new Cover(cover));
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

    private String normalizeDate(String date) {
        if (date == null) return null;
        date = getYearAfterSymbol(date, (char) 32);
        int year;
        try {
            year = Integer.parseInt(date);
        } catch (Exception e) {
            LOGGER.debug("DEBUG normalizing date [" + date + "] " + e.getMessage());
            year = 1900;
        }
        return String.valueOf(year);
    }

    private String getYearAfterSymbol(String string, char symbol) {
        int indexOfSymbol = string.indexOf(symbol);

        if ((indexOfSymbol > -1) && (indexOfSymbol + 5 < string.length())) {
            return string.substring(indexOfSymbol + 1, indexOfSymbol + 5);
        } else {
            return string;
        }
    }
}
