package xyz.enhorse.mpref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.enhorse.mpref.domain.Album;
import xyz.enhorse.mpref.parser.Parser;
import xyz.enhorse.mpref.parser.ParserFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         24.12.2015
 */
public class WebAlbum {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAlbum.class);

    private URL url;
    private Album album;


    public WebAlbum(String url) throws IllegalArgumentException, UnknownError {
        setUrl(url);
    }


    private void setUrl(String value) throws IllegalArgumentException, UnknownError {
        try {
            url = new URL(value);
            setAlbum();
        } catch (MalformedURLException ex) {
            String message = "Malformed URL \"" + value + "\" : " + ex.getMessage();
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
    }


    private void setAlbum() throws UnknownError {
        try {
            Parser parser = ParserFactory.getParser(url);
            album = parser.process();
        } catch (Exception ex) {
            String message = "Error setting album with URL \"" + String.valueOf(url) + "\" : " + ex.getMessage();
            LOGGER.error(message);
            throw new UnknownError(message);
        }
    }


    public String getSource() {
        return url.toString();
    }


    public String getName() {
        return album.getName();
    }


    public String getArtist() {
        return album.getArtist().getName();
    }


    public int getDate() {
        return album.getReleaseDate().year();
    }


    public String getCover() {
        return album.getCover().getURL().toString();
    }


    public String getDescription() {
        return album.description();
    }
}