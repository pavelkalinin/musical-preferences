package xyz.enhorse.mpref.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import xyz.enhorse.mpref.domain.Album;
import xyz.enhorse.mpref.domain.Artist;
import xyz.enhorse.mpref.domain.Cover;
import xyz.enhorse.mpref.domain.ReleaseDate;

/**
 * Implementation of {@code Parser} for parsing @see <a href="http://music.yandex.ru/">Yandex Music</a>
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */
@Parsers(source = "Yandex Music")
public class YandexMusicParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(YandexMusicParser.class);

    private static final String HOST = "music.yandex.ru";
    private static final String MANDATORY_PATH_ELEMENT = "album";

    private static final String SELECTOR_ALBUM = "div.page-album__head > div.page-album__title > h1";
    private static final String SELECTOR_ARTIST = "div.page-album__head > div.album-summary > a";
    private static final String SELECTOR_DATE = "div.page-album__head > div.album-summary > div.album-summary__group.album-summary__item";
    private static final String SELECTOR_COVER = "div.centerblock > div > div > div.page-album__head > img";
    private static final String ATTRIBUTE_ARTIST = "title";
    private static final String ATTRIBUTE_COVER = "srcset";

    private URL source;


    YandexMusicParser(final URL source) throws InapplicableURLException {
        setSource(source);
    }

    @Override
    public Album process() {
        Album result = null;
        try {
            CSSDocument document = new CSSDocument(source);

            String artist = document.getAttributeText(SELECTOR_ARTIST, ATTRIBUTE_ARTIST);
            String album = document.getSelectorText(SELECTOR_ALBUM);
            String date = document.getSelectorText(SELECTOR_DATE);
            String url = document.getAttributeText(SELECTOR_COVER, ATTRIBUTE_COVER);

            result = new Album(new Artist(artist), album,
                    new ReleaseDate(normalizeDate(date)),
                    new Cover(normalizeUrl(url)));

        } catch (Exception e) {
            LOGGER.error("ERROR parsing [" + source.toString() + "] " + e.getMessage());
        }
        return result;
    }


    private String normalizeDate(String date) {
        if (date == null) return null;
        date = getPartBeforeSymbol(date, (char) 160);
        int year;
        try {
            year = Integer.parseInt(date);
        } catch (Exception e) {
            LOGGER.debug("DEBUG normalizing date [" + date + "] " + e.getMessage());
            year = 1900;
        }
        return String.valueOf(year);
    }


    private String normalizeUrl(String url) {
        if (url == null) return null;

        url = url.trim();

        while (url.startsWith("/")) {
            url = url.substring(1);
        }

        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        return getPartBeforeSymbol(url, ' ');
    }


    private String getPartBeforeSymbol(String string, char symbol) {
        int indexOfSymbol = string.indexOf(symbol);

        return (indexOfSymbol > -1)
                ? string.substring(0, indexOfSymbol)
                : string;
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
