package xyz.enhorse.mpref.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.json.JsonCompatible;
import xyz.enhorse.mpref.json.JsonString;

import java.net.URL;


/**
 * Entity class represents Cover
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */
public class Cover implements JsonCompatible {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cover.class);

    private URL url;


    public Cover(String url) {
        setUrl(url);
    }


    public Cover(JsonString json) throws IllegalArgumentException {
        setFromJsonString(json);
    }


    private void setFromJsonString(JsonString json) throws IllegalArgumentException {
        if (json == null) throw new IllegalArgumentException("JsonString of " + getClass().getSimpleName()
                + " cannot be null!");

        Cover value = json.convertTo(getClass());
        if (value == null) throw new IllegalArgumentException("JsonString \"" + json.value()
                + "\" cannot be applicable to " + getClass().getSimpleName());

        url = value.getURL();
    }


    private void setUrl(String value) {
        try {
            url = new URL(value);
        } catch (Exception e) {
            LOGGER.debug("Error setting URL from the string: \"" + url + "\"; message = " + e.getMessage());
            url = null;
        }
    }


    public URL getURL() {
        return url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        String thatCover = o.toString();
        String thisCover = toString();

        return thatCover.equalsIgnoreCase(thisCover);
    }


    @Override
    public int hashCode() {
        return (getURL() != null)
                ? getURL().toString().toLowerCase().hashCode()
                : 0;
    }


    @Override
    public String toString() {
        return (url != null)
                ? url.toString()
                : "";
    }


    @Override
    public JsonString toJsonString() {
        return JsonString.generateFor(this);
    }


    private Cover() {} //for Jackson JSON engine
}