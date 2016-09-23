package xyz.enhorse.mpref.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.json.JsonCompatible;
import xyz.enhorse.mpref.json.JsonString;

import javax.validation.constraints.NotNull;

/**
 * Entity class represents {@code Album} with external link
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         29.12.2015
 */
public class ExternalLinkedAlbum implements JsonCompatible {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalLinkedAlbum.class);

    @NotNull
    private String link;
    @NotNull
    private Album album;


    public ExternalLinkedAlbum(String link, Album album) throws IllegalArgumentException {
        setLink(link);
        setAlbum(album);
    }


    public ExternalLinkedAlbum(JsonString json) throws IllegalArgumentException {
        setFromJsonString(json);
    }


    private void setFromJsonString(JsonString json) throws IllegalArgumentException {
        if (json == null) throw new IllegalArgumentException("JsonString of " + getClass().getSimpleName()
                + " cannot be null!");

        ExternalLinkedAlbum value = json.convertTo(getClass());
        if (value == null) throw new IllegalArgumentException("JsonString \"" + json.value()
                + "\" cannot be applicable to " + getClass().getSimpleName());

        setLink(value.getLink());
        setAlbum(value.getAlbum());
    }


    public String getLink() {
        return link;
    }


    private void setLink(String value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Link in ExternalLinkedAlbum cannot be null!");
        if (value.trim().isEmpty()) throw new IllegalArgumentException("Link in ExternalLinkedAlbum cannot be empty");
        link = value;
    }


    public Album getAlbum() {
        return album;
    }


    private void setAlbum(Album value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Album in ExternalLinkedAlbum cannot be null");
        album = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalLinkedAlbum that = (ExternalLinkedAlbum) o;

        return getLink().equalsIgnoreCase(that.getLink()) && getAlbum().equals(that.getAlbum());

    }

    @Override
    public int hashCode() {
        int result = getLink().toLowerCase().hashCode();
        result = 31 * result + getAlbum().hashCode();
        return result;
    }

    @Override
    public JsonString toJsonString() {
        return JsonString.generateFor(this);
    }


    private ExternalLinkedAlbum() {} //for Jackson JSON engine
}