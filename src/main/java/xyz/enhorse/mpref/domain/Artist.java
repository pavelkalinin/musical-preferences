package xyz.enhorse.mpref.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.json.JsonCompatible;
import xyz.enhorse.mpref.json.JsonString;

import javax.validation.constraints.NotNull;


/**
 * Entity class represents Artist
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 * 17.12.2015
 */
public class Artist implements JsonCompatible {
    private static final Logger LOGGER = LoggerFactory.getLogger(Artist.class);

    @NotNull
    private String name;


    public Artist(String name) throws IllegalArgumentException {
        setName(name);
    }


    public Artist(JsonString json) throws IllegalArgumentException {
        setFromJsonString(json);
    }


    private void setFromJsonString(JsonString json) throws IllegalArgumentException {
        if (json == null) throw new IllegalArgumentException("JsonString of " + getClass().getSimpleName()
                + " cannot be null!");

        Artist value = json.convertTo(getClass());
        if (value == null) throw new IllegalArgumentException("JsonString \"" + json.value()
                + "\" cannot be applicable to " + getClass().getSimpleName());

        setName(value.getName());
    }


    public String getName() {
        return name;
    }


    public void setName(String value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Artist name cannot be null!");
        if (value.trim().length() == 0) throw new IllegalArgumentException("Artist name cannot be empty!");

        name = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        return getName().equalsIgnoreCase(artist.getName());
    }


    @Override
    public int hashCode() {
        return getName().toLowerCase().hashCode();
    }


    @Override
    public String toString() {
        return getName();
    }


    @Override
    public JsonString toJsonString() {
        return JsonString.generateFor(this);
    }


    private Artist() {} //for Jackson JSON engine
}
